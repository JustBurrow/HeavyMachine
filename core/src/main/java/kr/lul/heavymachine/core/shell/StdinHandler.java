package kr.lul.heavymachine.core.shell;

/**
 * @author justburrow
 * @since 2021/05/06
 */
public interface StdinHandler extends Runnable {
  void handleStdin();

  @Override
  default void run() {
    handleStdin();
  }
}