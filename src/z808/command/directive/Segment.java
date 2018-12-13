package z808.command.directive;

import z808.command.directive.Directive;
import z808.memory.Memory;
import util.ExecutionException;

public class Segment extends Directive {
	public static String MNEMONIC = "SEGMENT";
	public static String REGEX = "(STACK|CODE|DATA) " + MNEMONIC;

	public String NAME = null;
	/**
	 * Creates and Segment
	 * Later this must have an equivalent Ends
	 * @param name the name of the segment
	 */
	public Segment(String name) {
		this.label = new String(name);
		this.size = 0;
	}

	public static Segment makeSegment(String from) throws ExecutionException{
		String []tokens = from.split(" ");

		if ( (!tokens[0].equals("CODE")) && (!tokens[0].equals("STACK")) && (!tokens.equals("DATA")) )
			throw new ExecutionException("Invalid Segment name \"" + tokens[0] + "\"");
		
		return new Segment(tokens[0]);
	}

	@Override
	public void exec (Memory mem) throws ExecutionException {
		throw new ExecutionException("Segment should never execute");
	}

	@Override
	public String toCode(){ return this.toString(); }

	@Override
	public String toString() {
		return this.label + " " + Segment.MNEMONIC;
	}
}
