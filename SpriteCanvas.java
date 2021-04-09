import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.MouseInfo;
import java.lang.*;
import java.util.Timer;
import java.util.TimerTask;

class GPencil extends GTool{
	public GPencil(SpriteCanvas canvas){
		super(canvas);
	}

	@Override
	public void update(){
		if(getMouseState()){
			canvas.putPixel(current_x, current_y);
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

	public void paintComponent(Graphics g){
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
		if(canvas.getWidth()/2 == getWidth()) return;
		setSize(new Dimension(canvas.getWidth()/2, canvas.getHeight()/2));
	}
}
