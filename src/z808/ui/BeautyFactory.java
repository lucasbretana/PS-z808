package z808.ui;

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
}
