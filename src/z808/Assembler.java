package z808;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import util.ExecutionException;
import util.NotImplementedException;

import z808.command.Command;
import z808.command.directive.*;
import z808.command.instruction.*;
import z808.memory.Address;
import z808.Module;

public class Assembler {
	/** Creates a module with the given commands
	 * @param code the code the assemble
	 * @return a new module
	 */
	public static Module assembleCode(List<Command> code) throws ExecutionException {
		Address PC = new Address(0x0);
		Integer IC = 0;

		Module m = new Module();

		List<?> extern_names = new ArrayList<String>();
		List<?> public_names = new ArrayList<String>();

		// TODO: @Bretana remove this
		extern_names = Arrays.asList("vet", "v_sz");
		public_names = Arrays.asList("one", "max");

		int lst_sz = 0;
		Address curAddr = new Address(0x0);
		for (Command cmd : code) {
			curAddr = new Address(curAddr.intValue() + lst_sz);
			lst_sz = cmd.getSize();
			if (cmd instanceof DW) {
				// find in the public_names
				if ( (cmd.getLabel() != null) && (public_names.indexOf( cmd.getLabel() ) != -1) ) {
					m.addGlobalSymbol(cmd, curAddr, true);
				} else if ( !cmd.isDefined() && (extern_names.indexOf( cmd.getLabel() ) != -1) ) {
					m.addUseSymbol(cmd);
				}
			} else {
				throw new NotImplementedException("UNFINISHED");
			}
			if (cmd.getSize() != 0)
				m.m_code.add(curAddr, cmd);
		}

		return m;
	}

	public static void fakeModule() throws ExecutionException {
		System.out.println(Assembler.assembleCode(Arrays.asList(new DW("one", 1), new DW("max"), new AddCTE("Sum", 1))).toString());
	}
}
