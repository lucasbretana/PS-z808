package z808.command.instruction;

import z808.command.Instruction;

import util.NotImplementedException;

public class Sub extends Instruction {
  protected int arg1;
  protected int arg2;
	
	public Sub (int address, int arg1, int ag2, String label) {
		this.opCode = 0x2B;
		this.size = 2;

		this.address = address;
		this.arg1 = arg1;
		this.arg2 = arg2;
		this.label = label;
		return;
	}

	public String toString() {
		return
			Integer.toString(this.opCode) +
			Integer.toString(this.arg1)   +
			Integer.toString(this.arg2);
	}
}
