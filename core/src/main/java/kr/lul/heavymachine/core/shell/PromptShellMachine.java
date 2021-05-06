package kr.lul.heavymachine.core.shell;

import kr.lul.heavymachine.core.machine.AbstractMachine;
import kr.lul.heavymachine.core.machine.Control;
import kr.lul.heavymachine.core.machine.MachineExecutionFailException;

/**
 * @author justburrow
 * @since 2021/05/02
 */
public class PromptShellMachine extends AbstractMachine<ShellBlueprint, ShellOutcome> implements ShellMachine {
  public PromptShellMachine(ShellBlueprint blueprint) {
    super(blueprint);
  }

  @Override
  protected ShellOutcome doExecute(Control control) throws MachineExecutionFailException {
    return null;
  }
}