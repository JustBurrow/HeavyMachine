package kr.lul.heavymachine.prototype;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import static java.lang.Thread.sleep;
import static kr.lul.common.util.Arguments.notNull;

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

    Runtime runtime = Runtime.getRuntime();

    try {
      String command = input.getCommand();
      System.out.println("COMMAND : " + command);

      Process process = runtime.exec(command);
      final List<String> out = new ArrayList<>();
      final List<String> error = new ArrayList<>();
      EXECUTOR.submit(() -> {
        final StringBuilder buffer = new StringBuilder();
        try (
            Reader stdout = new InputStreamReader(process.getInputStream());
            Writer prompt = new OutputStreamWriter(process.getOutputStream());
        ) {
          for (int cp = stdout.read(); 0 <= cp; cp = stdout.read()) {
            if ('\n' == cp) {
              String line = buffer.toString();
              out.add(line);
              buffer.setLength(0);
            } else if ('\r' == cp && 0 == buffer.length()) {
              // skip
            } else {
              buffer.appendCodePoint(cp);
            }

            switch (buffer.toString()) {
              case "Are you sure you want to continue? [y/N] ": {
                out.add(buffer.toString() + "y");
                buffer.setLength(0);

                prompt.write("y\n");
                prompt.flush();
                break;
              }
            }
          }
        } catch (IOException ignored) {
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