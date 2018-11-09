package z808.ui;

import javafx.scene.layout.VBox;
import javafx.scene.layout.HBox;
import javafx.scene.control.Label;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.ScrollPane;

import javafx.geometry.NodeOrientation;

public class BeautyFactory {

	public static final double SCREEN_WIDTH  = 1024;
	public static final double SCREEN_HEIGHT = 768;

	protected static TextArea CodeArea (String txt) {
		TextArea tx = new TextArea(txt);
		tx.setPrefSize(BeautyFactory.SCREEN_WIDTH  * 0.5,
									 BeautyFactory.SCREEN_HEIGHT * 0.85);
		tx.setWrapText(true);
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
		tx.setWrapText(true);
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
		tx.setWrapText(true);
		tx.setPrefSize(BeautyFactory.SCREEN_WIDTH  * 0.8,
									 BeautyFactory.SCREEN_HEIGHT * 0.2);
		tx.setStyle("-fx-opacity: 1;"
								+ "-fx-border-style: solid;"
								+ "-fx-border-color: silver;"
								+ "-fx-border-width: 2px;"
								+ "-fx-font-size: 20px;");
		return tx;
	}

	protected static ScrollPane RegistersArea () {
		ScrollPane region = new ScrollPane();
		region.setPrefSize(BeautyFactory.SCREEN_WIDTH  * 0.2,
											 BeautyFactory.SCREEN_HEIGHT * 0.3);
		HBox registers = new HBox(0);
		VBox names     = new VBox(0);
		VBox values    = new VBox(0);
		region.setContent(registers);
		region.setVbarPolicy(ScrollPane.ScrollBarPolicy.ALWAYS);
		boolean b = true;
		registers.getChildren().addAll(names, values);
		names.getChildren().add(BeautyFactory.MemoryLabel("Registers"));
		values.getChildren().add(BeautyFactory.MemoryLabel("Value"));
		names.getChildren().add(BeautyFactory.MemoryLabel("CL"));
		values.getChildren().add(BeautyFactory.MemoryEntry("0", b));
		names.getChildren().add(BeautyFactory.MemoryLabel("RI"));
		values.getChildren().add(BeautyFactory.MemoryEntry("0", b=!b));
		names.getChildren().add(BeautyFactory.MemoryLabel("REM"));
		values.getChildren().add(BeautyFactory.MemoryEntry("0", b=!b));
		names.getChildren().add(BeautyFactory.MemoryLabel("RBM"));
		values.getChildren().add(BeautyFactory.MemoryEntry("0", b=!b));
		names.getChildren().add(BeautyFactory.MemoryLabel("AX"));
		values.getChildren().add(BeautyFactory.MemoryEntry("0", b=!b));
		names.getChildren().add(BeautyFactory.MemoryLabel("DX"));
		values.getChildren().add(BeautyFactory.MemoryEntry("0", b=!b));
		return region;
	}

	protected static ScrollPane MemoryArea (int size) {
		ScrollPane region = new ScrollPane();
		region.setPrefSize(BeautyFactory.SCREEN_WIDTH  * 0.2,
											 BeautyFactory.SCREEN_HEIGHT * 0.475);
		HBox mem = new HBox(0);
		VBox idx = new VBox(0);
		VBox mm1 = new VBox(0);
		VBox mm2 = new VBox(0);
		region.setContent(mem);
		region.setVbarPolicy(ScrollPane.ScrollBarPolicy.ALWAYS);
		mem.getChildren().addAll(idx, mm1, mm2);
		idx.getChildren().add(BeautyFactory.MemoryLabel("Address"));
	  mm1.getChildren().add(BeautyFactory.MemoryLabel("+0"));
	  mm2.getChildren().add(BeautyFactory.MemoryLabel("+1"));
		for (int i = 0; i < size/2; ++i) {
			idx.getChildren().add(BeautyFactory.MemoryLabel(String.format("%04X", i*2)));
			mm1.getChildren().add(BeautyFactory.MemoryEntry("313", i%2 == 0));
			mm2.getChildren().add(BeautyFactory.MemoryEntry("313", i%2 != 0));
		}
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

	protected static HBox ToolBar() {
		HBox bar = new HBox(2);
		bar.setPrefSize(BeautyFactory.SCREEN_WIDTH  * 1,
										BeautyFactory.SCREEN_HEIGHT * 0.05);
		bar.setStyle("-fx-border-style: solid;"
								 + "-fx-border-color: silver;"
								 + "-fx-border-width: 2px;");
		bar.getChildren().add(new Button("MP"));
		bar.getChildren().add(new Button("A"));
		bar.getChildren().add(new Button("LG"));
		bar.getChildren().add(new Button(">"));
		bar.getChildren().add(new Button(">>"));
		return bar;
	}
}
