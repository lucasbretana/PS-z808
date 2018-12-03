package z808.command.instruction;

import z808.memory.Address;
import z808.memory.Memory;
import z808.command.instruction.Instruction;

import util.NotImplementedException;
import util.ExecutionException;

public class Call extends Instruction {
	public static final int OPCODE = 0xE80000;
	public static final int SIZE   = 3;

	private Address arg = null;  // the linker will substitute the function name for an address that will be in some module's global symbol table
	private String u_arg = null; // the function name

	public Call (String label, String call) {
		this.size = SIZE;
		this.code = OPCODE;

		this.label = label;
		this.u_arg = call;
	}

	public Call (String call) {
		this(null, call);
	}

	@Override
	public void exec (Memory mem) throws NotImplementedException, ExecutionException {
	}
	
	@Override
	public boolean isDefined() {
		return this.arg != null;
	}

	@Override
	public String getUndefValue() {
		return this.u_arg;
	}
}
