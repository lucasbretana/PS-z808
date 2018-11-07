package z808.ui;

import javafx.scene.layout.Border;
import javafx.scene.layout.BorderStroke;
import javafx.scene.paint.Color;
import javafx.scene.layout.BorderStrokeStyle;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.BorderWidths;

import javafx.scene.control.TextArea;

public class BeautyFactory {
  protected static Border generalBorder () {
		return new Border ((new BorderStroke(Color.BLACK, 
																				 BorderStrokeStyle.SOLID,
																				 CornerRadii.EMPTY,
																				 BorderWidths.DEFAULT)));
	}

	protected static TextArea ReadOnlyArea () {return ReadOnlyArea(null);}
	protected static TextArea ReadOnlyArea (String txt) {
		TextArea tx = new TextArea(txt);
		tx.setDisable(true);
		return tx;
	}
}
