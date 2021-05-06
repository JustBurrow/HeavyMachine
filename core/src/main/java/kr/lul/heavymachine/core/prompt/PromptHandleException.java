package kr.lul.heavymachine.core.prompt;

/**
 * @author justburrow
 * @since 2021/05/06
 */
public class PromptHandleException extends RuntimeException {
  public PromptHandleException() {
    super();
  }

  public PromptHandleException(String message) {
    super(message);
  }

  public PromptHandleException(String message, Throwable cause) {
    super(message, cause);
  }

  public PromptHandleException(Throwable cause) {
    super(cause);
  }
}