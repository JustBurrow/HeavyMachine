package kr.lul.heavymachine.core.shell;

import kr.lul.heavymachine.core.machine.Control;
import kr.lul.heavymachine.core.machine.MachineExecutionFailException;

import java.io.IOException;
import java.util.StringJoiner;

import static kr.lul.common.util.Arguments.notNull;

/**
 * @author justburrow
 * @since 2021/05/01
 */
public class SimpleShellMachine implements ShellMachine {
  private final ShellBlueprint blueprint;

  public SimpleShellMachine(ShellBlueprint blueprint) {
    this.blueprint = notNull(blueprint, "blueprint");
  }

  @Override
  public ShellBlueprint getBlueprint() {
    return this.blueprint;
  }

  @Override
  public String[] buildCommand(Control control) {
    notNull(control, "control");

    return this.blueprint.getCommand();
  }

  @Override
  public ShellOutcome execute(Control control) throws MachineExecutionFailException {
    notNull(control, "control");

    String[] command = buildCommand(control);
    ProcessBuilder builder = new ProcessBuilder(command);

    try {
      Process process = builder.start();

      int exitCode = process.waitFor();

      return new SimpleShellOutcome(exitCode);
    } catch (IOException | InterruptedException e) {
      throw new MachineExecutionFailException(e);
    }
  }

  @Override
  public String toString() {
    return new StringJoiner(", ", SimpleShellMachine.class.getSimpleName() + "[", "]")
               .add("blueprint=" + this.blueprint)
               .toString();
  }
}