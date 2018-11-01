package z808.command.instruction;

import z808.Memory;
import z808.command.instruction.Instruction;

import util.NotImplementedException;
import util.ExecutionException;

public class SubAX extends Instruction {
	public static final int OPCODE = 0X2BC0;
	public static final int SIZE   = 2;
	
	public SubAX (int address) {
		this(address, null);
	}

	public SubAX (int address, String label) {
		this.size = SubAX.SIZE;
		this.address = address;
		this.label = label;

		this.code = SubAX.OPCODE;
		return;
	}

	public Memory exec (Memory mem) throws NotImplementedException, ExecutionException {
		throw new NotImplementedException("TODO");
	}
}