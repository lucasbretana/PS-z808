package z808.command.instruction;

import z808.command.Command;
import util.NotImplementedException;

public abstract class Instruction extends Command {
	protected int opCode;
	public int getOpCode() {
		return this.opCode;
	}
}
