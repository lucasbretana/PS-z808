package z808.command.instruction;

import util.AZMRegexCommon;
import util.NotImplementedException;
import util.ExecutionException;

import java.util.List;
import java.util.ArrayList;

import z808.memory.Register;
import z808.memory.Memory;
import z808.command.instruction.Instruction;

public class AddAX extends Instruction {
	public static final int OPCODE = 0X03C0;
	public static final String MNEMONIC = "add";
	public static final String REGEX = "^(" + AZMRegexCommon.NAME_RGX + " )?" + MNEMONIC + " AX AX$";
	public static final int SIZE   = 2;
	
	public AddAX () {
		this(null);
	}

	public AddAX (String label) {
		this.size = AddAX.SIZE;
		this.label = label;

		this.code = AddAX.OPCODE;
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
		mem.AX.set( mem.AX.get() + mem.AX.get());

		// 8. Write back
		// 9. Program Counter increment
		mem.CL.set( mem.CL.get() + this.getSize() );
	}

	static public AddAX makeAddAX(String from) throws ExecutionException {
		String []tokens = from.split(" ");
		if (tokens.length < 3) throw new ExecutionException("This doesn't make any sense..mismatching expression");

		if (tokens.length == 4) {
			if ( (!tokens[1].equals(MNEMONIC)) || (!tokens[2].equals("AX")) || (!tokens[3].equals("AX")) ) 
				throw new ExecutionException("This doesn't make any sense..mismatching expression, invalid mnemonic or parameter");
			return new AddAX(tokens[0]);
		} else if (tokens.length == 3) {
			if ( (!tokens[0].equals(MNEMONIC)) || (!tokens[1].equals("AX")) || (!tokens[2].equals("AX")) ) 
				throw new ExecutionException("This doesn't make any sense..mismatching expression, invalid mnemonic or parameter");
			return new AddAX();
		}

		throw new ExecutionException("This doesn't make any sense..mismatching expression");
	}

	@Override
	public ArrayList<Register> asRegisters() {
		ArrayList<Register> l = new ArrayList<Register>(AddAX.SIZE);
		l.add(new Register(0x03));
		l.add(new Register(0xc0));
		return l;
	}
}
