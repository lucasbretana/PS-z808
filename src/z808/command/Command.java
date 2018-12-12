package z808.command;

import java.util.List;
import java.util.ArrayList;

import z808.memory.Memory;
import z808.memory.Register;

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

	// to be override by instructions to generate memory view
	public ArrayList<Register> asRegisters() {
		return new ArrayList<Register>(0);
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

