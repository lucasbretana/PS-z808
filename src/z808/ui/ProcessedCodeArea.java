package z808.ui;

import javafx.scene.control.TextArea;
import z808.ui.BeautyFactory;
import z808.Processor;

public class ProcessedCodeArea extends TextArea {
	private Processor machine;

	public ProcessedCodeArea () {
		super();
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

	public void setProcessor(Processor p) { this.machine = p; }

	public void updateScreen () {
		clear();
		appendText(this.machine.codeToString());
	}
}
