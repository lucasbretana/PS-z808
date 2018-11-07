package util;

/**
 * To be thrown when execution finishes successfully.
 */
public class FinishedException extends Exception {
	static final protected long serialVersionUID = 44L;

	public FinishedException(String msg) {
		super("Execution Finished: " + msg);
	}
}
