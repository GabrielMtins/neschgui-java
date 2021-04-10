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
