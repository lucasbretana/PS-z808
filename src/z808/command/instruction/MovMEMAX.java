package z808.command.instruction;

import z808.memory.Memory;
import z808.command.instruction.Instruction;

import util.NotImplementedException;
import util.ExecutionException;

public class MovMEMAX extends Instruction {
	public static final int OPCODE = 0xA30000;
	public static final int SIZE   = 3;
	
	public MovMEMAX () {
		this(null);
	}

	public MovMEMAX (String label) {
		this.size = MovMEMAX.SIZE;
		this.label = label;

		this.code = MovMEMAX.OPCODE;
		return;
	}

	public void exec (Memory mem) throws NotImplementedException, ExecutionException {
	}
}
