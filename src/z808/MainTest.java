package z808;

import java.util.Map;
import java.util.TreeMap;

import util.ExecutionException;
import util.NotImplementedException;
import util.FinishedException;

import z808.Processor;

import z808.command.Command;
import z808.command.directive.*;
import z808.command.instruction.*;

public class MainTest {
	public static void main(String...args)
		throws ExecutionException, NotImplementedException, FinishedException {

		TreeMap<Integer, Command> code = new TreeMap<Integer, Command>();
		code.put(0, new Equ (0, 5));       // EQU 5
		code.put(1, new AddCTE (1, 0x0));  // add AX 0x0
		code.put(4, new AddAX (4));        // add Ax Ax
		code.put(6, new AddAX (6));        // add Ax Ax
		code.put(8, new Hlt (8));          // hlt

		System.out.println("-- Code --");
		for (Map.Entry<Integer, Command> entry : code.entrySet()) {
			System.out.println(String.format("%04X %s"
																			 , entry.getKey()
																			 , entry.getValue()));
		}

		Processor p = new Processor ();
		System.out.println(p);
		System.out.println("Running code!");
		p.process(code);
		System.out.println(p);
	}
}

