package z808.command.directive;

import z808.command.directive.Directive;
import z808.memory.Memory;
import util.NotImplementedException;

//END MACRO
public class Endm extends Directive {
	public static String MNEMONIC = "ENDM";
	
	/**
	 * Create a ENDM 
	 * @param name macro name
	 */
	public Endm() {
		this.size = 0;
	}

	@Override
	public String toCode() {
	  return MNEMONIC;
	}

	@Override
	public void exec (Memory mem) throws NotImplementedException {
		throw new NotImplementedException("TODO");
	}

	@Override
	public String toString() {
		return this.label + " " + Endm.MNEMONIC;
	}
}
