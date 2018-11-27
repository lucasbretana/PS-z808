package z808.command;

import z808.memory.Memory;

import util.NotImplementedException;
import util.ExecutionException;
import util.FinishedException;

public abstract class Command {
	protected int size;
	protected String label = null;

	public abstract void exec (Memory mem) throws NotImplementedException, ExecutionException, FinishedException;

	public int getSize() {
		return this.size;
	}

	public String getLabel() {
		return this.label;
	}

	// to be overrite command that access the memory (like AddCTE)
	public boolean isDefined() {
		return true;
	}

	public String getUndefValue() {
		throw new RuntimeException("Value is already defined");
	}

	public void setUndefValue(int val) {
		throw new RuntimeException("Cannot set a new value for an already defined command");
	}

}

