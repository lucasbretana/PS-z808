package z808;

import z808.command.instruction.Instruction;
import z808.command.instruction.AddDX;

public class MainTest{
	public static void main(String...args) {
		Instruction a = new AddDX(0);
		System.out.println("add ax dx:" + a);
	//Instruction i = Sub.class.cast(a);	// class cast exception
											// Carefull with casts!
		//System.out.println("Add " + Add.OPCODE + " != " + i.getOpCode());
	}
}

