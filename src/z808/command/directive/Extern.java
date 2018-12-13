package z808.command.directive;

import java.lang.IllegalArgumentException;
import java.util.Arrays;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import z808.memory.Memory;
import z808.command.directive.Directive;

import util.AZMRegexCommon;
import util.ExecutionException;
import util.NotImplementedException;
import util.Tuple;

public class Extern extends Directive {
	public static String MNEMONIC = "EXTERN";
	public static final String REGEX = "^" + MNEMONIC + " " + "(" + AZMRegexCommon.NAME_RGX + ":" + Extern.NEAR 
								+ ")"
					+ "$";

	public static String WORD = "WORD";	// a sub routine name
	public static String NEAR = "NEAR"; // for an external variable's name
	public static String ABS  = "ABS";	// for absolute values

	private ArrayList<Tuple<String,String>> names = null;

	/**
	 * Creates a public name
	 * @param list public names
	 */
	public Extern(String[] list, String[] t_list) throws IllegalArgumentException {
		if (list.length != t_list.length) throw new IllegalArgumentException("Lists should have the exact same size");
		super.size = 0;

		int sz = list.length;
		this.names = new ArrayList<>(sz);
		for (int i=0 ; i<sz ; ++i) {
			this.names.add(i, new Tuple<>(list[i], t_list[i]));
		}
	}

	public ArrayList<Tuple<String,String>> getNames() {
		return this.names;
	}

	public static Extern makeExtern(String from) {
	  String []tokens = from.split(" ");

		if (!tokens[1].contains(",")) tokens[1] += ",";
		String []pairs = tokens[1].split(",");

	  ArrayList<String> names = new ArrayList<String>();
	  ArrayList<String> types = new ArrayList<String>();

		int sz = pairs.length;
		for(int i=0; i<sz ; ++i){
			tokens = pairs[i].split(":");
			names.add(tokens[0]);
			types.add(tokens[1]);
	  }

	  return new Extern(names.toArray(new String[0]), types.toArray(new String[0]));
	}

	@Override
	public void exec (Memory mem) throws ExecutionException {
		throw new ExecutionException("This should never reach to the processor.");
	}

	@Override
	public String toCode() { return this.toString(); }

	@Override
	public String toString() {
		String ret = Extern.MNEMONIC + " ";
		for (Tuple<String,String> t : this.names)
			ret += t.a + ":" + t.b + " ";
		return ret;
	}
}
