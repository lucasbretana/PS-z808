package z808;

import java.util.*;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.charset.Charset;

import util.FinishedException;
import util.ExecutionException;
import util.TestFaliedException;
import util.NotImplementedException;

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
		Boolean debug = false;
		if (args.length > 0) debug = Boolean.parseBoolean(args[0]);

		System.out.println("-- Running Processor Tests --");
		try {
			MainTest.ProcessorTests();
			System.out.println("Processor is Ok!");
		} catch (TestFaliedException e) {
		  System.err.println("Failed Processor Tests:" + e);
		}

		// @Bretana tests
		try {
			//z808.command.directive.GenericDirectiveTester.all(debug);
			//z808.Assembler.testModules(debug);
			// TODO add translator tests
			z808.Translator.testTranslator(debug);
		} catch (ExecutionException ex) {
			System.err.println("Something went wront on Bretana's tests");
		  ex.printStackTrace();
		}

		try {
			System.out.println("--- Macro Processor Tests ---");
			MainTest.MacroProcessorTests2();
		} catch (ExecutionException e) {
			System.out.println("Failed Macro Tests:  " + e);
		}

		// Implement try to other tests here.

		Linker.LinkerTests();
	}

	private static void ProcessorTests()
		throws ExecutionException, NotImplementedException,
					 FinishedException, TestFaliedException {
		String expected
			= "0000 0005\n"
			+ "0001 0002\n"
			+ "0002 B8 0001\n"
			+ "0005 8B F0\n"
			+ "0007 B8 0000\n"
			+ "000A F7 E6\n"
			+ "000C A3 0000\n"
			+ "000F F4\n";

		Program code = new Program();
		code.add(new Address(0x0), new Equ(5));         // Equ 5
		code.add(new Address(0x1), new Equ(2));         // Equ 2
		code.add(new Address(0x2), new MovAXMEM(0x1));  // mov AX 1
		code.add(new Address(0x5), new MovSIAX());      // mov SI AX
		code.add(new Address(0x7), new MovAXMEM(0x0));  // mov AX 0
		code.add(new Address(0xA), new MultSI());       // mul SI
		code.add(new Address(0xC), new MovMEMAX(0x0));  // mov 0 AX
		code.add(new Address(0x0f), new Hlt());          // hlt

		Processor p = new Processor();
		p.load(code);
		if (expected.compareTo(p.codeToString()) != 0)
			throw new TestFaliedException(-1, p.codeToString());
		expected
			= "CL:10\n"
			+ "RI:F4\n"
			+ "REM:0F\n"
			+ "RBM:F4\n"
			+ "SP:XX\n"
			+ "SR:XX\n"
			+ "AX:0A\n"
			+ "DX:00\n"
			+ "SI:02\n";
		p.process();
		if (expected.compareTo(p.registersToString()) != 0)
			throw new TestFaliedException(-2, p.registersToString());
		return;
	}

	private static void MacroProcessorTests() throws ExecutionException {
		MacroProcessor macro_proc = null;

		String label = "AddSub";
		ArrayList<Command> prog = new ArrayList<Command>();
		ArrayList<String> params = new ArrayList<String>();
		ArrayList<String> cmds = new ArrayList<String>();

		ArrayList<String> params_call = new ArrayList<String>();

		params.add("P1");
		params.add("P2");
		cmds.add("add P1 P1");
		cmds.add("sub P1 P2");
		cmds.add("add P1 P1");
		params_call.add("AX");
		params_call.add("DX");

		prog.add(new MacroDef(label, params, cmds)); 
		prog.add(new Endm());
		prog.add(new DW(5));
		prog.add(new DW(6));
		prog.add(new MacroCall(label, params_call));
		prog.add(new Hlt());
		macro_proc = new MacroProcessor(prog);
		System.out.println("\n-- B4 process --");

		for(Command str : prog) {
			System.out.print(str.toString() + "\n");
		}

		ArrayList<Command> result = new ArrayList<Command>();

		result = macro_proc.process(prog);
		System.out.println("\n-- After process --");
		
		for(Command cmd : result) {
			System.out.println(cmd.toString());
		}
	}

	private static void MacroProcessorTests2() throws ExecutionException, IOException {
		Translator transl = new Translator();
		MacroProcessor macro_proc = null;

		//le td do arquivo 
		//List<String> arq = Files.readAllLines(Paths.get("sample/", "ex3_macro.asm"), Charset.forName("UTF-8"));
		List<Command> prog = new ArrayList<Command>();
		ArrayList<String> params = new ArrayList<String>();
		ArrayList<String> commands = new ArrayList<String>();
		ArrayList<String> params_call1 = new ArrayList<String>();
		ArrayList<String> params_call2 = new ArrayList<String>();


		String label = "SomaMem";
		commands.add("add Mem1 Mem1");
		commands.add("add Mem1 Mem2");
		commands.add("sub Mem1 Mem2");
		commands.add("add Mem1 Mem2");
		commands.add("add Mem1 Mem2");
		//prog = transl.convertCode(arq);
		params.add("Mem1");
		params.add("Mem2");

		params_call1.add("AX");
		params_call1.add("DX");

		params_call2.add("AX");
		params_call2.add("AX");

		prog.add(new MacroDef(label, params, commands)); 
		prog.add(new Endm());
		prog.add(new Equ(8));
		prog.add(new Equ(3));
		prog.add(new MacroCall(label, params_call1));
		prog.add(new Equ(9));
		prog.add(new MacroCall(label, params_call2));
		prog.add(new Hlt());

		macro_proc = new MacroProcessor(prog);

		System.out.println("\n-- B4 process --");
		for(Command str : prog) {
			System.out.print(str.toString() + "\n");
		}

		ArrayList<Command> result = new ArrayList<Command>();
		result = macro_proc.process(prog);
		
		System.out.println("\n-- After process --");
		
		for(Command cmd : result) {
			System.out.println(cmd.toString());
		}
	}
}
