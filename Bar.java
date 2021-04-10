import javax.swing.*;
import java.awt.*;
import java.lang.*;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class Bar extends JPanel{
	private JToolBar toolBar;
	private Canvas canvas;
	private SpriteCanvas sprCanvas;
	private ColorChooser colorChooser;

	private class onChangeColor implements ActionListener{
		public void actionPerformed(ActionEvent e){
			Color color = JColorChooser.showDialog(canvas, "Choose color", canvas.getCurrentColor());
			if(color != null){
				canvas.changeCurrentColor(color);
				colorChooser.repaint();
				canvas.repaint();
			}
		}
	}

	public class onChangePencil implements ActionListener{
		public void actionPerformed(ActionEvent e){
			sprCanvas.changeTool("pencil");
		}
	}

	public class onChangeCopy implements ActionListener{
		public void actionPerformed(ActionEvent e){
			sprCanvas.changeTool("copy");
		}
	}

	public class onChangePaste implements ActionListener{
		public void actionPerformed(ActionEvent e){
			sprCanvas.changeTool("paste");
		}
	}

	public Bar(Canvas canvas, SpriteCanvas sprCanvas){
		this.canvas = canvas;
		this.sprCanvas = sprCanvas;

		setLayout(new GridLayout(1, 2));

		colorChooser = new ColorChooser(canvas);
		toolBar = new JToolBar(JToolBar.HORIZONTAL);

		JButton button = new JButton("Change color");
		button.addActionListener(new onChangeColor());
		toolBar.add(button);

		button = new JButton("Pencil");
		button.addActionListener(new onChangePencil());
		toolBar.add(button);

		button = new JButton("Copy");
		button.addActionListener(new onChangeCopy());
		toolBar.add(button);

		button = new JButton("Paste");
		button.addActionListener(new onChangePaste());
		toolBar.add(button);

		toolBar.add(colorChooser);
		add(toolBar);
	}
}
