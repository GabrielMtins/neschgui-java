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
import java.lang.*;

class CanvasFrame extends JPanel{
	private Canvas canvas;
	private SpriteCanvas spr;

	public CanvasFrame(JScrollBar scroll){
		setLayout(new GridLayout());

		canvas = new Canvas(128, 128);
		canvas.setScroll(scroll);

		spr = new SpriteCanvas(16, 16, canvas);

		add(canvas);
		add(spr);
	}

	public Canvas getCanvas(){
		return canvas;
	}

	public SpriteCanvas getSpriteCanvas(){
		return spr;
	}
}
