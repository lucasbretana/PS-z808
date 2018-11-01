package z808;

import z808.command.directive.DW;
import z808.command.directive.Dup;
import z808.command.directive.Directive;
import z808.command.instruction.Instruction;
import z808.command.instruction.AddDX;
import util.ExecutionException;

public class MainTest{
	public static void main(String...args) throws ExecutionException{
		Directive d = null;
		System.out.println("Testing Dup");
		d = new Dup(10);
		System.out.println(d.toString());
		d = new Dup(10, 2048);
		System.out.println(d.toString());
		d = new Dup(10, 'Y');
		System.out.println(d.toString());
		d = new Dup(10, new Address(0x2048));
		System.out.println(d.toString());

		System.out.println("Testing DW");
		d = new DW();
		System.out.println(d.toString());
		d = new DW(1024);
		System.out.println(d.toString());
		d = new DW('X');
		System.out.println(d.toString());
		d = new DW(new Address(0x1024));
		System.out.println(d.toString());
		d = new DW(new Dup(10));
		System.out.println(d.toString());

	}
}

