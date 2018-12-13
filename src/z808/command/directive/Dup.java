package z808.command.directive;

import util.AZMRegexCommon;
import util.ExecutionException;
import util.NotImplementedException;

import z808.command.Command;
import z808.memory.Memory;
import z808.memory.Address;

import z808.command.directive.Directive;

public class Dup extends Directive {
	public static final String MNEMONIC = "DUP";
	public static final String REGEX =  MNEMONIC + " (" + AZMRegexCommon.CHAR_RGX 
							    + "|" + AZMRegexCommon.INTEGER_RGX 
							    + "|([\\?])/)";

	private Object value  = null;
	private Class<?> type = null;
	private int count     = 0;

	/**
	 * Important constructor
	 * @param count how may spaces to reserve
	 * @param value the data
	 * @param type its type, so it can be cast
	 */
	private Dup(int count, Object value, Class<?> type) {
		this.count = count;
		this.value = value;
		this.type = type;
	}
	/**
	 * Creates an empty memory space
	 * Example in z808: DW 13 Dup ?
	 * @param count how many spaces to save
	 */
	public Dup(int count) { this(count, null, null); }
	/**
	 * Creaes a variable with an int
	 * Example in z808: DW 13 Dup 13
	 * @param count how many spaces to save
	 * @param value int value to save
	 */
	public Dup(int count, Integer value) {this(count, value, value.getClass());}
	/**
	 * Creates a variable with an address
	 * Example in z808: DW 13 Dup 0x0D
	 * @param count how many spaces to save
	 * @param addr address value to save
	 */
	public Dup(int count, Address addr){this(count, addr, addr.getClass());}
	/**
	 * Creates a variable with an char
	 * Example in z808: DW 13 Dup 'd'
	 * @param count how many spaces to save
	 * @param value char to save
	 */
	public Dup(int count, Character value) {this(count, value, value.getClass());}

	@Override
	public void exec (Memory mem) throws NotImplementedException {
		throw new NotImplementedException("TODO");
	}

	@Override
	public String toString() {
		return this.value != null ? this.value.toString() : "XXXX";
	}

	public String toCode() {
		String ret = this.count + " " + Dup.MNEMONIC + " ";
		if (this.value == null)
			ret += "313"; //empty memory, "random" values
		else if (this.value instanceof Character)
			ret += "'" + this.value.toString() + "'";
		else
			ret += this.value.toString();
		return ret;
	}

	@Override
	public int getSize() {
		return this.count;
	}
	/**
	 * This class should not have a lebel
	 * Its corresponding DW may have tho
	 * @param lbl nothing important
	 * @throws ExecutionException always
	 */
	@Override
	public void setLabel(String lbl) throws ExecutionException {
		throw new ExecutionException("A Dup never have a label, its DW may have tho");
	}

}
