package kr.lul.heavymachine.core.shell;

import kr.lul.heavymachine.core.machine.Outcome;

/**
 * @author justburrow
 * @since 2021/05/01
 */
public interface ShellOutcome extends Outcome {
  int getExitCode();

  @Override
  default boolean isSuccess() {
    return 0 == getExitCode();
  }
}