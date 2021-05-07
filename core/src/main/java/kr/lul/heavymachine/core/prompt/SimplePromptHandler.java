package kr.lul.heavymachine.core.prompt;

import org.slf4j.Logger;

import java.io.IOException;
import java.io.Reader;
import java.io.Writer;
import java.util.Map;
import java.util.StringJoiner;
import java.util.concurrent.ConcurrentHashMap;

import static java.lang.String.format;
import static kr.lul.common.util.Arguments.notEmpty;
import static kr.lul.common.util.Arguments.notNull;
import static org.slf4j.LoggerFactory.getLogger;

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
  private static final Logger LOGGER = getLogger(SimplePromptHandler.class);

  private int maxPrompt = 0;
  private int promptCapacity;
  private final Map<String, String> inputs;

  public SimplePromptHandler() {
    this.inputs = new ConcurrentHashMap<>();
  }

  public SimplePromptHandler(Map<String, String> inputs) {
    this.inputs = new ConcurrentHashMap<>();

    notNull(inputs, "inputs").forEach(this::addInput);
  }

  /**
   * @param prompt 입력을 해야 하는 표준출력 내용.
   * @param input  입력값.
   *
   * @return 기존 입력값.
   */
  public String addInput(String prompt, String input) {
    return addInput(prompt, input, true);
  }

  /**
   * @param prompt        프롬프트(입력을 해야 하는 표준출력) 내용.
   * @param input         입력값.
   * @param checkConflict 프롬프트 충돌 검사.
   *
   * @return 기존 입력값.
   */
  public String addInput(String prompt, String input, boolean checkConflict) {
    notEmpty(prompt, "prompt");
    notNull(input, "input");

    if (this.maxPrompt < prompt.length()) {
      this.maxPrompt = prompt.length();
      this.promptCapacity = 8 * this.maxPrompt;
    }

    if (checkConflict) {
      this.inputs.forEach((p, i) -> {
        if (p.startsWith(prompt) || prompt.startsWith(p))
          throw new IllegalArgumentException(format("prompt conflict : old=('%s'=>'%s'), new=('%s'=>'%s')", p, i, prompt, input));
      });
    }

    return this.inputs.put(prompt, input);
  }

  @Override
  public Runnable stdOutHandler(final Writer stdin, final Reader stdout, final Reader stderr) {
    if (0 >= this.maxPrompt)
      throw new IllegalStateException("maxPromptCapacity is not positive : maxPromptCapacity=" + this.maxPrompt);

    return () -> {
      final StringBuilder stdoutCache = new StringBuilder();
      try {
        for (int c = stdout.read(); 0 <= c; c = stdout.read()) {
          System.out.print((char) c);
          if (LOGGER.isTraceEnabled())
            LOGGER.trace("#stdOutHandler$run : c={}({})", c, Character.getName(c));
          stdoutCache.append((char) c);

          if (LOGGER.isTraceEnabled())
            LOGGER.trace("#stdOutHandler$run : stdoutCache='{}'", stdoutCache);
          evalPrompt(stdoutCache, stdin);

          // 가끔씩 표준출력 캐시를 비워준다.
          if (this.promptCapacity < stdoutCache.length())
            stdoutCache.delete(0, stdoutCache.length() - this.maxPrompt);
        }
      } catch (IOException e) {
        throw new PromptHandleException(e);
      }
    };
  }

  /**
   * 현재 프롬프트가 입력이 필요한지 확인해서 필요한 경우에 입력한다.
   *
   * @param stdoutCache 현재 표준출력 캐시.
   * @param stdin       표준입력.
   */
  private void evalPrompt(final StringBuilder stdoutCache, Writer stdin) throws IOException {
    for (Map.Entry<String, String> input : this.inputs.entrySet()) {
      if (0 <= stdoutCache.indexOf(input.getKey())) {
        stdin.write(input.getValue());
        stdin.write('\n');
        stdin.flush();
        stdoutCache.setLength(0);
        return;
      }
    }
  }

  @Override
  public String toString() {
    return new StringJoiner(", ", SimplePromptHandler.class.getSimpleName() + "[", "]")
               .add("maxPrompt=" + this.maxPrompt)
               .add("promptCapacity=" + this.promptCapacity)
               .add("inputs=" + this.inputs)
               .toString();
  }
}