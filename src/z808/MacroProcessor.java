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
   * @return a list of commands with same received commands but macro calls substituted by actual macros
   */
  public ArrayList<Command> process(List<Command> commands) {
    ArrayList<Command> modifiedCmds = new ArrayList<Command>(); 
    ArrayList<MacroDef> macroDefinitions = new ArrayList<MacroDef>();
    
    for(Command cmd : commands) {
      if(cmd instanceof MacroDef) {
        try {
          definitionMode((MacroDef) cmd, macroDefinitions);
        } catch (ExecutionException e) {
          System.out.println("Error: definition mode: " + e);
        }

      } else if(cmd instanceof MacroCall) {
        try {
          expansionMode((MacroCall) cmd, modifiedCmds, macroDefinitions);
        } catch (ExecutionException e) {
          System.out.println("Error: expansion mode: " + e);
        }

      } else if (!(cmd instanceof Endm)){
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
  private void definitionMode(MacroDef cmd, ArrayList<MacroDef> macroNames) throws ExecutionException {
    //MacroDef md = new MacroDef(cmd.getLabel(), MacroDef.class.cast(cmd));
    macroNames.add(cmd);
  }

  /**
   * Expansion mode substitute macro call with the actual code
   * @param call Macro Call command
   * @param modCmds list of modified commands, will end up bigger then received
   * @param defs Macro Definitions Table
   */
  private void expansionMode (MacroCall call, ArrayList<Command> modCmds, ArrayList<MacroDef> defs) throws ExecutionException {
    Translator transl = new Translator();
    Command c = null;
    int i = 0;

    for(MacroDef md : defs) {
      if(md.getLabel().equals(call.getLabel())) { //compara o label
        for(String cmd : md.commands) { //itera lista de comandos da macro definition
          for(String curParam : md.parameters) { //pega lista de parametros do comando
            cmd = cmd.replace(curParam, call.parameters.get(i)); //substitui os parametros
            ++i;
          }
          
          i = 0;
          c = transl.convertCode1(cmd); //traduz a string para comando
          modCmds.add(c); //adiciona o comando na lista final de comandos 
        }
      }
      c = null;
    }
  }
}