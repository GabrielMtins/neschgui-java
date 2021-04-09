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
		add(new JMenu("About"));
	}
}
