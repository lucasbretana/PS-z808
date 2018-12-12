package z808.command.instruction;

import java.util.ArrayList;

import z808.memory.Register;
import z808.memory.Address;
import z808.memory.Memory;
import z808.command.instruction.Instruction;

import util.AZMRegexCommon;
import util.NotImplementedException;
import util.ExecutionException;

public class Retn extends Instruction {
	public static final int OPCODE = 0xC3;
	public static final String MNEMONIC = "hlt";
	public static final String REGEX = "^(" + AZMRegexCommon.NAME_RGX + " )?" + MNEMONIC + "$";
	public static final int SIZE = 1;
	
	public Retn () {
		this(null);
	}

	public Retn (String label) {
		this.size = SIZE;
		this.label = label;

		this.code = OPCODE;
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
		// 7.1 Set next instruction
		mem.CL.set( mem.SP.get() );
		// 7.2 Set next instruction
		mem.SP.set( mem.SP.get() - 1 );

		// 8. Write back
		// 9. Program Counter increment
	}

	static public Retn makeRetn(String from) throws ExecutionException {
		String []tokens = from.split(" ");
		if (tokens.length < 1) throw new ExecutionException("This doesn't make any sense..mismatching expression");
		String label = (tokens.length == 2) ? tokens[0] : null;

		return new Retn(label);
	}

	@Override
	public ArrayList<Register> asRegisters() {
		ArrayList<Register> l = new ArrayList<Register>(SIZE);
		l.add(new Register(0xC3));
		return l;
	}

}
