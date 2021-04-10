import javax.swing.*;
import java.awt.*;
import java.lang.*;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.KeyAdapter;

public class Bar extends JPanel{
	private JToolBar toolBar;
	private Canvas canvas;
	private SpriteCanvas sprCanvas;
	private ColorChooser colorChooser;

	private void onChangeColor(){
		Color color = JColorChooser.showDialog(canvas, "Choose color", canvas.getCurrentColor());
		if(color != null){
			canvas.changeCurrentColor(color);
			colorChooser.repaint();
			canvas.repaint();
		}
	}

	private void onChangePencil(){
		sprCanvas.changeTool("pencil");
	}

	private void onChangeCopy(){
		sprCanvas.changeTool("copy");
	}

	private void onChangePaste(){
		sprCanvas.changeTool("paste");
	}

	private void setUpKeyBindings(){
		getInputMap().put(KeyStroke.getKeyStroke("C"), "copy");
		getActionMap().put("copy", new AbstractAction(){
			public void actionPerformed(ActionEvent e){ onChangeCopy(); }
		});

		getInputMap().put(KeyStroke.getKeyStroke("V"), "paste");
		getActionMap().put("paste", new AbstractAction(){
			public void actionPerformed(ActionEvent e){ onChangePaste(); }
		});

		getInputMap().put(KeyStroke.getKeyStroke("B"), "pencil");
		getActionMap().put("pencil", new AbstractAction(){
			public void actionPerformed(ActionEvent e){ onChangePencil(); }
		});

		getInputMap().put(KeyStroke.getKeyStroke("1"), "c0");
		getActionMap().put("c0", new AbstractAction(){
			public void actionPerformed(ActionEvent e){ colorChooser.setCurrentColor(0); }
		});

		getInputMap().put(KeyStroke.getKeyStroke("2"), "c1");
		getActionMap().put("c1", new AbstractAction(){
			public void actionPerformed(ActionEvent e){ colorChooser.setCurrentColor(1); }
		});

		getInputMap().put(KeyStroke.getKeyStroke("3"), "c2");
		getActionMap().put("c2", new AbstractAction(){
			public void actionPerformed(ActionEvent e){ colorChooser.setCurrentColor(2); }
		});

		getInputMap().put(KeyStroke.getKeyStroke("4"), "c3");
		getActionMap().put("c3", new AbstractAction(){
			public void actionPerformed(ActionEvent e){ colorChooser.setCurrentColor(3); }
		});
	}

	private void setUpButtons(){
		JButton button = new JButton("Change color");
		button.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent e){ onChangeColor(); }
		});
		toolBar.add(button);

		button = new JButton("Pencil");
		button.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent e){ onChangePencil(); }
		});
		toolBar.add(button);

		button = new JButton("Copy");
		button.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent e){ onChangeCopy(); }
		});
		toolBar.add(button);

		button = new JButton("Paste");
		button.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent e){ onChangePaste(); }
		});
		toolBar.add(button);
	}

	public Bar(Canvas canvas, SpriteCanvas sprCanvas){
		this.canvas = canvas;
		this.sprCanvas = sprCanvas;

		setLayout(new GridLayout(1, 2));

		colorChooser = new ColorChooser(canvas);
		toolBar = new JToolBar(JToolBar.HORIZONTAL);

		setUpKeyBindings();
		setUpButtons();

		toolBar.add(colorChooser);
		add(toolBar);
	}
}
