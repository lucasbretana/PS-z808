package util;

/**
 * To be thrown when execution fails for some reason.
 */
public class ExecutionException extends Exception {
	static final protected long serialVersionUID = 43L;

	public ExecutionException(String msg) {
		super("Execution halted: " + msg);
	}
	public ExecutionException(String msg, Throwable cause) {
		super("Execution halted: " + msg, cause);
	}
}
