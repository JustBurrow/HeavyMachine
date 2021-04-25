package kr.lul.heavymachine.prototype;

import java.util.List;

/**
 * @author justburrow
 * @since 2021/04/25
 */
public class Runner {
  public static void main(String[] args) {
    for (String command : List.of(
        "docker -l=debug image prune",
        "docker -l=debug pull mysql",
        "docker -l=debug image ls -a",
        "docker -l=debug inspect mysql",
        "ls -alh"
    )) {
      Input input = new Input(command);
      System.out.println(input);
      Machine machine = new Machine();
      Output output = machine.run(input);
      print(output);
    }
    Machine.EXECUTOR.shutdownNow();
  }

  private static void print(Output output) {
    System.out.println("EXIT CODE : " + output.getExitCode());
    if (output.getOutput().isEmpty()) {
      System.out.println("OUTPUT : N/A");
    } else {
      for (String line : output.getOutput()) {
        System.out.println("OUTPUT : " + line);
      }
    }

    if (output.getError().isEmpty()) {
      System.out.println("\nERROR : N/A");
    } else {
      for (String line : output.getError()) {
        System.out.println("\nERROR : " + line);
      }
    }
  }
}