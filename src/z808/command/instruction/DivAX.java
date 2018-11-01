package z808.command.instruction;

import z808.Memory;
import z808.command.instruction.Instruction;

import util.NotImplementedException;
import util.ExecutionException;

public class DivAX extends Instruction {
	public static final int OPCODE = 0XF7F0;
	public static final int SIZE   = 2;

	public DivAX (int address) {
		this(address, null);
	}

	public DivAX (int address, String label) {
		this.size = DivAX.SIZE;
		this.address = address;
		this.label = label;

		this.code = DivAX.OPCODE;
		return;
	}

	public Memory exec (Memory mem) throws NotImplementedException, ExecutionException {
		throw new NotImplementedException("TODO");
	}
}
