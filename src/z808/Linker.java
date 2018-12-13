package z808;

import java.util.ArrayList;
import java.util.Map;
import java.util.TreeMap;

import util.ExecutionException;
import util.InvalidOperationException;
import util.SymbolNotFound;
import util.TooLongValue;
import util.Tuple;
import z808.Module;
import z808.command.Command;
import z808.command.directive.DW;
import z808.command.directive.Extern;
import z808.command.directive.Public;
import z808.command.instruction.AddAX;
import z808.command.instruction.AddDX;
import z808.command.instruction.Call;
import z808.command.instruction.Hlt;
import z808.command.instruction.Instruction;
import z808.command.instruction.Jump;
import z808.command.instruction.MovAXMEM;
import z808.command.instruction.MovMEMAX;
import z808.command.instruction.Retn;
import z808.command.instruction.SubAX;
import z808.memory.Address;

public class Linker {
	private ArrayList<Symbol> m_GlobalSymbolTable;
	
	private ArrayList<Module> m_Module;
	private int m_ModuleAlign;
	
	public Linker() {
		m_GlobalSymbolTable = new ArrayList<>();
		
		m_Module = new ArrayList<>();
		m_ModuleAlign = 0;
	}
	
	public void InsertModule(Module module) throws ExecutionException {
		module.setModuleAlign(m_ModuleAlign);
		m_Module.add(module);
		
		for (Symbol symbol : module.global_symbol_table) {
			Number addr = symbol.getValue();
			
			if (symbol.isRelative())
				addr = ((int)addr + m_ModuleAlign);
			
			m_GlobalSymbolTable.add(new Symbol(symbol.getName(), addr, symbol.isAbs()));
		}
		
		m_ModuleAlign += module.getSize();
	}
	
	public Program LinkModules() throws SymbolNotFound, ExecutionException {
		fixSymbols();
		
		Program program = new Program();
		
		for (Module module : m_Module)
			for (Map.Entry<Address, Command> entry : module.getProgram().entrySet())
				program.add(entry.getKey(), entry.getValue());
		
		return program;
	}
	
	public static void LinkerTests() throws InvalidOperationException, TooLongValue, ExecutionException {
		System.out.println("-- Running Linker Tests --");
		
		ArrayList<Command> code = new ArrayList<>();
		
		code.add(new Public(
				new String[] { "Function1", "Function2", "Function3", "Function4", "externData1" }
		));
		code.add(new Extern(
				new String[] { } ,
				new String[] { }
		));
		
		code.add(new DW("externData1", new Address(100)));
		code.add(new AddAX("Function1"));
		code.add(new AddDX());
		code.add(new SubAX());
		code.add(new Retn());
		
		code.add(new AddAX("Function2"));
		code.add(new AddAX());
		code.add(new AddAX());
		code.add(new Retn());
		
		code.add(new AddAX("Function3"));
		code.add(new Call("Function1", new Address(0)));
		code.add(new AddAX());
		code.add(new Call("Function2", new Address(7)));
		code.add(new AddAX());
		code.add(new AddAX());
		code.add(new Retn());

		code.add(new AddAX("Function4"));
		code.add(new Call("Function3", new Address(14)));
		code.add(new AddAX());
		code.add(new MovAXMEM("externData1"));
		code.add(new AddAX());
		code.add(new MovMEMAX("externData1"));
		code.add(new Retn());
		
		ArrayList<Command> code2 = new ArrayList<>();
		
		code2.add(new Public(new String[] { }));
		code2.add(new Extern(
				new String[] { "Function4", "externData1" } ,
				new String[] { "NEAR", "DWORD" }
		));
		
		code2.add(new Call("Function4"));
		code2.add(new MovAXMEM("externData1"));
		code2.add(new Hlt());
		
		Module[] mod = new Module[2];

		Assembler asm = new Assembler();
		mod[0] = asm.assembleCode(code);
		mod[1] = asm.assembleCode(code2);
		
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
		
		System.out.println(mod[0].m_code.toString());
		System.out.println(mod[1].m_code.toString());
	}
	
	private void fixSymbols() throws SymbolNotFound, ExecutionException {
		for (Module module : m_Module) {
			if (module.use_table == null)
				continue;
			
			for (Tuple<Command,Address> useTable : module.use_table) {
				String symbolName = useTable.a.getUndefValue();
				
				Boolean fixed = false;
				
				for (Symbol symbol : m_GlobalSymbolTable) {
					if (symbolName.equalsIgnoreCase(symbol.getName())) {
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
	
	private void fixCommand(Tuple<Command,Address> command, Symbol symbol) throws ExecutionException {
		Instruction inst = (Instruction)command.a;
		inst.fixSymbol(symbol.getValue());
		
		if (command.a instanceof MovAXMEM)
			((MovAXMEM)command.a).arg = new Address(symbol.getValue());
		else if (command.a instanceof MovMEMAX)
			((MovMEMAX)command.a).arg = new Address(symbol.getValue());
		else if (command.a instanceof Jump)
			((Jump)command.a).arg = new Address(symbol.getValue());
		else if (command.a instanceof Call)
			((Call)command.a).arg = new Address(symbol.getValue());
		
		System.out.println("CMD FIXED: " + inst.toString());
	}
}
