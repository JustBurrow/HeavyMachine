package kr.lul.heavymachine.core.shell;

import kr.lul.heavymachine.core.machine.Control;
import kr.lul.heavymachine.core.machine.MachineExecutionFailException;

/**
 * @author justburrow
 * @since 2021/05/02
 */
public class PromptShellMachine implements ShellMachine {
  @Override
  public ShellBlueprint getBlueprint() {
    return null;
  }

  @Override
  public String[] buildCommand(Control control) {
    return new String[0];
  }

  @Override
  public ShellOutcome execute(Control control) throws MachineExecutionFailException {
    return null;
  }
}