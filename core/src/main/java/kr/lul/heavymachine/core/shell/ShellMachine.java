package kr.lul.heavymachine.core.shell;

import kr.lul.heavymachine.core.machine.Control;
import kr.lul.heavymachine.core.machine.Machine;
import kr.lul.heavymachine.core.machine.MachineExecutionFailException;

/**
 * 리눅스 쉘 자동화 머신.
 *
 * @author justburrow
 * @since 2021/04/30
 */
public interface ShellMachine extends Machine {
  @Override
  ShellBlueprint getBlueprint();

  /**
   * @param control 실행 제어.
   *
   * @return 쉘 커맨드.
   */
  String[] buildCommand(Control control);

  @Override
  ShellOutcome execute(Control control) throws MachineExecutionFailException;
}