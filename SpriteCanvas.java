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
import java.awt.datatransfer.StringSelection;
import java.awt.Toolkit;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.DataFlavor;

class GPaste extends GTool{
	public GPaste(SpriteCanvas canvas){
		super(canvas);
	}

	@Override
	public void update(){
		if(getMouseState()){
			Canvas c = canvas.getCanvas();
			Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
			String to_copy = "";

			try{
				to_copy = (String) clipboard.getData(DataFlavor.stringFlavor);
			}
			catch(Exception e){
				e.printStackTrace();
			}

			if(to_copy == null || to_copy.length() < 64) return;

			/* since the current color is going to be changed, we need to save
			 * its value before the operation */

			int before_color = c.getCurrentColorID();

			int sprite_x = current_x*2/canvas.getWidth();
			int sprite_y = current_y*2/canvas.getHeight();

			if(sprite_x < 0) sprite_x = 0;
			if(sprite_y < 0) sprite_y = 0;

			if(sprite_x > 2) sprite_x = 3;
			if(sprite_y > 2) sprite_y = 3;

			for(int j = 0; j < 8; j++){
				for(int i = 0; i < 8; i++){
					int current_color = Character.getNumericValue(to_copy.charAt(j*8+i));
					c.setCurrentColor(current_color);
					c.putPixelOnCurrentSprite(i + sprite_x*8, j + sprite_y*8);
				}
			}
			
			c.setCurrentColor(before_color);
			setMouseState(false);
			c.setActionAsLast();
		}
	}

	@Override
	public void render(Graphics g){
		// we just render a blue transparent color

		int width = canvas.getWidth();
		int height = canvas.getHeight();

		int selected_x = current_x*2/width;
		int selected_y = current_y*2/height;

		selected_x *= width/2;
		selected_y *= height/2;

		g.setColor(new Color(0, 255, 100, 100));
		g.fillRect(selected_x, selected_y, width/2, height/2);
	}
}

class GCopy extends GTool{
	public GCopy(SpriteCanvas canvas){
		super(canvas);
	}

	@Override
	public void update(){
		if(getMouseState()){
			Canvas c = canvas.getCanvas();
			String to_copy = "";

			int sprite_x = current_x*2/canvas.getWidth();
			int sprite_y = current_y*2/canvas.getHeight();

			if(sprite_x < 0) sprite_x = 0;
			if(sprite_y < 0) sprite_y = 0;

			if(sprite_x > 2) sprite_x = 3;
			if(sprite_y > 2) sprite_y = 3;

			/* to copy into the clipboard, the program creates an string which contains
			 * the id of the current color. After that, it can be read when we paste
			 * a sprite. */

			for(int j = 0; j < 8; j++){
				for(int i = 0; i < 8; i++){
					to_copy += c.getColorFromCurrentSprite(i + sprite_x*8, j + sprite_y*8);
				}
			}

			StringSelection selection = new StringSelection(to_copy);
			Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
			clipboard.setContents(selection, null);
			setMouseState(false);
		}
	}

	@Override
	public void render(Graphics g){
		// we just render a green transparent color

		int width = canvas.getWidth();
		int height = canvas.getHeight();

		int selected_x = current_x*2/width;
		int selected_y = current_y*2/height;

		selected_x *= width/2;
		selected_y *= height/2;

		g.setColor(new Color(0, 120, 255, 100));
		g.fillRect(selected_x, selected_y, width/2, height/2);
	}
}

class GPencil extends GTool{
	private boolean is_mouse_on_canvas;

	public GPencil(SpriteCanvas canvas){
		super(canvas);
		is_mouse_on_canvas = false;
	}

	@Override
	public void update(){
		if(getMouseState()){
			canvas.putPixel(current_x, current_y);
			is_mouse_on_canvas = true;
		}
		else if(is_mouse_on_canvas){
			canvas.putPixel(end_x, end_y);

			Canvas c = canvas.getCanvas();
			// we set the action as last because the canvas needs to know when it's the last action
			c.setActionAsLast();

			is_mouse_on_canvas = false;
		}
	}
}

public class SpriteCanvas extends JPanel{
	private int internal_width, internal_height;
	private boolean isMouseOn;
	private Timer timer;
	private Canvas canvas;
	private GTool current_tool;

	public SpriteCanvas(int w, int h, Canvas canvas){
		internal_width = w;
		internal_height = h;
		this.canvas = canvas;

		current_tool = new GPencil(this);

		addMouseListener(new input());

		timer = new Timer();
		timer.scheduleAtFixedRate(new updateLoop(), 100, 25);
	}

	public void changeTool(String tool){
		switch(tool){
			case "pencil":
			current_tool = new GPencil(this);
			break;

			case "copy":
			current_tool = new GCopy(this);
			break;

			case "paste":
			current_tool = new GPaste(this);
			break;
		}
	}

	public Canvas getCanvas(){
		return canvas;
	}

	public void paintComponent(Graphics g){
		super.paintComponent(g);

		Graphics2D g2d = (Graphics2D) g;

		int width = getWidth();
		int height = getHeight();

		g.setColor(canvas.getColorPalette(0));
		g.fillRect(0, 0, width, height);

		g2d.scale((double) width / internal_width, (double) height / internal_height);

		for(int x = 0; x < 16; x++){
			for(int y = 0; y < 16; y++){
				int color_index = canvas.getColorFromCurrentSprite(x, y);

				if(color_index == 0) continue;

				Color current_color = canvas.getColorPalette(color_index);

				g2d.setColor(current_color);
				g2d.fillRect(x, y, 1, 1);
			}
		}

		g2d.scale((double) internal_width / width, (double) internal_height / height);

		g.setColor(Color.white);
		g.drawLine(width/2, 0, width/2, height);
		g.drawLine(0, height/2, width, height/2);

		current_tool.render(g);
	}

	public void putPixel(int x, int y){
		int width = getWidth();
		int height = getHeight();

		if(x >= width || y >= height || x < 0 || y < 0) return;

		x = x*internal_width/width;
		y = y*internal_height/height;
		
		canvas.putPixelOnCurrentSprite(x, y);
	}

	private void updateMouse(){
		PointerInfo a = MouseInfo.getPointerInfo();
		Point b = a.getLocation();
		SwingUtilities.convertPointFromScreen(b, this);
		
		current_tool.setCurrentPosition((int) b.getX(), (int) b.getY());
		current_tool.update();
	}

	private class updateLoop extends TimerTask{

		@Override
		public void run(){
			resizeCanvas();
			updateMouse();
			repaint();
		}
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
			isMouseOn = true;
			current_tool.setInitialPosition(e.getX(), e.getY());
			current_tool.setMouseState(true);
		}

		@Override
		public void mouseReleased(MouseEvent e){
			isMouseOn = false;
			current_tool.setEndPosition(e.getX(), e.getY());
			current_tool.setMouseState(false);
		}
	}

	private void resizeCanvas(){
		if(canvas.getWidth()/2 == getWidth() && getWidth() == getHeight()) return;
		setSize(new Dimension(canvas.getWidth()/2, canvas.getHeight()/2));
	}
}
