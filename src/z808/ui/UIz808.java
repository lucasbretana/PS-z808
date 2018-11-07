package z808.ui;

import javafx.application.Application;
import javafx.stage.Stage;
import javafx.scene.Scene;

import javafx.scene.layout.GridPane;

import javafx.scene.layout.Region;

import javafx.scene.control.Button;

import util.NotImplementedException;

import z808.ui.BeautyFactory;

public class UIz808 extends Application {
	private GridPane mainPane;
	private Scene mainScene;

	private Region toolBar;
	private Region srcCode;
	private Region innCode;
	private Region regBank;
	private Region outArea;

	public static void main(String...args) throws NotImplementedException {
		UIz808.launch(args);
	}

	public void start(Stage stage) {
		stage.setTitle("The Marvelous z808 Simulator");
		stage.setResizable(false);

		this.mainPane = new GridPane();

		this.toolBar = new Button("toolBar");
		this.srcCode = BeautyFactory.CodeArea("srcCode");
		this.innCode = BeautyFactory.CodeArea("innCode");
		this.regBank = BeautyFactory.CodeArea("regBank");
		this.outArea = BeautyFactory.OutputArea("outArea");

		this.mainPane.add(toolBar, 0, 0, 1, 1);
		this.mainPane.add(srcCode, 0, 1, 1, 1);
		this.mainPane.add(innCode, 1, 1, 1, 1);
		this.mainPane.add(regBank, 2, 1, 1, 1);
		this.mainPane.add(outArea, 0, 2, 1, 1);
		
		this.mainScene = new Scene(this.mainPane, 1024, 768);
		stage.setScene(mainScene);
		stage.show();
	}
}
