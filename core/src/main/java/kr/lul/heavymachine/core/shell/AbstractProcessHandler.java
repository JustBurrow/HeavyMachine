package kr.lul.heavymachine.core.shell;

import org.slf4j.Logger;

import java.util.concurrent.ExecutorService;

import static kr.lul.common.util.Arguments.notNull;
import static org.slf4j.LoggerFactory.getLogger;

/**
 * @author justburrow
 * @since 2021/05/06
 */
public abstract class AbstractProcessHandler implements ProcessHandler {
  private static final Logger LOGGER = getLogger(AbstractProcessHandler.class);

  private Process process;
  private ExecutorService handlerExecutorService;
  private StdinHandler stdInHandler;
  private StdoutHandler stdOutHandler;
  private StderrHandler stdErrHandler;

  public AbstractProcessHandler(Process process, ExecutorService handlerExecutorService,
      StdinHandler stdInHandler, StdoutHandler stdOutHandler, StderrHandler stdErrHandler) {
    this.process = notNull(process, "process");
    this.handlerExecutorService = notNull(handlerExecutorService, "handlerExecutorService");
    this.stdInHandler = notNull(stdInHandler, "stdInHandler");
    this.stdOutHandler = notNull(stdOutHandler, "stdOutHandler");
    this.stdErrHandler = notNull(stdErrHandler, "stdErrHandler");
  }

  @Override
  public void handle() {
    try {
      this.handlerExecutorService.submit(this.stdInHandler);
      this.handlerExecutorService.submit(this.stdOutHandler);
      this.handlerExecutorService.submit(this.stdErrHandler);
    } catch (RuntimeException e) {
      throw e;
    } catch (Exception e) {
      throw new RuntimeException(e);
    }
  }
}