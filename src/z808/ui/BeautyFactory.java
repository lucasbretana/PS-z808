package z808.ui;

import javafx.scene.control.Label;
import javafx.scene.layout.TilePane;
import javafx.scene.control.TextArea;


public class BeautyFactory {

	protected static TextArea CodeArea (String txt) {
		TextArea tx = new TextArea(txt);
		tx.setStyle("-fx-background-color: antiquewhite;"
								+ "-fx-font-size: 20px;");
		return tx;
	}

	protected static TextArea ReadArea (String txt) {
		TextArea tx = new TextArea(txt);
		tx.setDisable(true);
		tx.setStyle("-fx-background-color: antiquewhite;"
								+ "-fx-opacity: 1;"
								+ "-fx-font-size: 20px;");
		return tx;
	}

	protected static TextArea OutputArea (String txt) {
		TextArea tx = new TextArea(txt);
		tx.setDisable(true);
		tx.setStyle("-fx-opacity: 1;"
								+ "-fx-text-fill: black;"
								+ "-fx-font-size: 20px;");
		tx.setMaxHeight(0.5);
		return tx;
	}

	protected static TilePane MemoryArea (int size) {
		TilePane mem = new TilePane();
		mem.setHgap(8);
		mem.setPrefColumns(2);
		mem.getChildren().add(new Label("Test"));
		mem.getChildren().add(new Label("Test"));
		// for (int i = 0; i < size; ++i)
		// 	mem.getChildren().add(BeautyFactory.MemoryEntry(i%2 == 0));
		return mem;
	}

	protected static Label MemoryEntry(boolean blue) {
		Label l = new Label("0");
		l.setStyle("-fx-background-color: " + (blue ? "blue" : "red") + ";"
							 + "-fx-font-size: 12px;");
		return l;
	}
}
