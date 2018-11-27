package z808;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import util.AZMRegexCommon;
import util.ExecutionException;
import util.InvalidOperationException;
import util.NotImplementedException;

import z808.Translator;

import z808.command.Command;
import z808.command.directive.*;
import z808.command.instruction.*;

/**
 * Translates a expanded code to internal representation
 */
public class Translator {
	/**
	 * Creates a translator
	 */
	public Translator() { }

	/**
	 * Converets the given list of string to commands
	 * @param raw_code the code the translated
	 */
	public List<Command> convertCode(List<String> raw_code) throws ExecutionException {
		ArrayList<Command> output = new ArrayList<>();

		Command c = null;
		for (String cmd : raw_code) {
			if (cmd.matches(Equ.REGEX)) {
				System.out.println("DEBUG, matched an Equ with \"" + cmd + "\"");
				c = makeEqu(cmd);
			} else {
				System.out.println("TODO: command string \"" + cmd + "\"");
				//throw new NotImplementedException("TODO: command string \"" + cmd + "\"");
			}

			output.add(c);
			c = null;
		}

		return output;
	}

	/**
	 * Creates a new Equ
	 * @param s_cmd the string representation
	 * @throws ExecutionException if there is an unexpected error
	 */
	private Equ makeEqu(String s_cmd) throws ExecutionException {
		// TODO @Bretana finish to add support to stuff like
		// <expression> + <expression>
		// -<expression>

		// only support
		// <int>
		// <char>

		String lbl = null;
		Integer val = null;
		Equ e = null;

		// 1. split
		String tokens[] = s_cmd.split("\\s");

		// sanity check
		if ( tokens.length == 2 ) {
			if (!tokens[0].equals(Equ.MNEMONIC) )
				throw new ExecutionException("This doesn't make any sense..mismatching expression, missing mnemonic");
			
			if ( tokens[1].matches(AZMRegexCommon.INTEGER_RGX) )
				val = convertZ808Int(tokens[1]);
			else if ( tokens[1].matches(AZMRegexCommon.CHAR_RGX) )
				val = Character.digit(tokens[1].charAt(0), 10);
			else
				throw new ExecutionException("This doesn't make any sense..mismatching expression, invalid parameter");

			return new Equ(val);
		}

		if ( tokens.length == 3 ) {
			if ( !tokens[1].equals(Equ.MNEMONIC) )
				throw new ExecutionException("This doesn't make any sense..mismatching expression, missing mnemonic");

			if ( tokens[2].matches(AZMRegexCommon.INTEGER_RGX) )
				val = convertZ808Int(tokens[2]);
			else if ( tokens[2].matches(AZMRegexCommon.CHAR_RGX) )
				val = Character.digit(tokens[2].charAt(0), 10);
			else
				throw new ExecutionException("This doesn't make any sense..mismatching expression, invalid parameter");

			return new Equ(tokens[0], val);
		}

		throw new ExecutionException("Invalid string for makeEqu, this doesn't make any sense");
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
		testCode1(verb);
		//regexTest(verb);
	}

	public static void testCode1(Boolean verb) throws ExecutionException {
		if (verb) System.err.println("-- Starting code #1 test --");
		Translator t = new Translator();

		List<Command> res = t.convertCode(Arrays.asList(
			"EQU 5",
			"add AX 0x0",
			"add AX AX",
			"add AX AX",
			"sub AX 0x0",
			"hlt"
		));

		if (verb) System.err.println("Resulting transaltor: " + t);
		if (verb) System.err.println("Resulting code: " + res);

		res = null;
		t = null;
		System.gc();
		System.err.println("-- Code#1 tests are OK --");
	}

	public static void regexTest(Boolean verb) throws ExecutionException {
		if (verb) System.err.println("-- Starting regex test --");
		Translator t = new Translator();

		if (verb) System.err.println(new Translator().convertCode(Arrays.asList(
			"five EQU 10",
			"five EQU a",
			"EQU 5",
			"add AX 0x0",
			"add AX AX",
			"add AX AX",
			"sub AX 0x0",
			"hlt"
		)));

		System.err.println("-- Regex tests are OK --");
	}
}
