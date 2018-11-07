package util;

/**
 * To be thrown when a immediate value is too big or to small (over 0xFFFF).
 */
public class TooLongValue extends ExecutionException {
	static final protected long serialVersionUID = 43L;

	public TooLongValue(Number value) {
		super("Value is to long for immediate use: " + value);
	}
}
