package kr.lul.heavymachine.core.shell;

import kr.lul.heavymachine.core.machine.AbstractBlueprint;

import java.util.List;
import java.util.StringJoiner;
import java.util.UUID;

import static java.util.UUID.randomUUID;
import static kr.lul.common.util.Arguments.matches;
import static kr.lul.common.util.Arguments.notEmpty;

/**
 * @author justburrow
 * @since 2021/05/01
 */
public class SimpleShellBlueprint extends AbstractBlueprint implements ShellBlueprint {
  public static final String COMMAND_PATTERN = "[^\r\n\t]+";

  private final List<String> command;

  public SimpleShellBlueprint(String command) {
    this(randomUUID(), command);
  }

  public SimpleShellBlueprint(UUID id, String command) {
    this(id, id.toString(), command);
  }

  public SimpleShellBlueprint(UUID id, String name, String command) {
    this(id, name, name, command);
  }

  public SimpleShellBlueprint(UUID id, String name, String description, String command) {
    super(id, name, description);
    notEmpty(command, "command");
    matches(command, COMMAND_PATTERN, "command");

    this.command = List.of(command.trim().split(" +"));
  }

  @Override
  public String[] getCommand() {
    return this.command.toArray(new String[0]);
  }

  @Override
  public String toString() {
    return new StringJoiner(", ", SimpleShellBlueprint.class.getSimpleName() + "[", "]")
               .add("command=" + this.command)
               .toString();
  }
}