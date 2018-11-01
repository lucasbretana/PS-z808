package z808.command.instruction;

import z808.Memory;
import z808.command.instruction.Instruction;

import util.NotImplementedException;
import util.ExecutionException;

public class AddAX extends Instruction {
	public static final int OPCODE = 0X03C0;
	public static final int SIZE   = 2;
	
	public AddAX (int address) {
		this(address, null);
	}

	public AddAX (int address, String label) {
		this.size = AddAX.SIZE;
		this.address = address;
		this.label = label;

		this.code = AddAX.OPCODE;
		return;
	}

	public Memory exec (Memory mem) throws NotImplementedException, ExecutionException {
		throw new NotImplementedException("TODO");
	}
}
