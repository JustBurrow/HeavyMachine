package kr.lul.heavymachine.shell;

import kr.lul.heavymachine.core.MachineRunException;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static kr.lul.heavymachine.shell.ShellOutcome.EXIT_CODE_UNKNOWN;

/**
 * @author justburrow
 * @since 2021/04/25
 */
public class ShellCommandException extends MachineRunException {
  private static final Pattern EXIT_CODE_PATTERN = Pattern.compile(".*error=(\\d+).*");

  private final int exitCode;

  public ShellCommandException() {
    super();
    this.exitCode = EXIT_CODE_UNKNOWN;
  }

  public ShellCommandException(String message) {
    super(message);
    this.exitCode = EXIT_CODE_UNKNOWN;
  }

  public ShellCommandException(String message, Throwable cause) {
    super(message, cause);
    this.exitCode = EXIT_CODE_UNKNOWN;
  }

  public ShellCommandException(Throwable cause) {
    super(cause);

    Matcher matcher = EXIT_CODE_PATTERN.matcher(cause.getMessage());
    this.exitCode = matcher.matches()
                        ? Integer.parseInt(matcher.group(1), 10)
                        : EXIT_CODE_UNKNOWN;
  }

  public int getExitCode() {
    return this.exitCode;
  }
}