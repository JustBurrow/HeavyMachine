package kr.lul.heavymachine.core.machine;

import java.util.UUID;

/**
 * 변경 불가능한 {@link Machine} 초기화 정보.
 *
 * @author justburrow
 * @since 2021/04/30
 */
public interface Blueprint {
  /**
   * @return ID.
   *
   * @see UUID#randomUUID() 기본값.
   */
  UUID getId();

  /**
   * @return 이름.
   *
   * @see #getId() 기본값.
   */
  String getName();

  /**
   * @return 설명.
   *
   * @see #getName() 기본값.
   */
  String getDescription();
}