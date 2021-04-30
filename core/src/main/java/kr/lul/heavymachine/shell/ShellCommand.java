package kr.lul.heavymachine.shell;

import kr.lul.heavymachine.core.Command;

import java.util.List;
import java.util.Objects;
import java.util.StringJoiner;

import static kr.lul.common.util.Arguments.notEmpty;

/**
 * @author justburrow
 * @since 2021/04/25
 */
public class ShellCommand implements Command {
  private final String command;
  private final List<String> commandList;

  public ShellCommand(String command) {
    this.command = notEmpty(command, "command");
    this.commandList = List.of(command.split("\\s+"));
  }

  public String getCommand() {
    return this.command;
  }

  public List<String> getCommandList() {
    return this.commandList;
  }

  public String[] getCommandArray() {
    return this.commandList.toArray(new String[0]);
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;
    ShellCommand that = (ShellCommand) o;
    return this.command.equals(that.command);
  }

  @Override
  public int hashCode() {
    return Objects.hash(this.command);
  }

  @Override
  public String toString() {
    return new StringJoiner(", ", ShellCommand.class.getSimpleName() + "[", "]")
               .add("command='" + this.command + "'")
               .add("commandList=" + this.commandList)
               .toString();
  }
}