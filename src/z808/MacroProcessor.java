package z808;

import java.util.List;
import java.util.ArrayList;

import util.Tuple;
import util.NotImplementedException;
import util.ExecutionException;

import z808.Translator;
import z808.command.Command;
import z808.command.directive.MacroDef;
import z808.command.directive.MacroCall;
import z808.command.directive.Endm;

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
    
    for(Command cmd : commands) {
      System.out.println(cmd.getClass().getName());
      if(cmd instanceof MacroDef) {
        definitionMode((MacroDef) cmd, macroDefinitions);
      } else if(cmd instanceof MacroCall) {
        expansionMode((MacroCall) cmd, modifiedCmds, macroDefinitions);
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
    Tuple<String, MacroDef> t = new Tuple<String, MacroDef>(cmd.getLabel(), MacroDef.class.cast(cmd));
    macroNames.add(t);
  }

  /**
   * Expansion mode substitute macro call with the actual code
   * @param call Macro Call command
   * @param modCmds list of modissfied commands, will end up bigger then received
   * @param defs Macro Definitions Table
   * @return modCmds expanded code
   */
  private void expansionMode (MacroCall call, ArrayList<Command> modCmds, ArrayList<Tuple<String, MacroDef>> defs) {
    Translator transl = new Translator();
    int i = 0;

    //changeParameters(call, defs);
    for(Tuple<String, MacroDef> md : defs) {
      if(md.a.equals(call.getLabel())) {
        for(Command cmd : md.b.commands) {
          for(String curParam : md.b.parameters) {
            transl.
          }
        }
      }
    }
    i = 0;
  }

}

/*

private void changeParameters(MacroCall call, ArrayList<Tuple<String, MacroDef>> macroDefinitions) {
  for(String curParam : md.b.parameters) { //formal
    //troca curParam call.parameters.get(idx_param)
                 
  md.b.parameters.get(idx_param).replace(curParam, call.parameters.get(idx_param));
  idx_param++;
}

    
    if(m.getLabel().equals(cmd.getLabel())) {
       //translator has to make string become code
    } else {
       //not t
    }
    
    new mCall()
    call translator
    look on both macro tables
    return list<command>
    */
 
