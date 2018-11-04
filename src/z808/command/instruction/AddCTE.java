package z808.command.instruction;

import z808.memory.Memory;
import z808.Address;
import z808.command.instruction.Instruction;

import util.NotImplementedException;
import util.ExecutionException;

public class AddCTE extends Instruction {
	public static final int OPCODE = 0X05;
	public static final int SIZE   = 3;

	private Address arg ;

	public AddCTE (int address, Address value) {
		this(address, null, value);
	}
	public AddCTE (int address, int value)
		throws ExecutionException {
		this(address, null, value);
	}
	public AddCTE (int address, String label, int value)
		throws ExecutionException {
		this(address, label, new Address(value));
	}

	public AddCTE (int address, String label, Address value) {
		this.size = AddCTE.SIZE;
		this.address = address;
		this.label = label;

		this.arg = value;
		this.code = AddCTE.OPCODE;
		return;
	}

	public void exec (Memory mem) throws NotImplementedException, ExecutionException {
		throw new NotImplementedException("TODO");
	}

	@Override
	public String toString() {
		return "05 " + arg;
	}
}
