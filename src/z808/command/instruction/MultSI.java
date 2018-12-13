package z808.command.instruction;

import java.util.ArrayList;

import z808.memory.Memory;
import z808.memory.Register;
import z808.command.instruction.Instruction;

import util.AZMRegexCommon;
import util.NotImplementedException;
import util.ExecutionException;

public class MultSI extends Instruction {
	public static final int OPCODE = 0XF7E6;
	public static final String MNEMONIC = "mul";
	public static final String REGEX = "^(" + AZMRegexCommon.NAME_RGX + " )?" + MNEMONIC + " SI$";
	public static final int SIZE = 2;

	public MultSI () {
		this(null);
	}

	public MultSI (String label) {
		this.size = MultSI.SIZE;
		this.label = label;

		this.code = MultSI.OPCODE;
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
		// 6. Second value fetch in case of address
		// 7. Execution
		mem.DX.set( (mem.AX.get() * mem.SI.get()) / 0x10000 );
		mem.AX.set( (mem.AX.get() * mem.SI.get()) % 0x10000 );

		// 8. Write back
		// 9. Program Counter increment
		mem.CL.set( mem.CL.get() + this.getSize() );
	}

	static public MultSI makeMultSI(String from) throws ExecutionException {
		String []tokens = from.split(" ");
		if (tokens.length < 2) throw new ExecutionException("This doesn't make any sense..mismatching expression");
		String label = (tokens.length == 3) ? tokens[0] : null;

		return new MultSI(label);
	}

	@Override
	public ArrayList<Register> asRegisters() {
		ArrayList<Register> l = new ArrayList<Register>(SIZE);
		l.add(new Register(0xF7));
		l.add(new Register(0xE0));
		return l;
	}
}
