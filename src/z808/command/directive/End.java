package z808.command.directive;

import util.ExecutionException;
import util.NotImplementedException;
import z808.command.directive.Directive;
import z808.Address;
import z808.memory.Memory;

public class End extends Directive {
	public static final String MNEMONIC = "END";

	private String entry_point = null;

	/**
	 * Creates an End directive without label
	 * @param a the addres of this directive
	 * @param entry_point the name of the module
	 */
	public End(Address a, String entry_point) {
		this(null, a, entry_point);
	}
	/**
	 * Creates an End directive without label
	 * @param lbl the label of the command
	 * @param a the addres of this directive
	 * @param entry_point the name of the module
	 */
	public End(String lbl, Address a, String entry_point) {
		this.label = lbl;
		this.address = a.intValue();
		this.size = 0;

		this.entry_point = entry_point;
	}

	@Override
	public void exec (Memory mem) throws NotImplementedException {
		throw new NotImplementedException("TODO");
	}

	@Override
	public String toString() {
		return ((this.label != null) ? (this.label + " ") : "") + End.MNEMONIC + " " + this.entry_point;
	}
}

