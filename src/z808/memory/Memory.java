package z808.memory;

import java.util.Map;
import java.util.TreeMap;
import java.util.ArrayList;

import util.NotImplementedException;
import util.ExecutionException;

import z808.memory.Address;
import z808.memory.Register;

/**
 * This class represents the internal memory of the z808 processor, registers and main memory.
 * @author Jonathas-Conceicao
 */
public class Memory {
	/* Register Bank */
	public Register CL;  // Program Counter
	public Register RI;  // Instruction Register
	public Register REM; // Memory Address Register
	public Register RBM; // Memory Buffer Register
	
	public Register AX;
	public Register DX;

	private TreeMap<Address, Register> mainMemory;

	/**
	 * Builds the memory with a specific memory size.
	 * @param memorySizeInK memory size in Kilo Bytes.
	 */
	public Memory(int memorySizeInK) {
		this.CL  = new Register(0);
		this.RI  = new Register(0);
		this.REM = new Register();
		this.RBM = new Register();

		this.AX  = new Register(0);
		this.DX  = new Register(0);

		this.mainMemory = new TreeMap<Address, Register>();
	}

	public void newMemoryEntry(Number address)
		throws ExecutionException {this.newMemoryEntry(new Address(address.intValue()), new Register());}
	public void newMemoryEntry(Number address, int value)
		throws ExecutionException {this.newMemoryEntry(new Address(address.intValue()), new Register(value));}
	public void newMemoryEntry(Address address) {this.newMemoryEntry(address, new Register());}
	public void newMemoryEntry(Address address, int value) {this.newMemoryEntry(address, new Register(value));}
	public void newMemoryEntry(Address address, Register value) {
		this.mainMemory.put(address, value);
	}

	public int get(Number address) throws ExecutionException {
		Register v = this.mainMemory.get(address);
		if (v == null)
			throw new ExecutionException ("Segmentation Fault!\n"
																		+ "Memory access outside of data segmentation: "
																		+ address + "\n");
		return v.intValue();
	}

	public Address getCurrentInstruction() throws ExecutionException {
		return new Address(this.CL.intValue());
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

	/**
	 * Gets a list of registers values to be displayed.
	 * @returns a list of registers
	 */
	public ArrayList<Register> getRegisters () {
		ArrayList<Register> l = new ArrayList<Register>(6); // Number of registers to avoid dinamic alocation
		l.add(CL);
		l.add(RI);
		l.add(REM);
		l.add(RBM);
		l.add(AX);
		l.add(DX);
		return l;
	}

	public String memoryToString() {
		String ret = "";
		for (Map.Entry<Address, Register> entry : this.mainMemory.entrySet()) {
			ret += entry.getKey() + " " + entry.getValue() + "\n";
		}
		return ret;
	}

	public String toString () {
		return
			"-- Registers --\n" + this.registersToString() +
			"-- Memory --\n" + this.memoryToString();
	}
}
