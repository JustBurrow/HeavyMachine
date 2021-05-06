package kr.lul.heavymachine.core.prompt;

import kr.lul.heavymachine.core.machine.Control;

import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;

/**
 * 실행중 사용자 입력이 필요한 프로세스에 자동으로 입력한다.
 *
 * {@link PromptMachine#execute(Control)}는 여러 번 호출할 수 있으므로, {@link PromptHandler}는 프로세스에 의존적인 상태를 가지면 안된다.
 *
 * @author justburrow
 * @since 2021/05/06
 */
public interface PromptHandler {
  Charset DEFAULT_CHARSET = StandardCharsets.UTF_8;

  void handle(PromptBlueprint blueprint, Control control, Process process);
}