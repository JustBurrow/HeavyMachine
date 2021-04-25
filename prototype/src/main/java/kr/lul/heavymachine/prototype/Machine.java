package kr.lul.heavymachine.prototype;

import java.io.*;
import java.nio.channels.Channels;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import static java.lang.Thread.sleep;
import static java.nio.channels.Channels.newChannel;
import static java.nio.channels.Channels.newReader;
import static java.nio.charset.StandardCharsets.UTF_8;
import static kr.lul.common.util.Arguments.notNull;
import static org.apache.commons.io.IOUtils.toBufferedReader;

/**
 * 쉘 명령어를 실행하는 프로토타입.
 *
 * @author justburrow
 * @since 2021/04/25
 */
public class Machine {
  private static final ExecutorService EXECUTOR = Executors.newCachedThreadPool();

  private final Object lock = new Object();

  public Output run(Input input) {
    notNull(input, "input");

    final List<String> out = new ArrayList<>();
    final List<String> error = new ArrayList<>();
    ProcessBuilder builder = new ProcessBuilder(input.getCommandArray());
    final Writer writer;
    try {
      Process process = builder.start();
      final StringBuilder outputSb = new StringBuilder();
      writer = Channels.newWriter(Channels.newChannel(process.getOutputStream()), UTF_8);
      EXECUTOR.submit(new Runnable() {
        private void read(Reader reader) throws Exception {
          int cp = reader.read();
          System.out.printf("OUTPUT : outputSb='%s', cp=%d(%s)\n", outputSb, cp, Character.getName(cp));
          char[] ch = Character.toChars(cp);
          if (0 == Arrays.compare(new char[]{'\n'}, ch) || 0 == Arrays.compare(new char[]{'\n', '\r'}, ch)) {
            String line = outputSb.toString();
            System.out.println("OUTPUT : " + line);
            out.add(line);
            outputSb.setLength(0);
          } else {
            outputSb.append((char) cp);
          }
        }

        @Override
        public void run() {
          synchronized (Machine.this.lock) {
            try (
                Reader reader = new InputStreamReader(process.getInputStream(), UTF_8);
            ) {
              do {
                read(reader);
                Machine.this.lock.notifyAll();
              }
              while (true);
            } catch (Exception ignored) {
            } finally {
              Machine.this.lock.notifyAll();
            }
          }
        }
      });
      EXECUTOR.submit(() -> {
        try (BufferedReader reader = toBufferedReader(newReader(newChannel(process.getErrorStream()), UTF_8))) {
          for (String line = reader.readLine(); null != line; line = reader.readLine()) {
            System.out.println("ERROR : " + line);
            error.add(line);
          }
        } catch (IOException e) {
          throw new RuntimeException(e);
        }
      });
      EXECUTOR.submit(new Runnable() {
        private void check() throws IOException {
          if (0 == outputSb.indexOf("Are you sure you want to continue? [y/N] ")) {
            System.out.println("CHECK : outputSb=" + outputSb.toString());

            writer.write("y\n");
            writer.flush();
            System.out.println("CHECK : input 'y'");

            out.add(outputSb.toString() + "y");
            outputSb.setLength(0);
          }
        }

        @Override
        public void run() {
          while (process.isAlive()) {
            synchronized (Machine.this.lock) {
              try {
                Machine.this.lock.wait();
                check();
              } catch (IOException | InterruptedException ignored) {
              } finally {
                Machine.this.lock.notifyAll();
              }
            }
          }
        }
      });
      while (process.isAlive()) {
        sleep(1L);
      }
      int exitCode = process.exitValue();

      return new Output(exitCode, out, error);
    } catch (IOException | InterruptedException e) {
      throw new RuntimeException(e);
    }
  }
}