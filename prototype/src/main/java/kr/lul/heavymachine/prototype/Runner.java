package kr.lul.heavymachine.prototype;

import java.util.List;

/**
 * @author justburrow
 * @since 2021/04/25
 */
public class Runner {
  public static void main(String[] args) {
    for (String command : List.of(
        "docker -l=debug image prune"
//        "docker -l=debug pull mysql",
//        "docker -l=debug image ls -a",
//        "docker -l=debug inspect mysql",
//        "ls -alh"
    )) {
      Input input = new Input(command);
      System.out.println(input);
      Machine machine = new Machine();
      Output output = machine.run(input);
//      print(output);
    }
  }

  private static void print(Output output) {
    System.out.println("EXIT CODE : " + output.getExitCode());
    System.out.println("OUTPUT :");
    for (String line : output.getOutput()) {
      System.out.println(line);
    }
    System.out.println("\n\nError :");
    for (String line : output.getError()) {
      System.out.println(line);
    }
  }
}