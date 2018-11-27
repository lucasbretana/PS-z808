package z808;

import util.ExecutionException;

import z808.memory.Address;

/**
 * This class should be used by: the Assembler, to create the module symbol tables
 *	and by the Linker, when it reads the tables and link the modules
 */
public class Symbol {
	private String name = null;
	private Number value = null;
	private Boolean absolute = null;

	/**
	 * Create a new symbols
	 * @param nm the name of the symbols
	 * @param num the value, either an Address or an integer value
	 * @param is_abs true if is an absolute value, false otherwise
	 * @throws ExecutionException if there was an error creating an Address
	 */
	public Symbol(String nm, Number num, boolean is_abs) throws ExecutionException {
		this.name = new String(nm);
		this.value = new Address(num.intValue());
		this.absolute = is_abs;
	}

	/**
	 * @return true if is an absolute symbol, false otherwise
	 */
	public boolean isAbs() { return this.absolute; }
	/**
	 * @return true if is an relative symbol, false otherwise
	 */
	public boolean isRelative() { return !this.absolute; }

	/**
	 * @return the Symbol name
	 */
	public String getName() {
		return this.name;
	}

	/**
	 * @return the symbol value, an int (but acctually and Address or a value)
	 */
	public int getValue() {
		return this.value.intValue();
	}
}
