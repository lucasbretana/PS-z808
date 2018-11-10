package z808.ui;

import javafx.scene.layout.HBox;
import javafx.scene.control.Button;

public class ToolBar extends HBox {
	private Button macroProcess;
	private Button assemble;
	private Button loadAndGo;
	private Button step;
	private Button run;

	public ToolBar() {
		super(2);
		setPrefSize(BeautyFactory.SCREEN_WIDTH  * 1,
										BeautyFactory.SCREEN_HEIGHT * 0.05);
		setStyle(BeautyFactory.GetStyle()
						 );

		this.macroProcess = new Button("MP");
		this.assemble = new Button("A");
		this.loadAndGo = new Button("LG");
		this.step = new Button(">");
		this.run = new Button(">>");

		getChildren().add(macroProcess);
		getChildren().add(assemble);
		getChildren().add(loadAndGo);
		getChildren().add(step);
		getChildren().add(run);
	}
}
