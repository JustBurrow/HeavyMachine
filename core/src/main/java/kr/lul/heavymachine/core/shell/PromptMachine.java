package kr.lul.heavymachine.core.shell;

import kr.lul.heavymachine.core.machine.AbstractMachine;
import kr.lul.heavymachine.core.machine.Control;

import java.io.IOException;
import java.util.concurrent.ExecutorService;
import java.util.function.BiFunction;

import static kr.lul.common.util.Arguments.notNull;

/**
 * TODO {@link ProcessHandler}를 {@link #doExecute(Control)} 실행시에 만들 수 있도록 빌더 도입.
 *
 * @author justburrow
 * @since 2021/05/02
 */
public class PromptMachine extends AbstractMachine<ShellBlueprint, ShellOutcome> implements ShellMachine {
  private final BiFunction<Process, ExecutorService, PromptHandler> promptHandlerBuilder;

  public PromptMachine(ShellBlueprint blueprint) {
    this(blueprint,
        (process, executorService) ->
            new SimplePromptHandlerBuilder()
                .process(process)
                .handlerExecutorService(executorService)
                .build());
  }

  public PromptMachine(ShellBlueprint blueprint, BiFunction<Process, ExecutorService, PromptHandler> promptHandlerBuilder) {
    super(blueprint);

    this.promptHandlerBuilder = notNull(promptHandlerBuilder, "promptHandlerBuilder");
  }

  @Override
  protected ShellOutcome doExecute(Control control) throws IOException, InterruptedException {
    String[] command = buildCommand(notNull(control, "control"));
    ProcessBuilder builder = new ProcessBuilder(command);
    Process process = builder.start();
    this.promptHandlerBuilder.apply(process, control.executorService())
        .handle();
    int exitCode = process.waitFor();

    return new SimpleShellOutcome(exitCode);
  }
}