package z808;

import z808.command.instruction.Instruction;
import z808.command.instruction.AddAX;
import z808.command.instruction.AddDX;
import z808.command.instruction.AddCTE;
import z808.command.instruction.DivAX;

public class MainTest {
	public static void main(String...args) {
		Instruction l1 = new AddDX (0);
		Instruction l2 = new AddCTE(2, 200);
		Instruction l3 = new AddAX (5);
		Instruction l4 = new DivAX (7);
		System.out.println(l1);
		System.out.println(l2);
		System.out.println(l3);
		System.out.println(l4);
	}
}

