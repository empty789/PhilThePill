package objects.entities;

import java.awt.Graphics;
import java.awt.Rectangle;
import java.util.ArrayList;

import objects.Object;
import objects.ObjectType;


public class Enemy extends Object{
	
	private float velX, velY;
	private float gravity = 0.5f;
	private final float MAX_VEL = 10;
	private boolean isAlive;
	
	public Enemy(int x, int y, int width, int height) {
		setX(x);
		setY(y);
		setWidth(width);
		setHeight(height);
		isAlive = true;
		setType(ObjectType.ENTITY);
		velX = 1;
	}	
	
	public void move() {
		
		
		
		setX(getX()+(int) velX);
		
		
	}
	
	public void tick(ArrayList<Object> obj) {
		
		for(int i = 0; i < obj.size(); i++) {
			if(getBoundsRight().intersects(obj.get(i).getBounds()) && obj.get(i).getType() == ObjectType.OBSTACLE ) {
				setX(obj.get(i).getX()-getWidth());
				velX *=-1;
			}
			
			if(getBoundsLeft().intersects(obj.get(i).getBounds()) && obj.get(i).getType() == ObjectType.OBSTACLE ) {
				setX(obj.get(i).getX() + obj.get(i).getWidth());
				velX *=-1;
	
			}
		}
	}
	
	public void render(Graphics g) {
		if(isAlive) {
			g.setColor(getColor());
			g.fillRect(getX(), getY(), getWidth(), getHeight());
		}
	}
	
	public boolean isAlive() {
		return isAlive;
	}

	public void setAlive(boolean isAlive) {
		this.isAlive = isAlive;
	}

	public Rectangle getBox() {
		return new Rectangle(getX(), getY(), getWidth(), getHeight());
	}
	//bot
	public Rectangle getBounds() {
		return new Rectangle(getX()+(getWidth()/2)-((getWidth()/2)/2), getY()+(getHeight()/2), getWidth()/2, getHeight()/2);
	}
	
	public Rectangle getBoundsTop() {
		return new Rectangle(getX()+(getWidth()/2)-((getWidth()/2)/2), getY(), getWidth()/2, getHeight()/2);
	}
	
	public Rectangle getBoundsRight() {
		return new Rectangle(getX()+getWidth()-5, getY()+5, 5, getHeight()-10);
	}
	
	public Rectangle getBoundsLeft() {
		return new Rectangle(getX(), getY()+5, 5, getHeight()-10);
	}
}
