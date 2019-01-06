package objects.items;

import java.awt.Color;
import java.awt.Graphics;

import objects.Object;
import objects.ObjectType;

public class Pipe extends Object{

	boolean isAlive;
	int flow;
	
	
	public Pipe(int x, int y, int width, int height, Color color) {
		setType(ObjectType.PIPE);
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
			if(getImage() != null) {
				g.drawImage(getImage(), getX(), getY(), getWidth(), getHeight(), null);
			}else {
				g.setColor(getColor());
				g.fillRect(getX(), getY(), getWidth(), getHeight());
			}
		}else {
			if(flow < 90) {
				g.setColor(getColor());
				g.drawString("+1 Pfeife", getX(), getY()+30-flow);
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
