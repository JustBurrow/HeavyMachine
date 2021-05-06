package kr.lul.heavymachine.core.shell;

import java.util.concurrent.ExecutorService;

import static kr.lul.common.util.Arguments.notNull;

/**
 * @author justburrow
 * @since 2021/05/06
 */
public class SimplePromptHandlerBuilder implements PromptHandlerBuilder {
  private Process process;
  private ExecutorService handlerExecutorService;

  @Override
  public SimplePromptHandlerBuilder process(Process process) {
    if (!notNull(process, "process").isAlive())
      throw new IllegalArgumentException("already terminated process : " + process);

    this.process = process;
    return this;
  }

  @Override
  public SimplePromptHandlerBuilder handlerExecutorService(ExecutorService handlerExecutorService) {
    this.handlerExecutorService = notNull(handlerExecutorService, "handlerExecutorService");
    return this;
  }

  @Override
  public SimplePromptHandler build() {
    if (null == this.process)
      throw new IllegalStateException("process does not set.");
    if (null == this.handlerExecutorService)
      throw new IllegalStateException("handlerExecutorService does not set.");

    return new SimplePromptHandler(this.process, this.handlerExecutorService);
  }
}