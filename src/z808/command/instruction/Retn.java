package z808.command.instruction;

import z808.memory.Memory;
import z808.command.instruction.Instruction;

import util.NotImplementedException;
import util.ExecutionException;

public class Retn extends Instruction {
	public static final int OPCODE = 0xC3;
	public static final int SIZE   = 1;
	
	public Retn () {
		this(null);
	}

	public Retn (String label) {
		this.size = SIZE;
		this.label = label;

		this.code = OPCODE;
		return;
	}

	public void exec (Memory mem) throws NotImplementedException, ExecutionException {
	}
}
