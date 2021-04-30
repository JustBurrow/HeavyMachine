package kr.lul.heavymachine.shell;

import kr.lul.heavymachine.core.Machine;

import java.io.IOException;

import static kr.lul.common.util.Arguments.notNull;

/**
 * @author justburrow
 * @since 2021/04/25
 */
public class ShellMachine implements Machine<ShellCommand, ShellOutcome> {
  private final StdInHandler stdin;
  private final StdOutHandler stdout;
  private final StdErrHandler stderr;

  public ShellMachine() {
    this(StdInHandler.NULL_HANDLER, StdOutHandler.NULL_HANDLER, StdErrHandler.NULL_HANDLER);
  }

  public ShellMachine(StdInHandler stdin, StdOutHandler stdout, StdErrHandler stderr) {
    this.stdin = notNull(stdin, "stdin");
    this.stdout = notNull(stdout, "stdout");
    this.stderr = notNull(stderr, "stderr");
  }

  private ShellOutcome doRun(ShellCommand input) throws IOException, InterruptedException, ShellCommandException {
    ProcessBuilder builder = new ProcessBuilder(input.getCommandList());
    Process process;
    try {
      process = builder.start();
    } catch (IOException e) {
      throw new ShellCommandException(e);
    }

    this.stdin.setStream(process.getOutputStream());
    this.stdout.setStream(process.getInputStream());
    this.stderr.setStream(process.getErrorStream());

    int exitCode = process.waitFor();
    ShellOutcome output = new ShellOutcome(exitCode);

    return output;
  }

  @Override
  public ShellOutcome run(ShellCommand input) {
    try {
      return doRun(input);
    } catch (ShellCommandException e) {
      return new ShellOutcome(e.getExitCode());
    } catch (IOException | InterruptedException e) {
      throw new RuntimeException(e);
    }
  }
}