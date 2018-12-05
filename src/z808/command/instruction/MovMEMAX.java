package z808.command.instruction;

import z808.memory.Address;
import z808.memory.Memory;
import z808.command.instruction.Instruction;

import util.NotImplementedException;
import util.ExecutionException;

public class MovMEMAX extends Instruction {
	public static final int OPCODE = 0xA30000;
	public static final int SIZE   = 3;
	
	public Address arg = null;
	private String u_arg = null; // variable name
	
	public MovMEMAX (String label, String call, Address _arg) {
		this.size = SIZE;
		this.code = OPCODE;

		this.label = label;
		this.u_arg = call;
		this.arg = _arg;
	}

	public MovMEMAX (String call) {
		this(null, call, null);
	}

	public MovMEMAX (String call, Address _arg) {
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
