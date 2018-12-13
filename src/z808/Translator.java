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
		String cmd = null;
		int sz = raw_code.size();
		for (int i=0;i<sz;++i) {
			cmd = raw_code.get(i);
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
				if (verb) System.err.printf("\nDEBUG, made a %s with \"%s\"", "Extern", cmd);
				c = Extern.makeExtern(cmd);
			} else if (cmd.contains(Public.MNEMONIC)) {
				if (verb) System.err.printf("\nDEBUG, made a %s with \"%s\"", "Public", cmd);
				c = Public.makePublic(cmd);
			} else if (cmd.contains(Segment.MNEMONIC)) {
				if (verb) System.err.printf("\nDEBUG, made a %s with \"%s\"", "Segment", cmd);
				c = Segment.makeSegment(cmd);
			} else if (cmd.contains(Ends.MNEMONIC)) {
				if (verb) System.err.printf("\nDEBUG, made a %s with \"%s\"", "Ends", cmd);
				c = Ends.makeEnds(cmd);
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
			} else if (cmd.matches(MovAXDX.REGEX)) {
				if (verb) System.err.printf("\nDEBUG, made a %s with \"%s\"", "MovAXDX", cmd);
				c = MovAXDX.makeMovAXDX(cmd);
			} else if (cmd.matches(MovDXAX.REGEX)) {
				if (verb) System.err.printf("\nDEBUG, made a %s with \"%s\"", "MovDXAX", cmd);
				c = MovDXAX.makeMovDXAX(cmd);
			} else if (cmd.matches(MovSIAX.REGEX)) {
				if (verb) System.err.printf("\nDEBUG, made a %s with \"%s\"", "MovSIAX", cmd);
				c = MovSIAX.makeMovSIAX(cmd);
			} else if (cmd.matches(MovAXMEM.REGEX)) {
				if (verb) System.err.printf("\nDEBUG, made a %s with \"%s\"", "MovAXMEM", cmd);
				c = MovAXMEM.makeMovAXMEM(cmd);
			} else if (cmd.matches(MovMEMAX.REGEX)) {
				if (verb) System.err.printf("\nDEBUG, made a %s with \"%s\"", "MovMEMAX", cmd);
				c = MovMEMAX.makeMovAXMEM(cmd);
			} else if (cmd.matches(Hlt.REGEX)) {
				if (verb) System.err.printf("\nDEBUG, made a %s with \"%s\"", "Hlt", cmd);
				c = Hlt.makeHlt(cmd);
			} else if (cmd.matches(Call.REGEX)) {
				if (verb) System.err.printf("\nDEBUG, made a %s with \"%s\"", "Call", cmd);
				c = Call.makeCall(cmd);
			} else if (cmd.matches(DivAX.REGEX)) {
				if (verb) System.err.printf("\nDEBUG, made a %s with \"%s\"", "DivAX", cmd);
				c = DivAX.makeDivAX(cmd);	
			} else if (cmd.matches(DivSI.REGEX)) {
				if (verb) System.err.printf("\nDEBUG, made a %s with \"%s\"", "DivSI", cmd);
				c = DivSI.makeDivSI(cmd);
			} else if (cmd.matches(Jump.REGEX)) {
				if (verb) System.err.printf("\nDEBUG, made a %s with \"%s\"", "Jump", cmd);
				c = Jump.makeJump(cmd);
			} else if (cmd.matches(MultAX.REGEX)) {
				if (verb) System.err.printf("\nDEBUG, made a %s with \"%s\"", "MultAX", cmd);
				c = MultAX.makeMultAX(cmd);
			} else if (cmd.matches(MultSI.REGEX)) {
				if (verb) System.err.printf("\nDEBUG, made a %s with \"%s\"", "MultSI", cmd);
				c = MultSI.makeMultSI(cmd);
			} else if (cmd.matches(PopAX.REGEX)) {
				if (verb) System.err.printf("\nDEBUG, made a %s with \"%s\"", "PopAX", cmd);
				c = PopAX.makePopAX(cmd);
			} else if (cmd.matches(PushAX.REGEX)) {
				if (verb) System.err.printf("\nDEBUG, made a %s with \"%s\"", "PushAX", cmd);
				c = PushAX.makePushAX(cmd);
			} else if (cmd.matches(Retn.REGEX)) {
				if (verb) System.err.printf("\nDEBUG, made a %s with \"%s\"", "Retn", cmd);
				c = Retn.makeRetn(cmd);
			} else if (cmd.equals("Inicio:")) {
				if (verb) System.err.printf("\nDEBUG, nothing with \"%s\"", cmd);
				continue;
			} else if (cmd.contains(MacroCall.MNEMONIC)) {
				if (verb) System.err.printf("\nDEBUG, made a %s with \"%s\"", "MacroCall", cmd);
				c = MacroCall.makeMacroCall(cmd);
			} else if (cmd.contains(MacroDef.MNEMONIC)) {
				if (verb) System.err.printf("\nDEBUG, made a %s with \"%s\"", "MacroDef", cmd);
				List<String> param = new ArrayList<>();
				String []tokens = cmd.split(" ");
				String name = tokens[0];

				for(int z=2;z<tokens.length;++z)
					param.add(tokens[z]);

				int j = i+1; // current position is safe
				String next = "";
				List<String> l = new ArrayList<>();
				while(true) {
					next = raw_code.get(j);
					if(next.equalsIgnoreCase(Endm.MNEMONIC)) break;

					l.add(next);
					++j;
				}

				i = j;
				c = new MacroDef(name, param, l);

			} else {
				System.out.println("\nTODO: command string \"" + cmd + "\"");
				throw new NotImplementedException("TODO: command string \"" + cmd + "\"");
			}

			if (c == null) throw new NullPointerException("Could not create a valid command");
			output.add(c);
			c = null;
		}
		return output;
	}

	public Command convertCode1(String raw_code) throws ExecutionException {
		List<String> l = new ArrayList<>(1);
		l.add(raw_code);
		return this.convertCode(l).get(0);
	}

	public static void testTranslator(boolean verb) throws ExecutionException {
		//testCode("code1.asm", verb);
		//testCode("code2.asm", verb);
		testCode("ex1_numbers.asm", verb);
		testCode("ex2_swap.asm", verb);
		testCode("ex3_macro.asm", verb);
	}

	public static void testCode(String code, Boolean verb) throws ExecutionException {
		if (verb) System.err.println("-- Starting " + code + " test --");
		Translator t = new Translator();

		List<Command> res = null;
		try {
			res = t.convertCode(Files.readAllLines(Paths.get("sample/", code), Charset.forName("UTF-8")));
		} catch (IOException ioE) {
			throw new ExecutionException("Some error reading the " + code + " file", ioE);
		}

		if (verb) {
			for(Command cmd : res)
				if(cmd instanceof Directive)
					System.err.println("Directive " + cmd.getClass().getSimpleName() + "!: " + Directive.class.cast(cmd).toCode());
				else if(cmd instanceof Instruction)
					System.err.println("Instruction " + cmd.getClass().getSimpleName() + "!: " + Instruction.class.cast(cmd).toString());
		}

		if (verb) System.err.println("Sub test, START testing Assembler");
		Module m = new z808.Assembler().assembleCode(res);
		if (verb) System.err.println("\nPrinting module\n" + m);
		if (verb) System.err.println("Sub test, ENDED testing Assembler");

		res = null;
		t = null;
		System.gc();
		System.err.println("-- " + code +" tests are OK --");
	}

}
