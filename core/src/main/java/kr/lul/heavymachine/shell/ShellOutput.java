package kr.lul.heavymachine.shell;

import kr.lul.heavymachine.core.Output;

import java.util.Objects;
import java.util.StringJoiner;

import static kr.lul.common.util.Arguments.notNegative;

/**
 * @author justburrow
 * @since 2021/04/25
 */
public class ShellOutput implements Output {
  public static final int EXIT_CODE_UNKNOWN = 255;
  
  private int exitCode;

  public ShellOutput(int exitCode) {
    this.exitCode = notNegative(exitCode);
  }

  public int getExitCode() {
    return this.exitCode;
  }

  @Override
  public boolean isSuccess() {
    return 0 == this.exitCode;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;
    ShellOutput that = (ShellOutput) o;
    return this.exitCode == that.exitCode;
  }

  @Override
  public int hashCode() {
    return Objects.hash(this.exitCode);
  }

  @Override
  public String toString() {
    return new StringJoiner(", ", ShellOutput.class.getSimpleName() + "[", "]")
               .add("exitCode=" + this.exitCode)
               .toString();
  }
}