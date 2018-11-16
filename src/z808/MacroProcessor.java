package z808;

import java.util.List;
import util.NotImplementedException;
import z808.command.Command;

/*
 * @author Alana Schwendler
 */ 
public class MacroProcessor {
  public MacroProcessor() throws NotImplementedException {
    throw new NotImplementedException("TODO");
  }

  public static List<Command> expansionMode () {
    /*
    new mCall()
    call translator
    look on both macro tables
    return list<command>
    */
    return null;
  }

  public static List<Command> definitionMode() {
    /*
    new mDef() 
    create macro definition table 
    macro name table
    */
    return null;
  }
}
