package z808.command.instruction;

import z808.memory.Memory;
import z808.command.instruction.Instruction;

import util.NotImplementedException;
import util.ExecutionException;

public class Call extends Instruction {
	public static final int OPCODE = 0xE80000;
	public static final int SIZE   = 3;
	
	public Call () {
		this(null);
	}

	public Call (String label) {
		this.size = SIZE;
		this.label = label;

		this.code = OPCODE;
		return;
	}

	public void exec (Memory mem) throws NotImplementedException, ExecutionException {
	}
	
	public boolean isDefined() {
		return false;
	}
}
