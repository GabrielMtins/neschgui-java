import javax.swing.*;
import java.awt.*;
import java.lang.*;

class CanvasFrame extends JPanel{
	private Canvas canvas;
	private SpriteCanvas spr;

	public CanvasFrame(JScrollBar scroll){
		setLayout(new GridLayout());

		canvas = new Canvas(128, 128);
		canvas.setScroll(scroll);

		spr = new SpriteCanvas(16, 16, canvas);

		add(canvas);
		add(spr);
	}

	public Canvas getCanvas(){
		return canvas;
	}

	public SpriteCanvas getSpriteCanvas(){
		return spr;
	}
}
