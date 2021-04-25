package kr.lul.heavymachine.prototype;

import java.util.List;
import java.util.Objects;

import static kr.lul.common.util.Arguments.notNull;

/**
 * @author justburrow
 * @since 2021/04/25
 */
public class Input {
  private List<String> command;

  public Input(String command) {
    this.command = List.of(notNull(command, "command").split("\\s+"));
  }

  public String getCommand() {
    return String.join(" ", this.command);
  }

  public String[] getCommandArray() {
    return this.command.toArray(new String[0]);
  }

  public List<String> getCommandList() {
    return this.command;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;
    return this.command.equals(((Input) o).command);
  }

  @Override
  public int hashCode() {
    return Objects.hash(this.command);
  }

  @Override
  public String toString() {
    return this.command.toString();
  }
}