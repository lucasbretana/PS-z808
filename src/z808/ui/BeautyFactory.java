package z808.ui;

import javafx.scene.control.TextArea;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.scene.layout.HBox;

import javafx.geometry.NodeOrientation;

public class BeautyFactory {

	public static final double SCREEN_WIDTH  = 1024;
	public static final double SCREEN_HEIGHT = 768;

	protected static TextArea CodeArea (String txt) {
		TextArea tx = new TextArea(txt);
		tx.setPrefSize(BeautyFactory.SCREEN_WIDTH  * 0.6,
									 BeautyFactory.SCREEN_HEIGHT * 0.7);
		tx.setStyle("-fx-background-color: antiquewhite;"
								+ "-fx-font-size: 20px;");
		return tx;
	}

	protected static TextArea ReadArea (String txt) {
		TextArea tx = new TextArea(txt);
		tx.setDisable(true);
		tx.setPrefSize(BeautyFactory.SCREEN_WIDTH  * 0.2,
									 BeautyFactory.SCREEN_HEIGHT * 0.7);
		tx.setStyle("-fx-background-color: antiquewhite;"
								+ "-fx-opacity: 1;"
								+ "-fx-font-size: 20px;");
		return tx;
	}

	protected static TextArea OutputArea (String txt) {
		TextArea tx = new TextArea(txt);
		tx.setDisable(true);
		tx.setPrefSize(BeautyFactory.SCREEN_WIDTH  * 0.8,
									 BeautyFactory.SCREEN_HEIGHT * 0.7);
		tx.setStyle("-fx-opacity: 1;"
								+ "-fx-text-fill: black;"
								+ "-fx-font-size: 20px;");
		tx.setMaxHeight(0.5);
		return tx;
	}

	protected static HBox MemoryArea (int size) {
		HBox mem = new HBox(0);
		VBox idx = new VBox(0);
		VBox mm1 = new VBox(0);
		VBox mm2 = new VBox(0);
		VBox mm3 = new VBox(0);
		mem.getChildren().addAll(idx, mm1, mm2, mm3);
		mem.setPrefSize(BeautyFactory.SCREEN_WIDTH  * 0.1,
										BeautyFactory.SCREEN_HEIGHT * 0.475);
		idx.getChildren().add(BeautyFactory.MemoryLabel("Address"));
	  mm1.getChildren().add(BeautyFactory.MemoryLabel("+0"));
	  mm2.getChildren().add(BeautyFactory.MemoryLabel("+1"));
	  mm3.getChildren().add(BeautyFactory.MemoryLabel("+2"));
		for (int i = 0; i < size/3; ++i) {
			idx.getChildren().add(BeautyFactory.MemoryLabel(String.format("%04X", i*3)));
			mm1.getChildren().add(BeautyFactory.MemoryEntry("313", i%2 == 0));
			mm2.getChildren().add(BeautyFactory.MemoryEntry("313", i%2 != 0));
			mm3.getChildren().add(BeautyFactory.MemoryEntry("313", i%2 == 0));
		}
		return mem;
	}

	protected static Label MemoryLabel(String v) {
		Label l = new Label(v);
		l.setStyle("-fx-background-color: white;"
							 + "-fx-text-align: center;"
							 + "-fx-border-style: solid;"
							 + "-fx-border-color: silver;"
							 + "-fx-border-width: 2px;"
							 + "-fx-font-size: 16px;");
		l.setNodeOrientation(NodeOrientation.RIGHT_TO_LEFT);
		l.setMinWidth(BeautyFactory.SCREEN_WIDTH  * 0.2 / 3);
		return l;
	}

	protected static Label MemoryEntry(String v, boolean blue) {
		Label l = new Label(v);
		l.setStyle("-fx-background-color: " + (blue ? "lightgray" : "lightsteelblue") + ";"
							 + "-fx-text-align: center;"
							 + "-fx-border-style: solid;"
							 + "-fx-border-color: silver;"
							 + "-fx-border-width: 2px;"
							 + "-fx-font-size: 16px;");
		l.setNodeOrientation(NodeOrientation.RIGHT_TO_LEFT);
		l.setMinWidth(BeautyFactory.SCREEN_WIDTH  * 0.2 / 3);
		return l;
	}
}
