import javax.swing.*;
import java.awt.*;
import java.lang.*;

public class App{
	JFrame frame;

	public App(){
		frame = new JFrame();
		frame.setTitle("neschgui");
		frame.setLayout(new BorderLayout());

		JScrollBar scroll = new JScrollBar();
		scroll.setMaximum(10000);
		scroll.setVisibleAmount(1000);

		CanvasFrame canvasframe = new CanvasFrame(scroll);

		Menu menu = new Menu(canvasframe.getCanvas());

		Bar bar = new Bar(canvasframe.getCanvas(), canvasframe.getSpriteCanvas());

		frame.add(menu, BorderLayout.PAGE_START);
		frame.add(scroll, BorderLayout.LINE_START);
		frame.add(canvasframe, BorderLayout.CENTER);
		frame.add(bar, BorderLayout.PAGE_END);

		frame.setVisible(true);
		frame.setSize(640*16/9, 640);
	}

	public static void main(String args[]){
		new App();
	}
}
