package z808.command;

import util.NotImplementedException;

public abstract class Command {
	protected int size;
	protected int address;
	protected String label;

	public int getSize() {
		return this.size;
	}
	public int getAddress() {
		return this.address;
	}
	public String getLabel() {
		return new String(this.label);
	}
}

