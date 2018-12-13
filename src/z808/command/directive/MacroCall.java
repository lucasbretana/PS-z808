package z808.command.directive;

import z808.command.directive.Directive;
import z808.memory.Memory;
import util.NotImplementedException;
import util.ExecutionException;
import util.AZMRegexCommon;

import java.util.List;
import java.util.ArrayList;

/**
 * @author Alana Schwendler
 */
public class MacroCall extends Directive {
	public List<String> parameters = null;
	public static final String MNEMONIC = "MCALL";
	public static final String REGEX = "^(" + AZMRegexCommon.NAME_RGX + " )" + MNEMONIC + " (" + AZMRegexCommon.NAME_RGX + ")*$";

	/**
	 * Creates a new MacroCall
	 * @param label Macro name
	 * @param params List of received parameters
	 */
	public MacroCall(String name, List<String> params) {
		super.label = new String(name);
		this.parameters = new ArrayList<String>(params);
	}

	@Override
	public String toCode() {
	  return MNEMONIC + " " + AZMRegexCommon.toString(this.parameters, ", ");
	}

	@Override
	public void exec (Memory mem) throws NotImplementedException {
		throw new NotImplementedException("TODO");
	}

	static public MacroCall makeMacroCall(String from) throws ExecutionException {
		String []tokens = from.split(" ");
		String name = tokens[0];
		List<String> params = new ArrayList<String>();

		int i;
		for(i = 2; i < tokens.length; i++) {
			params.add(tokens[i]);
		}

		return new MacroCall(name, params);
	}

	@Override
	public String toString() {
		String ret = this.label;
		
		for(String param : parameters) {
			ret += " " + param;
		}
		return ret;
	}

}
