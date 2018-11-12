package z808.ui;

import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.control.TextArea;
import javafx.scene.control.SingleSelectionModel;
import z808.ui.BeautyFactory;

public class CodeArea extends TabPane {
	SingleSelectionModel<Tab> selection;

	public CodeArea () {
		super();
		setPrefSize(BeautyFactory.SCREEN_WIDTH  * 0.5,
								BeautyFactory.SCREEN_HEIGHT * 0.85);
		Tab ftab = new Tab();
		this.selection = getSelectionModel();
		ftab.setText("fileX");
		CodeBox fbox = new CodeBox();
		ftab.setContent(fbox);
		getTabs().add(ftab);
	}
	public void updateScreen (String txt) {
		CodeBox b = ((CodeBox) this.selection.getSelectedItem().getContent());
		b.clear();
		b.appendText(txt);
	}

	private class CodeBox extends TextArea {
		public CodeBox () {
			super();
			setWrapText(true);
			setStyle(BeautyFactory.GetStyle()
							 + "-fx-background-color: antiquewhite;"
							 + "-fx-font-size: 20px;"
							 );
		}
	}
}
