package view;

import objects.entities.Player;

public class Camera {

	private int x, y;
	private MainWindow mw;
	
	public Camera(MainWindow mw, int x, int y) {
		this.mw = mw;
		this.x = x;
		this.y = y;
		
	}

	public void tick(Player player) {
		x = -player.getX() + mw.getMaxWidth()/2;
	}
	
	public int getX() {
		return x;
	}

	public void setX(int x) {
		this.x = x;
	}

	public int getY() {
		return y;
	}

	public void setY(int y) {
		this.y = y;
	}
	
	
}
