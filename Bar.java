/*
 	Copyright 2021 Gabriel Martins
	This file is part of Neschgui.

    Neschgui is free software: you can redistribute it and/or modify
    it under the terms of the GNU General Public License as published by
    the Free Software Foundation, either version 3 of the License, or
    (at your option) any later version.

    Neschgui is distributed in the hope that it will be useful,
    but WITHOUT ANY WARRANTY; without even the implied warranty of
    MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
    GNU General Public License for more details.

    You should have received a copy of the GNU General Public License
    along with Neschgui.  If not, see <https://www.gnu.org/licenses/>.
*/

// this files contains the toolbar that there's in the bottom of the program

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
		getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke("C"), "copy");
		getActionMap().put("copy", new AbstractAction(){
			public void actionPerformed(ActionEvent e){ onChangeCopy(); }
		});

		getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke("V"), "paste");
		getActionMap().put("paste", new AbstractAction(){
			public void actionPerformed(ActionEvent e){ onChangePaste(); }
		});

		getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke("B"), "pencil");
		getActionMap().put("pencil", new AbstractAction(){
			public void actionPerformed(ActionEvent e){ onChangePencil(); }
		});

		getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke("Z"), "undo");
		getActionMap().put("undo", new AbstractAction(){
			public void actionPerformed(ActionEvent e){ canvas.undoAction(); }
		});

		getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke("1"), "c0");
		getActionMap().put("c0", new AbstractAction(){
			public void actionPerformed(ActionEvent e){ colorChooser.setCurrentColor(0); }
		});

		getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke("2"), "c1");
		getActionMap().put("c1", new AbstractAction(){
			public void actionPerformed(ActionEvent e){ colorChooser.setCurrentColor(1); }
		});

		getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke("3"), "c2");
		getActionMap().put("c2", new AbstractAction(){
			public void actionPerformed(ActionEvent e){ colorChooser.setCurrentColor(2); }
		});

		getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke("4"), "c3");
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

		button = new JButton("Undo");
		button.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent e){ canvas.undoAction(); }
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
