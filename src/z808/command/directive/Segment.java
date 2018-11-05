package z808.command.directive;

import z808.command.directive.Directive;
import z808.memory.Memory;
import util.NotImplementedException;

public class Segment extends Directive {
	public static String MNEMONIC = "SEGMENT";
	/**
	 * Creates and Segment
	 * Later this must have an equivalent Ends
	 * @param name the name of the segment
	 */
	public Segment(String name) {
		this.label = new String(name);
		this.address = null;
		this.size = 0;
	}

	@Override
	public void exec (Memory mem) throws NotImplementedException {
		throw new NotImplementedException("TODO");
	}

	@Override
	public String toString() {
		return this.label + " " + Segment.MNEMONIC;
	}
}