package z808.command.instruction;

import z808.memory.Memory;
import z808.command.instruction.Instruction;

import util.NotImplementedException;
import util.ExecutionException;

public class Jump extends Instruction {
	public static final int OPCODE = 0xEB0000;
	public static final int SIZE   = 3;
	
	public Jump () {
		this(null);
	}

	public Jump (String label) {
		this.size = Jump.SIZE;
		this.label = label;

		this.code = Jump.OPCODE;
		return;
	}

	public void exec (Memory mem) throws NotImplementedException, ExecutionException {
	}
	
	public boolean isDefined() {
		return false;
	}
}
