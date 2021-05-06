package kr.lul.heavymachine.core.shell;

import java.util.concurrent.ExecutorService;

import static kr.lul.common.util.Arguments.notNull;

/**
 * @author justburrow
 * @since 2021/05/06
 */
public class SimpleProcessHandlerBuilder implements ProcessHandlerBuilder {
  private Process process;
  private ExecutorService handlerExecutorService;
  private StdinHandler stdinHandler;
  private StdoutHandler stdoutHandler;
  private StderrHandler stderrHandler;

  @Override
  public ProcessHandlerBuilder process(Process process) {
    this.process = notNull(process, "process");
    return this;
  }

  @Override
  public ProcessHandlerBuilder executorService(ExecutorService handlerExecutorService) {
    this.handlerExecutorService = notNull(handlerExecutorService, "handlerExecutorService");
    return this;
  }

  @Override
  public ProcessHandlerBuilder stdInHandler(StdinHandler handler) {
    this.stdinHandler = notNull(handler, "handler");
    return this;
  }

  @Override
  public ProcessHandlerBuilder stdOutHandler(StdoutHandler handler) {
    this.stdoutHandler = notNull(handler, "handler");
    return this;
  }

  @Override
  public ProcessHandlerBuilder stdErrHandler(StderrHandler handler) {
    this.stderrHandler = notNull(handler, "handler");
    return this;
  }

  @Override
  public SimpleProcessHandler build() {
    if (null == this.handlerExecutorService)
      throw new IllegalStateException("handlerExecutorService is null.");
    if (null == this.stdinHandler)
      throw new IllegalStateException("stdinHandler is null.");
    if (null == this.stdoutHandler)
      throw new IllegalStateException("stdoutHandler is null.");
    if (null == this.stderrHandler)
      throw new IllegalStateException("stderrHandler is null.");

    SimpleProcessHandler handler = new SimpleProcessHandler(this.process, this.handlerExecutorService,
        this.stdinHandler, this.stdoutHandler, this.stderrHandler);

    return handler;
  }
}