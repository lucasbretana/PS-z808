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
	static public void all(Boolean verb) throws ExecutionException {
		if (verb) System.err.println("## -- begin directives tests -- ##");
		testDW(verb);
		testDup(verb);
		testLinkageDirectives(verb);
		if (verb) System.err.println("## -- end directives tests -- ##");
	}
	
	static public void testLinkageDirectives(Boolean verb) throws ExecutionException {
		if (verb) System.err.println("-- Testing linkage directive --");
		Directive d = null;

		d = new Public(("lbl, lbl1, lbl2, lbl3, lbl4").split(","));
		if (verb) System.err.println("Random public: " + d);

		String []list = {"func1", "func2", "100"};
		String []t_list = {Extern.NEAR, Extern.WORD, Extern.ABS};

		d = new Extern(list, t_list);
		if (verb) System.err.println("Random extern: " + d);

		d = null;
		System.gc();
		if (verb != null) System.err.println("-- Linkage directives test OK --");
	}


	static public void testDW(Boolean verb) throws ExecutionException {
		Directive d = null;
		if (verb) System.err.println("-- Testing DW directive --");

		d = new DW();
		if (verb) System.err.println("Empty: " + d.toString());
		d = new DW(1024); 
		if (verb) System.err.println("Int: " + d.toString());
		d = new DW('X');
		if (verb) System.err.println("Char: " + d.toString());
		d = new DW(new Address(0x1024));
		if (verb) System.err.println("Addr: " + d.toString());
		d = new DW(new Dup(10));
		if (verb) System.err.println("Dup: " + d.toString());
		d = new DW("fst_label", new Dup(10));
		if (verb) System.err.println("Dup: " + d.toString());

		d = null;
		System.gc();
		if (verb != null) System.err.println("-- DW test OK --");
	}

	static public void testDup(Boolean verb) throws ExecutionException {
		if (verb) System.err.println("-- Testing Dup directive --");
		Directive d = null;

		d = new Dup(10);
		if (verb) System.err.println(d.toString());
		d = new Dup(10, 2048);
		if (verb) System.err.println(d.toString());
		d = new Dup(10, 'Y');
		if (verb) System.err.println(d.toString());
		d = new Dup(10, new Address(0x2048));
		if (verb) System.err.println(d.toString());
		try {
			d.setLabel("AnyLabel");
		} catch (ExecutionException ex) {
			if (verb) System.err.println("Catch the exception: " + ex.getLocalizedMessage());
		}
		d = null;
		System.gc();
		if (verb != null) System.err.println("-- Dup test OK --");
	}

}

