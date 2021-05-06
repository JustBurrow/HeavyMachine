package kr.lul.heavymachine.core.shell;

import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.Writer;

import static java.nio.charset.StandardCharsets.UTF_8;

/**
 * @author justburrow
 * @since 2021/05/06
 */
public interface PromptStdinHandler extends StdinHandler {
  Writer getStdin();

  default void setStdin(OutputStream stdin) {
    setStdin(new OutputStreamWriter(stdin, UTF_8));
  }

  void setStdin(Writer stdin);
}