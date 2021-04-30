package kr.lul.heavymachine.core.machine;

/**
 * 치멍적인 머신 실행 실패.
 *
 * @author justburrow
 * @since 2021/04/30
 */
public class MachineExecutionFailException extends RuntimeException {
  public MachineExecutionFailException() {
    super();
  }

  public MachineExecutionFailException(String message) {
    super(message);
  }

  public MachineExecutionFailException(String message, Throwable cause) {
    super(message, cause);
  }

  public MachineExecutionFailException(Throwable cause) {
    super(cause);
  }
}