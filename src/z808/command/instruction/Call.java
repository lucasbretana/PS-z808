package z808.command.instruction;

import java.util.ArrayList;

import z808.memory.Register;
import z808.memory.Address;
import z808.memory.Memory;
import z808.command.instruction.Instruction;

import util.NotImplementedException;
import util.ExecutionException;

public class Call extends Instruction {
	public static final int OPCODE = 0xE8;
	public static final int SIZE   = 3;

	public Address arg = null;  // the linker will substitute the function name for an address that will be in some module's global symbol table
	private String u_arg = null; // the function name

	public Call (String label, String call, Address _arg) {
		this.size = SIZE;
		this.code = OPCODE;

		this.label = label;
		this.u_arg = call;
		this.arg = _arg;
	}

	public Call (String call) {
		this(null, call, null);
	}

	public Call (String call, Address _arg) {
		this(null, call, _arg);
	}

	@Override
	public boolean isDefined() {
		return this.arg != null;
	}

	@Override
	public String getUndefValue() {
		return this.u_arg;
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
		// 7.1 Push the next instruction
		mem.modifyMemory(mem.SP, mem.CL.get() + this.getSize());
		mem.SP.set( mem.SP.get() + 1 );
		// 7.2 Set next instruction
		mem.CL.set( this.arg );

		// 8. Write back
		// 9. Program Counter increment
	}

	@Override
	public String toString() {
		return "E8 " + ((this.isDefined()) ? arg : this.u_arg);
	}

	@Override
	public ArrayList<Register> asRegisters() {
		ArrayList<Register> l = new ArrayList<Register>(AddCTE.SIZE);
		l.add(new Register(0xE8));
		l.add(new Register(this.arg.intValue() >> 16));
		l.add(new Register((this.arg.intValue() << 16) >> 16));
		return l;
	}
}
