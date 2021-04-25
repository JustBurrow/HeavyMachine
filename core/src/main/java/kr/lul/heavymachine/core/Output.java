package kr.lul.heavymachine.core;

/**
 * 명령 결과값.
 *
 * @author justburrow
 * @see Machine#run(Input)
 * @since 2021/04/25
 */
public interface Output {
  /**
   * 명령을 성공적으로 실행하였는가.
   *
   * @return 성공이면 {@code true}.
   */
  boolean isSuccess();
}