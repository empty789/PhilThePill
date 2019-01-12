package objects.entities;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.Random;

import objects.Object;
import objects.ObjectType;
import objects.misc.AdvancedBullet;


public class Boss extends Object{
	
	private float velX, velY;
	private boolean shooting;
	private int hitpoints;
	private ArrayList<AdvancedBullet> bulletList;
	private int cooldown;
	private BufferedImage image, image_shoot;
	private Random rand;
	
	public Boss(int x, int y, int width, int height) {
		setX(x);
		setY(y);
		setWidth(width);
		setHeight(height);
		setType(ObjectType.BOSS);
		velY = 2;
		hitpoints = 100;
		cooldown = 0;
		shooting = false;
		//bullet = new AdvancedBullet(ObjectType.BOSS, new Point(getX(), getY()), 10, 10, 700);
		bulletList = new ArrayList<AdvancedBullet>();
		rand = new Random();
	}	
	
	public void move() {
		setY(getY()+(int) velY);	
	}
	
	public void tick(ArrayList<Object> obj, Player p) {
		
		for(int i = 0; i < obj.size(); i++) {
			if(getBoundsTop().intersects(obj.get(i).getBounds()) &&( obj.get(i).getType() == ObjectType.OBSTACLE || obj.get(i).getType() == ObjectType.TIMEDOBSTACLE)) {
				velY *=-1;
				setY(obj.get(i).getY()+obj.get(i).getHeight()+5);

				
			}else if(getBounds().intersects(obj.get(i).getBounds()) &&( obj.get(i).getType() == ObjectType.OBSTACLE || obj.get(i).getType() == ObjectType.TIMEDOBSTACLE)) {
				velY *=-1;
				setY(obj.get(i).getY() - getHeight()-5);

			}
		}
		int rCd = rand.nextInt(30);
		//shoot
		if(cooldown >= 100+rCd) {
			int r = rand.nextInt(10);
			
			if(r >= 4) {
				shoot(p, true);
			}else {
				shoot(p, false);
			}
			
			shooting = true;
			cooldown = 0;
		}else if(cooldown >= 30) {
			shooting = false;	
		}
		cooldown++;
		//clear up dead bullets & collision
		for(int i = 0; i < bulletList.size(); i++) {
			//block
			for(int j = 0; j < obj.size(); j++) {
				if(bulletList.get(i).getBounds().intersects(obj.get(j).getBounds())) {
					if(obj.get(j).getType() == ObjectType.OBSTACLE || obj.get(i).getType() == ObjectType.TIMEDOBSTACLE) {
						bulletList.get(i).setAlive(false);		
					}
				}
			}
			
			
			//player
			if(bulletList.get(i).getBounds().intersects(p.getBox())) {
				bulletList.get(i).setAlive(false);
				p.getDamage();
			}
			
			if(!bulletList.get(i).isAlive()) {
				bulletList.remove(i);
			}
		}

		
	}
	
	public void render(Graphics g) {
		if(isAlive()) {
			if(getImages() != null) {
				ArrayList<BufferedImage> images = getImages();
				if(shooting) {
					g.drawImage(images.get(1), getX(), getY(), getWidth(), getHeight(), null);
				}else {
					g.drawImage(images.get(0), getX(), getY(), getWidth(), getHeight(), null);
				}
			}else {
				g.setColor(getColor());
				g.fillRect(getX(), getY(), getWidth(), getHeight());
			}
		}
	}
	
	public void setImage(BufferedImage image, BufferedImage image_shoot) {
		this.image = image;
		this.image_shoot = image_shoot;
	}
	
	public ArrayList<BufferedImage> getImages() {
		if(image == null || image_shoot == null)
			return null;
		
		ArrayList<BufferedImage> result = new ArrayList<BufferedImage>();
		result.add(image);
		result.add(image_shoot);
		
		return result;
	}
	
	public void shoot(Player p, boolean more) {
		
		if(more) {
			int r = rand.nextInt(999);
			System.out.println(r);
			if(r % 2== 0) {
				bulletList.add(new AdvancedBullet(ObjectType.BOSS, getPosition() , p.getCenterPosition(), 7, 10, 100, Color.MAGENTA.darker()));
				Point po = new Point(p.getCenterPosition().x, p.getCenterPosition().y+200);
				bulletList.add(new AdvancedBullet(ObjectType.BOSS, getPosition() , po, 7, 10, 100, Color.RED.darker()));
				po = new Point(p.getCenterPosition().x, p.getCenterPosition().y-200);
				bulletList.add(new AdvancedBullet(ObjectType.BOSS, getPosition() , po, 7, 10, 100, Color.MAGENTA.darker()));
				
			}else
			if(r % 3== 0) {
				Point po = new Point(getPosition().x-600, getPosition().y-400);
				bulletList.add(new AdvancedBullet(ObjectType.BOSS, getPosition() , po, 5, 10, 200, Color.RED.darker()));
				po = new Point(getPosition().x-600, getPosition().y-300);
				bulletList.add(new AdvancedBullet(ObjectType.BOSS, getPosition() , po, 5, 10, 200, Color.MAGENTA));
				po = new Point(getPosition().x-600, getPosition().y-200);
				bulletList.add(new AdvancedBullet(ObjectType.BOSS, getPosition() , po, 5, 10, 200, Color.RED.darker()));
				po = new Point(getPosition().x-600, getPosition().y-100);
				bulletList.add(new AdvancedBullet(ObjectType.BOSS, getPosition() , po, 5, 10, 200, Color.MAGENTA.darker()));
				po = new Point(getPosition().x-600, getPosition().y);
				bulletList.add(new AdvancedBullet(ObjectType.BOSS, getPosition() , po, 5, 10, 200, Color.RED.darker()));
			}else
			if(r % 5== 0) {
				Point po = new Point(getPosition().x-500, getPosition().y+400);
				bulletList.add(new AdvancedBullet(ObjectType.BOSS, getPosition() , po, 5, 10, 200, Color.MAGENTA.darker()));
				po = new Point(getPosition().x-600, getPosition().y+300);
				bulletList.add(new AdvancedBullet(ObjectType.BOSS, getPosition() , po, 5, 10, 200, Color.PINK.darker()));
				po = new Point(getPosition().x-600, getPosition().y+200);
				bulletList.add(new AdvancedBullet(ObjectType.BOSS, getPosition() , po, 5, 10, 200, Color.MAGENTA));
				po = new Point(getPosition().x-600, getPosition().y+100);
				bulletList.add(new AdvancedBullet(ObjectType.BOSS, getPosition() , po, 5, 10, 200, Color.PINK.darker()));
				po = new Point(getPosition().x-600, getPosition().y);
				bulletList.add(new AdvancedBullet(ObjectType.BOSS, getPosition() , po, 5, 10, 200, Color.MAGENTA.darker()));
			}else {
				Point po = new Point(getPosition().x-300, getPosition().y+400);
				bulletList.add(new AdvancedBullet(ObjectType.BOSS, getPosition() , po, 5, 10, 200, Color.MAGENTA.darker()));
				po = new Point(getPosition().x-400, getPosition().y+200);
				bulletList.add(new AdvancedBullet(ObjectType.BOSS, getPosition() , po, 5, 10, 200, Color.PINK.darker()));
				po = new Point(getPosition().x-500, getPosition().y);
				bulletList.add(new AdvancedBullet(ObjectType.BOSS, getPosition() , po, 5, 10, 200, Color.BLACK));
				po = new Point(getPosition().x-400, getPosition().y-200);
				bulletList.add(new AdvancedBullet(ObjectType.BOSS, getPosition() , po, 5, 10, 200, Color.PINK.darker()));
				po = new Point(getPosition().x-300, getPosition().y-400);
				bulletList.add(new AdvancedBullet(ObjectType.BOSS, getPosition() , po, 5, 10, 200, Color.MAGENTA.darker()));
			}
		}else {
			bulletList.add(new AdvancedBullet(ObjectType.BOSS, getPosition() , p.getCenterPosition(), 10, 10, 100, Color.MAGENTA.darker()));
		}
	}
	
	public ArrayList<AdvancedBullet> getBulletList() {
		return bulletList;
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
