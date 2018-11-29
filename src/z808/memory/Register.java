package z808.memory;

import util.NotImplementedException;
import util.ExecutionException;

import z808.memory.Address;

import javafx.beans.property.SimpleStringProperty;

public class Register extends Number implements Comparable<Number> {
	protected static final long serialVersionUID = 313L;

	private boolean defined;
	private int value;
	private SimpleStringProperty property;

	public Register (int v) {
		this.defined = true;
		this.value = v;
		this.property = new SimpleStringProperty(this.toString());
	}
	public Register () {
		this.defined = false;
		this.property = new SimpleStringProperty("XX");
	}

	public void set(int v) {this.defined = true;this.value = v; this.updateProperty();}
	public void set(Address v) {this.defined =true;this.value = v.intValue(); this.updateProperty();}
	public void set(Register r) throws ExecutionException {this.set(r.get()); this.updateProperty();}

	public int get() throws ExecutionException {
		if (this.defined)
			return this.value;
		else
			throw new ExecutionException ("Trying to get the value of a undefined register.");
	}

	public SimpleStringProperty getProperty() { return this.property; }
	public int intValue() { return this.value; }
	public float floatValue() { return (float) this.value; }
	public double doubleValue() { return (double) this.value; }
	public long longValue() { return (long) this.value; }

	public int compareTo(Number o) {return this.value - o.intValue();}

	@Override
	public String toString() {
		return (defined ? String.format("%02X", this.value) : "XX");
	}
	private void updateProperty() { this.property.set(this.toString()); }
}
