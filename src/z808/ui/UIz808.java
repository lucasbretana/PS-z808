package z808.ui;


import javafx.application.Application;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.Node;

import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Border;

import javafx.scene.layout.Region;

import javafx.scene.control.Label;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;

import javafx.geometry.Pos;

import util.NotImplementedException;

import z808.ui.BeautyFactory;

public class UIz808 extends Application {
	private BorderPane mainPane;
	private Scene mainScene;

	private Region top;
	private Region bottom;
	private Region center;
	private Region right;
	private Region left;

	private Border border;

	public static void main(String...args) throws NotImplementedException {
		UIz808.launch(args);
	}

	public void start(Stage stage) {
		stage.setTitle("z808 Simulator by UFPel");

		this.border = BeautyFactory.generalBorder();
		this.mainPane = new BorderPane();
		this.mainPane.setMaxSize(1, 1);
		// rootNode.setAlignment(Pos.CENTER);

		this.mainScene = new Scene(this.mainPane, 500, 500);
		stage.setScene(mainScene);

		this.top = new Button("Interactive buttons bar");
		this.bottom = BeautyFactory.ReadOnlyArea("ReadOnly output text area");
		this.center = BeautyFactory.ReadOnlyArea("Main code area");
		this.left = BeautyFactory.ReadOnlyArea("Current internal code representation text area");
		this.right = BeautyFactory.ReadOnlyArea("Registers bank");

		this.mainPane.setTop(this.top);
	  this.mainPane.setBottom(this.bottom);
		this.center.setBorder(this.border);
		this.mainPane.setCenter(this.center);
		this.mainPane.setLeft(this.left);
		this.mainPane.setRight(this.right);

		// rootNode.getChildren().addAll(btnRun, btnExit, response);
		stage.show();
	}
}
