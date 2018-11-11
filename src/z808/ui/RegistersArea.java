package z808.ui;

import javafx.scene.control.ScrollPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.TilePane;
import javafx.scene.control.Label;
import javafx.geometry.NodeOrientation;
import z808.ui.BeautyFactory;
import z808.memory.Address;
import z808.Processor;

public class RegistersArea extends ScrollPane {
	public static final double NODE_WIDTH = 93;
	public static final double NODE_HIGHT = 24;

	private TilePane regBox;
	private Processor machine;

	public RegistersArea () {
		super();
		setPrefSize(BeautyFactory.SCREEN_WIDTH  * 0.2,
								BeautyFactory.SCREEN_HEIGHT * 0.3);
		setVbarPolicy(ScrollPane.ScrollBarPolicy.ALWAYS);

		this.regBox = new TilePane();
		this.regBox.setHgap(0);
		this.regBox.setPrefColumns(2);
		setContent(this.regBox);
		boolean b = true;

		this.addInfoLabel("Registers");
		this.addInfoLabel("Value");

		this.addInfoLabel("CL");
		this.addRegister("XX", b);
		
		this.addInfoLabel("RI");
		this.addRegister("XX", b=!b);
		
		this.addInfoLabel("REM");
		this.addRegister("XX", b=!b);
		
		this.addInfoLabel("RBM");
		this.addRegister("XX", b=!b);
		
		this.addInfoLabel("AX");
		this.addRegister("XX", b=!b);
		
		this.addInfoLabel("DX");
		this.addRegister("XX", b=!b);
	}

	public void setProcessor(Processor p) { this.machine = p; }

	public void updateScreen () {
	}

	private void addInfoLabel(String v) {
		Label l = new Label(v);
		l.setStyle(BeautyFactory.GetStyle()
							 + "-fx-background-color: white;"
							 + "-fx-font-size: 16px;"
							 );
		l.setMinSize(NODE_WIDTH, NODE_HIGHT);
		l.setMaxSize(NODE_WIDTH, NODE_HIGHT);
		this.regBox.getChildren().add(l);
		return;
	}

	private void addRegister(String v, boolean blue) {
		Label l = new Label(v);
		l.setStyle(BeautyFactory.GetStyle()
							 + "-fx-background-color: " + (blue ? "lightsteelblue" : "lightgray") + ";"
							 + "-fx-font-size: 16px;");
		l.setNodeOrientation(NodeOrientation.RIGHT_TO_LEFT);
		l.setMinSize(NODE_WIDTH, NODE_HIGHT);
		l.setMaxSize(NODE_WIDTH, NODE_HIGHT);
		this.regBox.getChildren().add(l);
		return;
	}
}
