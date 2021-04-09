import javax.swing.*;
import java.awt.*;
import java.lang.*;

class CanvasFrame extends JPanel{
	private Canvas canvas;

	public CanvasFrame(JScrollBar scroll){
		setLayout(new GridLayout());

		canvas = new Canvas(128, 128);
		canvas.setScroll(scroll);

		SpriteCanvas spr = new SpriteCanvas(16, 16, canvas);

		add(canvas);
		add(spr);
	}

	public Canvas getCanvas(){
		return canvas;
	}
}
