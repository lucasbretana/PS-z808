package z808;

import z808.command.directive.DW;
import z808.command.directive.Directive;
import z808.command.instruction.Instruction;
import z808.command.instruction.AddDX;
import util.ExecutionException;

public class MainTest{
	public static void main(String...args) throws ExecutionException{
		Directive dw = null;
		dw = new DW();
		System.out.println(dw.toString());
		dw = new DW(1024);
		System.out.println(dw.toString());
		dw = new DW('X');
		System.out.println(dw.toString());
		dw = new DW(new Address(0x1024)); // also works with new Address("0x1024"); must be positive
		System.out.println(dw.toString());
		//dw = new DW(...);
		//System.out.println(dw.toString());
	}
}

