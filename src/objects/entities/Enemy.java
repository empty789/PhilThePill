package objects.entities;

import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

import objects.Object;
import objects.ObjectType;


public class Enemy extends Object{
	
	private float vel;
	private BufferedImage image_r, image_l;
	private boolean up, right;
	
	public Enemy(int x, int y, int width, int height, float vel, boolean up) {
		setX(x);
		setY(y);
		setWidth(width);
		setHeight(height);
		setType(ObjectType.ENTITY);
		this.vel = vel;
		this.up = up;
		right = false;
	}	
	
	public void move() {
		
		
		if(up) {
			setY(getY()+(int) vel);
		}else {
			setX(getX()+(int) vel);
		}
		
		
	}
	
	public void tick(ArrayList<Object> obj, Player p) {
		
		for(int i = 0; i < obj.size(); i++) {
			if(getBoundsRight().intersects(obj.get(i).getBounds())&& obj.get(i).isAlive() && (obj.get(i).getType() == ObjectType.OBSTACLE || obj.get(i).getType() == ObjectType.TIMEDOBSTACLE) ) {
				setX(obj.get(i).getX()-getWidth());
				vel *=-1;
			}
			
			if(getBoundsLeft().intersects(obj.get(i).getBounds())&& obj.get(i).isAlive() && (obj.get(i).getType() == ObjectType.OBSTACLE || obj.get(i).getType() == ObjectType.TIMEDOBSTACLE) ) {
				setX(obj.get(i).getX() + obj.get(i).getWidth());
				vel *=-1;
	
			}
			
			if(getBoundsTop().intersects(obj.get(i).getBounds()) && obj.get(i).isAlive() && (obj.get(i).getType() == ObjectType.OBSTACLE || obj.get(i).getType() == ObjectType.TIMEDOBSTACLE)) {
				vel *=-1;
				setY(obj.get(i).getY()+obj.get(i).getHeight()+5);
				
			}else if(getBounds().intersects(obj.get(i).getBounds()) && obj.get(i).isAlive() && (obj.get(i).getType() == ObjectType.OBSTACLE || obj.get(i).getType() == ObjectType.TIMEDOBSTACLE)) {
				vel *=-1;
				setY(obj.get(i).getY() - getHeight()-5);
				
			}
		}
		
		if(up) {
			//get orientation
			if(p.getX() > getX()) {
				right = true;
			}else {
				right = false;
			}
		}
	}
	
	public void render(Graphics g) {
		if(isAlive()) {
			if(getImages() != null) {
				ArrayList<BufferedImage> images = getImages();
				if(up) {
					if(right) {
						g.drawImage(images.get(0), getX(), getY(), getWidth(), getHeight(), null);
					}else {
						g.drawImage(images.get(1), getX(), getY(), getWidth(), getHeight(), null);
					}
				}else {
					if(vel > 0) {
						g.drawImage(images.get(0), getX(), getY(), getWidth(), getHeight(), null);
					}else {
						g.drawImage(images.get(1), getX(), getY(), getWidth(), getHeight(), null);
					}
				}
				
			}else {
				g.setColor(getColor());
				g.fillRect(getX(), getY(), getWidth(), getHeight());
			}
		}
	}
	
	public void setImage(BufferedImage r, BufferedImage l) {
		image_r = r;
		image_l = l;
	}
	
	public ArrayList<BufferedImage> getImages() {
		if(image_r == null || image_l == null)
			return null;
		
		ArrayList<BufferedImage> result = new ArrayList<BufferedImage>();
		result.add(image_r);
		result.add(image_l);
		
		return result;
	}
	


	public float getVel() {
		return vel;
	}

	public boolean isUp() {
		return up;
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
