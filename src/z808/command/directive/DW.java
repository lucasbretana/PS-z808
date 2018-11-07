package z808.command.directive;

import util.ExecutionException;
import util.NotImplementedException;
import z808.command.Command;
import z808.memory.Memory;
import z808.memory.Address;

public class DW extends Directive {
	public static final String MNEMONIC = "DW";

	private Object value  = null;
	private Class<?> type = null;

	/**
	 * Important constructor
	 * @param value the data to save
	 * @param type its type, so it can be cast
	 */
	private DW(Object value, Class<?> type) {
		this.size = 2; // WORDSIZE
		if (value instanceof Dup)
			this.size *= Dup.class.cast(value).getSize();
		this.value = value;
		this.type = type;
	}
	/**
	 * Creates an empty memory space
	 * Example in z808: DW ?
	 */
	public DW() { this(null, null); }
	/**
	 * Creaes a variable with an int
	 * Example in z808: DW 13
	 * @param value int value to save
	 */
	public DW(Integer value)  {this(value, value.getClass());}
	/**
	 * Creates a variable with an address
	 * Example in z808: DW 0x0D
	 * @param addr address value to save
	 */
	public DW(Address addr) {this(addr, addr.getClass());}
	/**
	 * Creates a variable with an char
	 * Example in z808: DW 'd'
	 * @param value char to save
	 */
	public DW(Character value) {this(value, value.getClass());}
	/**
	 * Creates a variable with a Dup directive
	 * Example in z808: DW 13 DUP 0x0d
	 * @param dup the directive to be executed
	 */
	public DW(Dup dup) {this(dup, dup.getClass());}

	public void exec (Memory mem) throws NotImplementedException, ExecutionException {
		throw new NotImplementedException("TODO");
	}

	/**
	 * Returns the z808 string equivalent of this DW directive
	 */
	public String toString() {
		String ret = "";
		if (this.getLabel() != null)
			ret += this.getLabel() + " ";
		ret += DW.MNEMONIC + " ";
		if (this.value == null)
			ret += "313"; //empty memory, "random" values
		else if (this.value instanceof Character)
			ret += "'" + this.value.toString() + "'";
		else
			ret += this.value.toString();
		return ret;
	}
}
