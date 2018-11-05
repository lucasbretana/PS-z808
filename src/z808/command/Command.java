package z808.command;

import z808.memory.Memory;

import util.NotImplementedException;
import util.ExecutionException;
import util.FinishedException;

public abstract class Command {
	protected int size;
	protected int address;
	protected String label = null;

	public abstract void exec (Memory mem) throws NotImplementedException, ExecutionException, FinishedException;

	public int getSize() {
		return this.size;
	}
	public int getAddress() {
		return this.address;
	}
	public String getLabel() {
		return this.label;
	}
}

