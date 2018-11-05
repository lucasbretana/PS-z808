package z808;

import java.util.Map;
import java.util.TreeMap;

import util.ExecutionException;

import z808.memory.Address;
import z808.command.Command;

public class Program extends TreeMap<Address, Command> {
	protected static final long serialVersionUID = 313L;
	TreeMap<Address, Command> code;

	public Program() {
		code = new TreeMap<Address, Command>();
	}

	public void put(Command cmd) throws ExecutionException {
		Command v = super.putIfAbsent(cmd.getAddress(), cmd);
		if (v != null)
			throw new ExecutionException("\nTrying set instruction to an occupied memory location.\n"
																	 + "Memory location: " + cmd.getAddress()
																	 + "Current instruction: " + v
																	 + "Trying to insert: " + cmd);
		return;
	}
}
