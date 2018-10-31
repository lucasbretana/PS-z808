package z808.command.directive;

import util.ExecutionException;
import util.NotImplementedException;
import z808.command.Command;
import z808.Memory;
import z808.Address;

public class DW extends Directive {
	public DW() throws NotImplementedException {
		throw new NotImplementedException("TODO");
	}
	public DW(Integer value) throws NotImplementedException {this();}
	public DW(Address addr)throws NotImplementedException {this();}
	public DW(Character value)throws NotImplementedException {this();}
	public DW(Directive dup)throws NotImplementedException {this();}

	public Memory exec (Memory mem) throws NotImplementedException, ExecutionException {
		throw new NotImplementedException("TODO");
	}
}

enum ValueType {
	INT, CHAR,
	ADDRESS, DUP
}
