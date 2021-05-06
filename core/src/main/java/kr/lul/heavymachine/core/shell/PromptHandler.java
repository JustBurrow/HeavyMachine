package kr.lul.heavymachine.core.shell;

/**
 * @author justburrow
 * @since 2021/05/06
 */
public interface PromptHandler extends ProcessHandler {
  /**
   * @param prompt 출력 프롬프트.
   * @param input
   *
   * @return
   */
  String add(String prompt, String input);

  String get(String prompt);
}