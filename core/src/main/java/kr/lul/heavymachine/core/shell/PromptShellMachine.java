package kr.lul.heavymachine.core.shell;

import kr.lul.heavymachine.core.machine.AbstractMachine;
import kr.lul.heavymachine.core.machine.Control;

import java.io.IOException;

import static kr.lul.common.util.Arguments.notNull;

/**
 * TODO {@link PromptHandler}를 {@link #doExecute(Control)} 실행시에 만들 수 있도록 빌더 도입.
 *
 * @author justburrow
 * @since 2021/05/02
 */
public class PromptShellMachine extends AbstractMachine<ShellBlueprint, ShellOutcome> implements ShellMachine {
  private final PromptHandler promptHandler;

  public PromptShellMachine(ShellBlueprint blueprint, PromptHandler promptHandler) {
    super(blueprint);

    this.promptHandler = notNull(promptHandler, "promptHandler");
  }

  @Override
  protected ShellOutcome doExecute(Control control) throws IOException, InterruptedException {
    String[] command = buildCommand(notNull(control, "control"));
    ProcessBuilder builder = new ProcessBuilder(command);
    Process process = builder.start();
    this.promptHandler.handle(process, control.executorService());
    int exitCode = process.waitFor();

    return new SimpleShellOutcome(exitCode);
  }
}