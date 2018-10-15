package util;

/**
 * Use when you are lazy
 * Don't return a null
 */
public class NotImplementedException extends Exception {
  static final protected long serialVersionUID = 42L;

  public NotImplementedException(String msg) {
    super("Yet to be implemented: " + msg);
  }

  public NotImplementedException(String msg, Throwable cause) {
    super("Yet to be implemented: " + msg, cause);
  }
}
