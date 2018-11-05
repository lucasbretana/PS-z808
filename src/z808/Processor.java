package z808;

import java.util.TreeMap;

import util.ExecutionException;
import util.NotImplementedException;
import util.FinishedException;

import z808.memory.Memory;
import z808.memory.Register;

import z808.command.Command;

public class Processor {
	private Memory memory;

	public Processor() throws NotImplementedException {
	  this.memory = new Memory();
	}

	public void process (TreeMap<Integer, Command> commands)
		throws ExecutionException {
		try { while (true) {this.step(commands);} }
		catch (FinishedException e) {
		}
		return;
	}

	public void step(TreeMap<Integer, Command> commands)
		throws ExecutionException, FinishedException {
		this.sanityCheck(this.memory);
		Command cmd =  commands.get(this.memory.getCurrentInstruction());
		if (cmd != null)
			cmd.exec(this.memory);
		else
			throw new ExecutionException ("Processor reached invalid intruction address:"
																		+ this.memory.getCurrentInstruction());
	}
	
	public String toString () {
		return this.memory.toString();
	}

	private void sanityCheck(Memory mem)
		throws ExecutionException {
		return;
	}
}
