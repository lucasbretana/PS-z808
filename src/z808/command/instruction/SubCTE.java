package z808.command.instruction;

import z808.memory.Memory;
import z808.Address;
import z808.command.instruction.Instruction;

import util.NotImplementedException;
import util.ExecutionException;

public class SubCTE extends Instruction {
	public static final int OPCODE = 0X2B;
	public static final int SIZE   = 3;

	private Address arg;

	public SubCTE (int address, Address value) {
		this(address, null, value);
	}
	public SubCTE (int address, int value)
		throws ExecutionException {
		this(address, null, value);
	}
	public SubCTE (int address, String label, int value)
		throws ExecutionException {
		this(address, label, new Address(value));
	}

	public SubCTE (int address, String label, Address value) {
		this.size = SubCTE.SIZE;
		this.address = address;
		this.label = label;

		this.arg = value;
		this.code = SubCTE.OPCODE;
		return;
	}

	public void exec (Memory mem) throws NotImplementedException, ExecutionException {
		throw new NotImplementedException("TODO");
	}

	@Override
	public String toString() {
		return "2B " + arg;
	}
}
