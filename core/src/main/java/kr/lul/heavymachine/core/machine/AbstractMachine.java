package kr.lul.heavymachine.core.machine;

import java.io.IOException;

import static kr.lul.common.util.Arguments.notNull;

/**
 * @author justburrow
 * @since 2021/05/06
 */
public abstract class AbstractMachine<B extends Blueprint, O extends Outcome> implements Machine {
  protected final B blueprint;

  public AbstractMachine(B blueprint) {
    this.blueprint = notNull(blueprint, "blueprint");
  }

  @Override
  public B getBlueprint() {
    return this.blueprint;
  }

  @Override
  public O execute(Control control) throws MachineExecutionFailException {
    try {
      return doExecute(notNull(control, "control"));
    } catch (IOException | InterruptedException e) {
      throw new MachineExecutionFailException(e);
    }
  }

  protected abstract O doExecute(Control control) throws IOException, InterruptedException;
}