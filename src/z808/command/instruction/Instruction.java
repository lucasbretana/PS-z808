package z808.command.instruction;

import z808.command.Command;
import util.NotImplementedException;

public abstract class Instruction extends Command {
	/*
	 * The hex representation of this line of code.
	 */
	protected int code;

	public int getOpCode() {
		return this.code;
	}

	public String toString() {
		String ret = "";
		int code = this.code; // Local copy to possible shift
		while (code > 0) {
			ret = String.format("%02X", code % 0x100) + " " + ret;
			code = code >> 8;
		}
		return ret;
	}
}
