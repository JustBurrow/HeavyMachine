package kr.lul.heavymachine.core.shell;

import java.util.concurrent.ExecutorService;

/**
 * @author justburrow
 * @since 2021/05/06
 */
public interface PromptHandler {
  /**
   * 프롬프트 처리.
   */
  void handle(Process process, ExecutorService executorService);
}