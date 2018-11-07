package z808.command.instruction;

import z808.memory.Memory;
import z808.command.instruction.Instruction;

import util.NotImplementedException;
import util.ExecutionException;

public class DivSI extends Instruction {
	public static final int OPCODE = 0XF7F6;
	public static final int SIZE   = 2;

	public DivSI () {
		this(null);
	}

	public DivSI (String label) {
		this.size = DivSI.SIZE;
		this.label = label;

		this.code = DivSI.OPCODE;
		return;
	}

	public void exec (Memory mem) throws NotImplementedException, ExecutionException {
		throw new NotImplementedException("TODO");
	}
}
