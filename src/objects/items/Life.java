package objects.items;

import java.awt.Color;
import java.awt.Graphics;

import objects.Object;
import objects.ObjectType;

public class Life extends Object{

	boolean isAlive;
	int flow;
	
	
	public Life(int x, int y, int width, int height, Color color) {
		setType(ObjectType.LIFE);
		setX(x);
		setY(y);
		setWidth(width);
		setHeight(height);
		setColor(color);
		isAlive = true;
		flow = 1;
	}
	
	public void render(Graphics g) {
		if(isAlive) {
			g.setColor(getColor());
			g.fillRect(getX(), getY(), getWidth(), getHeight());
		}else {
			if(flow < 90) {
				g.setColor(getColor());
				g.drawString("+1 Leben", getX(), getY()+30-flow);
				flow+=3;
			}
		}
	}


	
	public boolean isAlive() {
		return isAlive;
	}

	public void setAlive(boolean isAlive) {
		this.isAlive = isAlive;
	}
	
}
