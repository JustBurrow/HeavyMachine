package kr.lul.heavymachine.core.prompt;

import kr.lul.heavymachine.core.machine.Outcome;

/**
 * @author justburrow
 * @since 2021/05/06
 */
public interface PromptOutcome extends Outcome {
  int exitCode();

  @Override
  default boolean isSuccess() {
    return 0 == exitCode();
  }
}