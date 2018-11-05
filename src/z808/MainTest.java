package z808;

import java.util.Map;
import java.util.TreeMap;

import util.ExecutionException;
import util.NotImplementedException;
import util.FinishedException;

import z808.Processor;
import z808.memory.Address;
import z808.command.Command;
import z808.command.directive.*;
import z808.command.instruction.*;

public class MainTest {
	public static void main(String...args)
		throws ExecutionException, NotImplementedException, FinishedException {

		TreeMap<Address, Command> code = new TreeMap<Address, Command>();
		Address a0 = new Address(0); code.put(a0, new Equ (a0, 5));       // EQU 5
		Address a1 = new Address(1); code.put(a1, new AddCTE (a1, 0x0));  // add AX 0x0
		Address a4 = new Address(4); code.put(a4, new AddAX (a4));        // add Ax Ax
		Address a6 = new Address(6); code.put(a6, new AddAX (a6));        // add Ax Ax
		Address a8 = new Address(8); code.put(a8, new Hlt (a8));          // hlt

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
