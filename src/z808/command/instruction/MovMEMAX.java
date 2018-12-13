package z808.command.instruction;

import java.util.ArrayList;

import z808.memory.Register;
import z808.memory.Address;
import z808.memory.Memory;
import z808.command.instruction.Instruction;

import util.AZMRegexCommon;
import util.NotImplementedException;
import util.ExecutionException;

public class MovMEMAX extends Instruction {
	public static final int OPCODE = 0xA3;
	public static final String MNEMONIC = "mov";
	public static final String REGEX = "^(" + AZMRegexCommon.NAME_RGX + " )?" + MNEMONIC + " (" + AZMRegexCommon.NAME_RGX + "|" + AZMRegexCommon.INTEGER_RGX + ") AX$";
	public static final int SIZE   = 3;

	public Address arg = null;
	private String u_arg = null; // variable name

	public MovMEMAX (String label, String call, Address _arg) {
		this.size = SIZE;
		this.code = OPCODE;

		this.label = label;
		this.u_arg = call;
		this.arg = _arg;
	}

	public MovMEMAX (String call) {
		this(null, call, null);
	}

	public MovMEMAX (String call, Address _arg) {
		this(null, call, _arg);
	}

	public MovMEMAX (String label, String call) {
		this(label, call, null);
	}

	public MovMEMAX (String call, int _arg) throws ExecutionException {
		this(null, call, new Address(_arg));
	}

	public MovMEMAX (int _arg) throws ExecutionException {
		this(null, null, new Address(_arg));
	}

	@Override
	public boolean isDefined() {
		return this.arg != null;
	}

	@Override
	public String getUndefValue() {
		return this.u_arg;
	}

	public void exec (Memory mem)
		throws NotImplementedException, ExecutionException {
		// 1. Intruction Fetch
		mem.REM.set(mem.CL);
		// 2. Decode
		mem.RBM.set(this.code % 0x100);
		mem.RI.set(mem.RBM);
		// 3. Arg fetch
		// 4. Value fetch in case of address
		// 5. Second arg fetch
		// 6. Second Value fetch in case of address

		// 7. Execution
		mem.modifyMemory(this.arg, mem.AX);

		// 8. Write back
		// 9. Program Counter increment
		mem.CL.set( mem.CL.get() + this.getSize() );
	}

	@Override
	public String toString() {
		return "A3 " + ((this.isDefined()) ? arg : this.u_arg);
	}

	static public MovMEMAX makeMovAXMEM(String from) throws ExecutionException {
		String []tokens = from.split(" ");
		if (tokens.length < 3) throw new ExecutionException("This doesn't make any sense..mismatching expression");
		String label;
		String arg;

		if (tokens.length == 4) {
			label = tokens[0];
			arg = tokens[2];
		} else {
			label = null;
			arg = tokens[1];
		}

		if ( arg.matches(AZMRegexCommon.INTEGER_RGX) )
			return new MovMEMAX(label, AZMRegexCommon.convertZ808Int(arg));
		return new MovMEMAX(label, arg);
	}

	@Override
	public ArrayList<Register> asRegisters() {
		ArrayList<Register> l = new ArrayList<Register>(SIZE);
		l.add(new Register(0xA3));
		l.add(new Register(this.arg.intValue() >> 16));
		l.add(new Register((this.arg.intValue() << 16) >> 16));
		return l;
	}
}
