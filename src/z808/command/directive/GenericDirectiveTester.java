package z808.command.directive;

import util.ExecutionException;
import util.NotImplementedException;
import util.FinishedException;

import z808.Program;
import z808.Processor;
import z808.memory.Address;
import z808.command.Command;
import z808.command.directive.*;
import z808.command.instruction.*;

public class GenericDirectiveTester {
	static public void all() throws ExecutionException {
		System.out.println("## -- begin directives tests -- ##");
		testDW();
		System.out.println("## -- end directives tests -- ##");
	}
	
	static public void testDW() throws ExecutionException {
		Directive d = null;
		System.err.println(" -- Testing DW directive --");
		d = new DW();
		System.out.println("Empty: " + d.toString());
		d = new DW(1024);
		System.out.println("Int: " + d.toString());
		d = new DW('X');
		System.out.println("Char: " + d.toString());
		d = new DW(new Address(0x1024));
		System.out.println("Addr: " + d.toString());
		d = new DW(new Dup(10));
		System.out.println("Dup: " + d.toString());
		d = new DW("fst_label", new Dup(10));
		System.out.println("Dup: " + d.toString());
		d = null;
		System.gc();
	}

	static public void testDup() throws ExecutionException {
		Directive d = null;
		d = new Dup(10);
		System.out.println(d.toString());
		d = new Dup(10, 2048);
		System.out.println(d.toString());
		d = new Dup(10, 'Y');
		System.out.println(d.toString());
		d = new Dup(10, new Address(0x2048));
		System.out.println(d.toString());
		try {
			d.setLabel("AnyLabel");
		} catch (ExecutionException ex) {
			System.err.println(ex.getLocalizedMessage());
		}
	}

		//d = new End("Test", new Address(1), "First Module w label");
		//System.out.println(d.toString());
		//d = new End(new Address(1), "First Module");
		//System.out.println(d.toString());

		//d = new Segment("ExampleName");
		//System.out.println(d.toString());
		//d = new Ends("ExampleName");
		//System.out.println(d.toString());

}

