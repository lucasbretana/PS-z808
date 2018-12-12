package z808.command.instruction;

import java.util.ArrayList;

import z808.memory.Register;
import z808.memory.Address;
import z808.memory.Memory;
import z808.command.instruction.Instruction;

import util.NotImplementedException;
import util.ExecutionException;

public class MovDXAX extends Instruction {
	public static final int OPCODE = 0x8BD0;
	public static final int SIZE   = 2;

	public MovDXAX () {
		this(null);
	}

	public MovDXAX (String label) {
		this.size = MovDXAX.SIZE;
		this.label = label;

		this.code = MovDXAX.OPCODE;
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
		// 4. Value fetch in case of address
		// 5. Second arg fetch
		// 6. Second Value fetch in case of address

		// 7. Execution
		mem.DX.set( mem.AX.get() );

		// 8. Write back
		// 9. Program Counter increment
		mem.CL.set( mem.CL.get() + this.getSize() );
	}

	@Override
	public ArrayList<Register> asRegisters() {
		ArrayList<Register> l = new ArrayList<Register>(SIZE);
		l.add(new Register(0x8B));
		l.add(new Register(0xD0));
		return l;
	}
}
