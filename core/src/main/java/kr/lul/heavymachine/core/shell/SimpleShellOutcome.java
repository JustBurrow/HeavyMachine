package kr.lul.heavymachine.core.shell;

import java.util.StringJoiner;

import static kr.lul.common.util.Arguments.notNegative;

/**
 * @author justburrow
 * @since 2021/05/02
 */
public class SimpleShellOutcome implements ShellOutcome {
  private int exitCode;

  public SimpleShellOutcome(int exitCode) {
    this.exitCode = notNegative(exitCode, "exitCode");
  }

  @Override
  public int getExitCode() {
    return this.exitCode;
  }

  @Override
  public String toString() {
    return new StringJoiner(", ", SimpleShellOutcome.class.getSimpleName() + "[", "]")
               .add("exitCode=" + this.exitCode)
               .add("success=" + isSuccess())
               .toString();
  }
}