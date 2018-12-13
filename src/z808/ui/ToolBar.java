package z808.ui;

import javafx.scene.layout.HBox;
import javafx.scene.control.Button;

import java.util.List;
import z808.command.Command;
import z808.ui.OutputArea;
import z808.ui.CodeArea;
import z808.Translator;
import z808.MacroProcessor;
import z808.Assembler;
import z808.Module;
import z808.Linker;
import z808.Processor;
import z808.Program;

public class ToolBar extends HBox {
	private Button reset;
	private Button addSource;
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
		setStyle(BeautyFactory.GetStyle());

		this.reset = new Button("<->");
		this.addSource = new Button(" + ");
		this.macroProcess = new Button("MP");
		this.assemble = new Button("A");
		this.loadAndGo = new Button("LG");
		this.step = new Button(" > ");

		this.reset.setMaxHeight(Double.MAX_VALUE);
		this.reset.prefWidth(this.reset.getHeight());
		this.addSource.setMaxHeight(Double.MAX_VALUE);
		this.addSource.prefWidth(this.addSource.getHeight());
		this.macroProcess.setMaxHeight(Double.MAX_VALUE);
		this.macroProcess.prefWidth(this.macroProcess.getHeight());
		this.assemble.setMaxHeight(Double.MAX_VALUE);
		this.assemble.prefWidth(this.assemble.getHeight());
		this.loadAndGo.setMaxHeight(Double.MAX_VALUE);
		this.loadAndGo.prefWidth(this.loadAndGo.getHeight());
		this.step.setMaxHeight(Double.MAX_VALUE);
		this.step.prefWidth(this.step.getHeight());

		getChildren().addAll(reset,
												 addSource,
												 macroProcess,
												 assemble,
												 loadAndGo,
												 step);
	}

	public void setProcessor(UIz808 ui, Processor p, OutputArea oArea, CodeArea cArea) {
		this.machine = p;
		MacroProcessor mcrPr = null;
		Translator trans = new Translator();
		Assembler assmb = new Assembler();

		this.reset.setOnAction((event) -> {
				ui.configMachine();
				ui.updateScreen();
			});

		this.addSource.setOnAction((event) -> {
				cArea.addSourceFile();
				ui.updateScreen();
			});

		this.macroProcess.setOnAction((event) -> {
				try {
					List<String> lines = cArea.getCode();
					List<Command> code = trans.convertCode(lines);
					// mcrPr.process(code); // TODO use real MP method
					oArea.updateScreen(code.toString());
					ui.updateScreen();
				} catch (Exception e) {
					oArea.updateScreen(e.getMessage());
				}
			});

		this.assemble.setOnAction((event) -> {
				try {
					List<String> lines = cArea.getCode();
					List<Command> code = trans.convertCode(lines);
					// mcrPr.process(code); // TODO use real MP method
					Module mod = assmb.assembleCode(code);
					oArea.updateScreen(mod.toString());
					ui.updateScreen();
				} catch (Exception e) {
					oArea.updateScreen(e.getMessage());
				}
			});

		this.loadAndGo.setOnAction((event) -> {
				try {
					Linker lng = new Linker();
					for (List<String> src : cArea.getAllCode()) {
						List<Command> code = trans.convertCode(src);
						// mcrPr.process(code); // TODO use real MP method
						Module mod = assmb.assembleCode(code);
						lng.InsertModule(mod);
						ui.updateScreen();
					}
					Program finalProgram = lng.LinkModules();
					this.machine.load(finalProgram);
				} catch (Exception e) {
					oArea.updateScreen(e.getMessage());
				}
			});

		this.step.setOnAction((event) -> {
				try {
					this.machine.step();
					ui.updateScreen();
				} catch (Exception e) {
					oArea.updateScreen(e.getMessage());
				}
			});
	}

	public void updateScreen () { }
}
