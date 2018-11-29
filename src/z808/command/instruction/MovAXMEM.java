package z808.command.instruction;

import z808.memory.Memory;
import z808.command.instruction.Instruction;

import util.NotImplementedException;
import util.ExecutionException;

public class MovAXMEM extends Instruction {
	public static final int OPCODE = 0xB80000;
	public static final int SIZE   = 3;
	
	public MovAXMEM () {
		this(null);
	}

	public MovAXMEM (String label) {
		this.size = MovAXMEM.SIZE;
		this.label = label;

		this.code = MovAXMEM.OPCODE;
		return;
	}

	public void exec (Memory mem) throws NotImplementedException, ExecutionException {
	}
	
	public boolean isDefined() {
		return false;
	}
}
