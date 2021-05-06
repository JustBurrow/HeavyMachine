package kr.lul.heavymachine.core.shell;

import java.io.Writer;

import static kr.lul.common.util.Arguments.notNull;

/**
 * @author justburrow
 * @since 2021/05/06
 */
public abstract class AbstractPromptStdinHandler implements PromptStdinHandler {
  protected Writer stdin;

  @Override
  public Writer getStdin() {
    return this.stdin;
  }

  @Override
  public void setStdin(Writer stdin) {
    this.stdin = notNull(stdin, "stdin");
  }
}