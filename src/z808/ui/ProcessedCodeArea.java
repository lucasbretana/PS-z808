package z808.ui;

import javafx.scene.control.TextArea;
import z808.ui.BeautyFactory;

public class ProcessedCodeArea extends TextArea {
	public ProcessedCodeArea (String txt) {
		super(txt);
		setPrefSize(BeautyFactory.SCREEN_WIDTH  * 0.3,
								BeautyFactory.SCREEN_HEIGHT * 0.85);
		setDisable(true);
		setWrapText(true);
		setStyle(BeautyFactory.GetStyle()
						 + "-fx-background-color: antiquewhite;"
						 + "-fx-font-size: 20px;"
						 + "-fx-opacity: 1;"
						 );
	}
}
