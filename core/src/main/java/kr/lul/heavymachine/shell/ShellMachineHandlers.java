package kr.lul.heavymachine.shell;

/**
 * 기본적인 IO 핸들러 모음.
 *
 * @author justburrow
 * @since 2021/04/30
 */
public abstract class ShellMachineHandlers {
  /**
   * 하는 일 없는 {@code stdin} 핸들러.
   */
  public static final StdInHandler NULL_STDIN_HANDLER = stream -> {
  };
  /**
   * 하는 일 없는 {@code stdout} 핸들러.
   */
  public static final StdOutHandler NULL_STDOUT_HANDLER = stream -> {
  };
  /**
   * 하는 일 없는 {@code stderr} 핸들러.
   */
  public static final StdErrHandler NULL_STDERR_HANDLER = stream -> {
  };

  public ShellMachineHandlers() {
    throw new UnsupportedOperationException();
  }
}