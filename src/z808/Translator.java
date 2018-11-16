package z808;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

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
	public static final String NAME_RGX = "([\\_]|[\\?]|[\\$]|[\\@])([a-zA-Z_0-9]*)";

	private ArrayList<String> code = null;

	private static final String EquRegEx = NAME_RGX + "\\s" + Equ.MNEMONIC + "\\s" + NAME_RGX;

	/**
	 * Creates a translator for a given list code
	 * @param expanded_code the code the translated
	 */
	public Translator(List<String> expanded_code) throws Exception {
		this.code = new ArrayList<String>(expanded_code);
	}

	public List<Command> convert() throws ExecutionException {
		ArrayList<Command> output = new ArrayList<Command>();

		for (String cmd : this.code) {
			if (cmd.matches(EquRegEx)) {
				System.err.println("Name: " + cmd.split(" ")[0] + " value: " + cmd.split(" ")[2]);
				output.add(new Equ("", 0));
			} else {
				throw new NotImplementedException("TODO");
			}
		}

		return output;
	}

	public static void main(String...args) throws Exception {
		try {
			List<String> l = Arrays.asList("test0 EQU 10", "test1 EQU 11");

			System.out.println("list " + l);
			new Translator(l).convert();
		} catch (Exception e) {
			e.printStackTrace();
			System.err.println("\n" + e.getMessage());
		}
	}

}
