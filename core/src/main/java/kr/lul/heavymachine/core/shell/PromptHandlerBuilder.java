package kr.lul.heavymachine.core.shell;

import java.util.concurrent.ExecutorService;

/**
 * @author justburrow
 * @since 2021/05/06
 */
public interface PromptHandlerBuilder {
  PromptHandlerBuilder process(Process process);

  PromptHandlerBuilder handlerExecutorService(ExecutorService handlerExecutorService);

  PromptHandler build();
}