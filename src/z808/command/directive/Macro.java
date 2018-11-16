package z808.command.directive;

import z808.command.directive.Directive;
import util.NotImplementedException;

public class Macro extends Directive {
	public static String MNEMONIC = "MACRO";
	public List<String> parameters = null;
	public List<String> commands = null;

	/*
	 * Creates a new Macro
	 * @param name Macro name
	 * @param params List of received parameters
	 * @param cmds List of future macro commands
	 */
	public Macro(String name, List<String> params, List<String> cmds) {
		this.label = new String(name);
		this.parameters = params;
		this.commands = cmds;
	}

	@Override
	public void exec(Memory mem) throws new NotImplementedException, ExecutionException {
		throw new NotImplementedException("TODO");
	}

	@Override
	public String toString() {
		return this.label + " " + Macro.MNEMONIC;
	}

}
