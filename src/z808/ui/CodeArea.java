package z808.ui;

import javafx.scene.control.TextArea;
import z808.ui.BeautyFactory;

public class CodeArea extends TextArea {
	public CodeArea () {
		super();
		setPrefSize(BeautyFactory.SCREEN_WIDTH  * 0.5,
								BeautyFactory.SCREEN_HEIGHT * 0.85);
		setWrapText(true);
		setStyle(BeautyFactory.GetStyle()
						 + "-fx-background-color: antiquewhite;"
						 + "-fx-font-size: 20px;"
						 );
	}
	public void updateScreen (String txt) {
		clear();
		appendText(txt);
	}
}
