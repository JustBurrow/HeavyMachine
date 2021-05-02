package kr.lul.heavymachine.core.machine;

import java.util.Map;
import java.util.StringJoiner;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutorService;

import static java.util.concurrent.Executors.newCachedThreadPool;
import static kr.lul.common.util.Arguments.notEmpty;
import static kr.lul.common.util.Arguments.notNull;

/**
 * @author justburrow
 * @since 2021/05/02
 */
public class MapControl implements Control {
  private ExecutorService executorService;
  private Map<String, Object> options;

  public MapControl() {
    this(newCachedThreadPool());
  }

  public MapControl(ExecutorService executorService) {
    this(executorService, Map.of());
  }

  public MapControl(ExecutorService executorService, Map<String, Object> options) {
    this.executorService = notNull(executorService, "executorService");
    this.options = new ConcurrentHashMap<>(notNull(options, "options"));
  }

  @Override
  public ExecutorService executorService() {
    return this.executorService;
  }

  @Override
  public void put(String key, Object option) {
    this.options.put(notEmpty(key, "key"), option);
  }

  @Override
  public Object get(String key) {
    return this.options.get(notEmpty(key, "key"));
  }

  @Override
  public String toString() {
    return new StringJoiner(", ", MapControl.class.getSimpleName() + "[", "]")
               .add("executorService=" + this.executorService)
               .add("options=" + this.options)
               .toString();
  }
}