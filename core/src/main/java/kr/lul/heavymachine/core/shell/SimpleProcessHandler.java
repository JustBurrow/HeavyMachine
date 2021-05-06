package kr.lul.heavymachine.core.shell;

import java.util.concurrent.ExecutorService;

/**
 * @author justburrow
 * @since 2021/05/06
 */
public class SimpleProcessHandler extends AbstractProcessHandler {
  public SimpleProcessHandler(Process process, ExecutorService handlerExecutorService,
      StdinHandler stdInHandler, StdoutHandler stdOutHandler, StderrHandler stdErrHandler) {
    super(process, handlerExecutorService, stdInHandler, stdOutHandler, stdErrHandler);
  }
}