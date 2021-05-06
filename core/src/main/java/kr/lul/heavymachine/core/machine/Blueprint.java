package kr.lul.heavymachine.core.machine;

import java.util.UUID;

/**
 * {@link Machine}의 기능 정보. immutable.
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