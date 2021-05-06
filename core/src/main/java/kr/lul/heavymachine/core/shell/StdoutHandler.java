package kr.lul.heavymachine.core.shell;

/**
 * @author justburrow
 * @since 2021/05/06
 */
public interface StdoutHandler extends Runnable {
  void handleStdout();

  @Override
  default void run() {
    handleStdout();
  }
}