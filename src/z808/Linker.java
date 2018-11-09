package z808;

import java.util.ArrayList;

import util.SymbolNotFound;
import z808.Module;
import z808.command.Command;

public class Linker {
	private ArrayList<Triple<String, Number, Boolean>> m_GlobalSymbolTable;
	private ArrayList<Module> m_Module;
	
	public Linker() {
		m_GlobalSymbolTable = new ArrayList<>();
		m_Module = new ArrayList<>();
	}
	
	public void InsertModule(Module module) {
		m_Module.add(module);

		for (Triple<String, Number, Boolean> symbol : module.global_symbol_table)
			m_GlobalSymbolTable.add(symbol);
	}
	
	public void LinkModules() throws SymbolNotFound {
		fixSymbols();
		linkModules();
	}
	
	public static void LinkerTests() {
		System.out.println("-- Running Linker Tests --");
		
		Linker l = new Linker();
		l.InsertModule(new Module());
		l.InsertModule(new Module());
		l.InsertModule(new Module());
		l.InsertModule(new Module());
		
		try {
			l.LinkModules();
		} catch (SymbolNotFound e) {
			System.err.println("Failed Linker Tests:" + e);
		} finally {
			System.out.println("-- Linker Tests Finished --");
		}
	}
	
	private void fixSymbols() throws SymbolNotFound {
		for (Module module : m_Module) {
			if (module.use_table == null)
				continue;
			
			for (Tuple<Command, Integer> useTable : module.use_table) {
				String symbolName = useTable.a.getLabel(); // symbol name = label??
				
				Boolean fixed = false;
				
				for (Triple<String, Number, Boolean> symbol : m_GlobalSymbolTable) {
					if (symbolName == symbol.a) {
						fixCommand(useTable, symbol);
						
						fixed = true;
						break;
					}
				}
				
				if (!fixed) {
					throw new SymbolNotFound(symbolName + " not found!");
				}
			}
		}
	}
	
	private void linkModules() {
		//TODO: Link Modules.
	}
	
	private void fixCommand(Tuple<Command, Integer> command, Triple<String, Number, Boolean> symbol) {
		// TODO: Fix Command.
	}
}
