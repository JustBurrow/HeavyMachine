package kr.lul.heavymachine.core.machine;

import java.util.concurrent.ExecutorService;

import static kr.lul.common.util.Arguments.notNull;

/**
 * 머신 실행 제어.
 *
 * @author justburrow
 * @since 2021/04/30
 */
public interface Control {
  /**
   * {@link Machine#execute(Control)} 실행이 자식 스레드가 필요할 경우에 사용.
   *
   * @return 머신용 스레드 관리 서비스.
   */
  ExecutorService executorService();

  /**
   * 옵션 설정.
   *
   * @param key    옵션 이름.
   * @param option 옵션 값.
   */
  void put(String key, Object option);

  /**
   * @param key 옵션 이름.
   *
   * @return 제어 옵션.
   */
  Object get(String key);

  /**
   * @param key  옵션 이름.
   * @param type 옵션 타입.
   * @param <V>  옵션 타입.
   *
   * @return 제어 옵션.
   */
  default <V> V get(String key, Class<V> type) {
    return notNull(type, "type")
               .cast(get(notNull(key, "key")));
  }
}