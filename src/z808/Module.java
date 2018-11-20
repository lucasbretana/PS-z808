package z808;

import java.util.ArrayList;

import z808.Program;
import z808.memory.Address;
import z808.command.Command;

import util.ExecutionException;
import util.NotImplementedException;

public class Module {
	protected Program m_code = null;

	// go through this and create a big global symbol table

	// .:here are symbols that are public and defined in this module:.
	// | -=name=- | -=relative=- | -=value|address=- |
	public ArrayList<Triple<String, Number, Boolean>> global_symbol_table = null;

	// .:here are the symbol that are used, undefined and extern in this module:.
	// | -=command=- | -=plus_value=- | -- the command have the label to match and changeValue function to change the info
	public ArrayList<Command> use_table = null;

	public Module() {
		this.m_code = new Program();
		this.global_symbol_table = new ArrayList<Triple<String, Number, Boolean>>();
	}

	public Module(Program prog) {
		this.m_code = prog;
		this.global_symbol_table = new ArrayList<Triple<String, Number, Boolean>>();
	}
	
	// TODO @Bretana this method may be changed to receive just the label|name insted of the whole command
	/**
	 * Adds a new symbols the global symbol table
	 * Which means that this variable|address can be used elsewhere, outside this module
	 * @param cmd the command that generate the Symbol
	 * @param value either an integer or an address that represent that symbol value
	 * @param is_relative wheter or not the symbol have its value changed, default is false
	 */
	public void addGlobalSymbol(Command cmd, Number value, Boolean is_relative) throws NotImplementedException {
		System.err.printf("CMD %s -- VALUE %s -- %s\n\n", cmd.getLabel(), value.toString(), is_relative);
		Triple<String,Number,Boolean> t3 = new Triple<String,Number,Boolean>(cmd.getLabel(), value, is_relative);
		System.err.println("T3 " + t3);
		this.global_symbol_table.add(t3);
	}
	public void addGlobalSymbol(Command c, Integer value) throws NotImplementedException { this.addGlobalSymbol(c, value, false); }

	public void addUseSymbol(Command c) throws NotImplementedException {
		if (this.use_table == null)
			this.use_table = new ArrayList<Command>();
		
		use_table.add(c);
	}

	public void addLocalSymbol(Command c) throws NotImplementedException {
		System.err.printf("Current command: <%s, %s>\n", c.getLabel(), c);
		throw new NotImplementedException("TODO");
	}
	
	public int getCodeSize() {
		return m_code.size();
	}
}

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

