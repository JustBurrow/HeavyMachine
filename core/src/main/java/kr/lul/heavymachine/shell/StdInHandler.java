package kr.lul.heavymachine.shell;

import java.io.OutputStream;

/**
 * @author justburrow
 * @since 2021/04/25
 */
public interface StdInHandler {
  StdInHandler NULL_HANDLER = stream -> {
  };

  void setStdIn(OutputStream stream);
}