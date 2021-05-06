package kr.lul.heavymachine.core.prompt;

import kr.lul.heavymachine.core.machine.AbstractMachine;
import kr.lul.heavymachine.core.machine.Control;
import kr.lul.heavymachine.core.machine.Machine;

import java.io.IOException;
import java.util.List;

import static kr.lul.common.util.Arguments.notNull;

/**
 * @author justburrow
 * @since 2021/05/06
 */
public class PromptMachine extends AbstractMachine<PromptBlueprint, PromptOutcome> implements Machine {
  private PromptBlueprint blueprint;
  private PromptHandler promptHandler;

  public PromptMachine(PromptBlueprint blueprint, PromptHandler promptHandler) {
    super(blueprint);
    this.promptHandler = notNull(promptHandler, "promptHandler");
  }

  protected List<String> getCommand(Control control) {
    // TODO 옵션 사용.
    return this.blueprint.baseCommand();
  }

  @Override
  public PromptOutcome doExecute(Control control) throws IOException, InterruptedException {
    notNull(control, "control");

    ProcessBuilder builder = new ProcessBuilder(getCommand(control));
    Process process = builder.start();
    this.promptHandler.handle(this.blueprint, control, process);
    int exitCode = process.waitFor();

    return new SimplePromptOutcome(exitCode);
  }
}