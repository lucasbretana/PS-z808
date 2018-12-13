package z808;

import java.util.Map;
import java.util.List;

import util.ExecutionException;
import util.NotImplementedException;
import util.FinishedException;

import z808.Program;
import z808.memory.Memory;
import z808.memory.Address;
import z808.memory.Register;
import z808.command.Command;

/**
 * This class represents the execution aspec of the z808 processor.
 * @author Jonathas-Conceicao
 */
public class Processor {
	private Memory memory;
	private Program commands;

	/**
	 * Builds the processor with a specific memory size;
	 * @param memorySizeInK memory size in Kilo Bytes;
	 * @exception ExecutionException if the memory is bigger than 64Kib or lower than 0.
	 */
	public Processor(int memorySizeInK) throws ExecutionException {
		if (memorySizeInK > 64 || memorySizeInK < 0 )
			throw new ExecutionException ("Invalid memory size: " + memorySizeInK + "Kib\n");
		this.memory = new Memory(memorySizeInK);
	}

	/**
	 * Constructor to build the processor with max memory, 64Kib;
	 * @exception ExecutionException if the memory is bigger than 64Kib or lower than 0.
	 */
	public Processor() throws ExecutionException {this(64);}

	/**
	 * Run a indefinite number of steps until the program stops.
	 * @exception ExecutionException if any of the commands loaded fails for some reason.
	 */
	public void process ()
		throws ExecutionException {
		try { while (true) {this.step();} }
		catch (FinishedException e) {
		}
		return;
	}

	/**
	 * Runs one single step (instruction) of the program.
	 * @exception ExecutionException if any of current command fails;
	 * @exception FinishedException if no program is loaded or if a instruction indicates end of program.
	 */
	public void step()
		throws ExecutionException, FinishedException {
		this.sanityCheck(this.memory);
		if (this.commands == null) throw new FinishedException ("No program loaded");
		Command cmd = this.commands.get(this.memory.getCurrentInstruction());
		cmd.exec(this.memory);
		return;
	}

	/**
	 * Loads the program to the memory
	 */
	public void load(Program cmds) throws ExecutionException {
		if (this.commands != null) this.commands.merge(cmds);
		else this.commands = cmds;

		this.memory.load(this.commands.memoryView());
		return;
	}

	/**
	 * Gets a list of registers values to be displayed.
	 * @returns a list of registers
	 */
	public List<Register> getRegisters () {
		return this.memory.getRegisters();
	}

	/**
	 * Gets a list of memory locations to be displayed.
	 * @returns a list of registers
	 */
	public List<Register> getMemoryRegisters () {
		return this.memory.getMemoryRegisters();
	}

	
	public String codeToString() {
		return this.commands.toString();
	}

	public String registersToString () {
		return this.memory.registersToString();
	}

	public String toString () {
		return "-- Code --\n"
			+ this.codeToString()
			+ this.memory.toString();
	}

	private void sanityCheck(Memory mem)
		throws ExecutionException {
		return;
	}
}
