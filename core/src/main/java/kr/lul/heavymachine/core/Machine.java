package kr.lul.heavymachine.core;

/**
 * 명령 실행 단위.
 *
 * @author justburrow
 * @since 2021/04/25
 */
public interface Machine<I extends Input, O extends Output> {
  O run(I input);
}