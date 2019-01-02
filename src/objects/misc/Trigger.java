package objects.misc;

import java.awt.Color;
import java.awt.Point;

import objects.Object;
import objects.ObjectType;

public class Trigger extends Object{

	private boolean isAlive;
	private Point tpPoint;
	
	public Trigger(int x, int y, int width, int height, Color c, ObjectType type) {
		setX(x);
		setY(y);
		setWidth(width);
		setHeight(height);
		setType(type);
		setColor(c);
		isAlive = true;
	}
	
	public Trigger(int x, int y, int width, int height, Color c, ObjectType type, Point tpPoint) {
		setX(x);
		setY(y);
		setWidth(width);
		setHeight(height);
		setType(type);
		setColor(c);
		isAlive = true;
		this.tpPoint = tpPoint;
	}
	public boolean isAlive() {
		return isAlive;
	}

	public void setAlive(boolean isAlive) {
		this.isAlive = isAlive;
	}
	
	public Point getTpPoint() {
		return tpPoint;
	}
	
	
}
