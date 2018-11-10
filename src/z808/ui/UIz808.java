package z808.ui;

import javafx.application.Application;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Region;

import util.NotImplementedException;

import z808.ui.BeautyFactory;
import z808.ui.ToolBar;
import z808.ui.CodeArea;
import z808.ui.ProcessedCodeArea;
import z808.ui.RegistersArea;
import z808.ui.MemoryArea;
import z808.ui.OutputArea;

public class UIz808 extends Application {
	private GridPane mainPane;
	private Scene mainScene;

	private Region toolBar;
	private Region srcCode;
	private Region innCode;
	private Region regBank;
	private Region mainMem;
	private Region outArea;

	public static void main(String...args) {
		UIz808.launch(args);
	}

	public void start(Stage stage) {
		stage.setTitle("The Marvelous z808 Simulator");
		stage.setResizable(false);

		this.toolBar = new ToolBar();
		this.srcCode = new CodeArea("* Código fonte\n"
																+ "* Com labels, diretivas\n"
																+ "* Da forma que entra para o processador de macros");
		this.innCode = new ProcessedCodeArea("* Código processado atual\n"
																				 + "* Marcos expandidos\n"
																				 + "* Endereço trocads ou indefinidos\n"
																				 + "* Depende do que já foi executado sobre o código");
		this.mainMem = new MemoryArea(512);
		this.regBank = new RegistersArea();
		this.outArea = new OutputArea("Saída");

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
	}
}
