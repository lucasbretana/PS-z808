package z808.command.directive;

import z808.command.directive.Directive;
import z808.memory.Memory;
import util.NotImplementedException;

import java.util.List;
import java.util.ArrayList;

/**
 * @author Alana Schwendler
 */
public class Macro extends Directive {
	public static String MNEMONIC = "MACRO";
	public List<String> parameters = null;
	public List<String> commands = null;

	/**
	 * Creates a new Macro
	 * @param name Macro name
	 * @param params List of received parameters
	 * @param cmds List of future macro commands
	 */
	public Macro(String name, List<String> params, List<String> cmds) {
		this.label = new String(name);
		this.parameters = new ArrayList(params);
		this.commands = new ArrayList(cmds);
	}

	@Override
	public void exec (Memory mem) throws NotImplementedException {
		throw new NotImplementedException("TODO");
	}

	@Override
	public String toString() {
		return this.label + " " + Macro.MNEMONIC + " " + this.parameters;
	}

}
