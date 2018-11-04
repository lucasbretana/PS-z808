package z808.memory;

import util.NotImplementedException;

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

	public int intValue() { return this.value; }
	public float floatValue() { return (float) this.value; }
	public double doubleValue() { return (double) this.value; }
	public long longValue() { return (long) this.value; }

	@Override
	public String toString() {
		return (defined ? String.format("%02X", this.value) : "XX");
	}
}
