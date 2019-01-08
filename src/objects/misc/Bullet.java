package objects.misc;
import java.awt.Point;

import objects.Object;
import objects.ObjectType;

public class Bullet extends Object{
	
	private ObjectType owner;
	private float velX;
	private Point start;
	private int range, rangeDecay, dmg;

	
	public Bullet(ObjectType owner, int dmg, int range) {
		setWidth(20);
		setHeight(10);
		this.owner = owner;
		this.range = range;
		this.dmg = dmg;
		velX = 10.0f;
		setType(ObjectType.BULLET);
		setAlive(true);
	}
	
	
	public void move() {
		
		
		if(rangeDecay > 0) {
			setX((int) (getX()+velX));
			
			rangeDecay -=Math.abs(velX);
		}else {
			setAlive(false);
			rangeDecay = range;
			System.out.println("test123");
		}
		
		
//		//range decay
//		if(velX > 0) {
//			if(getX() >= start.getX() + range) {
//				isAlive = false;
//			}
//		}else if(velX < 0) {
//			if(getX() <= start.getX()-range) {
//				isAlive = false;
//			}
//		}
	}



	public void resetRange() {
		rangeDecay = range;
	}

	public ObjectType getOwner() {
		return owner;
	}


	public void setVelX(float velX) {
		this.velX = velX;
	}


	
	public float getVelX() {
		return velX;
	}


	public Point getStart() {
		return start;
	}


	public void setStart(Point start) {
		this.start = start;
	}


	public int getDmg() {
		return dmg;
	}


	public void setDmg(int dmg) {
		this.dmg = dmg;
	}
	
	
	
}
