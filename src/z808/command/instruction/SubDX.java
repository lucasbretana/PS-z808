package z808.command.instruction;

import z808.memory.Memory;
import z808.command.instruction.Instruction;

import util.NotImplementedException;
import util.ExecutionException;

public class SubDX extends Instruction {
	public static final int OPCODE = 0X2BC4;
	public static final int SIZE   = 2;
	
	public SubDX (int address) {
		this(address, null);
	}

	public SubDX (int address, String label) {
		this.size = SubDX.SIZE;
		this.address = address;
		this.label = label;

		this.code = SubDX.OPCODE;
		return;
	}

	public void exec (Memory mem) throws NotImplementedException, ExecutionException {
		throw new NotImplementedException("TODO");
	}
}
