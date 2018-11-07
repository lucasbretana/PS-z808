package util;

/**
 * To be thrown on MainTest when any test fails.
 */
public class TestFaliedException extends Exception {
	static final protected long serialVersionUID = 42L;
	private static final String msg = "Error: Test falied with error value:";
	/**
	 * @param val Non 0 error value.
	 */ 
	public TestFaliedException(Integer val) {
		super(msg + val + "\n");
	}
	/**
	 * @param val Non 0 error value;
	 * @param obj Additional info about the exception.
	 */ 
	public TestFaliedException(Integer val, Object obj) {
		super(msg + val + "\n" + obj);
	}
}
