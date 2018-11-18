package z808.command.directive;

import z808.command.directive.Directive;
import z808.memory.Memory;
import util.NotImplementedException;

import java.util.List;
import java.util.ArrayList;

/**
 * @author Alana Schwendler
 */
public class MacroCall extends Directive {
	public String label = null;
	public List<String> parameters = null;

	/**
	 * Creates a new MacroCall
	 * @param label Macro name
	 * @param params List of received parameters
	 */
	public MacroCall(String name, List<String> params) {
		this.label = new String(name);
		this.parameters = new ArrayList<String>(params);
	}

	@Override
	public void exec (Memory mem) throws NotImplementedException {
		throw new NotImplementedException("TODO");
		//change parameters [?]
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