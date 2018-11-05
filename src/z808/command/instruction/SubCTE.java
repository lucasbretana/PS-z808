package z808.command.instruction;

import z808.memory.Memory;
import z808.memory.Address;
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

	public void exec (Memory mem)
		throws NotImplementedException, ExecutionException {
		// 1. Intruction Fetch
		mem.REM.set(this.getAddress());
		// 2. Decode
		mem.RBM.set(this.code % 0x100);
		mem.RI.set(mem.RBM);
		// 3. Arg fetch
		mem.REM.set(this.arg);
		// 4. Value fetch in case of address
		mem.RBM.set(mem.get(mem.REM));
		// 5. Second arg fetch
		// 6. Second value fetch in case of address

		// 7. Execution
		mem.AX.set( mem.AX.get() - mem.RBM.get() );

		// 8. Write back
		// 9. Program Counter increment
		mem.CL.set( mem.CL.get() + this.getSize() );
	}

	@Override
	public String toString() {
		return "2B " + arg;
	}
}
