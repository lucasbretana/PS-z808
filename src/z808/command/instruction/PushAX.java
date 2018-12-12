package z808.command.instruction;

import java.util.ArrayList;

import z808.memory.Register;
import z808.memory.Address;
import z808.memory.Memory;
import z808.command.instruction.Instruction;

import util.NotImplementedException;
import util.ExecutionException;

public class PushAX extends Instruction {
	public static final int OPCODE = 0x50;
	public static final int SIZE   = 1;

	public PushAX () {
		this(null);
	}

	public PushAX (String label) {
		this.size = PushAX.SIZE;
		this.label = label;

		this.code = PushAX.OPCODE;
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
		mem.modifyMemory(mem.SP, mem.AX.get());
		mem.SP.set( mem.SP.get() + 1 );

		// 8. Write back
		// 9. Program Counter increment
		mem.CL.set( mem.CL.get() + this.getSize() );
	}

	@Override
	public ArrayList<Register> asRegisters() {
		ArrayList<Register> l = new ArrayList<Register>(SIZE);
		l.add(new Register(0x50));
		return l;
	}
}
