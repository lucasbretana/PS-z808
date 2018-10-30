package z808.command;

import z808.Command;
import util.NotImplementedException;

public abstract class Instruction extends Command {
	protected int opCode;
	public int getOpCode() {
		return this.opCode;
	}
}
