package kr.lul.heavymachine.core.machine;

/**
 * {@link Machine} 팩토리.
 *
 * @author justburrow
 * @since 2021/04/30
 */
public interface MachinePrinter<B extends Blueprint, M extends Machine> {
  /**
   * @param blueprint 초기화 정보.
   *
   * @return 초기화된 머신.
   */
  M print(B blueprint);
}