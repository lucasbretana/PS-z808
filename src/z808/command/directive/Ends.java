package z808.command.directive;

import z808.command.directive.Directive;
import z808.memory.Memory;
import util.NotImplementedException;

public class Ends extends Directive {
	public static String MNEMONIC = "ENDS";
	/**
	 * Creates and Ends
	 * Later this must have an equivalent Segment
	 * @param name the name of the segment
	 */
	public Ends(String name) {
		this.label = new String(name);
		this.size = 0;
	}

	@Override
	public String toCode() { return this.toString(); }

	@Override
	public void exec (Memory mem) throws NotImplementedException {
		throw new NotImplementedException("TODO");
	}

	@Override
	public String toString() {
		return this.label + " " + Ends.MNEMONIC;
	}
}
