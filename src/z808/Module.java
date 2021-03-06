package z808;

import java.util.ArrayList;
import java.util.Map;
import java.util.TreeMap;
import java.util.function.Predicate;

import z808.Program;
import z808.Symbol;
import z808.memory.Address;
import z808.command.Command;

import util.ExecutionException;
import util.NotImplementedException;
import util.Tuple;

public class Module {
	private String m_name = null;
	private int m_size = 0;
	protected Program m_code = null;

	// go through this and create a big global symbol table

	// .:here are symbols that are public and defined in this module:.
	// | -=name=- | -=value|address=- | -=absolute=- |
	public ArrayList<Symbol> global_symbol_table = null;
	// .:here are symbols that are defined and used only in this module:.
	// | -=name=- | -=value|address=- | -=absolute=- |
	public ArrayList<Symbol> local_symbol_table = null;
	// .:here are the symbol that are used, undefined and extern in this module:.
	// | -=command=- | -=plus_value=- | -- the command have the label to match and changeValue function to change the info
	public ArrayList<Tuple<Command,Address>> use_table = null;

	public Module() {
		this.m_code = new Program();
		this.global_symbol_table = new ArrayList<Symbol>();
		this.local_symbol_table = new ArrayList<Symbol>();
		this.use_table = new ArrayList<>();
	}

	// TODO @Bretana those "add symbols functions to some table" (except use table) should verify for symbols redefinition
	// TODO @Bretana this method may be changed to receive just the label|name insted of the whole command
	/**
	 * Adds a new symbols the global symbol table
	 * Which means that this variable|address can be used elsewhere, outside this module
	 * @param cmd the command that generate the Symbol
	 * @param value either an integer or an address that represent that symbol value
	 */
	public void addGlobalSymbol(Command cmd, Number value) throws ExecutionException {
		Symbol s = new Symbol(cmd.getLabel(), value, ( cmd instanceof z808.command.directive.Equ) );
		this.global_symbol_table.add(s);
	}

	public Number findInGlobalByName(String name) {
		Symbol t = this.findInTable(s -> s.getName().equalsIgnoreCase(name), this.global_symbol_table);
		return (t == null) ? null : new Integer(t.getValue());
	}

	/**
	 * Adds a new symbols the local symbol table
	 * Which means those symbol are for this module only and just need to be relocated
	 * @param cmd the command that generate the Symbol
	 * @param value either an integer or an address that represent that symbol value
	 */
	public void addLocalSymbol(Command cmd, Number value) throws ExecutionException {
		Symbol s = new Symbol(cmd.getLabel(), value, ( cmd instanceof z808.command.directive.Equ) );
		this.local_symbol_table.add(s);
	}

	public Number findInLocalByName(String name) {
		Symbol t = this.findInTable(s -> s.getName().equalsIgnoreCase(name), this.local_symbol_table);
		return (t == null) ? null : new Integer(t.getValue());
	}

	// TODO @Bretana one of those parameter is unnecessary, this will be used by the linker to replace values in undefined commands
	/**
	 * Adds a new symbols the use table
	 * Which means those symbol must be defined elsewhere
	 * @param cmd the command that uses the Symbol
	 * @param addr the address of that command
	 */
	public void addUseSymbol(Command cmd, Address addr) {
		Tuple <Command,Address>t = new Tuple<Command,Address>(cmd, addr);
		this.use_table.add(t);
	}

	public Address findInUseByName(String name) {
		Tuple <?,Address>t = this.findInTable(t2 -> t2.a.getLabel().equalsIgnoreCase(name), this.use_table);
		return (t == null) ? null : t.b;
	}

	public void setName(String module_name) {
		this.m_name = new String(module_name);
	}

	private <T> T findInTable(Predicate<T> match, ArrayList<T> table) {
		for ( T t : table ) {
			if ( match.test(t) )
				return t;
		}
		return null;
	}

	public int getSize() { return this.m_size; }
	public void incSize(int plus) throws ExecutionException {
		if (plus < 0)
			throw new ExecutionException("Cannot decrease module size");
		this.m_size += plus;
	}

	public void setModuleAlign(int da, int sa, int ca) throws ExecutionException {
		TreeMap<Address, Command> newcode = new TreeMap<>();

		for (Map.Entry<Address,Command> entry : m_code.entrySet()) {
			Address addr = entry.getKey();
			Address nAddr = null;
			switch (this.m_code.rangeAddress(addr)) {
			case Program.IS_IN_DATA_SEGMENT:
				nAddr = new Address(addr.intValue() + da);
				break;
			case Program.IS_IN_STACK_SEGMENT:
				nAddr = new Address(addr.intValue() + sa);
				break;
			case Program.IS_IN_CODE_SEGMENT:
				nAddr = new Address(addr.intValue() + ca);
				break;
			}
			newcode.put(nAddr, entry.getValue());
		}

		for (Symbol symbol : global_symbol_table) {
			if (symbol.isRelative()) {
				int addr = symbol.getValue();
				Address nAddr = null;
				switch (this.m_code.rangeAddress(new Address(addr))) {
				case Program.IS_IN_DATA_SEGMENT:
					nAddr = new Address(addr + da);
					break;
				case Program.IS_IN_STACK_SEGMENT:
					nAddr = new Address(addr + sa);
					break;
				case Program.IS_IN_CODE_SEGMENT:
					nAddr = new Address(addr + ca);
					break;
				}
				symbol.setValue(nAddr);
			}
		}

		m_code.clear();
		m_code.putAll(newcode);
	}

	public Program getProgram() {
		return this.m_code;
	}

	@Override
	public String toString() {
		String ret = "-- begin module --\n";
		ret += "Name: " + ((this.m_name == null) ? "undef." : this.m_name) + "\n";
		ret += "Seg. size " + this.m_size+ "B\n";

		ret += "## global table\n";
		if (this.global_symbol_table.isEmpty()) ret += "<empty>\n";
		for (Symbol s : this.global_symbol_table)
			ret += s.getName() + " at 0x" + s.getValue() + ((s.isAbs()) ? " a" : " r") + "\n"; 

		ret += "## local table\n";
		if (this.local_symbol_table.isEmpty()) ret += "<empty>\n";
		for (Symbol s : this.local_symbol_table)
			ret += s.getName() + " at 0x" + s.getValue() + ((s.isAbs()) ? " a" : " r") + "\n"; 

		ret += "## use table\n";
		for (Tuple<Command,Address> t2 : this.use_table)
			ret += t2.a.getUndefValue() + " at 0x" + t2.b + "\n";

		ret += "## code\n";
		ret += this.m_code;
		ret += " CS mod at 0x" + this.m_code.getStartCodeSegment() + " with size " + this.m_code.getSizeCodeSegment() + "\n";
		ret += " DS mod at 0x" + this.m_code.getStartDataSegment() + " with size " + this.m_code.getSizeDataSegment() + "\n";
		ret += " SS mod at 0x" + this.m_code.getStartStackSegment() + " with size " + this.m_code.getSizeStackSegment() + "\n";
		ret += "-- end module --";

		return ret;
	}
}
