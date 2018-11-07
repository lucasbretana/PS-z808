package z808;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import util.ExecutionException;
import util.NotImplementedException;

import z808.command.Command;
import z808.command.directive.*;
import z808.memory.Address;
import z808.Module;

public class Assembler {
	/** Creates a module with the given commands
	 * @param code the code the assemble
	 * @return a new module
	 */
	public static Module Assemble(List<Command> code) throws ExecutionException {
		Address PC = new Address(0x0);
		Integer IC = 0;

		Module m = new Module();

		List<String> extern_names = new ArrayList();
		List<String> public_names = new ArrayList();

		// TODO: @Bretana remove
		extern_names = Arrays.asList("one", "vet");
		public_names = Arrays.asList("one", "vet");

		for (Command c : code) {
			if (c instanceof DW) {
				// find in the public_names
				if ( (c.getLabel() != null) && (public_names.indexOf( c.getLabel() ) != -1) ) {
					m.addGlobalSymbol(c);
				} else if (c.hasUndefined
			} else {
				throw new NotImplementedException("TODO");
			}
			if (c.getSize() != 0)
				m.m_code.add(PC.intValue() + c.getSize())
		}

		return m;
	}

	public static void fakeModule() throws ExecutionException {
		System.out.println(Assembler.Assemble(Arrays.asList(new DW("one", 2), new DW("vet", new Dup(2)))).toString());
	}
}
