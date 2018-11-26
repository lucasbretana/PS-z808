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
	// name? equ value
	public static final String REGEX = "(" + AZMRegexCommon.NAME_RGX + ")?\\s?" +  MNEMONIC;// + "[" + AZMRegexCommon.EXPRESSION + "]|[" + AZMRegexCommon.CHAR_RGX + "]";
	public static final int SIZE = 1;

	private int arg;
	
	public Equ (int value) throws TooLongValue { this(null, value); }
	public Equ (String label, int value) throws TooLongValue {
		if (Math.abs(value) >= 0xFFFF)
			throw new TooLongValue (value);
		this.size = Equ.SIZE;
		this.label = label;

		this.arg = value;
		return;
	}

	public void exec (Memory mem)
		throws ExecutionException {
		// 1. Create Memory entry
		mem.newMemoryEntry(mem.CL, this.arg);

		// 2. Program Counter increment
		mem.CL.set( mem.CL.get() + this.getSize() );
	}

	public String toString() {
		return String.format("%04X", this.arg);
	}
}
