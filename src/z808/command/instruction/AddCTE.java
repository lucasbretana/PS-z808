package z808.command.instruction;

import util.AZMRegexCommon;
import util.ExecutionException;
import util.NotImplementedException;
import util.TooLongValue;

import z808.memory.Memory;
import z808.memory.Address;
import z808.command.instruction.Instruction;

public class AddCTE extends Instruction {
	public static final String MNEMONIC = "add";
	public static final String REGEX = "^(" + AZMRegexCommon.NAME_RGX + " )?" + MNEMONIC + " AX (" + AZMRegexCommon.NAME_RGX + "|" + AZMRegexCommon.INTEGER_RGX + ")$";
	public static final int OPCODE = 0X05;
	public static final int SIZE   = 3;

	private int arg;
	private String u_arg = null;
	private boolean defined = false;

	public AddCTE (String value) {
		this(null, value);
	}

	public AddCTE (int value)
		throws ExecutionException, TooLongValue {
		this(null, value);
	}

	public AddCTE (String label, String value) {
		this.size = AddCTE.SIZE;
		this.label = label;

		this.u_arg = value;
		this.code = AddCTE.OPCODE;
	}
	public AddCTE (String label, Integer value) throws TooLongValue {
		if (value.intValue() >= 0xFFFF)
			throw new TooLongValue (value);
		this.size = AddCTE.SIZE;
		this.label = label;

		this.arg = value;
		this.code = AddCTE.OPCODE;
		this.defined = true;
		return;
	}

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
		// 6. Second Value fetch in case of address

		// 7. Execution
		mem.AX.set( mem.AX.get() + mem.RBM.get() );

		// 8. Write back
		// 9. Program Counter increment
		mem.CL.set( mem.CL.get() + this.getSize() );
	}

	@Override
	public String getUndefValue() {
		return this.u_arg;
	}

	@Override
	public void setUndefValue(int val) {
		if (this.isDefined()) super.setUndefValue(val);
		this.arg = val;
	}

	@Override
	public boolean isDefined() {
		return this.defined;
	}

	@Override
	public String toString() {
		if (this.defined)
			return "05 " + arg;
		else
			return AddCTE.MNEMONIC + " " + this.u_arg;
	}

	static public AddCTE makeAddCTE(String from) throws ExecutionException {
		String []tokens = from.split(" ");
		if (tokens.length < 3) throw new ExecutionException("This doesn't make any sense..mismatching expression");

		if (tokens.length == 4) {
			if ( (!tokens[1].equals(MNEMONIC)) || (!tokens[2].equals("AX")) ) 
				throw new ExecutionException("This doesn't make any sense..mismatching expression, invalid mnemonic or parameter");

			if ( tokens[3].matches(AZMRegexCommon.INTEGER_RGX) )
				return new AddCTE(tokens[0], AZMRegexCommon.convertZ808Int(tokens[3]));
			else if ( tokens[3].matches(AZMRegexCommon.NAME_RGX) )
				return new AddCTE(tokens[0], tokens[3]);

		} else if (tokens.length == 3) {
			if ( (!tokens[0].equals(MNEMONIC)) || (!tokens[1].equals("AX")) ) 
				throw new ExecutionException("This doesn't make any sense..mismatching expression, invalid mnemonic or parameter");
			return new AddCTE(tokens[2]);
		}

		throw new ExecutionException("This doesn't make any sense..mismatching expression");
	}

}
