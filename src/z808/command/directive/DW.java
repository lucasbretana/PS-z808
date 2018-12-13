package z808.command.directive;

import java.util.ArrayList;

import util.AZMRegexCommon;
import util.ExecutionException;
import util.NotImplementedException;

import z808.command.Command;
import z808.memory.Memory;
import z808.memory.Address;
import z808.memory.Register;

public class DW extends Directive {
	public static final String MNEMONIC = "DW";

	private Object value  = null;
	private Class<?> type = null;

	/**
	 * Important constructor
	 * @param lbl the name of the variable, default is empty one
	 * @param value the data to save
	 * @param type its type, so it can be cast
	 */
	private DW(String lbl, Object value, Class<?> type) {
		this.size = 2; // WORDSIZE
		this.label = new String(lbl);
		if (value instanceof Dup)
			this.size *= Dup.class.cast(value).getSize();
		this.value = value;
		this.type = type;
	}
	/**
	 * Creates an empty memory space
	 * Example in z808: DW ?
	 * @param lbl the name of the variable, default is empty one
	 */
	public DW(String lbl) { this(lbl, null, null); }
	public DW() { this("", null, null); }
	/**
	 * Creaes a variable with an int
	 * Example in z808: DW 13
	 * @param lbl the name of the variable, default is empty one
	 * @param value int value to save
	 */
	public DW(String lbl, Integer value)  {this(lbl, value, value.getClass());}
	public DW(Integer value)  {this("", value, value.getClass());}
	/**
	 * Creates a variable with an address
	 * Example in z808: DW 0x0D
	 * @param lbl the name of the variable, default is empty one
	 * @param addr address value to save
	 */
	public DW(String lbl, Address addr) {this(lbl, addr, addr.getClass());}
	public DW(Address addr) {this("", addr, addr.getClass());}
	/**
	 * Creates a variable with an char
	 * Example in z808: DW 'd'
	 * @param lbl the name of the variable, default is empty one
	 * @param value char to save
	 */
	public DW(String lbl, Character value) {this(lbl, value, value.getClass());}
	public DW(Character value) {this("", value, value.getClass());}
	/**
	 * Creates a variable with a Dup directive
	 * Example in z808: DW 13 DUP 0x0d
	 * @param lbl the name of the variable, default is empty one
	 * @param dup the directive to be executed
	 */
	public DW(String lbl, Dup dup) {this(lbl, dup, dup.getClass());}
	public DW(Dup dup) {this("", dup, dup.getClass());}

	public void exec (Memory mem) throws NotImplementedException, ExecutionException {
		throw new NotImplementedException("TODO");
	}

	@Override
	public String toString() {
		return this.value != null ? this.value.toString() : "XXXX";
	}

	/**
	 * Returns the z808 string equivalent of this DW directive
	 */
	public String toCode() {
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

	@Override
	public ArrayList<Register> asRegisters() {
		ArrayList<Register> l = new ArrayList<Register>(this.size);
		int value = Integer.class.cast(this.value).intValue();
		for (int i = 0; i < this.size/2; ++i) {
			l.add(new Register((value << 16) >> 16));
			l.add(new Register(value >> 16));
		}
		return l;
	}
}
