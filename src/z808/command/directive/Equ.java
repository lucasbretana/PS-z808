package z808.command.directive;

import java.lang.Math;

import util.AZMRegexCommon;
import util.ExecutionException;
import util.NotImplementedException;
import util.TooLongValue;

import z808.command.Command;
import z808.memory.Memory;
import z808.memory.Address;

public class Equ extends Directive {
	// name EQU
	public static final String MNEMONIC = "EQU";
	public static final String REGEX = "^(" + AZMRegexCommon.NAME_RGX + " )?" + MNEMONIC + " " + "([a-zA-Z]|"  + AZMRegexCommon.INTEGER_RGX + ")"+ "$";
	public static final int SIZE = 1;

	private int arg;
	
	@Deprecated // TODO remove
	public Equ (int value) throws TooLongValue { this(null, value); }
	public Equ (String label, int value) throws TooLongValue {
		if (Math.abs(value) >= 0xFFFF)
			throw new TooLongValue (value);
		this.size = Equ.SIZE;
		this.label = label;

		this.arg = value;
		return;
	}

	// TODO remove?
	public void exec (Memory mem)
		throws ExecutionException {
		// 1. Create Memory entry
		mem.modifyMemory(mem.CL, this.arg);

		// 2. Program Counter increment
		mem.CL.set( mem.CL.get() + this.getSize() );
	}

	@Override
	public String toString() {
		return String.format("%04X", this.arg);
	}

	@Override
	public String toCode() {
		return ((this.label != null) ? this.label + " " : "") + MNEMONIC + " " + this.arg;
	}

	/**
	 * Creates a new Equ
	 * @param s_cmd the string representation
	 * @throws ExecutionException if there is an unexpected error
	 */
	static public Equ makeEqu(String s_cmd) throws ExecutionException {
		// TODO @Bretana finish to add support to stuff like
		// // ain't gonna happen
		// <expression> + <expression>
		// -<expression>

		// only support
		// <int>
		// <char>

		String lbl = null;
		Integer val = null;
		Equ e = null;

		// 1. split
		String tokens[] = s_cmd.split("\\s");

		// sanity check
		if ( tokens.length == 2 ) {
			if (!tokens[0].equals(Equ.MNEMONIC) )
				throw new ExecutionException("This doesn't make any sense..mismatching expression, missing mnemonic");
			
			if ( tokens[1].matches(AZMRegexCommon.INTEGER_RGX) )
				val = AZMRegexCommon.convertZ808Int(tokens[1]);
			else if ( tokens[1].matches(AZMRegexCommon.CHAR_RGX) )
				val = Character.digit(tokens[1].charAt(0), 10);
			else
				throw new ExecutionException("This doesn't make any sense..mismatching expression, invalid parameter");

			return new Equ(val);
		}

		if ( tokens.length == 3 ) {
			if ( !tokens[1].equals(Equ.MNEMONIC) )
				throw new ExecutionException("This doesn't make any sense..mismatching expression, missing mnemonic");
			
			if ( tokens[2].matches(AZMRegexCommon.CHAR_RGX) )
				val = (int)tokens[2].charAt(0); // TODO change once the character has simple quotes '-'
			else if ( tokens[2].matches(AZMRegexCommon.INTEGER_RGX) )
				val = AZMRegexCommon.convertZ808Int(tokens[2]);
			else
				throw new ExecutionException("This doesn't make any sense..mismatching expression, invalid parameter");

			return new Equ(tokens[0], val);
		}

		throw new ExecutionException("Invalid string for makeEqu, this doesn't make any sense");
	}

}
