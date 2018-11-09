package z808.ui;

import javafx.scene.control.ScrollPane;
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
		tx.setPrefSize(BeautyFactory.SCREEN_WIDTH  * 0.5,
									 BeautyFactory.SCREEN_HEIGHT * 0.85);
		tx.setStyle("-fx-background-color: antiquewhite;"
								+ "-fx-border-style: solid;"
								+ "-fx-border-color: silver;"
								+ "-fx-border-width: 2px;"
								+ "-fx-font-size: 20px;");
		return tx;
	}

	protected static TextArea ReadArea (String txt) {
		TextArea tx = new TextArea(txt);
		tx.setDisable(true);
		tx.setPrefSize(BeautyFactory.SCREEN_WIDTH  * 0.3,
									 BeautyFactory.SCREEN_HEIGHT * 0.85);
		tx.setStyle("-fx-background-color: antiquewhite;"
								+ "-fx-border-style: solid;"
								+ "-fx-border-color: silver;"
								+ "-fx-border-width: 2px;"
								+ "-fx-opacity: 1;"
								+ "-fx-font-size: 20px;");
		return tx;
	}

	protected static TextArea OutputArea (String txt) {
		TextArea tx = new TextArea(txt);
		tx.setDisable(true);
		tx.setPrefSize(BeautyFactory.SCREEN_WIDTH  * 0.8,
									 BeautyFactory.SCREEN_HEIGHT * 0.2);
		tx.setStyle("-fx-opacity: 1;"
								+ "-fx-border-style: solid;"
								+ "-fx-border-color: silver;"
								+ "-fx-border-width: 2px;"
								+ "-fx-font-size: 20px;");
		return tx;
	}

	protected static ScrollPane MemoryArea (int size) {
		ScrollPane region = new ScrollPane();
		HBox mem = new HBox(0);
		VBox idx = new VBox(0);
		VBox mm1 = new VBox(0);
		VBox mm2 = new VBox(0);
		mem.getChildren().addAll(idx, mm1, mm2);
		idx.getChildren().add(BeautyFactory.MemoryLabel("Address"));
	  mm1.getChildren().add(BeautyFactory.MemoryLabel("+0"));
	  mm2.getChildren().add(BeautyFactory.MemoryLabel("+1"));
		for (int i = 0; i < size/2; ++i) {
			idx.getChildren().add(BeautyFactory.MemoryLabel(String.format("%04X", i*2)));
			mm1.getChildren().add(BeautyFactory.MemoryEntry("313", i%2 == 0));
			mm2.getChildren().add(BeautyFactory.MemoryEntry("313", i%2 != 0));
		}
		region.setPrefSize(BeautyFactory.SCREEN_WIDTH  * 0.2,
											 BeautyFactory.SCREEN_HEIGHT * 0.475);
		region.setContent(mem);
		return region;
	}

	protected static Label MemoryLabel(String v) {
		Label l = new Label(v);
		int fontSize = (v.length() > 4) ? 12 : 16;
		l.setStyle("-fx-background-color: white;"
							 + "-fx-text-align: center;"
							 + "-fx-border-style: solid;"
							 + "-fx-border-color: silver;"
							 + "-fx-border-width: 2px;"
							 + "-fx-font-size: " + fontSize + "px;");
		l.setNodeOrientation(NodeOrientation.RIGHT_TO_LEFT);
		l.setMinSize(62, 24);
		l.setMaxSize(62, 24);
		return l;
	}

	protected static Label MemoryEntry(String v, boolean blue) {
		Label l = new Label(v);
		l.setStyle("-fx-background-color: " + (blue ? "lightsteelblue" : "lightgray") + ";"
							 + "-fx-text-align: center;"
							 + "-fx-border-style: solid;"
							 + "-fx-border-color: silver;"
							 + "-fx-border-width: 2px;"
							 + "-fx-font-size: 16px;");
		l.setNodeOrientation(NodeOrientation.RIGHT_TO_LEFT);
		l.setMinSize(62, 24);
		l.setMaxSize(62, 24);
		return l;
	}
}
