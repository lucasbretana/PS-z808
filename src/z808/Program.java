package z808;

import java.util.Map;
import java.util.TreeMap;

import util.ExecutionException;
import util.InvalidOperationException;
import util.SegmentationException;

import z808.memory.Address;
import z808.memory.Register;
import z808.command.Command;

/**
 * This class represents any program that's ready to be loaded to the processor.
 * @author Jonathas-Conceicao
 */
public class Program extends TreeMap<Address, Command> {
	protected static final long serialVersionUID = 313L;
	private Address startDataSegment = null;
	private Integer sizeDataSegment = null;
	private Address startStackSegment = null;
	private Integer sizeStackSegment = null;
	private Address startCodeSegment = null;
	private Integer sizeCodeSegment = null;

	/**
	 * Adds a  new command the current program;
	 * @param addr Address of where the command should be loaded;
	 * @param cmd  The command to be inserted;
	 * @throws InvalidOperationException thrown if the addr already contains any command.
	 */
	public void add(Address addr, Command cmd) throws InvalidOperationException {
		Command v = super.putIfAbsent(addr, cmd);
		if (v != null)
			throw new InvalidOperationException("\nTrying set instruction to an occupied memory location.\n"
																	 + "Memory location: " + addr
																	 + "Current instruction: " + v
																	 + "Trying to insert: " + cmd);
		return;
	}

	public Address getStartDataSegment() { return this.startDataSegment; }
	public void    setStartDataSegment(Address s) { this.startDataSegment = s; }
	public Integer getSizeDataSegment() { return this.sizeDataSegment; }
	public void    setSizeDataSegment(Integer s) { this.sizeDataSegment = s; }

	public Address getStartStackSegment() { return this.startStackSegment; }
	public void    setStartStackSegment(Address s) { this.startStackSegment = s; }
	public Integer getSizeStackSegment() { return this.sizeStackSegment; }
	public void    setSizeStackSegment(Integer s) { this.sizeStackSegment = s; }

	public Address getStartCodeSegment() { return this.startCodeSegment; }
	public void    setStartCodeSegment(Address s) { this.startCodeSegment = s; }
	public Integer getSizeCodeSegment() { return this.sizeCodeSegment; }
	public void    setSizeCodeSegment(Integer s) { this.sizeCodeSegment = s; }

	/**
	 * Get fetch the command from it's address;
	 * @parameter addr Address of command's start;
	 * @exception SegmentationException thrown if there's no command for the input.
	 */
	public Command get(Address addr) throws SegmentationException {
		Command c = super.get(addr);
		if (c != null) return c;
		throw new SegmentationException ("Trying to fetch instruction from outside code segment:" + addr);
	}

	public TreeMap<Address, Register> memoryView() throws ExecutionException {
		TreeMap<Address, Register> mv = new TreeMap<Address, Register>();
		for (Map.Entry<Address, Command> entry : entrySet()) {
			Address addr = entry.getKey();
			Command cmd = entry.getValue();
			int i = 0;
			for (Register rg : cmd.asRegisters()) {
				Address crr = new Address(addr.intValue() + i);
				Register v  = mv.putIfAbsent(crr, rg);
				if (v != null)
					throw new InvalidOperationException("\nFailed to generate memory view.\n"
																							+ "Memory location: " + crr
																							+ "Reg value: " + v
																							+ "Trying to insert: " + rg);
				i++;
			}
		}
		return mv;
	}

	public void merge(Program p) throws ExecutionException {
		for (Map.Entry<Address, Command> entry : p.entrySet()) {
			this.add(entry.getKey(), entry.getValue());
		}
	}

	public String toString() {
		String ret = "";
		for (Map.Entry<Address, Command> entry : entrySet()) {
			ret += entry.getKey() + " " + entry.getValue() + "\n";
		}
		return ret;
	}
}
