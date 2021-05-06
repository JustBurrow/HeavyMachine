package kr.lul.heavymachine.core.shell;

/**
 * {@link ProcessHandler#handle()}에서 프롬프트를 포함, 자식 스레드의 프로세스 IO 처리 중 발생한 예외를 부모 스레드로 전달하기 위한 예외.
 * 원본 예외를 전달해야 하므로 {@link #getCause()}는 항상 {@code null}이 아니어야 한다.
 *
 * @author justburrow
 * @see ProcessHandler#handle() 발생원.
 * @since 2021/05/06
 */
public class PromptHandleException extends Exception {
  /**
   * @param message 에러 메시지.
   * @param cause   non-null.
   */
  public PromptHandleException(String message, Throwable cause) {
    super(message, cause);
  }

  /**
   * @param cause non-null.
   */
  public PromptHandleException(Throwable cause) {
    super(cause);
  }
}