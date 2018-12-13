package z808;

import java.io.IOException;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.charset.Charset;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import util.ExecutionException;
import util.InvalidOperationException;
import util.NotImplementedException;
import util.Tuple;

import z808.command.Command;
import z808.command.directive.*;
import z808.command.instruction.*;
import z808.memory.Address;
import z808.Module;

public class Assembler {
	/**
	 * Creates a module
	 */
	public Assembler() { }

	/** 
	 * Creates a new module from the list of commands
	 * @param code the code the assemble
	 * @return a new module
	 */
	public Module assembleCode(List<Command> code) throws ExecutionException {
		Module mod = new Module();

		int seg_left = 3;
		Integer cur_seg_size = null;
		Address cur_seg_addr = null;
		String cur_seg_name = null;

		if(!(code.get(0) instanceof Segment)) {
			System.err.println("Warning! Everything on the same Segment");
			cur_seg_name = "Codigo";
			seg_left = 0;

			mod.m_code.setSizeCodeSegment(new Integer(0));
			mod.m_code.setStartCodeSegment(new Address(0x0));

			cur_seg_size = mod.m_code.getSizeCodeSegment();
		}

		List<Tuple<String,String>> extern_names = new ArrayList<>();;
		ArrayList<String> public_names = new ArrayList<>();;

		int lst_seg_sz = 0;
		int lst_sz = 0;
		Address curAddr = new Address(0x0);
		// FIRST STEP
		for (Command cmd : code) {
			// 0. talk segments
			if (cmd instanceof Segment) {
				if (seg_left-- == 0)
					throw new ExecutionException("No more than three segments");

				if (cur_seg_name != null)
					throw new ExecutionException("Segment " + cur_seg_name + " did not have an End");

				cur_seg_name = Segment.class.cast(cmd).getName();
				if (Segment.class.cast(cmd).getName().equals("Codigo")) {
					mod.m_code.setStartCodeSegment(new Address(curAddr.intValue() + lst_sz));
				} else if(Segment.class.cast(cmd).getName().equals("Pilha")) {
					mod.m_code.setStartStackSegment(new Address(curAddr.intValue() + lst_sz));
				} else{
					mod.m_code.setStartDataSegment(new Address(curAddr.intValue() + lst_sz));
				}
				continue;
			} else if (cmd instanceof Ends) {
				if (Ends.class.cast(cmd).getName().equals("Codigo")) {
					mod.m_code.setSizeCodeSegment(mod.getSize() - lst_seg_sz);
					lst_seg_sz += mod.m_code.getSizeCodeSegment();
				} else if(Ends.class.cast(cmd).getName().equals("Pilha")) {
					mod.m_code.setSizeStackSegment(mod.getSize() - lst_seg_sz);
					lst_seg_sz += mod.m_code.getSizeStackSegment();
				} else{
					mod.m_code.setSizeDataSegment(mod.getSize() - lst_seg_sz);
					lst_seg_sz += mod.m_code.getSizeDataSegment();
				}

				cur_seg_name = null;
				cur_seg_size = null;
				cur_seg_addr = null;
				continue;
			}


			// 1. every commnad has its address, based on previous command's size n' address
			curAddr = new Address(curAddr.intValue() + lst_sz);
			try {
				lst_sz = cmd.getSize();
			} catch (Exception ex){
				ex.printStackTrace();
				System.err.println(ex.getMessage());
				System.err.println(cmd);
				System.err.println(cmd.getClass().getSimpleName() + " " + cmd);
				System.exit(1);
			}

			// 2. check for linkage directives
			if (cmd instanceof Public) {
				public_names = Public.class.cast(cmd).getNames();
				continue;
			} else if (cmd instanceof Extern) {
				extern_names = Extern.class.cast(cmd).getNames();
				continue;
			}

			// 3. commands with label become a local/global symol
			if ( ( cmd.getLabel() != null) && !cmd.getLabel().trim().equalsIgnoreCase("") ) {
				if (cmd instanceof Equ) System.out.println("REMOVE: Equ goes into some table" + cmd.toString());
				// 3.a global symbol
				if ( (public_names != null) && (public_names.contains(cmd.getLabel().trim())) )
					mod.addGlobalSymbol(cmd, curAddr);
				else // 3.b only defined and not global symbols go to local
					mod.addLocalSymbol(cmd, curAddr);
			}

			try {
				// 4. only commands that ocupies some memory go to the program
				if ( cmd.getSize() != 0 )
					mod.m_code.add(curAddr, cmd);
				mod.incSize(cmd.getSize());
			} catch (InvalidOperationException invO) {
				throw new ExecutionException(invO.getMessage() + "\n" + "Probably an invalid Org directive", invO);
			}

			// 5. some commands modifie the internal state of the assembler
			if ( cmd instanceof End ) {
				End cmd_end = End.class.cast(cmd);
				mod.setName(cmd_end.getEntryPoint());
				break;
			// } else if ( cmd instanceof z808.command.directive.Org ) {
			// ORG changes the curAddr
			}

		}
		// SECOND STEP
		//
		lst_sz = 0;
		curAddr = new Address(0x0);
		for (Map.Entry<Address,Command> entry : mod.m_code.entrySet()) {
			Command cmd = entry.getValue();
			Address addr = entry.getKey();

			// sanity check
			if ( !addr.equals(curAddr) )
				throw new ExecutionException("Invalid address, doesn't match second count.\n" +
						" Command is " + cmd.toString() +
						" Was at " + addr.toString() +
						" Should be " + curAddr);
			lst_sz = cmd.getSize();
			curAddr = new Address(curAddr.intValue() + lst_sz);
			// end check

			// deal with undefined symbols
			if ( !cmd.isDefined() ){
				Number new_value = mod.findInGlobalByName(cmd.getUndefValue());
				if (new_value == null)
					new_value = mod.findInLocalByName(cmd.getUndefValue());

				if (new_value == null) {
					Tuple<String,String> present = extern_names.stream().filter(t -> t.a.equals(cmd.getUndefValue())).findAny().orElseThrow(() -> { return new ExecutionException("Undefined symbol (\"" + cmd.getUndefValue() + "\") not present in extern directive, used on " + cmd.toString() + " at " + addr + " an " + cmd.getClass().getSimpleName());});

					mod.addUseSymbol(cmd, addr);
				} else {
					// redefine the command value
					// TODO @Bretana as soon as the directive have a changedUndefinedValue method
					// cmd.defineValue(new_value);
					cmd.setUndefValue(new_value.intValue());
				}
			}
		}

		return mod;
	}



	// Test
	/**
	 * Tests some modules
	 * @param verb is true for most output, false for start/end messages and null for only afirmative messages
	 */
	public static void testModules(Boolean verb) throws ExecutionException { testM1(verb); testM2(verb); }
	public static void testM1(Boolean verb) throws ExecutionException {
		if (verb) System.err.println("-- M1 test --"); // debug output
		Assembler azm = new Assembler();
		
		Module m = azm.assembleCode(Arrays.asList(
			new Public("pone,pmax,ppow".split(",")), 
			new Extern("etwo,eresult,epow".split(","), (Extern.ABS + "," + Extern.WORD + "," + Extern.NEAR).split(",")), 
			new DW("pone", 1), 
			new DW("pmax"), 
			new DW("ppow"), 
			new AddCTE("sum1", "etwo"), 
			new AddCTE("sum2", "eresult"), 
			new End("module1")
		));

		if (verb == null) return; // no output
		if (verb) //verbose output
			System.err.println(m.toString());
		if (verb != null) System.err.println("-- M1 test OK --"); // debug output
	}

	public static void testM2(Boolean verb) throws ExecutionException {
		if (verb) System.err.println("-- M2 test --"); // debug output
		Assembler azm = new Assembler();
		Translator tran = new Translator();
		
		List<Command> res = null;
		try {
			res = tran.convertCode(Files.readAllLines(Paths.get("sample/", "code1.asm"), Charset.forName("UTF-8")));
		} catch (IOException ioE) {
			throw new ExecutionException("Some error reading the code1.asm file", ioE);
		}
		if (verb) {
			for(Command cmd : res)
				if(cmd instanceof Directive)
					System.out.println("Directive " + cmd.getClass().getSimpleName() + "!: " + Directive.class.cast(cmd).toCode());
				else if(cmd instanceof Instruction)
					System.out.println("Instruction " + cmd.getClass().getSimpleName() + "!: " + Instruction.class.cast(cmd).toString());
		}
		Module m = azm.assembleCode(res);

		if (verb == null) return; // no output
		if (verb) //verbose output
			System.err.println(m.toString());
		if (verb != null) System.err.println("-- M2 test OK --"); // debug output
	}

}
