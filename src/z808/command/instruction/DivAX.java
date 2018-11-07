package z808.command.instruction;

import z808.memory.Memory;
import z808.command.instruction.Instruction;

import util.NotImplementedException;
import util.ExecutionException;

public class DivAX extends Instruction {
	public static final int OPCODE = 0XF7F0;
	public static final int SIZE   = 2;

	public DivAX () {
		this(null);
	}

	public DivAX (String label) {
		this.size = DivAX.SIZE;
		this.label = label;

		this.code = DivAX.OPCODE;
		return;
	}

	public void exec (Memory mem) throws NotImplementedException, ExecutionException {
		throw new NotImplementedException("TODO");
	}
}
