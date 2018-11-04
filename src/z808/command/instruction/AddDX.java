package z808.command.instruction;

import z808.memory.Memory;
import z808.command.instruction.Instruction;

import util.NotImplementedException;
import util.ExecutionException;

public class AddDX extends Instruction {
	public static final int OPCODE = 0X03C2;
	public static final int SIZE   = 2;

	public AddDX (int address) {
		this(address, null);
	}

	public AddDX (int address, String label) {
		this.size = AddDX.SIZE;
		this.address = address;
		this.label = label;

		this.code = AddDX.OPCODE;
		return;
	}

	public void exec (Memory mem) throws NotImplementedException, ExecutionException {
		throw new NotImplementedException("TODO");
	}
}
