package kr.lul.heavymachine.core.machine;

/**
 * 머신 실행 결과.
 *
 * @author justburrow
 * @since 2021/04/30
 */
public interface Outcome {
  boolean isSuccess();

  default boolean isFail() {
    return !isSuccess();
  }
}