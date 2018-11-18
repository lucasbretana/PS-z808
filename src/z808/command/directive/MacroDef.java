package z808.command.directive;

import z808.command.directive.Directive;
import z808.command.Command;
import z808.memory.Memory;
import util.NotImplementedException;

import java.util.List;
import java.util.ArrayList;

/**
 * @author Alana Schwendler
 */
public class MacroDef extends Directive {
	public static String MNEMONIC = "MACRO";
	public String label = null;
	public List<String> parameters = null;
	public List<String> commands = null;

	/**
	 * Creates a new MacroDef
	 * @param name Macro name
	 * @param params List of received parameters
	 * @param cmds List of future macro commands
	 */
	public MacroDef(String name, List<String> params, List<String> cmds) {
		this.label = new String(name);
		this.parameters = new ArrayList<String>(params);
		this.commands = new ArrayList<String>(cmds);
	}

	/**
	 * Creates a new MacroDef without header, used on macro def table
	 * @param name Macro's name
	 * @param cmds Macro's routine
	 */
	public MacroDef(String name, List<String> cmds) {
		this.label = new String(name);
		this.commands = new ArrayList<String>(cmds);
	}

	public List<String> getCommands() {
		return this.commands;
	}

	@Override
	public void exec (Memory mem) throws NotImplementedException {
		throw new NotImplementedException("TODO");
	}

	@Override
	public String toString() {
		String ret;
		ret = this.label + " " + MacroDef.MNEMONIC;
		
		for(String param : parameters) {
			ret += " " + param;
		}

		for(String cmds : commands) {
			ret += " " + cmds;
		}
		return ret;
	}

}