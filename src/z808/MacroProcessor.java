package z808;

import java.util.List;
import java.util.ArrayList;

import util.NotImplementedException;
import util.ExecutionException;
import z808.command.Command;
import z808.command.directive.MacroDef;
import z808.command.directive.MacroCall;

/**
 * @author Alana Schwendler
 */
public class MacroProcessor {
  public List<Command> commands = null; 

  public MacroProcessor(List<Command> cmds) throws ExecutionException {
    if(cmds.isEmpty()) {
      throw new ExecutionException ("Invalid list of commands: Empty \n");
    }    
    this.commands = new ArrayList<>(cmds); 
  }
  
  /**
   * Search the whole code for macro def and macro call
   * @param commands List of commands a.k.a. code itself
   * @return a list of commands with expanded macros
   */
  public ArrayList<Command> process(ArrayList<Command> commands) {
    ArrayList<Command> modifiedCmds = null; 
    ArrayList<MacroDef> macroDefinitions = null;
    
    for(Command cmd : commands) {
      if(cmd instanceof MacroDef) {
        definitionMode(cmd, macroDefinitions);
      } else if(cmd instanceof MacroCall) {
        expansionMode(cmd, modifiedCmds, macroDefinitions);
      } else {
        modifiedCmds.add(cmd);
      }
    }

    return modifiedCmds;
  }

  /**
   * Definition mode fills the Macro Definitions Table
   * @param cmd command to save at the table
   * @param macroNames Macro Definitions Table
   */
  private void definitionMode(Command cmd, ArrayList<MacroDef> macroNames) {
    macroNames.add(MacroDef.class.cast(cmd));
  }

  /**
   * Expansion mode substitute macro call with the actual code
   * @param cmd Macro Call command
   * @param modCmds list of modified commands, will end up bigger then received
   * @param defs Macro Definitions Table
   * @return expanded code
   */
  private void expansionMode (Command cmd, ArrayList<Command> modCmds, ArrayList<MacroDef> defs) {
    
    for(MacroDef m : defs) {
      if(m.getLabel().equals(cmd.getLabel())) {
        //translator has to make string become code
      } else {
        //not t
      }
    }
   
    /*
    new mCall()
    call translator
    look on both macro tables
    return list<command>
    */
  }

}
