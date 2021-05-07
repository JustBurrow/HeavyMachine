package kr.lul.heavymachine.core.prompt;

import kr.lul.heavymachine.core.machine.Control;

import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.Reader;
import java.io.Writer;
import java.nio.charset.Charset;
import java.util.concurrent.ExecutorService;

import static kr.lul.common.util.Arguments.notNull;

/**
 * @author justburrow
 * @since 2021/05/06
 */
public abstract class AbstractPromptHandler implements PromptHandler {
  private Charset stdInCharset;
  private Charset stdOutCharset;
  private Charset stdErrCharset;

  public AbstractPromptHandler() {
    this(DEFAULT_CHARSET);
  }

  public AbstractPromptHandler(Charset charset) {
    this(charset, charset, charset);
  }

  public AbstractPromptHandler(Charset stdInCharset, Charset stdOutCharset, Charset stdErrCharset) {
    this.stdInCharset = notNull(stdInCharset, "stdInCharset");
    this.stdOutCharset = notNull(stdOutCharset, "stdOutCharset");
    this.stdErrCharset = notNull(stdErrCharset, "stdErrCharset");
  }

  /**
   * 별도의 스레드에서 지속적으로 표준입력을 해야 하는지 여부.
   *
   * @return 입력을 발생시키는 별도의 스레드가 필요하면 {@code true}. 기본값 {@code false}.
   *
   * @see #stdInHandler(Writer, Reader, Reader) {@code true}를 반환하도록 오버라이드는 하는 경우, 함께 오버라이드 해야 한다.
   */
  public boolean hasStdInHandler() {
    return false;
  }

  /**
   * 기본값 : 아무것도 안함.
   *
   * @param stdin  표준입력.
   * @param stdout 표준출력.
   * @param stderr 표준에러.
   *
   * @return 표준입력을 발생시키는 스레드. non-null.
   *
   * @see #hasStdInHandler() {@code true}를 반환하도록 오버라이드는 하는 경우, 함께 오버라이드 해야 한다.
   */
  public Runnable stdInHandler(Writer stdin, Reader stdout, Reader stderr) {
    throw new UnsupportedOperationException("override before use.");
  }

  /**
   * 프로프트 처리 스레드. 반환값의 {@link Runnable#run()}가 실절적인 핸들러 기능을 수행한다.
   *
   * @param stdin  표준입력.
   * @param stdout 표준출력.
   * @param stderr 표준에러.
   *
   * @return 프롬프트 처리 스레드. non-null.
   */
  public abstract Runnable stdOutHandler(Writer stdin, Reader stdout, Reader stderr);

  /**
   * 별도의 스레드에서 지속적으로 표준에러를 처리해야 하는지 여부.
   *
   * @return 표준에러를 처리하는 별도의 스레드가 필요하면 {@code true}. 기본값 {@code false}.
   *
   * @see #stdErrHandler(Writer, Reader, Reader) {@code true}를 반환하도록 오버라이드는 하는 경우, 함께 오버라이드 해야 한다.
   */
  public boolean hasStdErrHandler() {
    return false;
  }

  /**
   * 기본값 : 아무것도 안함.
   *
   * @param stdin  표준입력.
   * @param stdout 표준출력.
   * @param stderr 표준에러.
   *
   * @return 표준에러를 처리하는 스레드. non-null.
   *
   * @see #hasStdErrHandler() {@code true}를 반환하도록 오버라이드는 하는 경우, 함께 오버라이드 해야 한다.
   */
  public Runnable stdErrHandler(Writer stdin, Reader stdout, Reader stderr) {
    throw new UnsupportedOperationException("override before use.");
  }

  @Override
  public void handle(PromptBlueprint blueprint, Control control, Process process) {
    notNull(blueprint, "blueprint");
    notNull(control, "control");
    notNull(process, "process");
    if (!process.isAlive())
      throw new IllegalArgumentException("already terminated process : " + process);

    ExecutorService executorService = control.executorService();
    try (
        Writer stdin = new OutputStreamWriter(process.getOutputStream(), this.stdInCharset);
        Reader stdout = new InputStreamReader(process.getInputStream(), this.stdOutCharset);
        Reader stderr = new InputStreamReader(process.getErrorStream(), this.stdErrCharset)
    ) {
      // 표준출력만 인식해서 처리한다.
      executorService.submit(stdOutHandler(stdin, stdout, stderr));

      if (hasStdInHandler())
        executorService.submit(stdInHandler(stdin, stdout, stderr));
      if (hasStdErrHandler())
        executorService.submit(stdErrHandler(stdin, stdout, stderr));
    } catch (PromptHandleException e) {
      throw e;
    } catch (Exception e) {
      throw new PromptHandleException(e);
    }
  }
}