package kr.lul.heavymachine.core.prompt;

import kr.lul.common.util.ValidationException;
import kr.lul.common.util.Validator;
import kr.lul.heavymachine.core.machine.Blueprint;

import java.util.List;

import static java.lang.String.format;

/**
 * @author justburrow
 * @since 2021/05/06
 */
public interface PromptBlueprint extends Blueprint {
  String COMMAND_PATTERN = "[^\n\r]+";

  Validator<String> COMMAND_VALIDATOR = command -> {
    if (null == command)
      throw new ValidationException(command, "command", "command is null.");
    else if (command.isEmpty())
      throw new ValidationException(command, "command", "command is empty.");
    else if (!command.matches(COMMAND_PATTERN))
      throw new ValidationException(command, "command",
          format("illegal command pattern : command='%s', pattern='%s'", command, COMMAND_PATTERN));
  };

  List<String> baseCommand();
}