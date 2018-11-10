package z808.ui;

import javafx.scene.control.ScrollPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.layout.HBox;
import javafx.scene.control.Label;
import javafx.geometry.NodeOrientation;
import z808.ui.BeautyFactory;

public class RegistersArea extends ScrollPane {
	private HBox regBox;
	private VBox namBox;
	private VBox valBox;

	public RegistersArea () {
		super();
		setPrefSize(BeautyFactory.SCREEN_WIDTH  * 0.2,
								BeautyFactory.SCREEN_HEIGHT * 0.3);
		setVbarPolicy(ScrollPane.ScrollBarPolicy.ALWAYS);

		this.regBox = new HBox(0);
		this.namBox = new VBox(0);
		this.valBox = new VBox(0);
		setContent(this.regBox);
		this.regBox.getChildren().addAll(this.namBox, this.valBox);
		boolean b = true;

		this.addInfoLabel("Registers", this.namBox);
		this.addInfoLabel("Value"    , this.valBox);

		this.addInfoLabel("CL", this.namBox);
		this.addRegister("0", b, this.valBox);
		
		this.addInfoLabel("RI", this.namBox);
		this.addRegister("0", b=!b, this.valBox);
		
		this.addInfoLabel("REM", this.namBox);
		this.addRegister("0", b=!b, this.valBox);
		
		this.addInfoLabel("RBM", this.namBox);
		this.addRegister("0", b=!b, this.valBox);
		
		this.addInfoLabel("AX", this.namBox);
		this.addRegister("0", b=!b, this.valBox);
		
		this.addInfoLabel("DX", this.namBox);
		this.addRegister("0", b=!b, this.valBox);
	}

	private void addInfoLabel(String v, Pane p) {
		Label l = new Label(v);
		l.setStyle(BeautyFactory.GetStyle()
							 + "-fx-background-color: white;"
							 + "-fx-font-size: 16px;"
							 );
		l.setNodeOrientation(NodeOrientation.RIGHT_TO_LEFT);
		l.setMinSize(62, 24);
		l.setMaxSize(62, 24);
		p.getChildren().add(l);
		return;
	}

	private void addRegister(String v, boolean blue, Pane p) {
		Label l = new Label(v);
		l.setStyle(BeautyFactory.GetStyle()
							 + "-fx-background-color: " + (blue ? "lightsteelblue" : "lightgray") + ";"
							 + "-fx-font-size: 16px;");
		l.setNodeOrientation(NodeOrientation.RIGHT_TO_LEFT);
		l.setMinSize(62, 24);
		l.setMaxSize(62, 24);
		p.getChildren().add(l);
		return;
	}
}
