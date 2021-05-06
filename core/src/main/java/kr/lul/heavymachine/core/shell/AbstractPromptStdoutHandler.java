package kr.lul.heavymachine.core.shell;

import java.io.Reader;

import static kr.lul.common.util.Arguments.notNull;

/**
 * @author justburrow
 * @since 2021/05/06
 */
public abstract class AbstractPromptStdoutHandler implements PromptStdoutHandler {
  private Reader stdout;

  @Override
  public Reader getStdout() {
    return this.stdout;
  }

  @Override
  public void setStdout(Reader stdout) {
    this.stdout = notNull(stdout, "stdout");
  }
}