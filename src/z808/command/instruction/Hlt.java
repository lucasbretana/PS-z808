package z808.command.instruction;

import z808.memory.Memory;
import z808.memory.Address;
import z808.command.instruction.Instruction;

import util.NotImplementedException;
import util.ExecutionException;
import util.FinishedException;

public class Hlt extends Instruction {
	public static final int OPCODE = 0XF4;
	public static final int SIZE   = 1;
	
	public Hlt (Address address) {
		this(address, null);
	}

	public Hlt (Address address, String label) {
		this.size = Hlt.SIZE;
		this.address = address;
		this.label = label;

		this.code = Hlt.OPCODE;
		return;
	}

	public void exec (Memory mem)
		throws NotImplementedException, ExecutionException, FinishedException {
		// 1. Intruction Fetch
		mem.REM.set(this.getAddress());
		// 2. Decode
		mem.RBM.set(this.code % 0x100);
		mem.RI.set(mem.RBM);
		// 3. Arg fetch
		// 4. Value fetch in case of address
		// 5. Second arg fetch
		// 6. Second value fetch in case of address

		// 7. Execution

		// 8. Write back
		// 9. Program Counter increment
		mem.CL.set( mem.CL.get() + this.getSize() );

		throw new FinishedException("HLT INSTRUCTION");
	}
}
