package objects.misc;
import java.awt.Point;

import objects.Object;
import objects.ObjectType;

public class Bullet extends Object{
	
	private ObjectType owner;
	private Boolean isAlive;
	private float velX;
	private Point start;
	private int range;

	
	public Bullet(ObjectType owner, int range) {
		setWidth(20);
		setHeight(10);
		this.owner = owner;
		this.range = range;
		velX = 10.0f;
		isAlive = false;
		setType(ObjectType.BULLET);
	}
	
	
	public void move() {
		setX((int) (getX()+velX));
		
		//range decay
		if(velX > 0) {
			if(getX() >= start.getX() + range) {
				isAlive = false;
			}
		}else if(velX < 0) {
			if(getX() <= start.getX()-range) {
				isAlive = false;
			}
		}
	}


	public Boolean isAlive() {
		return isAlive;
	}


	public void setAlive(Boolean isAlive) {
		this.isAlive = isAlive;
	}


	public ObjectType getOwner() {
		return owner;
	}


	public void setVelX(float velX) {
		this.velX = velX;
	}


	public Point getStart() {
		return start;
	}


	public void setStart(Point start) {
		this.start = start;
	}
	
	
	
}
