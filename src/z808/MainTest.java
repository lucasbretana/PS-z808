package z808;

import z808.command.instruction.Instruction;
import z808.command.instruction.Add;
import z808.command.instruction.Sub;

public class MainTest{
	public static void main(String...args){
		Instruction a = new Add(0, 6, 5);
		Instruction s = new Sub(0, 6, 5);

	//Instruction i = Sub.class.cast(a);	// class cast exception
											// Carefull with casts!
		//System.out.println("Add " + Add.OPCODE + " != " + i.getOpCode());

	}
}

