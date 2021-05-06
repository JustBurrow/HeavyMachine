package kr.lul.heavymachine.core.prompt;

import java.io.Reader;
import java.io.Writer;
import java.nio.charset.Charset;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import static kr.lul.common.util.Arguments.notEmpty;
import static kr.lul.common.util.Arguments.notNull;

/**
 * 표준출력이 지정한 문자열과 일치하면 대응하는 입력을 실행한다.
 *
 * 한 번 입력하면 표준출력 추적을 초기화한다. 입력값으로 {@code Use default => yes}, {@code Use default password => false}을 설정했을 경우,
 * {@code Use default password}를 무시한다.
 *
 * @author justburrow
 * @since 2021/05/06
 */
public class SimplePromptHandler extends AbstractPromptHandler {
  private int maxPromptCapacity = -1;
  private final Map<String, String> inputs;

  public SimplePromptHandler() {
    this.inputs = new ConcurrentHashMap<>();
  }

  public SimplePromptHandler(Charset charset) {
    super(charset);
    this.inputs = new ConcurrentHashMap<>();
  }

  public SimplePromptHandler(Charset stdInCharset, Charset stdOutCharset, Charset stdErrCharset) {
    super(stdInCharset, stdOutCharset, stdErrCharset);
    this.inputs = new ConcurrentHashMap<>();
  }

  public SimplePromptHandler(Map<String, String> inputs) {
    this.inputs = new ConcurrentHashMap<>(notNull(inputs, "inputs"));
  }

  public SimplePromptHandler(Charset charset, Map<String, String> inputs) {
    super(charset);
    this.inputs = new ConcurrentHashMap<>(notNull(inputs, "inputs"));
  }

  public SimplePromptHandler(Charset stdInCharset, Charset stdOutCharset, Charset stdErrCharset, Map<String, String> inputs) {
    super(stdInCharset, stdOutCharset, stdErrCharset);
    this.inputs = new ConcurrentHashMap<>(notNull(inputs, "inputs"));
  }

  /**
   * @param prompt
   * @param input
   *
   * @return 기존 입력값.
   */
  public String addInput(String prompt, String input) {
    notEmpty(prompt, "prompt");
    notNull(input, "input");

    if (this.maxPromptCapacity < prompt.length())
      this.maxPromptCapacity = prompt.length();

    return this.inputs.put(prompt, input);
  }

  @Override
  protected Runnable stdOutHandler(final Writer stdin, final Reader stdout, final Reader stderr) {
    notNull(stdin, "stdin");
    notNull(stdout, "stdout");
    notNull(stderr, "stderr");

    if (0 >= this.maxPromptCapacity)
      throw new IllegalStateException("maxPromptCapacity is not positive : maxPromptCapacity=" + this.maxPromptCapacity);

    return () -> {
    };
  }
}