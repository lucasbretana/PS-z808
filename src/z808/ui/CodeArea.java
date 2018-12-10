package z808.ui;

import java.util.Arrays;
import java.util.ArrayList;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.control.TextArea;
import javafx.scene.control.SingleSelectionModel;
import z808.ui.BeautyFactory;

public class CodeArea extends TabPane {
	SingleSelectionModel<Tab> selection;
	private int srcCount;

	public CodeArea () {
		super();
		setPrefSize(BeautyFactory.SCREEN_WIDTH  * 0.5,
								BeautyFactory.SCREEN_HEIGHT * 0.85);
		this.selection = getSelectionModel();
		this.srcCount = 0;
		addSourceFile();
	}

	public void addSourceFile() {
		Tab ftab = new Tab();
		ftab.setText("Src " + ++srcCount);
		CodeBox fbox = new CodeBox();
		ftab.setContent(fbox);
		getTabs().add(ftab);
	}

	public void updateScreen (String txt) {
		CodeBox b = ((CodeBox) this.selection.getSelectedItem().getContent());
		b.clear();
		b.appendText(txt);
	}

	public ArrayList<String> getCode () {
		CodeBox b = ((CodeBox) this.selection.getSelectedItem().getContent());
		String[] lines = b.getText().split("\n");
		return new ArrayList<String>(Arrays.asList(lines));
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
