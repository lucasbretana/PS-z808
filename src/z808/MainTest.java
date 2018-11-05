package z808;

import java.util.Map;
import java.util.TreeMap;

import util.ExecutionException;
import util.NotImplementedException;

import z808.Processor;

import z808.command.Command;
import z808.command.directive.*;
import z808.command.instruction.*;

public class MainTest {
	public static void main(String...args)
		throws ExecutionException, NotImplementedException {

		TreeMap<Integer, Command> code = new TreeMap<Integer, Command>();
		code.put(0, new AddDX (0));
		code.put(2, new AddAX (2));
		code.put(4, new SubDX (4));
		code.put(6, new Hlt (6));

		System.out.println("-- Code --");
		for (Map.Entry<Integer, Command> entry : code.entrySet()) {
			System.out.println(String.format("%04X %s"
																			 , entry.getKey()
																			 , entry.getValue()));
		}

		Processor p = new Processor ();
		System.out.println(p);
		p.process(code);
		System.out.println(p);
	}
}

