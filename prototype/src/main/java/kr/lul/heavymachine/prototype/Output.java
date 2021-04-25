package kr.lul.heavymachine.prototype;

import java.util.List;
import java.util.Objects;
import java.util.StringJoiner;

import static kr.lul.common.util.Arguments.notNegative;
import static kr.lul.common.util.Arguments.notNull;

/**
 * @author justburrow
 * @since 2021/04/25
 */
public class Output {
  private final int exitCode;
  private List<String> output;
  private List<String> error;

  public Output(int exitCode, List<String> output, List<String> error) {
    this.exitCode = notNegative(exitCode, "exitCode");
    this.output = notNull(output, "output");
    this.error = notNull(error, "error");
  }

  public int getExitCode() {
    return this.exitCode;
  }

  public List<String> getOutput() {
    return this.output;
  }

  public List<String> getError() {
    return this.error;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;
    Output that = (Output) o;
    return this.output.equals(that.output) && this.error.equals(that.error);
  }

  @Override
  public int hashCode() {
    return Objects.hash(this.output, this.error);
  }

  @Override
  public String toString() {
    return new StringJoiner(", ", Output.class.getSimpleName() + " [", "]")
               .add("exitCode=" + this.exitCode)
               .add("output=" + this.output)
               .add("error=" + this.error)
               .toString();
  }
}