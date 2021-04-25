package kr.lul.heavymachine.prototype;

import org.apache.commons.io.IOUtils;

import java.io.IOException;
import java.util.List;

import static java.nio.charset.StandardCharsets.UTF_8;
import static kr.lul.common.util.Arguments.notNull;

/**
 * 쉘 명령어를 실행하는 프로토타입.
 *
 * @author justburrow
 * @since 2021/04/25
 */
public class Machine {
  public Output run(Input input) {
    notNull(input, "input");

    Runtime runtime = Runtime.getRuntime();

    try {
      String command = input.getCommand();
      System.out.println("COMMAND : " + command);

      Process process = runtime.exec(command);
      while (process.isAlive()) {
        Thread.sleep(100L);
      }

      int exitCode = process.exitValue();
      List<String> out = IOUtils.readLines(process.getInputStream(), UTF_8);
      List<String> error = IOUtils.readLines(process.getErrorStream(), UTF_8);

      return new Output(exitCode, out, error);
    } catch (IOException | InterruptedException e) {
      throw new RuntimeException(e);
    }
  }
}