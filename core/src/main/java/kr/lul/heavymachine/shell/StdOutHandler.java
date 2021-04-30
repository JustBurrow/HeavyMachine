package kr.lul.heavymachine.shell;

import java.io.InputStream;

/**
 * @author justburrow
 * @since 2021/04/25
 */
public interface StdOutHandler {
  StdOutHandler NULL_HANDLER = stream -> {
  };

  void setStdOut(InputStream stream);
}