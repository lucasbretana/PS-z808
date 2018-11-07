package z808;

import java.util.Map;

import util.ExecutionException;
import util.NotImplementedException;
import util.FinishedException;

import z808.Program;
import z808.Processor;
import z808.memory.Address;
import z808.command.Command;
import z808.command.directive.*;
import z808.command.instruction.*;

public class MainTest {
	public static void main(String...args)
		throws ExecutionException, NotImplementedException, FinishedException {

		Program code = new Program();
		code.add(new Address(0x0), new Equ (5));       // EQU 5
		code.add(new Address(0x1), new AddCTE (0x0));  // add AX 0x0
		code.add(new Address(0x4), new AddAX ());      // add AX AX
		code.add(new Address(0x6), new AddAX ());      // add AX AX
		code.add(new Address(0x8), new SubCTE (0x0));  // sub AX 0x0
		code.add(new Address(0xb), new Hlt ());        // hlt

		System.out.println("-- Code --");
		for (Map.Entry<Address, Command> entry : code.entrySet()) {
			System.out.println(entry.getKey() + " " + entry.getValue());
		}

		Processor p = new Processor ();
		System.out.println(p);
		System.out.println("Running code!");
		p.process(code);
		System.out.println(p);

		z808.command.directive.GenericDirectiveTester.all();
	}
}
