package kr.lul.heavymachine.core;

/**
 * @author justburrow
 * @since 2021/04/25
 */
public class MachineRunException extends Exception {
  public MachineRunException() {
    super();
  }

  public MachineRunException(String message) {
    super(message);
  }

  public MachineRunException(String message, Throwable cause) {
    super(message, cause);
  }

  public MachineRunException(Throwable cause) {
    super(cause);
  }
}