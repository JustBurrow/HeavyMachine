package kr.lul.heavymachine.core.shell;

import kr.lul.heavymachine.core.machine.Blueprint;

/**
 * 쉘 커맨드를 실행하는 {@link ShellMachine}을 생성하는 초기화 정보.
 *
 * @author justburrow
 * @since 2021/05/01
 */
public interface ShellBlueprint extends Blueprint {
  /**
   * @return 쉘 커맨드.
   */
  String[] getCommand();
}