package z808.command.directive;

import java.util.Arrays;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import z808.memory.Memory;
import z808.command.directive.Directive;
import util.NotImplementedException;
import util.ExecutionException;

public class Public extends Directive {
	public static String MNEMONIC = "PUBLIC";
	private ArrayList<String> names = null;

	/**
	 * Creates a public name
	 * @param list public names
	 */
	public Public(String...list) {
		super.size = 0;
		// TODO: maybe remove dupliclates, don't think is really necessary
		this.names = new ArrayList<String>(Arrays.<String>stream(list).map(s -> s.trim()).collect(Collectors.toList()));
	}

	public ArrayList<String> getNames() {
		return this.names;
	}

	@Override
	public void exec (Memory mem) throws ExecutionException {
		throw new ExecutionException("This shouls never reach to the processor.");
	}

	@Override
	public String toString() {
		return Public.MNEMONIC + " " + this.names.stream().reduce("", (x, y) -> x.trim() + " " + y.trim());
	}
}
