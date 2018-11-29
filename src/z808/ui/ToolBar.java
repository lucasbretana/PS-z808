package z808.ui;

import javafx.scene.layout.HBox;
import javafx.scene.control.Button;

import java.util.List;
import java.io.StringWriter;
import java.io.PrintWriter;
import z808.command.Command;
import z808.ui.OutputArea;
import z808.ui.CodeArea;
import z808.Translator;
import z808.Processor;
import z808.Assembler;
import z808.Module;


public class ToolBar extends HBox {
	private Button macroProcess;
	private Button assemble;
	private Button loadAndGo;
	private Button step;
	private Button run;

	private Processor machine;
	private Translator translator;

	public ToolBar() {
		super(2);
		setPrefSize(BeautyFactory.SCREEN_WIDTH  * 1,
										BeautyFactory.SCREEN_HEIGHT * 0.05);
		setStyle(BeautyFactory.GetStyle()
						 );

		this.macroProcess = new Button("MP");
		this.assemble = new Button("A");
		this.loadAndGo = new Button("LG");
		this.step = new Button(">");
		this.run = new Button(">>");

		getChildren().add(macroProcess);
		getChildren().add(assemble);
		getChildren().add(loadAndGo);
		getChildren().add(step);
		getChildren().add(run);
	}

	public void setProcessor(Processor p, OutputArea oArea, CodeArea cArea) {
		this.machine = p;
		this.step.setOnAction((event) -> {
				try {
					this.machine.step();
				} catch (Exception e) {
					StringWriter sw = new StringWriter();
					PrintWriter pw = new PrintWriter(sw);
					e.printStackTrace(pw);
					oArea.updateScreen(sw.toString());
				}
			});

		this.assemble.setOnAction((event) -> {
				try {
					Translator trans = new Translator();
					Assembler assmb = new Assembler();
					List<String> lines = cArea.getCode();
					List<Command> code = trans.convertCode(lines);
					Module mod = assmb.assembleCode(code);
					this.machine.load(mod.getProgram());
				} catch (Exception e) {
					StringWriter sw = new StringWriter();
					PrintWriter pw = new PrintWriter(sw);
					e.printStackTrace(pw);
					oArea.updateScreen(sw.toString());
				}
			});
	}

	public void updateScreen () { }
}
