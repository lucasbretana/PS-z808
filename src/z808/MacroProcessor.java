package z808;

import java.util.List;
import java.util.ArrayList;

import util.Tuple;
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
   * @return modifiedCmds a list of commands with expanded macros
   */
  public ArrayList<Command> process(ArrayList<Command> commands) {
    ArrayList<Command> modifiedCmds = null; 
    ArrayList<Tuple<String, MacroDef>> macroDefinitions = null;
    Translator t = new Translator();
    
    for(Command cmd : commands) {
      if(cmd instanceof MacroDef) {
        definitionMode(cmd, macroDefinitions);
      } else if(cmd instanceof MacroCall) {
        expansionMode(cmd, modifiedCmds, macroDefinitions);
      } else {
        modifiedCmds.add(cmd);
      }
    }
    //WHEN/WHERE DO I USE IT? :
    //modifiedCmds.add(t.convertCode1(cmd));
    return modifiedCmds;
  }

  /**
   * Definition mode fills the Macro Definitions Table
   * @param cmd command to save at the table
   * @param macroNames Macro Definitions Table
   */
  private void definitionMode(Command cmd, ArrayList<Tuple<String, MacroDef>> macroNames) {
    macroNames.add(cmd.getLabel(), MacroDef.class.cast(cmd));
  }

  /**
   * Expansion mode substitute macro call with the actual code
   * @param call Macro Call command
   * @param modCmds list of modissfied commands, will end up bigger then received
   * @param defs Macro Definitions Table
   * @return modCmds expanded code
   */
  private ArrayList<Command> expansionMode (MacroCall call, ArrayList<Command> modCmds, ArrayList<Tuple<String, MacroDef>> defs) {
    changeParameters(call, defs);
    for(Tuple<String, MacroDef> t : defs) {
      if(t.a.equals(call.getLabel())) {
        while(!t.b instanceof Endm) { //enquanto não acabar a macro, adiciona os comandos equivalentes na lista
          modCmds.add(t.b);
        }
      }
    }
    return modCmds;
  }

  private void changeParameters(MacroCall call, ArrayList<Tuple<String, MacroDef>> macroDefinitions) {
    int i = 0; 
    for(Tuple<String, MacroDef> md : macroDefinitions) {
      if(md.getLabel().equals(call.getLabel())) {
        for(String s : md.b.commands) {
          md.b.parameters.set(i, call.parameters.get(i));
          i++;
        }
      }
      i = 0;
    }
  }

}

    /*if(m.getLabel().equals(cmd.getLabel())) {
       //translator has to make string become code
    } else {
       //not t
    }
    
    new mCall()
    call translator
    look on both macro tables
    return list<command>
    */
 
