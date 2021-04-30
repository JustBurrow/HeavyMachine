package kr.lul.heavymachine.core;

import kr.lul.common.util.Anchor;

/**
 * @author justburrow
 * @since 2021/04/25
 */
public abstract class HeavyMachineCoreAnchor extends Anchor {
  public static final Package PACKAGE = HeavyMachineCoreAnchor.class.getPackage();
  public static final String PACKAGE_NAME = HeavyMachineCoreAnchor.class.getPackageName();

  protected HeavyMachineCoreAnchor() {
    throw new UnsupportedOperationException();
  }
}