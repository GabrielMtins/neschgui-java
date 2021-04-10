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

public class GTool{
	SpriteCanvas canvas;

	protected int initial_x, initial_y;
	protected int end_x, end_y;
	protected int current_x, current_y;
	protected boolean mouse_state;

	public GTool(SpriteCanvas canvas){
		this.canvas = canvas;
		initial_x = 0;
		initial_y = 0;
		current_x = 0;
		current_y = 0;
		end_x = 0;
		end_y = 0;
		mouse_state = false;
	}

	public void setMouseState(boolean mouse_state){
		this.mouse_state = mouse_state;
	}

	public boolean getMouseState(){
		return mouse_state;
	}

	public void setInitialPosition(int x, int y){
		initial_x = x;
		initial_y = y;
	}

	public void setCurrentPosition(int x, int y){
		current_x = x;
		current_y = y;
	}

	public void setEndPosition(int x, int y){
		end_x = x;
		end_y = y;
	}

	public void update(){
	}

	public void render(Graphics g){
	}
}
