package kr.lul.heavymachine.core.shell;

/**
 * @author justburrow
 * @since 2021/05/06
 */
public interface StderrHandler extends Runnable {
  void handleStderr();

  @Override
  default void run() {
    handleStderr();
  }
}