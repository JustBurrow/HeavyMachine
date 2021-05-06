package kr.lul.heavymachine.core.shell;

import org.slf4j.Logger;

import java.io.*;
import java.util.Map;
import java.util.StringJoiner;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutorService;

import static java.nio.charset.StandardCharsets.UTF_8;
import static kr.lul.common.util.Arguments.notEmpty;
import static kr.lul.common.util.Arguments.notNull;
import static org.slf4j.LoggerFactory.getLogger;

/**
 * 특정 {@code STDOUT} 출력에 정해진 프롬프트 입력을 수행한다.
 *
 * <p>프롬프트 출력과 같은 행에 입력한다고 가정한다.</p>
 *
 * 예)
 * <pre><code>
 * Please input password :
 * </code></pre>
 *
 * @author justburrow
 * @since 2021/05/06
 */
public class SimplePromptHandler implements PromptHandler {
  private static final Logger LOGGER = getLogger(SimplePromptHandler.class);

  private final Process process;
  private final Reader stdout;
  private final Writer stdin;
  private final ExecutorService handlerExecutorService;

  private final StringBuilder stdoutBuffer;
  private final String lineSeparator;

  /**
   * {@code Map<prompt, input>} 목록.
   */
  private final Map<String, String> inputs;

  public SimplePromptHandler(Process process, ExecutorService handlerExecutorService) {
    this(process, handlerExecutorService, System.lineSeparator());
  }

  public SimplePromptHandler(Process process, ExecutorService handlerExecutorService, String lineSeparator) {
    this.process = notNull(process, "process");
    this.stdin = new OutputStreamWriter(process.getOutputStream());
    this.stdout = new InputStreamReader(process.getInputStream(), UTF_8);
    this.handlerExecutorService = notNull(handlerExecutorService, "handlerExecutorService");

    this.stdoutBuffer = new StringBuilder();
    this.lineSeparator = notEmpty(lineSeparator, "lineSeparator");
    this.inputs = new ConcurrentHashMap<>();
  }

  @Override
  public String add(String prompt, String input) {
    return this.inputs.put(notEmpty(prompt, "prompt"), notNull(input, "input"));
  }

  @Override
  public String get(String prompt) {
    return this.inputs.get(prompt);
  }

  public Map<String, String> asMap() {
    return Map.copyOf(this.inputs);
  }

  private void handleStdout() throws IOException {
    for (int cp = this.stdout.read(); 0 <= cp; cp = this.stdout.read()) {
      this.stdoutBuffer.appendCodePoint(cp);

      if (0 <= this.stdoutBuffer.indexOf(this.lineSeparator))
        this.stdoutBuffer.setLength(0);
      else
        handlePrompt();
    }
  }

  private void handlePrompt() throws IOException {
    for (String prompt : this.inputs.keySet()) {
      if (0 > this.stdoutBuffer.indexOf(prompt))
        continue;

      String input = this.inputs.get(prompt);
      this.stdin.write(input);
      this.stdin.write(this.lineSeparator);
      this.stdin.flush();
    }
  }

  @Override
  public void handle() {
    if (this.inputs.isEmpty())
      LOGGER.warn("#handle empty inputs : {}", this);

    if (!this.process.isAlive())
      throw new IllegalStateException("process is not alive : " + this.process);

    this.handlerExecutorService.submit(() -> {
      try (this.stdout; this.stdin) {
        handleStdout();
      } catch (IOException e) {
        throw new RuntimeException(e);
      }
    });
  }

  @Override
  public String toString() {
    return new StringJoiner(", ", SimplePromptHandler.class.getSimpleName() + "[", "]")
               .add("process=" + this.process)
               .add("handlerExecutorService=" + this.handlerExecutorService)
               .add("stdoutBuffer=" + this.stdoutBuffer)
               .add("lineSeparator='" + this.lineSeparator + "'")
               .add("inputs=" + this.inputs)
               .toString();
  }
}