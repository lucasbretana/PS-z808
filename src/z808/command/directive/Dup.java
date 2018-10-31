package z808.command.directive;

import util.ExecutionException;
import util.NotImplementedException;
import z808.command.Command;
import z808.Memory;
import z808.Address;

import z808.command.directive.Directive;

public class Dup extends Directive {
	public static final String MNEMONIC = "DUP";
	public static final int OPCODE      = 131; // TODO: check @Bretana

	private Object value  = null;
	private Class<?> type = null;
	private int count     = 0;

	private Dup(int count, Object value, Class<?> type) {
		this.count = count;
		this.value = value;
		this.type = type;
	}
	public Dup(int count) { this(count, null, null); }
	public Dup(int count, Integer value) throws NotImplementedException {this(count, value, value.getClass());}
	public Dup(int count, Address addr)throws NotImplementedException {this(count, addr, addr.getClass());}
	public Dup(int count, Character value) throws NotImplementedException {this(count, value, value.getClass());}
	public Dup(int count, Directive dup)throws NotImplementedException {this(count, dup, dup.getClass());} // TODO: test @Bretana

	@Override
	public Memory exec(Memory m) throws NotImplementedException {
		throw new NotImplementedException("TODO");
	}

	@Override
	public String toString() {
		String ret = this.count + " " + Dup.MNEMONIC + " ";
		if (this.value == null)
			ret += "313"; //empty memory, "random" values
		else if (this.value instanceof Character)
			ret += "'" + this.value.toString() + "'";
		else
			ret += this.value.toString();
		return ret;
	}
}
