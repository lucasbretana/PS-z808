package z808.command.instruction;

import z808.memory.Memory;
import z808.command.instruction.Instruction;

import util.NotImplementedException;
import util.ExecutionException;

public class SubDX extends Instruction {
	public static final int OPCODE = 0X2BC4;
	public static final int SIZE   = 2;
	
	public SubDX (int address) {
		this(address, null);
	}

	public SubDX (int address, String label) {
		this.size = SubDX.SIZE;
		this.address = address;
		this.label = label;

		this.code = SubDX.OPCODE;
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
		// 4. Arg fetch in case of address
		// 5. Second arg fetch
		// 6. Second arg fetch in case of address

		// 7. Execution
		mem.AX.set( mem.AX.get() - mem.DX.get());

		// 8. Write back
		// 9. Program Counter increment
		mem.CL.set( mem.CL.get() + this.getSize() );
	}
}
