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
		code.put(new Equ (new Address(0), 5));       // EQU 5
		code.put(new AddCTE (new Address(1), 0x0));  // add AX 0x0
		code.put(new AddAX (new Address(4)));        // add Ax Ax
		code.put(new AddAX (new Address(6)));        // add Ax Ax
		code.put(new Hlt (new Address(8)));          // hlt

		System.out.println("-- Code --");
		for (Map.Entry<Address, Command> entry : code.entrySet()) {
			System.out.println(entry.getKey() + " " + entry.getValue());
		}

		Processor p = new Processor ();
		System.out.println(p);
		System.out.println("Running code!");
		p.process(code);
		System.out.println(p);
	}
}
