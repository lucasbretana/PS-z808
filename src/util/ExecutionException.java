package util;

/**
 * To be thrown when execution fails for some reason.
 */
public class ExecutionException extends Exception {
	static final protected long serialVersionUID = 43L;

	public ExecutionException(String msg) {
		super("Execution halted: " + msg);
	}
}
