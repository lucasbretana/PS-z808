package z808.ui;

import javafx.scene.control.ScrollPane;
import javafx.scene.layout.VBox;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.control.Label;
import javafx.geometry.NodeOrientation;
import z808.ui.BeautyFactory;

public class MemoryArea extends ScrollPane {
	private HBox mem;
	private VBox idx;
	private VBox mm1;
	private VBox mm2;

	public MemoryArea (int size) {
		super();
		setPrefSize(BeautyFactory.SCREEN_WIDTH  * 0.2,
								BeautyFactory.SCREEN_HEIGHT * 0.3);
		setVbarPolicy(ScrollPane.ScrollBarPolicy.ALWAYS);

		this.mem = new HBox(0);
		this.idx = new VBox(0);
		this.mm1 = new VBox(0);
		this.mm2 = new VBox(0);
		setContent(this.mem);
		this.mem.getChildren().addAll(idx, mm1, mm2);
		
		this.addInfoLabel("Address", idx);
		this.addInfoLabel("+0", mm1);
		this.addInfoLabel("+1", mm2);
		for (int i = 0; i < size/2; ++i) {
			this.addInfoLabel(String.format("%04X", i*2), idx);
			this.addRegister("313", i%2 == 0, mm1);
			this.addRegister("313", i%2 != 0, mm2);
		}
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
