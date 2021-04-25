package kr.lul.heavymachine.shell;

import java.io.InputStream;

/**
 * @author justburrow
 * @since 2021/04/25
 */
public interface StdOutHandler {
  StdOutHandler NULL_HANDLER = new StdOutHandler() {
    @Override
    public void setStream(InputStream stream) {
    }
  };

  void setStream(InputStream stream);
}