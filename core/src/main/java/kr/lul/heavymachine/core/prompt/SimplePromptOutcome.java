package kr.lul.heavymachine.core.prompt;

import java.util.Objects;

import static java.lang.String.format;
import static kr.lul.common.util.Arguments.notNegative;

/**
 * @author justburrow
 * @since 2021/05/06
 */
public class SimplePromptOutcome implements PromptOutcome {
  private final int exitCode;

  public SimplePromptOutcome(int exitCode) {
    this.exitCode = notNegative(exitCode, "exitCode");
  }

  @Override
  public int exitCode() {
    return this.exitCode;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;
    SimplePromptOutcome that = (SimplePromptOutcome) o;
    return this.exitCode == that.exitCode;
  }

  @Override
  public int hashCode() {
    return Objects.hash(this.exitCode);
  }

  @Override
  public String toString() {
    return format("%s[exitCode=%d, success=%b]", SimplePromptOutcome.class.getSimpleName(), this.exitCode, isSuccess());
  }
}