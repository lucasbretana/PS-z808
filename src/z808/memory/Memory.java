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
	public Register SP;  // Stack Pointer
	public Register SR;  // Status Register

	public Register AX;
	public Register DX;
	public Register SI;

	private TreeMap<Address, Register> mainMemory;

	/**
	 * Builds the memory with a specific memory size.
	 * @param memorySizeInK memory size in Kilo Bytes.
	 */
	public Memory(int memorySizeInK) throws ExecutionException {
		this.CL  = new Register(0);
		this.RI  = new Register(0);
		this.REM = new Register();
		this.RBM = new Register();
		this.SP  = new Register();
		this.SR  = new Register();

		this.AX  = new Register(0);
		this.DX  = new Register(0);
		this.SI  = new Register(0);

		this.mainMemory = new TreeMap<Address, Register>();
		for (int i = 0; i < memorySizeInK * 1024; ++i) {
			this.newMemoryEntry(i);
		}
	}

	private void newMemoryEntry(Number address) throws ExecutionException {
		this.mainMemory.put(new Address(address.intValue()), new Register());
	}

	public void modifyMemory(Number addr, Number val) throws ExecutionException {
		this.mainMemory.get(new Address(addr.intValue())).set(val.intValue());
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

	/**
	 * Load Registers into corresponding memory addresses.
	 * @param p Map of Address to Register to be loaded
	 */
	public void load(Map<Address, Register> p) throws ExecutionException {
		for (Map.Entry<Address, Register> entry : p.entrySet()) {
			this.modifyMemory(entry.getKey(), entry.getValue());
		}
	}

	public String registersToString () {
		String ret =
			"CL:"  + CL  + "\n" +
			"RI:"  + RI  + "\n" +
			"REM:" + REM + "\n" +
			"RBM:" + RBM + "\n" +
			"SP:"  + SP  + "\n" +
			"SR:"  + SR + "\n"  +
			"AX:"  + AX  + "\n" +
			"DX:"  + DX  + "\n" +
			"SI:"  + SI  + "\n" +
			"";
		return ret;
	}

	/**
	 * Gets a list of registers values to be displayed.
	 * @returns a list of registers
	 */
	public ArrayList<Register> getRegisters () {
		ArrayList<Register> l = new ArrayList<Register>();
		l.add(CL);
		l.add(RI);
		l.add(REM);
		l.add(RBM);
		l.add(SP);
		l.add(SR);
		l.add(AX);
		l.add(DX);
		l.add(SI);
		return l;
	}

	/**
	 * Gets a list of memory locations to be displayed.
	 * @returns a list of registers
	 */
	public ArrayList<Register> getMemoryRegisters () {
		ArrayList<Register> l = new ArrayList<Register>(this.mainMemory.size());
		for (Map.Entry<Address, Register> entry : this.mainMemory.entrySet()) {
			l.add(entry.getValue());
		}
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
