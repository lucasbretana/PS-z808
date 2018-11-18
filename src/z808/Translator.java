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
	private ArrayList<String> code = null;
	// Equ regex
	private static final String EquRegEx = AZMRegexCommon.NAME_RGX + "\\s" + Equ.MNEMONIC + ".*";

	/**
	 * Creates a translator for a given list code
	 * @param expanded_code the code the translated
	 */
	public Translator(List<String> expanded_code) throws Exception {
		this.code = new ArrayList<String>(expanded_code);
	}

	/**
	 * Converets the internal commands list to a new list
	 */
	public List<Command> convert() throws ExecutionException {
		ArrayList<Command> output = new ArrayList<Command>();


		Command c = null;
		for (String cmd : this.code) {
			if (cmd.matches(EquRegEx)) {
				c = makeEqu(cmd);
			} else {
				throw new ExecutionException("\"" + cmd + "\" didn't match any know regular expression");
			}

			System.out.println("Added " + c.toString());
			output.add(c);
		}

		//throw new NotImplementedException("TODO");
		return output;
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
}
