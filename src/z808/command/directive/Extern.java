package z808.command.directive;

import java.lang.IllegalArgumentException;
import java.util.Arrays;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import z808.memory.Memory;
import z808.command.directive.Directive;
import util.NotImplementedException;
import util.ExecutionException;

public class Extern extends Directive {
	public static String MNEMONIC = "EXTERN";
	public static ExternType WORD = ExternType.WORD;
	public static ExternType NEAR = ExternType.NEAR;
	public static ExternType ABS = ExternType.ABS;

	private ArrayList<Tuple<String,ExternType>> names = null;

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
			System.out.printf("List [%d] = <%s, %s>\n", i, list[i], t_list[i].toString());
			this.names.add(i, new Tuple<>(list[i], t_list[i]));
		}
	}

	public ArrayList<Tuple<String,ExternType>> getNames() {
		return this.names;
	}

	@Override
	public void exec (Memory mem) throws ExecutionException {
		throw new ExecutionException("This should never reach to the processor.");
	}

}
enum ExternType {
	ABS ("ABS"),
	NEAR ("NEAR"),
	WORD ("WORD");
	String type = null;
	ExternType(String t){
		if (t == ExternType.ABS) {
		this.type = t;
		}
	}
}

class Tuple<A,B>{
	A a; B b;
	Tuple(A a, B b){ this.a=a; this.b=b; }
	@Override
	public String toString() { return this.a + " " + this.b; }
}
