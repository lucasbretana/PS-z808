package util;

/**
 * To be thrown when execution fails for some reason.
 */
public class SymbolNotFound extends Exception {
	static final protected long serialVersionUID = 43L;

	public SymbolNotFound(String msg) {
		super("Execution halted: " + msg);
	}
	public SymbolNotFound(String msg, Throwable cause) {
		super("Execution halted: " + msg, cause);
	}
}
