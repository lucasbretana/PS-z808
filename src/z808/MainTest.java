package z808;

import z808.command.directive.DW;
import z808.command.directive.Directive;
import z808.command.instruction.Instruction;
import z808.command.instruction.AddDX;
import util.ExecutionException;

public class MainTest{
	public static void main(String...args) throws ExecutionException{
		Instruction a = new AddDX(0);
		System.out.println("add ax dx:" + a);

		Directive dw = new DW(new Integer(3));
	}
}

