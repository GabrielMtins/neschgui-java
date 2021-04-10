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

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class Menu extends JMenuBar{
	Canvas canvas;

	private class onNewEvent implements ActionListener{
		public void actionPerformed(ActionEvent e){
			canvas.cleanRom();
		}
	}

	private class onOpenEvent implements ActionListener{
		public void actionPerformed(ActionEvent e){
			canvas.loadRom();
		}
	}

	private class onSaveEvent implements ActionListener{
		public void actionPerformed(ActionEvent e){
			canvas.saveRom(true);
		}
	}

	private class onSaveAsEvent implements ActionListener{
		public void actionPerformed(ActionEvent e){
			canvas.saveRom(false);
		}
	}

	private class onExitEvent implements ActionListener{
		public void actionPerformed(ActionEvent e){
			System.exit(0);
		}
	}

	private class onHelpEvent implements ActionListener{
		public void actionPerformed(ActionEvent e){
			JOptionPane.showMessageDialog(canvas.getParent(), "Neschgui\nThis program is under the GPL v3 license or later");
		}
	}

	public Menu(Canvas canvas){
		JMenu menu = new JMenu("File");
		this.canvas = canvas;

		JMenuItem item = new JMenuItem("New");
		item.addActionListener(new onNewEvent());
		menu.add(item);

		item = new JMenuItem("Open");
		item.addActionListener(new onOpenEvent());
		menu.add(item);

		item = new JMenuItem("Save");
		item.addActionListener(new onSaveEvent());
		menu.add(item);

		item = new JMenuItem("Save As");
		item.addActionListener(new onSaveAsEvent());
		menu.add(item);

		item = new JMenuItem("Exit");
		item.addActionListener(new onExitEvent());
		menu.add(item);

		add(menu);

		menu = new JMenu("Help");

		item = new JMenuItem("About");
		item.addActionListener(new onHelpEvent());
		menu.add(item);

		add(menu);
	}
}
