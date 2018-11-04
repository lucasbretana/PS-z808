package z808.memory;

import util.NotImplementedException;

import z808.memory.Register;

public class Memory {
	/* Register Bank */
	public Register CL;  // Program Counter
	public Register RI;  // Instruction Register
	public Register REM; // Memory Address Register
	public Register RBM; // Memory Buffer Register
	
	public Register AX;
	public Register DX;

	public Memory() {
		this.CL  = new Register(0);
		this.RI  = new Register(0);
		this.REM = new Register();
		this.RBM = new Register();

		this.AX  = new Register(0);
		this.DX  = new Register(0);
	}

	public int getCurrentInstruction() {
		return this.CL.intValue();
	}

	public String registersToString () {
		String ret =
			"CL:"  + CL  + "\n" +
			"RI:"  + RI  + "\n" +
			"REM:" + REM + "\n" +
			"RBM:" + RBM + "\n" +
			"AX:"  + AX  + "\n" +
			"DX:"  + DX  + "\n";
		return ret;
	}

	public String toString () {
		return "-- Registers --\n" + this.registersToString() +
			"-- Memory --";
	}
}
