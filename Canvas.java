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
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.filechooser.FileFilter;
import java.util.*;

/* this class contains the informations about the old state of a pixel.
 * it is used to undo an action */

class OldState{
	public boolean isLastAction;
	public int selected_sprite;
	public int x, y;
	public int old_color;
	
	public OldState(int selected_sprite, int x, int y, int old_color){
		isLastAction = false;
		this.x = x;
		this.y = y;
		this.selected_sprite = selected_sprite;
		this.old_color = old_color;
	}

	public void setActionAsLast(){
		isLastAction = true;
	}
}

public class Canvas extends JPanel{
	private Rom rom;

	private int selected_sprite;
	private int selected_sprite_x;
	private int selected_sprite_y;
	private int selected_sprite_offset;

	private int old_offset;

	private int sprite_size;
	private int num_of_sprite;

	private int internal_width, internal_height;
	private Color []palette;
	private JScrollBar scroll;

	private int current_color;

	private Timer timer;

	private LinkedList<OldState> old_state_list;

	public Canvas(int w, int h){
		internal_width = w;
		internal_height = h;

		old_state_list = new LinkedList<OldState>();

		addMouseListener(new input());

		palette = new Color[4];
		palette[0] = new Color(0, 0, 0);
		palette[1] = new Color(255, 0, 0);
		palette[2] = new Color(0, 255, 0);
		palette[3] = new Color(0, 0, 255);

		current_color = 1;
		sprite_size = 8;
		num_of_sprite = 16;

		rom = new Rom();

		timer = new Timer();
		timer.scheduleAtFixedRate(new updateLoop(), 100, 25);
	}

	public void setScroll(JScrollBar s){
		scroll = s;
	}

	public void cleanRom(){
		selected_sprite_x = 0;
		selected_sprite_y = 0;
		selected_sprite_offset = 0;
		selected_sprite = 0;

		rom = new Rom();
		scroll.setValue(0);
		repaint();
	}

	public void saveRom(boolean saveAsSameFilename){
		if(rom.getFilename() == null) saveAsSameFilename = false;

		if(saveAsSameFilename){
			rom.save(null);
		}
		else{
			FileFilter filter = new FileNameExtensionFilter("NES file", "nes");
			JFileChooser chooser = new JFileChooser();
			chooser.addChoosableFileFilter(filter);
			int return_val = chooser.showSaveDialog(this);

			if(return_val == JFileChooser.APPROVE_OPTION){
				rom.save(chooser.getSelectedFile().getAbsolutePath());
			}
		}
	}

	public void loadRom(){
		JFileChooser chooser = new JFileChooser();
		int return_val = chooser.showOpenDialog(this);

		if(return_val == JFileChooser.APPROVE_OPTION){
			rom = new Rom(chooser.getSelectedFile().getAbsolutePath());
			scroll.setValue(0);
			selected_sprite_x = 0;
			selected_sprite_y = 0;
			selected_sprite_offset = 0;
			selected_sprite = 0;
		}

		repaint();
	}

	public void setCurrentColor(int color_index){
		current_color = color_index;
	}

	public Color getCurrentColor(){
		return palette[current_color];
	}

	public int getCurrentColorID(){
		return current_color;
	}

	public void changeCurrentColor(Color c){
		palette[current_color] = c;
		repaint();
	}

	public Color getColorPalette(int index){
		return palette[index];
	}

	public int getColorFromCurrentSprite(int x, int y){
		int offset = x/sprite_size + (int)(y/sprite_size)*num_of_sprite + selected_sprite;

		if(x >= sprite_size) x -= sprite_size;
		if(y >= sprite_size) y -= sprite_size;

		return rom.getPixel(x, y, offset);
	}

	public void selectSprite(int x, int y){
		int width = getWidth();
		int height = getHeight();

		x = x*internal_width/width;
		y = y*internal_height/height;

		int offset = getCurrentOffset();

		int sprite_x = x/sprite_size;
		int sprite_y = y/sprite_size;
		
		if(sprite_x < 0) sprite_x = 0;
		if(sprite_y < 0) sprite_y = 0;
		if(sprite_x > num_of_sprite-2) sprite_x = num_of_sprite-2;
		if(sprite_y > num_of_sprite-2) sprite_y = num_of_sprite-2;

		selected_sprite = sprite_x + sprite_y*num_of_sprite + offset*num_of_sprite;
		selected_sprite_x = sprite_x;
		selected_sprite_y = sprite_y;
		selected_sprite_offset = offset;
		repaint();
	}

	public void putPixelOnCurrentSprite(int x, int y){
		/* We need to calculate this offset because a sprite is composed
		 * of 4 minor sprites, so we divide by x and y by sprite_size.
		 * For example, if x = 10, y = 0, the offset will be 1 */

		int offset = x/sprite_size + (int)(y/sprite_size)*num_of_sprite;

		int old_color = rom.getPixel(x % sprite_size, y % sprite_size, selected_sprite + offset);

		while(old_state_list.size() > 0xffff) old_state_list.removeFirst();

		old_state_list.addLast(new OldState(selected_sprite, x, y, old_color));

		rom.putPixel(x % sprite_size, y % sprite_size, selected_sprite + offset, current_color);
		repaint();
	}

	private boolean reverseAction(){
		try{
			boolean ret_value;

			OldState state = old_state_list.getLast();
			ret_value = state.isLastAction;

			int old_color = current_color;
			current_color = state.old_color;

			int old_selected_sprite = selected_sprite;

			selected_sprite = state.selected_sprite;
			old_state_list.removeLast();

			putPixelOnCurrentSprite(state.x, state.y);

			selected_sprite = old_selected_sprite;
			current_color = old_color;

			old_state_list.removeLast();

			return ret_value;
		}
		catch(Exception e){
			return true;
		}
	}
	
	public void setActionAsLast(){
		try{
			old_state_list.getLast().setActionAsLast();
		}
		catch(Exception e){
		}
	}

	public void undoAction(){
		reverseAction();
		while(!reverseAction()){
		}
		setActionAsLast();
		repaint();
	}

	private int getCurrentOffset(){
		int offset = scroll.getValue();
		int num_of_char = rom.getSize()/(num_of_sprite*num_of_sprite);
		offset = offset*num_of_char/scroll.getMaximum();
		return offset;
	}

	private void drawSprite(Graphics2D g2d, int k, int l, int offset){
		for(int i = 0; i < 8; i++){
			for(int j = 0; j < 8; j++){
				int x = k*sprite_size+i;
				int y = l*sprite_size+j;

				int color_index = rom.getPixel(i, j, l*num_of_sprite + k + num_of_sprite*offset);

				Color current_color = palette[color_index];

				g2d.setColor(current_color);

				g2d.fillRect(x, y, 1, 1);
			}
		}
	}

	private void drawAllSprites(Graphics2D g2d, int offset){
		for(int k = 0; k < 16; k++){
			for(int l = 0; l < 16; l++){
				drawSprite(g2d, k, l, offset);
			}
		}

	}

	public void paintComponent(Graphics g){
		super.paintComponent(g);

		Graphics2D g2d = (Graphics2D) g;

		int width = getWidth();
		int height = getHeight();

		int offset = getCurrentOffset();

		g2d.scale((double) width / internal_width, (double) height / internal_height);

		g.setColor(palette[0]);
		g.fillRect(0, 0, width, height);

		drawAllSprites(g2d, offset);

		// draw outlines
		g2d.setColor(Color.white);
		g2d.drawRect(selected_sprite_x*sprite_size,
			selected_sprite_y*sprite_size + (selected_sprite_offset - offset)*sprite_size,
		2*sprite_size, 2*sprite_size);
	}

	private class updateLoop extends TimerTask{
		@Override
		public void run(){
			resizeCanvas();
			if(getCurrentOffset() != old_offset){
				repaint();
				old_offset = getCurrentOffset();
			}
		}
	}

	private void resizeCanvas(){
		if(getWidth() == getHeight()) return;

		if(getWidth() > getHeight()){
			setSize(new Dimension(getHeight(), getHeight()));
		}
		if(getWidth() < getHeight()){
			setSize(new Dimension(getWidth(), getWidth()));
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
			selectSprite(e.getX(), e.getY());
		}

		@Override
		public void mouseReleased(MouseEvent e){
		}
	}

}
