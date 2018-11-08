package z808;

import java.util.ArrayList;
import java.util.function.Predicate;

import z808.Program;
import z808.memory.Address;
import z808.command.Command;

import util.ExecutionException;
import util.NotImplementedException;

public class Module {
	private String m_name = null;
	protected Program m_code = null;

	// go through this and create a big global symbol table

	// .:here are symbols that are public and defined in this module:.
	// | -=name=- | -=value|address=- | -=absolute=- |
	public ArrayList<Triple<String, Number, Boolean>> global_symbol_table = null;
	// .:here are symbols that are defined and used only in this module:.
	// | -=name=- | -=value|address=- | -=absolute=- |
	public ArrayList<Triple<String, Number, Boolean>> local_symbol_table = null;
	// .:here are the symbol that are used, undefined and extern in this module:.
	// | -=command=- | -=plus_value=- | -- the command have the label to match and changeValue function to change the info
	public ArrayList<Tuple<Command,Address>> use_table = null;

	public Module() {
		this.m_code = new Program();
		this.global_symbol_table = new ArrayList<Triple<String, Number, Boolean>>();
		this.local_symbol_table = new ArrayList<Triple<String, Number, Boolean>>();
	}

	// TODO @Bretana those "add symbols functions to some table" (except use table) should verify for symbols redefinition
	// TODO @Bretana this method may be changed to receive just the label|name insted of the whole command
	/**
	 * Adds a new symbols the global symbol table
	 * Which means that this variable|address can be used elsewhere, outside this module
	 * @param cmd the command that generate the Symbol
	 * @param value either an integer or an address that represent that symbol value
	 */
	public void addGlobalSymbol(Command cmd, Number value) {
		Triple<String,Number,Boolean> t3 = new Triple<String,Number,Boolean>(cmd.getLabel(), value, ( cmd instanceof z808.command.directive.Equ) );
		this.global_symbol_table.add(t3);
	}

	public Number findInGlobalByName(String name) {
		Triple <?,Number,?> t = this.findInTable(t3 -> t3.a.equalsIgnoreCase(name), this.global_symbol_table);
		return (t == null) ? null : t.b;
	}

	/**
	 * Adds a new symbols the local symbol table
	 * Which means those symbol are for this module only and just need to be relocated
	 * @param cmd the command that generate the Symbol
	 * @param value either an integer or an address that represent that symbol value
	 */
	public void addLocalSymbol(Command cmd, Number value) {
		Triple<String,Number,Boolean> t3 = new Triple<String,Number,Boolean>(cmd.getLabel(), value, ( cmd instanceof z808.command.directive.Equ) );
		this.local_symbol_table.add(t3);
	}

	public Number findInLocalByName(String name) {
		Triple <?,Number,?> t = this.findInTable(t3 -> t3.a.equalsIgnoreCase(name), this.local_symbol_table);
		return (t == null) ? null : t.b;
	}

	// TODO @Bretana one of those parameter is desnecessary, this will be used by the linker to replace values in undefined commands
	/**
	 * Adds a new symbols the use table
	 * Which means those symbol must be defined elsewhere
	 * @param cmd the command that uses the Symbol
	 * @param addr the address of that command
	 */
	public void addUseSymbol(Command cmd, Address addr) throws NotImplementedException {
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
}

// TODO @Bretana make those n-uple into a Symbol if possible

class Tuple<A, B> {
	A a = null; B b = null;
	Tuple(A a, B b) {
		this.a = a; this.b = b;
	}
}

class Triple<A, B, C> {
	A a = null; B b = null; C c = null;
	Triple(A a, B b, C c) {
		this.a = a; this.b = b; this.c = c;
	}
}

class Quadruple<A, B, C, D> {
	A a = null; B b = null; C c = null; D d = null;
	Quadruple(A a, B b, C c, D d) {
		this.a = a; this.b = b; this.c = c; this.d = d;
	}
}

