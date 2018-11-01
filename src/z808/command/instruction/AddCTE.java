package z808.command.instruction;

import z808.Memory;
import z808.command.instruction.Instruction;

import util.NotImplementedException;
import util.ExecutionException;

public class AddCTE extends Instruction {
	public static final int OPCODE = 0X05;
	public static final int SIZE   = 3;

	private int arg;

	public AddCTE (int address, int value) {
		this(address, null, value);
	}

	public AddCTE (int address, String label, int value) {
		this.size = AddCTE.SIZE;
		this.address = address;
		this.label = label;

		this.arg = value;
		this.code = AddCTE.OPCODE;
		return;
	}

	public Memory exec (Memory mem) throws NotImplementedException, ExecutionException {
		throw new NotImplementedException("TODO");
	}

	@Override
	public String toString() {
		return "05 " + arg;
	}
}
