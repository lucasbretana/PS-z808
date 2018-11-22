package z808;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import util.ExecutionException;
import util.InvalidOperationException;
import util.NotImplementedException;

import z808.command.Command;
import z808.command.directive.*;
import z808.command.instruction.*;
import z808.memory.Address;
import z808.Module;

public class Assembler {
	public Assembler() { }

	/** Creates a module with the given commands
	 * @param code the code the assemble
	 * @return a new module
	 */
	public Module assembleCode(List<Command> code) throws ExecutionException {
		Module mod = new Module();

		List<?> extern_names = null;
		ArrayList<String> public_names = null;

		// TODO: @Bretana remove this
		extern_names = Arrays.asList("evet", "ev_sz"); // extern_names = ( code.stream().findFirst(x -> (x instanceof z808.command.directive.Extern)) // some magic cast).asList();

		int lst_sz = 0;
		Address curAddr = new Address(0x0);
		// FIRST STEP
		for (Command cmd : code) {
			System.out.println("> " + cmd.getClass().getSimpleName());
			// 1. every commnad has its address, based on previous command's size n' address
			curAddr = new Address(curAddr.intValue() + lst_sz);
			lst_sz = cmd.getSize();

			// 2. check for linkage directives
			if (cmd instanceof Public)
				public_names = Public.class.cast(cmd).getNames();
			//else if (cmd instanceof Extern)
			//	extern_names = Extern.class.cast(cmd).getSymbols();

			// 3. commands with label become a local/global symol
			if ( ( cmd.getLabel() != null) && !cmd.getLabel().trim().equalsIgnoreCase("") ) {
				// 3.a global symbol
				if ( public_names.contains(cmd.getLabel().trim()) )
					mod.addGlobalSymbol(cmd, curAddr);
				// 3.b for now, every global symbol is also a local one
				mod.addLocalSymbol(cmd, curAddr);
			}

			// 4. some commands modifie the internal state of the assembler
			if ( cmd instanceof z808.command.directive.End ) {
				z808.command.directive.End cmd_end = z808.command.directive.End.class.cast(cmd);
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
				Number new_value = mod.findInGlobalByName(cmd.getLabel());
				if (new_value == null)
					new_value = mod.findInLocalByName(cmd.getLabel());

				if (new_value == null) {
					if ( extern_names.contains( cmd.getLabel() ) )
						mod.addUseSymbol(cmd, addr);
					else
						throw new ExecutionException("Undefined symbols not present in extern directive");
				} else {
						// redefine the command value
						// TODO @Bretana as soon as the directive have a changedUndefinedValue method
						// cmd.defineValue(new_value);
				}
			}
		}

		return mod;
	}

	public static void fakeModule() throws ExecutionException {

		System.out.println(new Assembler().assembleCode(Arrays.asList(new Public("pone, pmax".split(",")), new DW("pone", 1), new DW("pmax"), new AddCTE("sum", 1), new End("module1"))).toString());
	}

}
