package z808.command.directive;

import util.ExecutionException;
import util.NotImplementedException;
import z808.command.Command;
import z808.Memory;
import z808.Address;

public class DW extends Directive {
	public static final String MNEMONIC = "DW";
	public static final int OPCODE      = 131;

	private Object value  = null;
	private Class<?> type = null;

	private DW(Object value, Class<?> type) {
		this.value = value;
		this.type = type;
	}
	public DW() { }
	public DW(Integer value) throws NotImplementedException {this(value, value.getClass());}
	public DW(Address addr)throws NotImplementedException {this(addr, addr.getClass());}
	public DW(Character value)throws NotImplementedException {this(value, value.getClass());}
	public DW(Directive dup)throws NotImplementedException {this(dup, dup.getClass());} // TODO: test @Bretana

	public Memory exec (Memory mem) throws NotImplementedException, ExecutionException {
		throw new NotImplementedException("TODO");
	}

	public String toString() {
		String ret = DW.MNEMONIC + " ";
		if (this.value == null)
			ret += "313"; //empty memory, "random" values
		else if (this.value instanceof Character)
			ret += "'" + this.value.toString() + "'";
		else
			ret += this.value.toString();
		return ret;
	}
}
