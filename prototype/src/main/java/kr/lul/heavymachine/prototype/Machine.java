package kr.lul.heavymachine.prototype;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.util.Arrays;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

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

  public Output run(Input input) {
    notNull(input, "input");

    ProcessBuilder builder = new ProcessBuilder(input.getCommandArray());
    try {
      Process process = builder.start();
      final StringBuilder outputSb = new StringBuilder();
      EXECUTOR.submit(() -> {
        try (
            Reader reader = new InputStreamReader(process.getInputStream(), UTF_8);
        ) {
          do {
            int cp = reader.read();
            char[] ch = Character.toChars(cp);
            if (0 == Arrays.compare(new char[]{'\n'}, ch) || 0 == Arrays.compare(new char[]{'\n', '\r'}, ch)) {
              outputSb.setLength(0);
            } else {
              outputSb.append((char) cp);
            }
          } while (true);
        } catch (IOException e) {
        }
      });
      EXECUTOR.submit(() -> {
        try (BufferedReader reader = toBufferedReader(newReader(newChannel(process.getErrorStream()), UTF_8))) {
          for (String line = reader.readLine(); null != line; line = reader.readLine()) {
          }
        } catch (IOException e) {
          throw new RuntimeException(e);
        }
      });
      EXECUTOR.submit(() -> {
        while (process.isAlive()) {
          //noinspection CatchMayIgnoreException
          try {
            Thread.sleep(0L, 10);

            if (0 <= outputSb.indexOf("Are you sure you want to continue? [y/N] ")) {
              System.out.println("require input.");
              wait();
            }
          } catch (InterruptedException e) {
          }
        }
      });
      int exitCode = process.waitFor();

      return null;
    } catch (IOException | InterruptedException e) {
      throw new RuntimeException(e);
    }
  }
}