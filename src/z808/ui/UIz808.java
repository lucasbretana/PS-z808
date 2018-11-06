package z808.ui;

import javafx.application.Application;
import javafx.stage.Stage;
import javafx.scene.Node;
import javafx.scene.layout.BorderPane;
import javafx.scene.control.Label;
import javafx.scene.control.Button;
import javafx.scene.Scene;
import javafx.geometry.Pos;

import util.NotImplementedException;

public class UIz808 extends Application {
	private BorderPane mainPane;
	private Scene mainScene;

	private Node top;
	private Node bottom;
	private Node center;
	private Node right;
	private Node left;

	public static void main(String...args) throws NotImplementedException {
		UIz808.launch(args);
	}

	@Override
	public void start(Stage stage) {
		stage.setTitle("z808 Simulator by UFPel");

		this.mainPane = new BorderPane();
		// rootNode.setAlignment(Pos.CENTER);

		this.mainScene = new Scene(this.mainPane, 500, 500);
		stage.setScene(mainScene);

		this.top = new Label("Interactive buttons bar");
		this.bottom = new Label("ReadOnly output text area");
		this.center = new Label("Main code area");
		this.left = new Label("Current internal code representation text area");
		this.right = new Label("Registers bank");

		this.mainPane.setTop(this.top);
	  this.mainPane.setBottom(this.bottom);
		this.mainPane.setCenter(this.center);
		this.mainPane.setLeft(this.left);
		this.mainPane.setRight(this.right);

		// rootNode.getChildren().addAll(btnRun, btnExit, response);
		stage.show();
	}
}
