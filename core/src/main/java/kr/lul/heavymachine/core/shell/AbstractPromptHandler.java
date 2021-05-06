package kr.lul.heavymachine.core.shell;

import org.slf4j.Logger;

import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.Reader;
import java.io.Writer;
import java.nio.charset.Charset;
import java.util.StringJoiner;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Future;

import static java.nio.charset.StandardCharsets.UTF_8;
import static kr.lul.common.util.Arguments.notNull;
import static org.slf4j.LoggerFactory.getLogger;

/**
 * @author justburrow
 * @since 2021/05/06
 */
public abstract class AbstractPromptHandler implements PromptHandler {
  private static final Logger LOGGER = getLogger(AbstractPromptHandler.class);

  private final Charset cs;

  private Process process;

  protected Writer stdin;
  protected Reader stdout;
  protected Reader stderr;

  private Future<?> stdinFuture;
  private Future<?> stdoutFuture;
  private Future<?> stderrFuture;

  /**
   * 프로세스 입출력을
   */
  public AbstractPromptHandler() {
    this(UTF_8);
  }

  public AbstractPromptHandler(Charset cs) {
    this.cs = notNull(cs, "cs");
  }

  @Override
  public void handle(Process process, ExecutorService executorService) {
    if (LOGGER.isTraceEnabled())
      LOGGER.trace("#handle args : process={}, executorService={}", process, executorService);
    notNull(executorService, "executorService");

    this.process = notNull(process, "process");

    this.stdin = new OutputStreamWriter(process.getOutputStream(), this.cs);
    this.stdout = new InputStreamReader(process.getInputStream(), this.cs);
    this.stderr = new InputStreamReader(process.getErrorStream(), this.cs);

    Runnable stdinHandler = stdInHandler();
    Runnable stdoutHandler = stdOutHandler();
    Runnable stderrHandler = stdErrHandler();
    if (LOGGER.isDebugEnabled())
      LOGGER.debug("#run : stdinHandler={}, stdoutHandler={}, stderrHandler={}", stdinHandler, stdoutHandler, stderrHandler);

    try {
      this.stdinFuture = executorService.submit(stdinHandler);
      this.stdoutFuture = executorService.submit(stdoutHandler);
      this.stderrFuture = executorService.submit(stderrHandler);
    } catch (RuntimeException e) {
      throw e;
    } catch (Exception e) {
      throw new RuntimeException(e);
    }
  }

  protected abstract Runnable stdInHandler();

  protected abstract Runnable stdOutHandler();

  protected abstract Runnable stdErrHandler();

  @Override
  public String toString() {
    return new StringJoiner(", ", AbstractPromptHandler.class.getSimpleName() + "[", "]")
               .add("process=" + this.process)
               .add("stdin=" + this.stdin)
               .add("stdout=" + this.stdout)
               .add("stderr=" + this.stderr)
               .toString();
  }
}