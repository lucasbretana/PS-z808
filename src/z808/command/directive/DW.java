package z808.command.directive;

import util.AZMRegexCommon;
import util.ExecutionException;
import util.NotImplementedException;

import z808.command.Command;
import z808.memory.Memory;
import z808.memory.Address;

public class DW extends Directive {
	public static final String MNEMONIC = "DW";
	public static final String REGEX = "^(" + AZMRegexCommon.NAME_RGX + " )?" + MNEMONIC + " (" + AZMRegexCommon.CHAR_RGX + "|" + AZMRegexCommon.INTEGER_RGX + "|([\\?]))$";
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

	public void exec(Memory mem) throws NotImplementedException, ExecutionException {
		throw new NotImplementedException("TODO");
	}

	static public DW makeDW(String from) throws ExecutionException {
		String []tokens = from.split(" ");
		if (tokens.length < 2) throw new ExecutionException("This doesn't make any sense..mismatching expression");

		// label DW int|char|?
		if (tokens.length == 3) {
			if (!tokens[1].equals(MNEMONIC))
				throw new ExecutionException("This doesn't make any sense..mismatching expression, invalid mnemonic");

			if ( tokens[2].matches("\\?") )
				return new DW(tokens[0]);
			else if ( tokens[2].matches(AZMRegexCommon.CHAR_RGX) )
				return new DW(tokens[0], tokens[2].charAt(0));
			else if ( tokens[2].matches(AZMRegexCommon.INTEGER_RGX) )
				return new DW(tokens[0], AZMRegexCommon.convertZ808Int(tokens[2]));
		} else if (tokens.length == 2) {
			// label DW int|char|?
			if (!tokens[0].equals(MNEMONIC))
				throw new ExecutionException("This doesn't make any sense..mismatching expression, invalid mnemonic");

			if ( tokens[1].matches("\\?") )
				return new DW();
			else if ( tokens[1].matches(AZMRegexCommon.CHAR_RGX) )
				return new DW(tokens[1]);
			else if ( tokens[1].matches(AZMRegexCommon.INTEGER_RGX) )
				return new DW(AZMRegexCommon.convertZ808Int(tokens[1]));
		} else if (tokens.length == 4){
			// DW 10 Dup int|char|?
			if ( (!tokens[0].equals(MNEMONIC)) || (!tokens[2].equals(Dup.MNEMONIC)) )
				throw new ExecutionException("This doesn't make any sense..mismatching expression, invalid mnemonic or Dup");

			if ( tokens[3].matches("\\?") )
				return new DW(new Dup(Integer.parseInt(tokens[1])));
			else if ( tokens[3].matches(AZMRegexCommon.CHAR_RGX) )
				return new DW(new Dup(Integer.parseInt(tokens[1]), tokens[3].charAt(0)));
			else if ( tokens[3].matches(AZMRegexCommon.INTEGER_RGX) )
				return new DW(new Dup(Integer.parseInt(tokens[1]), AZMRegexCommon.convertZ808Int(tokens[3])));
		} else if (tokens.length == 5){
			// label DW 10 Dup int|char|?
			if ( (!tokens[1].equals(MNEMONIC)) || (!tokens[3].equals(Dup.MNEMONIC)) )
				throw new ExecutionException("This doesn't make any sense..mismatching expression, invalid mnemonic or Dup");

			if ( tokens[4].matches("\\?") )
				return new DW(tokens[0], new Dup(Integer.parseInt(tokens[2])));
			else if ( tokens[4].matches(AZMRegexCommon.CHAR_RGX) )
				return new DW(tokens[0], new Dup(Integer.parseInt(tokens[2]), tokens[4].charAt(0)));
			else if ( tokens[4].matches(AZMRegexCommon.INTEGER_RGX) )
				return new DW(tokens[0], new Dup(Integer.parseInt(tokens[2]), AZMRegexCommon.convertZ808Int(tokens[4])));
		}


		throw new ExecutionException("This doesn't make any sense..mismatching expression");
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
}
