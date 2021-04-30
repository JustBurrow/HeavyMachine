package kr.lul.heavymachine.core.machine;

/**
 * 자동화 최소 단위.
 *
 * @author justburrow
 * @since 2021/04/30
 */
public interface Machine {
  /**
   * @return 머신 초기화 설정.
   */
  Blueprint getBlueprint();

  /**
   * 자동화된 기능을 수행한다.
   *
   * @param control 실행 제어 옵션.
   *
   * @return 실행 결과.
   *
   * @throws MachineExecutionFailException 치명적인 실행 실패.
   */
  Outcome execute(Control control) throws MachineExecutionFailException;
}