package kr.lul.heavymachine.core;

import kr.lul.heavymachine.core.machine.Blueprint;
import kr.lul.heavymachine.core.machine.Machine;
import kr.lul.heavymachine.core.machine.MachinePrinter;

import static kr.lul.common.util.Arguments.notNull;

/**
 * {@link kr.lul.heavymachine.core} 머신용 딜리게이터.
 *
 * @author justburrow
 * @since 2021/04/30
 */
public class CoreMachinePrinter implements MachinePrinter<Blueprint, Machine> {
  @Override
  public Machine print(Blueprint blueprint) {
    notNull(blueprint, "blueprint");

    // TODO

    throw new UnsupportedOperationException("not implemented.");
  }
}