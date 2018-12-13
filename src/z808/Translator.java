package z808;

import java.io.IOException;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.charset.Charset;

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
		boolean verb = !true;
		ArrayList<Command> output = new ArrayList<>();

		Command c = null;
		for (String cmd : raw_code) {
			if (cmd.matches(Equ.REGEX)) {
				if (verb) System.err.printf("\nDEBUG, made a %s with \"%s\"", "Equ", cmd);
				c = Equ.makeEqu(cmd);
			} else if ((cmd.matches(DW.REGEX)) || (cmd.contains(Dup.MNEMONIC))) {
				if (verb) System.err.printf("\nDEBUG, made a %s with \"%s\"", "DW", cmd);
				c = DW.makeDW(cmd);
			} else if (cmd.matches(End.REGEX)) {
				if (verb) System.err.printf("\nDEBUG, made a %s with \"%s\"", "End", cmd);
				c = End.makeEnd(cmd);
			} else if (cmd.contains(Extern.MNEMONIC)) {
				System.err.printf("\nDEBUG, made a %s with \"%s\"", "Extern", cmd);
				c = Extern.makeExtern(cmd);
			} else if (cmd.contains(Public.MNEMONIC)) {
				System.err.printf("\nDEBUG, made a %s with \"%s\"", "Public", cmd);
				c = Public.makePublic(cmd);
			} else if (cmd.matches(AddAX.REGEX)) {
				if (verb) System.err.printf("\nDEBUG, made a %s with \"%s\"", "AddAX", cmd);
				c = AddAX.makeAddAX(cmd);
			} else if (cmd.matches(AddDX.REGEX)) {
				if (verb) System.err.printf("\nDEBUG, made a %s with \"%s\"", "AddDX", cmd);
				c = AddDX.makeAddDX(cmd);
			} else if (cmd.matches(AddCTE.REGEX)) {
				if (verb) System.err.printf("\nDEBUG, made a %s with \"%s\"", "AddCTE", cmd);
				c = AddCTE.makeAddCTE(cmd);
			} else if (cmd.matches(SubAX.REGEX)) {
				if (verb) System.err.printf("\nDEBUG, made a %s with \"%s\"", "SubAX", cmd);
				c = SubAX.makeSubAX(cmd);
			} else if (cmd.matches(SubDX.REGEX)) {
				if (verb) System.err.printf("\nDEBUG, made a %s with \"%s\"", "SubDX", cmd);
				c = SubDX.makeSubDX(cmd);
			} else if (cmd.matches(SubCTE.REGEX)) {
				if (verb) System.err.printf("\nDEBUG, made a %s with \"%s\"", "SubCTE", cmd);
				c = SubCTE.makeSubCTE(cmd);
			} else {
				if (verb) System.out.println("\nTODO: command string \"" + cmd + "\"");
				else throw new NotImplementedException("TODO: command string \"" + cmd + "\"");
			}

			output.add(c);
			c = null;
		}
		return output;
	}

	public static void testTranslator(Boolean verb) throws ExecutionException {
		testCode1(verb);
		testCode2(verb);
	}

	public static void testCode1(Boolean verb) throws ExecutionException {
		if (verb) System.err.println("-- Starting code #1 test --");
		Translator t = new Translator();

		List<Command> res = null;
		try {
			res = t.convertCode(Files.readAllLines(Paths.get("sample/", "code1.asm"), Charset.forName("UTF-8")));
		} catch (IOException ioE) {
			throw new ExecutionException("Some error reading the code1.asm file", ioE);
		}

		if (verb) System.err.println("Resulting transaltor: " + t);
		if (verb) System.err.println("Resulting code: " + res);

		if (verb) {
			for(Command cmd : res)
				if(cmd instanceof Directive)
					System.out.println("Directive " + cmd.getClass().getSimpleName() + "!: " + Directive.class.cast(cmd).toCode());
		}

		res = null;
		t = null;
		System.gc();
		System.err.println("-- Code#1 tests are OK --");
	}

	public static void testCode2(Boolean verb) throws ExecutionException {
		if (verb) System.err.println("-- Starting code #2 test --");
		Translator t = new Translator();

		try {
			if (verb) System.err.println(new Translator().convertCode(Files.readAllLines(Paths.get("sample/", "code2.asm"), Charset.forName("UTF-8"))));
		} catch (IOException ioE) {
			throw new ExecutionException("Some error reading the code2.asm file", ioE);
		}

		System.err.println("-- Code#2 tests are OK --");
	}
}
