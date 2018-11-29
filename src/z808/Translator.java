package z808;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import util.AZMRegexCommon;
import util.ExecutionException;
import util.InvalidOperationException;
import util.NotImplementedException;

import z808.command.Command;
import z808.command.directive.*;
import z808.command.instruction.*;

/**
 * Translates a expanded code to internal representation
 */
public class Translator {
	private static final String DwRegEx = AZMRegexCommon.NAME_RGX + "\\s" + Equ.MNEMONIC + ".*";
	private static final String EquRegEx = AZMRegexCommon.NAME_RGX + "\\s" + Equ.MNEMONIC + ".*";
	/**
	 * Creates a translator
	 */
	public Translator() throws Exception { }

	/**
	 * Converets the given list of string to commands
	 * @param raw_code the code the translated
	 */
	public List<Command> convertCode(List<String> raw_code) throws ExecutionException {
		ArrayList<Command> output = new ArrayList<Command>();

		Command c = null;
		for (String cmd : raw_code) {
			if (cmd.matches(EquRegEx)) {
				c = makeEqu(cmd);
			} else {
				throw new NotImplementedException("TODO: command string \"" + cmd + "\"");
			}

			output.add(c);
		}

		return output;
	}

	/**
	 * TODO @alanaschwendler
	 * remove this, only for tests
	 */
	public Command convertCode1(String raw_code) throws ExecutionException {
		List<String> l = new ArrayList<>(1);
		l.add(raw_code);
		return this.convertCode(l).get(0);
	}

	/**
	 * Creates a new Equ
	 * @param s_cmd the string representation
	 * @throws ExecutionException if there is an unexpected error
	 */
	private Equ makeEqu(String s_cmd) throws ExecutionException {
		String lbl = null;
		Integer val = null;
		Equ e = null;

		// 1. split
		String tokens[] = s_cmd.split("\\s");

		// sanity check
		if ( (tokens.length < 3) || (!tokens[1].equals(Equ.MNEMONIC)) )
			throw new ExecutionException("This doesn't make any sense..mismatching expression");

		lbl = tokens[0];

		// TODO @Bretana finish to add support to stuff like
		// <expression> + <expression>
		// -<expression>

		// only support
		// <int>
		// <char>
		if (tokens[2].matches(AZMRegexCommon.INTEGER_RGX))
			val = this.convertZ808Int(tokens[2]);
		else if (tokens[2].matches("[a-zA-Z]"))
			val = Character.digit(tokens[2].charAt(0), 10);

		return new Equ(lbl, val);
	}


	/**
	 * Converts a Z808 representation of int to a Java int
	 * @param s_val the string code of the int
	 * @throws ExecutionException if there is an error converting
	 */
	private Integer convertZ808Int(String s_val) throws ClassCastException {
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

	public static void testTranslator(Boolean verb) throws ExecutionException {
			 //Translator(l).convert().toString())
	}
}
