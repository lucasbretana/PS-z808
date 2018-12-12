package z808.command.instruction;

import java.util.ArrayList;

import z808.memory.Register;
import z808.memory.Address;
import z808.memory.Memory;
import z808.command.instruction.Instruction;

import util.AZMRegexCommon;
import util.NotImplementedException;
import util.ExecutionException;

public class PopAX extends Instruction {
	public static final int OPCODE = 0x58;
	public static final String MNEMONIC = "pop";
	public static final String REGEX = "^(" + AZMRegexCommon.NAME_RGX + " )?" + MNEMONIC + " AX$";
	public static final int SIZE = 1;

	public PopAX () {
		this(null);
	}

	public PopAX (String label) {
		this.size = PopAX.SIZE;
		this.label = label;

		this.code = PopAX.OPCODE;
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
		mem.AX.set(mem.get( mem.SP ));
		mem.SP.set( mem.SP.get() - 1 );

		// 8. Write back
		// 9. Program Counter increment
		mem.CL.set( mem.CL.get() + this.getSize() );
	}

	static public PopAX makePopAX(String from) throws ExecutionException {
		String []tokens = from.split(" ");
		if (tokens.length < 2) throw new ExecutionException("This doesn't make any sense..mismatching expression");
		String label = (tokens.length == 3) ? tokens[0] : null;

		return new PopAX(label);
	}

	@Override
	public ArrayList<Register> asRegisters() {
		ArrayList<Register> l = new ArrayList<Register>(SIZE);
		l.add(new Register(0x58));
		return l;
	}
}
