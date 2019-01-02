package objects.entities;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.Rectangle;
import java.util.ArrayList;

import objects.Object;
import objects.ObjectType;
import objects.misc.AdvancedBullet;


public class Boss extends Object{
	
	private float velX, velY;
	private boolean isAlive;
	private int hitpoints;
	private ArrayList<AdvancedBullet> bulletList;
	private int cooldown;
	
	public Boss(int x, int y, int width, int height) {
		setX(x);
		setY(y);
		setWidth(width);
		setHeight(height);
		isAlive = true;
		setType(ObjectType.BOSS);
		velY = 2;
		hitpoints = 100;
		cooldown = 0;
		//bullet = new AdvancedBullet(ObjectType.BOSS, new Point(getX(), getY()), 10, 10, 700);
		bulletList = new ArrayList<AdvancedBullet>();
	}	
	
	public void move() {
		setY(getY()+(int) velY);	
	}
	
	public void tick(ArrayList<Object> obj, Player p) {
		
		for(int i = 0; i < obj.size(); i++) {
			if(getBoundsTop().intersects(obj.get(i).getBounds()) && obj.get(i).getType() == ObjectType.OBSTACLE ) {
				velY *=-1;
				setY(obj.get(i).getY()+obj.get(i).getHeight()+5);
				
			}else if(getBounds().intersects(obj.get(i).getBounds()) && obj.get(i).getType() == ObjectType.OBSTACLE ) {
				velY *=-1;
				setY(obj.get(i).getY() - getHeight()-5);
				
			}
		}
		
		//shoot
		if(cooldown >= 200) {
			shoot(p);
			cooldown = 0;
		}else {
			cooldown++;
		}
		
		//clear up dead bullets & collision
		for(int i = 0; i < bulletList.size(); i++) {
			//block
			for(int j = 0; j < obj.size(); j++) {
				if(bulletList.get(i).getBounds().intersects(obj.get(j).getBounds())) {
					if(obj.get(j).getType() == ObjectType.OBSTACLE ) {
						bulletList.get(i).setIsAlive(false);		
					}
				}
			}
			
			
			//player
			if(bulletList.get(i).getBounds().intersects(p.getBox())) {
				bulletList.get(i).setIsAlive(false);
				p.getDamage();
			}
			
			if(!bulletList.get(i).isAlive()) {
				bulletList.remove(i);
			}
		}

		
	}
	
	public void render(Graphics g) {
		if(isAlive) {
			g.setColor(getColor());
			g.fillRect(getX(), getY(), getWidth(), getHeight());
		}
	}
	
	public void shoot(Player p) {
		bulletList.add(new AdvancedBullet(ObjectType.BOSS, getPosition(), p.getCenterPosition(), 10, 10, 100));
	}
	
	public ArrayList<AdvancedBullet> getBulletList() {
		return bulletList;
	}
	
	public boolean isAlive() {
		return isAlive;
	}

	public void setAlive(boolean isAlive) {
		this.isAlive = isAlive;
	}

	public Point getPosition() {
		return new Point(getX(), getY());
	}
	
	public int getHitpoints() {
		return hitpoints;
	}

	public void setHitpoints(int hitpoints) {
		this.hitpoints = hitpoints;
	}

	public Rectangle getBox() {
		return new Rectangle(getX(), getY(), getWidth(), getHeight());
	}
	//bot
	public Rectangle getBounds() {
		return new Rectangle(getX()+(getWidth()/2)-((getWidth()/2)/2), getY()+(getHeight()/2)+3, getWidth()/2, getHeight()/2);
	}
	
	public Rectangle getBoundsTop() {
		return new Rectangle(getX()+(getWidth()/2)-((getWidth()/2)/2), getY()-3, getWidth()/2, getHeight()/2);
	}
	
	public Rectangle getBoundsRight() {
		return new Rectangle(getX()+getWidth()-5, getY()+5, 5, getHeight()-10);
	}
	
	public Rectangle getBoundsLeft() {
		return new Rectangle(getX(), getY()+5, 5, getHeight()-10);
	}
}
