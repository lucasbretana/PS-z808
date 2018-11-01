package z808;

import util.ExecutionException;

public class Address extends Number {
	protected static final long serialVersionUID = 313L;
	int value;

	/**
	 * Creates an addres 
	 * @param i a positive number
	 * @throws ExecutionException in case there is an error
	 */
	public Address(int i) throws ExecutionException{
		if ( i<0 ) throw new ExecutionException("Invalid address (NEG)");
		this.value = i;
	}

	public int intValue() { return this.value; }
	public double doubleValue() { return this.value * 1.0; }
	public float floatValue() { return (new Double(this.value * 1.0)).floatValue(); }
	public long longValue() { return this.value * 1L; }

	@Override
	public String toString() {
		return "0x" + Integer.toHexString(this.value);
	}
}
