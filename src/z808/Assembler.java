package z808;

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

		List<Tuple<String,String>> extern_names = null;
		ArrayList<String> public_names = null;

		int lst_sz = 0;
		Address curAddr = new Address(0x0);
		// FIRST STEP
		for (Command cmd : code) {
			// 1. every commnad has its address, based on previous command's size n' address
			curAddr = new Address(curAddr.intValue() + lst_sz);
			lst_sz = cmd.getSize();

			// 2. check for linkage directives
			if (cmd instanceof Public)
				public_names = Public.class.cast(cmd).getNames();
			else if (cmd instanceof Extern)
				extern_names = Extern.class.cast(cmd).getNames();

			// 3. commands with label become a local/global symol
			if ( ( cmd.getLabel() != null) && !cmd.getLabel().trim().equalsIgnoreCase("") ) {
				// 3.a global symbol
				if ( public_names.contains(cmd.getLabel().trim()) )
					mod.addGlobalSymbol(cmd, curAddr);
				// 3.b for now, every global symbol is also a local one
				mod.addLocalSymbol(cmd, curAddr);
			}

			// 4. some commands modifie the internal state of the assembler
			if ( cmd instanceof End ) {
				End cmd_end = End.class.cast(cmd);
				mod.setName(cmd_end.getEntryPoint());
			// } else if ( cmd instanceof z808.command.directive.Org ) {
			// ORG changes the curAddr
			}

			try {
				// 5. only commands that ocupies some memory go to the program
				if ( cmd.getSize() != 0 )
					mod.m_code.add(curAddr, cmd);
				mod.incSize(cmd.getSize());
			} catch (InvalidOperationException invO) {
				throw new ExecutionException(invO.getMessage() + "\n" + "Probably an invalid Org directive", invO);
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

					Tuple<String,String> present = extern_names.stream().filter(t -> t.a.equals(cmd.getUndefValue())).findAny().orElseThrow(() -> { return new ExecutionException("Undefined symbol not present in extern directive");});

				mod.addUseSymbol(cmd, addr);

				} else {
						// redefine the command value
						// TODO @Bretana as soon as the directive have a changedUndefinedValue method
						// cmd.defineValue(new_value);
				}
			}
		}

		return mod;
	}



	// TODO @Bretana: move this
	public static void fakeModule() throws ExecutionException {

		System.err.println(new Assembler().assembleCode(Arrays.asList(new Public("pone, pmax".split(",")), new Extern("etwo ".split(" "), (Extern.WORD+" ").split(" ")), new DW("pone", 1), new DW("pmax"), new AddCTE("sum", "etwo"), new End("module1"))).toString());
	}

}
