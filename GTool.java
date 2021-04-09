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
}
