package z808.command.directive;

import z808.command.directive.Directive;
import z808.memory.Memory;
import util.ExecutionException;

public class Ends extends Directive {
	public static String MNEMONIC = "ENDS";
	public static String REGEX = "(STACK|CODE|DATA) " + MNEMONIC;

	public String name = null;

	/**
	 * Creates and Ends
	 * Later this must have an equivalent Segment
	 * @param name the name of the segment
	 */
	public Ends(String name) {
		this.size = 0;
		this.name = name;
	}

	public static Ends makeEnds(String from) throws ExecutionException{
		String []tokens = from.split(" ");

		if ( (!tokens[0].equals("CODE")) && (!tokens[0].equals("STACK")) && (!tokens.equals("DATA")) )
			throw new ExecutionException("Invalid Ends name \"" + tokens[0] + "\"");
		
		return new Ends(tokens[0]);
	}

	@Override
	public String toCode() { return this.toString(); }

	@Override
	public void exec (Memory mem) throws ExecutionException {
		throw new ExecutionException("Ends should not execute");
	}

	@Override
	public String toString() {
		return this.label + " " + Ends.MNEMONIC;
	}
}
