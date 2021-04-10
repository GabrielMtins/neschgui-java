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

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.MouseInfo;
import java.lang.*;
import java.util.Timer;
import java.util.TimerTask;

public class ColorChooser extends JPanel{
	int current_color;
	Canvas canvas;

	public ColorChooser(Canvas canvas){
		addMouseListener(new input());
		current_color = 1;
		this.canvas = canvas;
		resizeCanvas();
	}

	public void paintComponent(Graphics g){
		super.paintComponent(g);

		resizeCanvas();

		int width = getWidth();
		int height = getHeight();

		g.setColor(Color.black);
		g.fillRect(0, 0, width, height);

		for(int i = 0; i < 4; i++){
			g.setColor(canvas.getColorPalette(i));
			g.fillRect(i*height, 0, height, height);
		}

		g.setColor(Color.white);
		g.drawRect(current_color*height, 0, height, height);

		g.drawLine(current_color*height, 0, current_color*height+height, height);
		g.drawLine(current_color*height, height, current_color*height+height, 0);
	}

	private void resizeCanvas(){
		if(getWidth() == getHeight()*4) return;

		setSize(new Dimension(getHeight()*4, getHeight()));
	}

	public void setCurrentColor(int color_id){
		current_color = color_id;
		canvas.setCurrentColor(color_id);
		repaint();
	}

	private class input implements MouseListener{
		@Override
		public void mouseClicked(MouseEvent e){
		}

		@Override
		public void mouseEntered(MouseEvent e){
		}

		@Override
		public void mouseExited(MouseEvent e){
		}

		@Override
		public void mousePressed(MouseEvent e){
			setCurrentColor(e.getX() / getHeight());
		}

		@Override
		public void mouseReleased(MouseEvent e){
		}
	}
}
