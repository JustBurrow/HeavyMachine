package kr.lul.heavymachine.shell;

import java.io.InputStream;

/**
 * @author justburrow
 * @since 2021/04/25
 */
public interface StdErrHandler {
  StdErrHandler NULL_HANDLER = new StdErrHandler() {
    @Override
    public void setStream(InputStream stream) {
    }
  };

  void setStream(InputStream stream);
}