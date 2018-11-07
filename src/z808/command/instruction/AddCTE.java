package z808.command.instruction;

import z808.memory.Memory;
import z808.memory.Address;
import z808.command.instruction.Instruction;

import util.NotImplementedException;
import util.ExecutionException;
import util.TooLongValue;

public class AddCTE extends Instruction {
	public static final int OPCODE = 0X05;
	public static final int SIZE   = 3;

	private Address arg ;

	public AddCTE (Address value) throws TooLongValue {
		this(null, value);
	}
	public AddCTE (int value)
		throws ExecutionException, TooLongValue {
		this(null, value);
	}
	public AddCTE (String label, int value)
		throws ExecutionException, TooLongValue {
		this(label, new Address(value));
	}

	public AddCTE (String label, Address value) throws TooLongValue {
		if (value.intValue() >= 0xFFFF)
			throw new TooLongValue (value);
		this.size = AddCTE.SIZE;
		this.label = label;

		this.arg = value;
		this.code = AddCTE.OPCODE;
		return;
	}

	public void exec (Memory mem)
		throws NotImplementedException, ExecutionException {
		// 1. Intruction Fetch
		mem.REM.set(mem.CL);
		// 2. Decode
		mem.RBM.set(this.code % 0x100);
		mem.RI.set(mem.RBM);
		// 3. Arg fetch
		mem.REM.set(this.arg);
		// 4. Value fetch in case of address
		mem.RBM.set(mem.get(mem.REM));
		// 5. Second arg fetch
		// 6. Second Value fetch in case of address

		// 7. Execution
		mem.AX.set( mem.AX.get() + mem.RBM.get() );

		// 8. Write back
		// 9. Program Counter increment
		mem.CL.set( mem.CL.get() + this.getSize() );
	}

	@Override
	public String toString() {
		return "05 " + arg;
	}
}
