package z808;

import java.util.Map;
import java.util.TreeMap;

import util.ExecutionException;
import util.InvalidOperationException;
import util.SegmentationException;

import z808.memory.Address;
import z808.command.Command;

/**
 * This class represents any program that's ready to be loaded to the processor.
 * @author Jonathas-Conceicao
 */
public class Program extends TreeMap<Address, Command> {
	protected static final long serialVersionUID = 313L;

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

	public String toString() {
		String ret = "";
		for (Map.Entry<Address, Command> entry : entrySet()) {
			ret += entry.getKey() + " " + entry.getValue() + "\n";
		}
		return ret;
	}
}
