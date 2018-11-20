package z808;

import java.util.ArrayList;

import util.ExecutionException;
import util.InvalidOperationException;
import util.SymbolNotFound;
import util.TooLongValue;
import z808.Module;
import z808.command.Command;
import z808.command.directive.DW;
import z808.command.directive.Equ;
import z808.command.instruction.AddAX;
import z808.command.instruction.AddCTE;
import z808.command.instruction.Hlt;
import z808.command.instruction.Instruction;
import z808.command.instruction.Jump;
import z808.command.instruction.MovAXMEM;
import z808.command.instruction.SubCTE;
import z808.memory.Address;

public class Linker {
	private ArrayList<Triple<String, Number, Boolean>> m_GlobalSymbolTable;
	
	private ArrayList<Module> m_Module;
	private int m_ModuleAlign;
	
	public Linker() {
		m_GlobalSymbolTable = new ArrayList<>();
		
		m_Module = new ArrayList<>();
		m_ModuleAlign = 0;
	}
	
	public void InsertModule(Module module) {
		m_Module.add(module);

		for (Triple<String, Number, Boolean> symbol : module.global_symbol_table) {
			if (symbol.c) // relative
				symbol.b = ((int)symbol.b + m_ModuleAlign);
			
			m_GlobalSymbolTable.add(symbol);
		}
		
		m_ModuleAlign += module.getCodeSize();
	}
	
	public void LinkModules() throws SymbolNotFound {
		fixSymbols();
		linkModules();
	}
	
	public static void LinkerTests() throws InvalidOperationException, TooLongValue, ExecutionException {
		System.out.println("-- Running Linker Tests --");
		
		Program code = new Program();
		code.add(new Address(0x0), new Equ (5));       			// EQU 5
		code.add(new Address(0x1), new AddCTE (0x0));  			// ADD AX, 0x0
		code.add(new Address(0x4), new MovAXMEM ("varTest"));   // MOV AX, varTest
		code.add(new Address(0x6), new AddAX ("labelJmp"));     // labelJmp: ADD AX, AX
		code.add(new Address(0x8), new SubCTE (0x0));  			// SUB AX, 0x0
		code.add(new Address(0xb), new Jump ("labelJmp"));  	// JMP labelJmp
		code.add(new Address(0xe), new Hlt ());        			// HLT
		code.add(new Address(0xf), new DW ("varTest", 5));      // DW 5
		
		Module[] mod = new Module[2];
		
		mod[0] = new Module(code);
		mod[0].addGlobalSymbol(code.get(new Address(0xf)), 0xf, true);
		mod[0].addGlobalSymbol(code.get(new Address(0x6)), 0x6, true);
		mod[0].addUseSymbol(code.get(new Address(0x4)));
		mod[0].addUseSymbol(code.get(new Address(0xb)));
		
		mod[1] = new Module();
		
		Linker l = new Linker();
		l.InsertModule(mod[0]);
		l.InsertModule(mod[1]);
		
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
			
			for (Command useTable : module.use_table) {
				String symbolName = useTable.getLabel();
				
				Boolean fixed = false;
				
				for (Triple<String, Number, Boolean> symbol : m_GlobalSymbolTable) {
					if (symbolName.equals(symbol.a)) {
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
	
	private void fixCommand(Command command, Triple<String, Number, Boolean> symbol) {
		Instruction inst = (Instruction)command;
		inst.fixSymbol((int)symbol.b);
		
		System.out.println("CMD FIXED: " + inst.toString());
	}
}
