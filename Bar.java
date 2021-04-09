import javax.swing.*;
import java.awt.*;
import java.lang.*;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class Bar extends JPanel{
	private JToolBar toolBar;
	private Canvas canvas;
	private ColorChooser colorChooser;

	private class onChangeColor implements ActionListener{
		public void actionPerformed(ActionEvent e){
			JColorChooser chooser = new JColorChooser();
			Color color = JColorChooser.showDialog(canvas, "Choose color", canvas.getCurrentColor());
			canvas.changeCurrentColor(color);
			colorChooser.repaint();
		}
	}

	public Bar(Canvas canvas){
		this.canvas = canvas;

		setLayout(new GridLayout(1, 2));

		colorChooser = new ColorChooser(canvas);
		toolBar = new JToolBar(JToolBar.HORIZONTAL);

		JButton button = new JButton("Change color");
		button.addActionListener(new onChangeColor());
		toolBar.add(button);

		add(toolBar);
		add(colorChooser);
	}
}
