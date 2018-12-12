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
				System.out.println("TOREMOVE, Equ \"" + cmd + "\"");
				c = Equ.makeEqu(cmd);
			} else if (cmd.matches(AddAX.REGEX)) {
				System.out.println("TOREMOVE, AddAX \"" + cmd + "\"");
				c = AddAX.makeAddAX(cmd);
			} else if (cmd.matches(AddDX.REGEX)) {
				System.out.println("TOREMOVE, AddDX \"" + cmd + "\"");
				c = AddDX.makeAddDX(cmd);
			} else if (cmd.matches(AddCTE.REGEX)) {
				System.out.println("TOREMOVE, AddCTE \"" + cmd + "\"");
				c = AddCTE.makeAddCTE(cmd);
			} else if (cmd.matches(SubAX.REGEX)) {
				System.out.println("TOREMOVE, SubAX \"" + cmd + "\"");
				//c = SubAX.makeSubAX(cmd);
			} else if (cmd.matches(SubDX.REGEX)) {
				System.out.println("TOREMOVE, SubDX \"" + cmd + "\"");
				//c = SubDX.makeSubDX(cmd);
			} else if (cmd.matches(SubCTE.REGEX)) {
				System.out.println("TOREMOVE, SubCTE \"" + cmd + "\"");
				//c = SubCTE.makeSubCTE(cmd);
			} else {
				System.out.println("TODO: command string \"" + cmd + "\"");
				//throw new NotImplementedException("TODO: command string \"" + cmd + "\"");
			}

			output.add(c);
			c = null;
		}

		return output;
	}


	public static void testTranslator(Boolean verb) throws ExecutionException {
		testCode1(verb);
		regexTest(verb);
	}

	public static void testCode1(Boolean verb) throws ExecutionException {
		if (verb) System.err.println("-- Starting code #1 test --");
		Translator t = new Translator();

		List<Command> res = t.convertCode(Arrays.asList(
			"EQU 5",
			"add AX 0",
			"add AX AX",
			"add AX AX",
			"sub AX 0",
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
			"sum1 add AX AX",
			"add AX AX",
			"sum2 add AX DX",
			"add AX DX",
			"sum3 add AX 10",
			"add AX 10",
			"sum4 add AX five",
			"add AX five",
			"dif1 sub AX AX",
			"sub AX AX",
			"dif2 sub AX DX",
			"sub AX DX",
			"dif3 sub AX 10",
			"sub AX 10",
			"dif4 sub AX five",
			"sub AX five",
			"hlt"
		)));

		System.err.println("-- Regex tests are OK --");
	}
}
