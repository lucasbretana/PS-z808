package z808.command.instruction;

import java.lang.Math;
import java.util.ArrayList;

import util.AZMRegexCommon;
import util.ExecutionException;
import util.NotImplementedException;
import util.TooLongValue;

import z808.memory.Memory;
import z808.memory.Address;
import z808.memory.Register;
import z808.command.instruction.Instruction;

public class SubCTE extends Instruction {
	public static final String MNEMONIC = "sub";
	public static final String REGEX = "^(" + AZMRegexCommon.NAME_RGX + " )?" + MNEMONIC + " AX (" + AZMRegexCommon.NAME_RGX + "|" + AZMRegexCommon.INTEGER_RGX + ")$";
	public static final int OPCODE = 0X2B;
	public static final int SIZE   = 3;

	private Integer arg;
	private String u_arg;

	public SubCTE (String value) {
		this(null, value);
	}

	public SubCTE (int value)
		throws ExecutionException, TooLongValue {
		this(null, value);
	}

	public SubCTE (String label, Integer value) throws TooLongValue {
		if (value.intValue() >= 0xFFFF)
			throw new TooLongValue (value);
		this.size = SubCTE.SIZE;
		this.label = label;

		this.arg = value;
		this.code = SubCTE.OPCODE;
		return;
	}

	public SubCTE (String label, String value) {
		this.size = AddCTE.SIZE;
		this.label = label;

		this.u_arg = value;
		this.code = AddCTE.OPCODE;
	}

	@Override
	public boolean isDefined() {
		return this.u_arg == null;
	}

	@Override
	public String getUndefValue() {
		if (isDefined()) return this.u_arg;
		else return null;
	}

	@Override
	public void exec (Memory mem)
		throws NotImplementedException, ExecutionException {
		// 1. Intruction Fetch
		mem.REM.set(mem.CL);
		// 2. Decode
		mem.RBM.set(this.code % 0x100);
		mem.RI.set(mem.RBM);
		// 3. Arg fetch
		mem.REM.set(this.arg);
		// 4. Value fetch in case of address
		mem.RBM.set(mem.get(mem.REM));
		// 5. Second arg fetch
		// 6. Second value fetch in case of address

		// 7. Execution
		mem.AX.set( mem.AX.get() - mem.RBM.get() );

		// 8. Write back
		// 9. Program Counter increment
		mem.CL.set( mem.CL.get() + this.getSize() );
	}

	@Override
	public String toString() {
		return "2B " + String.format("%04X", this.arg);
	}

	static public SubCTE makeSubCTE(String from) throws ExecutionException {
		String []tokens = from.split(" ");
		if (tokens.length < 3) throw new ExecutionException("This doesn't make any sense..mismatching expression");

		if (tokens.length == 4) {
			if ( (!tokens[1].equals(MNEMONIC)) || (!tokens[2].equals("AX")) ) 
				throw new ExecutionException("This doesn't make any sense..mismatching expression, invalid mnemonic or parameter");

			if ( tokens[3].matches(AZMRegexCommon.INTEGER_RGX) )
				return new SubCTE(tokens[0], AZMRegexCommon.convertZ808Int(tokens[3]));
			else if ( tokens[3].matches(AZMRegexCommon.NAME_RGX) )
				return new SubCTE(tokens[0], tokens[3]);

		} else if (tokens.length == 3) {
			if ( (!tokens[0].equals(MNEMONIC)) || (!tokens[1].equals("AX")) ) 
				throw new ExecutionException("This doesn't make any sense..mismatching expression, invalid mnemonic or parameter");
			return new SubCTE(tokens[2]);
		}

		throw new ExecutionException("This doesn't make any sense..mismatching expression");
	}

	@Override
	public ArrayList<Register> asRegisters() {
		ArrayList<Register> l = new ArrayList<Register>(SubCTE.SIZE);
		l.add(new Register(0x2b));
		l.add(new Register(this.arg.intValue() >> 16));
		l.add(new Register((this.arg.intValue() << 16) >> 16));
		return l;
	}
}
