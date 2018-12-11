package z808.ui;

import java.util.List;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.TilePane;
import javafx.scene.control.Label;
import javafx.geometry.NodeOrientation;
import z808.ui.BeautyFactory;
import z808.memory.Address;
import z808.memory.Register;
import z808.Processor;

public class MemoryArea extends ScrollPane {
	public static final double NODE_WIDTH = 62;
	public static final double NODE_HIGHT = 24;

	private TilePane mem;
	private Processor machine;
	private int insertIndex;
	private int colourIndex;

	public MemoryArea () {
		super();
		setPrefSize(BeautyFactory.SCREEN_WIDTH  * 0.2,
								BeautyFactory.SCREEN_HEIGHT * 0.3);
		setVbarPolicy(ScrollPane.ScrollBarPolicy.ALWAYS);

		this.mem = new TilePane();
		this.mem.setHgap(0);
		this.mem.setPrefColumns(3);
		setContent(this.mem);

		this.addInfoLabel("Address");
		this.addInfoLabel("+0");
		this.addInfoLabel("+1");
		this.insertIndex = 0;
		this.colourIndex = 0;
	}

	public void setProcessor(Processor p) {
		this.machine = p;
		List<Register> l = this.machine.getMemoryRegisters();
		for (Register r: l) {
			if (this.insertIndex >= 256) break; // TODO: @JONATHAS small memory to avoid slowdown on UI
			this.addRegister(r);
		}
	}

	public void updateScreen () {
	}

	private void addInfoLabel(String v) {
		Label l = new Label(v);
		l.setStyle(BeautyFactory.GetStyle()
							 + "-fx-background-color: white;"
							 + "-fx-font-size: 16px;"
							 );
		l.setMinSize(NODE_WIDTH, NODE_HIGHT);
		l.setMaxSize(NODE_WIDTH, NODE_HIGHT);
		this.mem.getChildren().add(l);
		return;
	}

	private void addRegister(Register r) {
		Label l = new Label();
		boolean blue = this.colourIndex < 2;
		l.setStyle(BeautyFactory.GetStyle()
							 + "-fx-background-color: " + (blue ? "lightsteelblue" : "lightgray") + ";"
							 + "-fx-font-size: 16px;");
		l.setNodeOrientation(NodeOrientation.RIGHT_TO_LEFT);
		l.setMinSize(NODE_WIDTH, NODE_HIGHT);
		l.setMaxSize(NODE_WIDTH, NODE_HIGHT);
		l.textProperty().bind(r.getProperty());

		if (this.insertIndex % 2 == 0) {
			this.addInfoLabel(String.format("%04X", this.insertIndex*2));
		}
		this.mem.getChildren().add(l);
		this.insertIndex += 1;
		this.colourIndex = (this.colourIndex + 1) % 4;
		return;
	}
}
