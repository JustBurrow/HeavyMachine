package kr.lul.heavymachine.shell;

import java.io.OutputStream;

/**
 * @author justburrow
 * @since 2021/04/25
 */
public interface StdInHandler {

  void setStdIn(OutputStream stream);
}