package z808.command.directive;

import util.AZMRegexCommon;;
import util.ExecutionException;
import util.NotImplementedException;

import z808.command.directive.Directive;
import z808.memory.Memory;

public class End extends Directive {
	public static final String MNEMONIC = "END";
	public static final String REGEX = "^(" + AZMRegexCommon.NAME_RGX + " )?" + MNEMONIC + " (" + AZMRegexCommon.NAME_RGX + ")$";

	private String entry_point = null;

	/**
	 * Creates an End directive without label
	 * @param entry_point the name of the module
	 */
	public End(String entry_point) {
		this(null, entry_point);
	}
	/**
	 * Creates an End directive without label
	 * @param lbl the label of the command
	 * @param entry_point the name of the module
	 */
	public End(String lbl, String entry_point) {
		this.label = lbl;
		this.size = 0;

		this.entry_point = entry_point;
	}

	public String getEntryPoint() {
		return this.entry_point;
	}

	public static End makeEnd(String from) throws ExecutionException {
	  String []tokens = from.split(" ");

	  if(tokens.length == 2)
	    return new End(tokens[1]);
	  if(tokens.length == 3)
	    return new End(tokens[2]);

	  throw new ExecutionException("Invalid End construction");
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

