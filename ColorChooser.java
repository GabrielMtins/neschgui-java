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
	}

	private void resizeCanvas(){
		if(getWidth() == getHeight()*4) return;

		setSize(new Dimension(getHeight()*4, getHeight()));
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
			current_color = e.getX() / getHeight();
			canvas.setCurrentColor(current_color);
			repaint();
		}

		@Override
		public void mouseReleased(MouseEvent e){
		}
	}
}
