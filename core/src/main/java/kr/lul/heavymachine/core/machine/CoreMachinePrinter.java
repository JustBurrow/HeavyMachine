package kr.lul.heavymachine.core.machine;

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

    throw new UnsupportedOperationException("not implemented.");
  }
}