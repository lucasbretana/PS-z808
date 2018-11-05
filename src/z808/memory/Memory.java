package z808.memory;

import java .util.Map;
import java.util.TreeMap;

import util.NotImplementedException;

import z808.memory.Address;
import z808.memory.Register;

public class Memory {
	/* Register Bank */
	public Register CL;  // Program Counter
	public Register RI;  // Instruction Register
	public Register REM; // Memory Address Register
	public Register RBM; // Memory Buffer Register
	
	public Register AX;
	public Register DX;

	private TreeMap<Address, Register> memory;
	
	public Memory() {
		this.CL  = new Register(0);
		this.RI  = new Register(0);
		this.REM = new Register();
		this.RBM = new Register();

		this.AX  = new Register(0);
		this.DX  = new Register(0);

		this.memory = new TreeMap<Address, Register>();
	}

	public void newMemoryEntry(Address address) {this.newMemoryEntry(address, new Register());}
	public void newMemoryEntry(Address address, int value) {this.newMemoryEntry(address, new Register(value));}
	public void newMemoryEntry(Address address, Register value) {
		this.memory.put(address, value);
	}

	public int get(Number address) {
		return this.memory.get(address).intValue();
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

	public String memoryToString() {
		String ret = "";
		for (Map.Entry<Address, Register> entry : this.memory.entrySet()) {
			ret += String.format("%04X %s\n"
													 , entry.getKey()
													 , entry.getValue());
		}
		return ret;
	}
	
	public String toString () {
		return "-- Registers --\n" + this.registersToString() +
			"-- Memory --" + this.memoryToString();
	}
}
