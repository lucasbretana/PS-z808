package z808.memory;

import util.NotImplementedException;
import util.ExecutionException;

public class Register extends Number {
	protected static final long serialVersionUID = 313L;

	private boolean defined;
	private int value;

	public Register (int v) {
		this.defined = true;
		this.value = v;
	}
	public Register () {
		this.defined = false;
	}

	public void set(int v) {this.defined = true;this.value = v;}
	public void set(Register r) throws ExecutionException {this.set(r.get());}

	public int get() throws ExecutionException {
		if (this.defined)
			return this.value;
		else
			throw new ExecutionException ("Trying to get the value of a undefined register.");
	}
	
	public int intValue() { return this.value; }
	public float floatValue() { return (float) this.value; }
	public double doubleValue() { return (double) this.value; }
	public long longValue() { return (long) this.value; }

	@Override
	public String toString() {
		return (defined ? String.format("%02X", this.value) : "XX");
	}
}
