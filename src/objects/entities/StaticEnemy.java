package objects.entities;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

import objects.Object;
import objects.ObjectType;
import objects.misc.AdvancedBullet;


public class StaticEnemy extends Object{
	
	private boolean isAlive, right;
	private BufferedImage image_r, image_l;
	private ArrayList<AdvancedBullet> bulletList;
	private int cooldown;
	
	public StaticEnemy(int x, int y, int width, int height) {
		setX(x);
		setY(y);
		setWidth(width);
		setHeight(height);
		isAlive = true;
		setType(ObjectType.ENTITYSTATIC);
		right = false;
		bulletList = new ArrayList<AdvancedBullet>();
		cooldown = 0;
	}	
	
	
	public void tick(ArrayList<Object> obj, Player p) {
		
		//get orientation
		if(p.getX() > getX()) {
			right = true;
		}else {
			right = false;
		}
		
		//shoot
		if(cooldown >= 200) {
			shoot(p, right);
			cooldown = 0;	
		}
		cooldown++;
		//clear up dead bullets & collision
		for(int i = 0; i < bulletList.size(); i++) {
			//block
			for(int j = 0; j < obj.size(); j++) {
				if(bulletList.get(i).getBounds().intersects(obj.get(j).getBounds())) {
					if(obj.get(j).getType() == ObjectType.OBSTACLE || obj.get(j).getType() == ObjectType.TIMEDOBSTACLE) {
						bulletList.get(i).setAlive(false);		
					}
				}
			}
			
			
			//player
			if(bulletList.get(i).getBounds().intersects(p.getBox())) {
				bulletList.get(i).setAlive(false);
				if(!p.isImmune())
					p.getDamage();
			}
			
			if(!bulletList.get(i).isAlive()) {
				bulletList.remove(i);
			}
		}
	}
	
	public void render(Graphics g) {
		if(isAlive) {
			if(getImages() != null) {
				ArrayList<BufferedImage> images = getImages();
				if(right) {
					g.drawImage(images.get(0), getX(), getY(), getWidth(), getHeight(), null);
				}else {
					g.drawImage(images.get(1), getX(), getY(), getWidth(), getHeight(), null);
				}
				
			}else {
				g.setColor(getColor());
				g.fillRect(getX(), getY(), getWidth(), getHeight());
			}
		}
	}
	
	public void shoot(Player p, boolean right) {
		Point point = getPosition();
		if(right) {
			point.setLocation(getX()+getWidth()-20, getY()+getHeight()/2);
			bulletList.add(new AdvancedBullet(ObjectType.BOSS, point , p.getCenterPosition(), 10, 10, 100, Color.PINK));
		}else {
			point.setLocation(getX()-10, getY()+getHeight()/2);
			bulletList.add(new AdvancedBullet(ObjectType.BOSS, point , p.getCenterPosition(), 10, 10, 100, Color.PINK));
		}
	}
	
	public ArrayList<AdvancedBullet> getBulletList() {
		return bulletList;
	}
	
	private Point getPosition() {
		return new Point(getX(), getY());
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
