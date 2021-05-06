package kr.lul.heavymachine.core.shell;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;

import static java.nio.charset.StandardCharsets.UTF_8;

/**
 * @author justburrow
 * @since 2021/05/06
 */
public interface PromptStderrHandler extends StderrHandler {
  default void setStderr(InputStream stderr) {
    setStderr(new InputStreamReader(stderr, UTF_8));
  }

  void setStderr(Reader stderr);

  Reader getStderr();
}