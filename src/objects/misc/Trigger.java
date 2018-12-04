package objects.misc;

import java.awt.Color;

import objects.Object;
import objects.ObjectType;

public class Trigger extends Object{

	private boolean isAlive;
	
	public Trigger(int x, int y, int width, int height) {
		setX(x);
		setY(y);
		setWidth(width);
		setHeight(height);
		setType(ObjectType.QUESTTRIGGER);
		isAlive = true;
	}
	
	public boolean isAlive() {
		return isAlive;
	}

	public void setAlive(boolean isAlive) {
		this.isAlive = isAlive;
	}
	
	
	
}
