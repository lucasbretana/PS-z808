package z808.command.directive;

import z808.command.Command;
import util.NotImplementedException;
import util.ExecutionException;

public abstract class Directive extends Command {
	/**
	 * This exists just so the directives can have less constructor variants
	 * It may become the default for all Commands
	 * @param lbl the label to be used
	 * @throws ExecutionException if a already setted label is changed
	 */
	public void setLabel(String lbl) throws ExecutionException {
		if ((this.label == null) || (this.label.trim().equals("")))
			this.label = new String(lbl);
		else
			throw new ExecutionException("Cannot change a already set label");
	}

	public abstract String toCode();
}
