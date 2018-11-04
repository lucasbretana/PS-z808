package z808;

import java .util.Map;
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
		commands
			.get(this.memory.getCurrentInstruction())
			.exec(this.memory);
	}
	
	public String toString () {
		return this.memory.toString();
	}

	private void sanityCheck(Memory cmd)
		throws ExecutionException {
		return;
	}
}
