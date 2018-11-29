package util;

/**
 * To be thrown when execution fails for some reason.
 */
public class InvalidOperationException extends ExecutionException {
	static final protected long serialVersionUID = 43L;

	public InvalidOperationException(String msg) {
		super("An invalid operation could not be perform: " + msg);
	}
	public InvalidOperationException(String msg, Throwable cause) {
		super("An invalid operation could not be perform: " + msg, cause);
	}
}
