package z808.command.instruction;

import java.util.ArrayList;

import z808.memory.Memory;
import z808.memory.Address;
import z808.memory.Register;
import z808.command.instruction.Instruction;

import util.NotImplementedException;
import util.ExecutionException;
import util.TooLongValue;

public class AddCTE extends Instruction {
	public static final String MNEMONIC = "AddCTE";
	public static final int OPCODE = 0X05;
	public static final int SIZE   = 3;

	private Address arg;
	private String u_arg = null;
	private boolean defined = false;

	public AddCTE (String value) {
		this(null, value);
	}
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

	public AddCTE (String label, String value) {
		this.size = AddCTE.SIZE;
		this.label = label;

		this.u_arg = value;
		this.code = AddCTE.OPCODE;
	}

	public AddCTE (String label, Address value) throws TooLongValue {
		if (value.intValue() >= 0xFFFF)
			throw new TooLongValue (value);
		this.size = AddCTE.SIZE;
		this.label = label;

		this.arg = value;
		this.code = AddCTE.OPCODE;
		this.defined = true;
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
	public String getUndefValue() {
		return this.u_arg;
	}

	@Override
	public boolean isDefined() {
		return this.defined;
	}

	@Override
	public String toString() {
		if (this.defined)
			return "05 " + arg;
		else
			return AddCTE.MNEMONIC + " " + this.u_arg;
	}

	@Override
	public ArrayList<Register> asRegisters() {
		ArrayList<Register> l = new ArrayList<Register>(AddCTE.SIZE);
		l.add(new Register(0x05));
		l.add(new Register(this.arg.intValue() >> 16));
		l.add(new Register((this.arg.intValue() << 16) >> 16));
		return l;
	}
}
