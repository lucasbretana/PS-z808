package z808.command.instruction;

import z808.Memory;
import z808.command.instruction.Instruction;

import util.NotImplementedException;
import util.ExecutionException;

public class SubCTE extends Instruction {
	public static final int OPCODE = 0X2B;
	public static final int SIZE   = 3;

	private int arg;

	public SubCTE (int address, int value) {
		this(address, null, value);
	}

	public SubCTE (int address, String label, int value) {
		this.size = SubCTE.SIZE;
		this.address = address;
		this.label = label;

		this.arg = value;
		this.code = SubCTE.OPCODE;
		return;
	}

	public Memory exec (Memory mem) throws NotImplementedException, ExecutionException {
		throw new NotImplementedException("TODO");
	}

	@Override
	public String toString() {
		return "2B " + arg;
	}
}
