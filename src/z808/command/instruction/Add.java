package z808.command.instruction;

import z808.command.instruction.Instruction;

import util.NotImplementedException;

public class Add extends Instruction {
  public static final int OPCODE = 0x03;
  public static final int SIZE   = 2;

  protected int arg1;
  protected int arg2;
	
	public Add (int address, int arg1, int arg2) {
          this(address, arg1, arg2, null);
        }
	public Add (int address, int arg1, int arg2, String label) {
		this.opCode = Add.OPCODE;
		this.size   = Add.SIZE;

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
