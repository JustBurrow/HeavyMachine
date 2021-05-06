package kr.lul.heavymachine.core.shell;

import kr.lul.heavymachine.core.machine.AbstractMachine;
import kr.lul.heavymachine.core.machine.Control;

import java.io.IOException;
import java.util.StringJoiner;

import static kr.lul.common.util.Arguments.notNull;

/**
 * @author justburrow
 * @since 2021/05/01
 */
public class SimpleShellMachine extends AbstractMachine<ShellBlueprint, ShellOutcome> implements ShellMachine {
  public SimpleShellMachine(ShellBlueprint blueprint) {
    super(blueprint);
  }

  @Override
  protected ShellOutcome doExecute(Control control) throws IOException, InterruptedException {
    notNull(control, "control");

    String[] command = buildCommand(control);
    ProcessBuilder builder = new ProcessBuilder(command);
    Process process = builder.start();
    int exitCode = process.waitFor();

    return new SimpleShellOutcome(exitCode);
  }

  @Override
  public String toString() {
    return new StringJoiner(", ", SimpleShellMachine.class.getSimpleName() + "[", "]")
               .add("blueprint=" + this.blueprint)
               .toString();
  }
}