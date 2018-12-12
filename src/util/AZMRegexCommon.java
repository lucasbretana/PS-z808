package util;

public interface AZMRegexCommon {
	public static final String CHAR_RGX = "[a-zA-Z]";
	public static final String SPACE = "\\s";

	public static final String NAME_RGX = "([\\_]|[\\?]|[\\$]|[\\@]|[a-zA-Z_0-9])([a-zA-Z_0-9]*)";
	public static final String INTEGER_RGX = "([a-fA-F_0-9]*)([b]|[d]|[h])?";
	public static final String EXPRESSION = "([a-fA-F_0-9]*)";

	/**
	 * Converts a Z808 representation of int to a Java int
	 * @param s_val the string code of the int
	 * @throws ExecutionException if there is an error converting
	 */
	static public Integer convertZ808Int(String s_val) throws ClassCastException {
		int val = 0;
		if (s_val.endsWith("b")) {
			s_val = s_val.substring(0, s_val.length() -1);
			val = Integer.parseInt(s_val, 2);
		} else if (s_val.endsWith("d")) {
			s_val = s_val.substring(0, s_val.length() -1);
			val = Integer.parseInt(s_val, 10);
		} else if (s_val.endsWith("h")) {
			s_val = s_val.substring(0, s_val.length() -1);
			val = Integer.parseInt(s_val, 16);
		} else {
			val = Integer.parseInt(s_val);
		}

		return new Integer(val);
	}

}
