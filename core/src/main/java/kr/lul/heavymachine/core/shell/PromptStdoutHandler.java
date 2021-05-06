package kr.lul.heavymachine.core.shell;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;

import static java.nio.charset.StandardCharsets.UTF_8;

/**
 * @author justburrow
 * @since 2021/05/06
 */
public interface PromptStdoutHandler extends StdoutHandler {
  Reader getStdout();

  default void setStdout(InputStream stdout) {
    setStdout(new InputStreamReader(stdout, UTF_8));
  }

  void setStdout(Reader stdout);
}