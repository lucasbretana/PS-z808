package z808.ui;

import javafx.scene.control.TextArea;
import z808.ui.BeautyFactory;

public class OutputArea extends TextArea {

	public OutputArea () {
		super();
		setPrefSize(BeautyFactory.SCREEN_WIDTH  * 0.8,
								BeautyFactory.SCREEN_HEIGHT * 0.2);
		// setDisable(true);
		setWrapText(true);
		setStyle(BeautyFactory.GetStyle()
						 + "-fx-font-size: 12px;"
						 + "-fx-opacity: 1;"
						 );
	}

	public void updateScreen (String txt) {
		appendText(txt);
		appendText("\n");
	}
}
