package kr.lul.heavymachine.core.shell;

import kr.lul.heavymachine.core.machine.Control;

import java.util.concurrent.ExecutorService;

import static kr.lul.common.util.Arguments.notNull;

/**
 * @author justburrow
 * @since 2021/05/06
 */
public interface ProcessHandlerBuilder {
  /**
   * @param process 대상 프로세스.
   *
   * @return {@code this} 인스턴스.
   */
  ProcessHandlerBuilder process(Process process);

  /**
   * @param executorService 입출력 처리 스레드를 관리할 {@link ExecutorService}.
   *
   * @return {@code this} 인스턴스.
   */
  ProcessHandlerBuilder executorService(ExecutorService executorService);

  /**
   * @param handler {@code stdin} 핸들러.
   *
   * @return {@code this} 인스턴스.
   */
  ProcessHandlerBuilder stdInHandler(StdinHandler handler);

  /**
   * @param handler {@code stdout} 핸들러.
   *
   * @return {@code this} 인스턴스.
   */
  ProcessHandlerBuilder stdOutHandler(StdoutHandler handler);

  /**
   * @param handler {@code stderr} 핸들러.
   *
   * @return {@code this} 인스턴스.
   */
  ProcessHandlerBuilder stdErrHandler(StderrHandler handler);

  /**
   * @param control {@link kr.lul.heavymachine.core.machine.Machine} 실행 제어 옵션.
   *
   * @return {@code this} 인스턴스.
   */
  default ProcessHandlerBuilder executorService(Control control) {
    return executorService(notNull(control, "control").executorService());
  }

  ProcessHandler build();
}