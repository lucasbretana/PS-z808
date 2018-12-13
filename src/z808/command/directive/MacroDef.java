package z808.command.directive;

import java.util.List;
import java.util.ArrayList;

import util.AZMRegexCommon;
import util.NotImplementedException;

import z808.command.directive.Directive;
import z808.command.Command;
import z808.memory.Memory;

/**
 * @author Alana Schwendler
 */
public class MacroDef extends Directive {
	public static String MNEMONIC = "MACRO";
	public List<String> parameters = null;
	public List<String> commands = null;

	/**
	 * Creates a new MacroDef
	 * @param name Macro name
	 * @param params List of received parameters
	 * @param cmds List of future macro commands
	 */
	public MacroDef(String name, List<String> params, List<String> cmds) {
		super.label = new String(name);
		this.parameters = new ArrayList<String>(params);
		this.commands = new ArrayList<String>(cmds);
	}

	public List<String> getCommands() {
		return this.commands;
	}

	public List<String> getParameters() {
		return this.parameters;
	}

	@Override
	public String toCode() {
	  String ret = "";
	  ret += MNEMONIC + " " + this.getLabel() + " " + AZMRegexCommon.toString(this.parameters, ", ") + "\n";
	  ret += AZMRegexCommon.toString(this.commands, "\n");

	  return ret;
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
		//ret += "\n";

		for(String cmds : commands) {
			ret += "\n" + cmds;
		}
		return ret;
	}

}
