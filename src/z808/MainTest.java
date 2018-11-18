package z808;

import java.util.*;

import util.TestFaliedException;
import util.ExecutionException;
import util.NotImplementedException;
import util.FinishedException;

import z808.Program;
import z808.Processor;
import z808.Translator;
import z808.memory.Address;
import z808.command.Command;
import z808.command.directive.*;
import z808.command.instruction.*;

public class MainTest {
	public static void main(String...args)
		throws Exception {
	  //System.out.println("-- Running Processor Tests --");
		//try {
		//	MainTest.ProcessorTests();
		//	System.out.println("Processor is Ok!");
		//} catch (TestFaliedException e) {
		//  System.err.println("Failed Processor Tests:" + e);
		//}

		//// @Bretana tests
		//try {
		//  z808.command.directive.GenericDirectiveTester.all();
		//  z808.Assembler.fakeModule();
		//} catch (ExecutionException ex) {
		//  ex.printStackTrace();
		//}

		translatorTests(args);
		// Implement try to other tests here.
	}

	private static void ProcessorTests()
		throws ExecutionException, NotImplementedException,
					 FinishedException, TestFaliedException {
		String expected
			= "0000 0005\n"
			+ "0001 05 0000\n"
			+ "0004 03 C0\n"
			+ "0006 03 C0\n"
			+ "0008 2B 0000\n"
			+ "000B F4\n";

		Program code = new Program();
		code.add(new Address(0x0), new Equ (5));       // EQU 5
		code.add(new Address(0x1), new AddCTE (0x0));  // add AX 0x0
		code.add(new Address(0x4), new AddAX ());      // add AX AX
		code.add(new Address(0x6), new AddAX ());      // add AX AX
		code.add(new Address(0x8), new SubCTE (0x0));  // sub AX 0x0
		code.add(new Address(0xb), new Hlt ());        // hlt

		Processor p = new Processor();
		p.load(code);
		if (expected.compareTo(p.codeToString()) != 0)
			throw new TestFaliedException(-1, p.codeToString());
		expected
			= "CL:0C\n"
			+ "RI:F4\n"
			+ "REM:0B\n"
			+ "RBM:F4\n"
			+ "AX:0F\n"
			+ "DX:00\n";
		p.process();
		if (expected.compareTo(p.registersToString()) != 0)
			throw new TestFaliedException(-2, p.registersToString());
		return;
	}


	private static void translatorTests(String...args) throws Exception {
		try {
			List<String> l = Arrays.asList(args);
			System.out.println(new Translator(l).convert().toString());
		} catch (Exception e) {
			e.printStackTrace();
			System.err.println("\n" + e.getMessage());
		}
	}

}

