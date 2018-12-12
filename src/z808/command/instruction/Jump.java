package z808.command.instruction;

import z808.memory.Address;
import z808.memory.Memory;
import z808.command.instruction.Instruction;

import util.NotImplementedException;
import util.ExecutionException;

public class Jump extends Instruction {
	public static final int OPCODE = 0xEB0000;
	public static final int SIZE   = 3;
	
	public Address arg = null;  // the linker will substitute the function name for an address that will be in some module's global symbol table
	private String u_arg = null; // the function name

	public Jump (String label, String call, Address _arg) {
		this.size = SIZE;
		this.code = OPCODE;

		this.label = label;
		this.u_arg = call;
		this.arg = _arg;
	}

	public Jump (String call) {
		this(null, call, null);
	}

	public Jump (String call, Address _arg) {
		this(null, call, _arg);
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
