package util;

public interface AZMRegexCommon {
	public static final String CHAR_RGX = "[a-zA-Z]";
	public static final String SPACE = "\\s";

	public static final String NAME_RGX = "([\\_]|[\\?]|[\\$]|[\\@]|[a-zA-Z_0-9])([a-zA-Z_0-9]*)";
	public static final String INTEGER_RGX = "([a-fA-F_0-9]*)([b]|[d]|[h])?";
}
