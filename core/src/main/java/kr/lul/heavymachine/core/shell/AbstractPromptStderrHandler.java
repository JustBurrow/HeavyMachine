package kr.lul.heavymachine.core.shell;

import java.io.Reader;

import static kr.lul.common.util.Arguments.notNull;

/**
 * @author justburrow
 * @since 2021/05/06
 */
public abstract class AbstractPromptStderrHandler implements PromptStderrHandler {
  protected Reader stderr;

  @Override
  public void setStderr(Reader stderr) {
    this.stderr = notNull(stderr, "stderr");
  }

  @Override
  public Reader getStderr() {
    return this.stderr;
  }
}