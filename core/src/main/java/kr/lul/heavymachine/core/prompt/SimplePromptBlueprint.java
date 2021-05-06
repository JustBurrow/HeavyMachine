package kr.lul.heavymachine.core.prompt;

import kr.lul.heavymachine.core.machine.AbstractBlueprint;

import java.util.List;
import java.util.UUID;

import static java.util.UUID.randomUUID;

/**
 * @author justburrow
 * @since 2021/05/06
 */
public class SimplePromptBlueprint extends AbstractBlueprint implements PromptBlueprint {
  private List<String> baseCommand;

  public SimplePromptBlueprint(String command) {
    this(randomUUID(), command);
  }

  public SimplePromptBlueprint(UUID id, String command) {
    this(id, id.toString(), command);
  }

  public SimplePromptBlueprint(UUID id, String name, String command) {
    this(id, name, name, command);
  }

  public SimplePromptBlueprint(UUID id, String name, String description, String command) {
    super(id, name, description);

    COMMAND_VALIDATOR.validate(command);

    this.baseCommand = List.of(command.trim().split("[ \t]+"));
  }

  @Override
  public List<String> baseCommand() {
    return this.baseCommand;
  }
}