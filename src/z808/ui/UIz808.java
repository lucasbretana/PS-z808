package z808.ui;

import javafx.application.Application;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.layout.GridPane;

import z808.ui.BeautyFactory;
import z808.ui.ToolBar;
import z808.ui.CodeArea;
import z808.ui.ProcessedCodeArea;
import z808.ui.RegistersArea;
import z808.ui.MemoryArea;
import z808.ui.OutputArea;

import util.TestFaliedException;
import util.ExecutionException;
import util.NotImplementedException;
import util.FinishedException;

import z808.Program;
import z808.Processor;
import z808.memory.Address;
import z808.command.Command;
import z808.command.directive.*;
import z808.command.instruction.*;

public class UIz808 extends Application {
	private GridPane mainPane;
	private Scene mainScene;

	private ToolBar           toolBar;
	private CodeArea          srcCode;
	private ProcessedCodeArea innCode;
	private MemoryArea        mainMem;
	private RegistersArea     regBank;
	private OutputArea        outArea;

	private Processor machine;

	public static void main(String...args) {
		UIz808.launch(args);
	}

	public void start(Stage stage) {
		stage.setTitle("The Marvelous z808 Simulator");
		stage.setResizable(false);

		this.toolBar = new ToolBar();
		this.srcCode = new CodeArea();
		this.innCode = new ProcessedCodeArea();
		this.mainMem = new MemoryArea();
		this.regBank = new RegistersArea();
		this.outArea = new OutputArea();

		this.mainPane = new GridPane();
		this.mainPane.add(toolBar, 0, 0, 3, 1);
		this.mainPane.add(innCode, 0, 1, 1, 2);
		this.mainPane.add(srcCode, 1, 1, 1, 2);
		this.mainPane.add(regBank, 2, 1, 1, 1);
		this.mainPane.add(mainMem, 2, 2, 1, 2);
		this.mainPane.add(outArea, 0, 3, 2, 1);

		this.mainScene = new Scene(this.mainPane,
															 BeautyFactory.SCREEN_WIDTH,
															 BeautyFactory.SCREEN_HEIGHT);
		stage.setScene(mainScene);
		stage.show();

		// Call for screen update
		this.configMachine();
		this.updateScreen();
	}

	public void configMachine () {
		try {
			this.machine = new Processor();
			this.innCode.setProcessor(this.machine);
			this.mainMem.setProcessor(this.machine);
			this.regBank.setProcessor(this.machine);
			this.toolBar.setProcessor(this, this.machine, this.outArea, this.srcCode);

			// TODO: @Jonathas
			// Source code and processor iniciation, this will be changed until final release
			Program code = new Program();
			code.add(new Address(0x0), new Equ (5));       // EQU 5
			code.add(new Address(0x1), new AddCTE (0x0));  // add AX 0x0
			code.add(new Address(0x4), new AddAX ());      // add AX AX
			code.add(new Address(0x6), new AddAX ());      // add AX AX
			code.add(new Address(0x8), new SubCTE (0x0));  // sub AX 0x0
			code.add(new Address(0xb), new Hlt ());        // hlt
			this.machine.load(code);
		} catch (ExecutionException e) {
			this.reportError(e.getMessage());
		}
	}

	public void updateScreen() {
		try {
			this.toolBar.updateScreen();
			this.srcCode.updateScreen("EQU 5\n" +
																"add AX 0x0\n" +
																"add AX AX\n" +
																"add AX AX\n" +
																"sub AX 0x0\n" +
																"hlt\n");
			this.innCode.updateScreen();
			this.mainMem.updateScreen();
			this.regBank.updateScreen();
		} catch (Exception e) {
			reportError(e.getMessage());
		}
	}

	private void reportError(String e) {
		this.outArea.updateScreen(e);
	}
}
