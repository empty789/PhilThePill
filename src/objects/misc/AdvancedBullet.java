package objects.misc;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Point;
import objects.Object;
import objects.ObjectType;

public class AdvancedBullet extends Object{

	private ObjectType owner;

	private int lifeTime, speed, dmg;
	private Point cPos;
	private Point pPos;
	private Point nPos;
	private double angle;
	private Point destination;
	private Point origin;
	
	public AdvancedBullet(ObjectType owner,Point origin, Point destination, int speed, int dmg, int lifeTime, Color c) {
		setWidth(20);
		setHeight(20);
		setX(origin.x);
		setY(origin.y);
		this.owner = owner;
		this.origin = origin;
		this.destination = destination;
		this.cPos = origin;
		this.angle = getAngle();
		this.speed = speed;
		this.dmg = dmg;
		this.lifeTime = lifeTime;
		setColor(c);

		
	}

//	private double getAngle() {
//		return Math.atan2(endPoint.getY() - startPoint.getY(), endPoint.getX() - startPoint.getX() - Math.PI/2);
//	}
	
	private double getAngle() {
		return Math.atan2(destination.getY() - origin.getY(), 
				destination.getX() - origin.getX()) + Math.PI/2;
	}
	
	public void move() {
		if(lifeTime > 0) {
			
			double xDirection = Math.sin(angle) * speed;
			double yDirection = Math.cos(angle) * -speed;
			
			double newX = cPos.x + xDirection;
			double newY = cPos.y + yDirection;
			
			Point p = new Point();
			p.setLocation(newX, newY);
			nPos = p;
			pPos = cPos;
			cPos.setLocation(newX, newY);
			setX(cPos.x);
			setY(cPos.y);
			lifeTime-=1;
	
		}else {
			setAlive(false);
		}
	}

	public void render(Graphics g) {
		g.setColor(getColor());
		g.fillOval(cPos.x+getWidth()/2, cPos.y+getHeight()/2, getWidth(), getHeight());

	}



	public ObjectType getOwner() {
		return owner;
	}

	public int getDmg() {
		return dmg;
	}
	
	
	
	
}
