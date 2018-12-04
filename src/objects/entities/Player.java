package objects.entities;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.ArrayList;

import javax.imageio.ImageIO;

import objects.Object;
import objects.ObjectType;
import objects.items.Life;
import objects.misc.Bullet;

public class Player extends Object {

	private static final int WIDTH = 50;
	private static final int HEIGHT = 100;
	
	private float velX, velY;
	private float gravity = 0.5f;
	private final float MAX_VEL = 10;

	
	private int lifes;
	private boolean big;
	private boolean immune;
	private int immuneTime;
	private Bullet bullet;
	private Point spawn;
	
	private boolean left, right, up, jumping = false;
	private boolean falling = true;
	
	private ArrayList<BufferedImage> images;
	
	public Player() {
		setType(ObjectType.PLAYER);
		setX(400);
		setY(400);
		setWidth(WIDTH);
		setHeight(HEIGHT);
		setColor(Color.RED);
		spawn = new Point(getX(), getY());
		
		
		lifes = 3;
		big = true;
		immune = false;
		immuneTime = 0; 
		bullet = new Bullet(ObjectType.PLAYER, 400);
		images = new ArrayList<BufferedImage>(); // idle, left, l_fall, l_jump, right, r_fall, r_jump
		for(int i = 0; i < 7; i++) {
			try {
				images.add(ImageIO.read(Player.class.getResource("/image/player/"+i+".png")));
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	
	public void render(Graphics g) {
		if(left) {
			if(velY > 2) {//jump
				g.drawImage(images.get(2),getX(), getY(), getWidth(), getHeight(), null);
			}else if(velY < 0) {//fall
				g.drawImage(images.get(3),getX(), getY(), getWidth(), getHeight(), null);
			}else {
				g.drawImage(images.get(1),getX(), getY(), getWidth(), getHeight(), null);
			}
		}else if(right) {
			if(velY > 2) {//jump
				g.drawImage(images.get(5),getX(), getY(), getWidth(), getHeight(), null);
			}else if(velY < 0) {//fall
				g.drawImage(images.get(6),getX(), getY(), getWidth(), getHeight(), null);
			}else {
				g.drawImage(images.get(4),getX(), getY(), getWidth(), getHeight(), null);
			}
		}else {
			g.drawImage(images.get(0),getX(), getY(), getWidth(), getHeight(), null);
		}
	}
	
	public void move() {
		setX(getX()+(int) velX);
		setY(getY()+(int) velY);
		
		
		if(right) {
			velX = 5;
		}
		if(left) {
			velX = -5;
		}
		if(!right && !left) {
			velX = 0;
		}
		if(up) {
			if(!jumping && velY == 0) {
				velY = -20;
				
				
			}
			jumping = true;
		}
		if(falling || jumping) {
			velY += gravity;
			if(velY > MAX_VEL) {
				velY = MAX_VEL;
			}
		}
		
		if(bullet.isAlive()) {
			bullet.move();
		}
	}
	
	public void tick(ArrayList<Object> obj) {
		//System.out.println(falling + " "+jumping);
		if(big) {
			setWidth((int) (WIDTH*1.25));
			setHeight((int) (HEIGHT*1.25));
		}else if(!big) {
			setWidth(WIDTH);
			setHeight(HEIGHT);
		}
		
		if(immune) {
			immuneTime++;
			if(immuneTime >= 90) { //ticks
				immune = false;
				immuneTime = 0;
			}
		}
		
		for(int i = 0; i < obj.size(); i++) {
			//System.out.println(velY);
			//obstacle collision
			if(getBoundsTop().intersects(obj.get(i).getBounds()) && obj.get(i).getType() == ObjectType.OBSTACLE ) {
				setY(obj.get(i).getY()+obj.get(i).getHeight());
				velY = 0;

			}
			
			if(getBounds().intersects(obj.get(i).getBounds()) && obj.get(i).getType() == ObjectType.OBSTACLE ) {
				//work on this, workaround for flickering
				if(big)
					setY(obj.get(i).getY()-getHeight()+1);
				
				if(!big)
					setY(obj.get(i).getY()-getHeight());
				
				velY = 0;
				falling = false;
				jumping = false;
				
			}else {
				if(left || right)
					falling = true;
				
				
				
			}
			
			if(getBoundsRight().intersects(obj.get(i).getBounds()) && obj.get(i).getType() == ObjectType.OBSTACLE ) {
				setX(obj.get(i).getX()-getWidth());

			}
			
			if(getBoundsLeft().intersects(obj.get(i).getBounds()) && obj.get(i).getType() == ObjectType.OBSTACLE ) {
				setX(obj.get(i).getX() + obj.get(i).getWidth());


			}
			
			//life
			if(getBox().intersects(obj.get(i).getBounds()) && obj.get(i).getType() == ObjectType.LIFE) {
				Life l = (Life) obj.get(i);
				if(l.isAlive()) {
					setLifes(getLifes()+1);
					l.setAlive(false);
				}
			}
			

			
			
			//enemy collision
			if(obj.get(i).getType() == ObjectType.ENTITY){
				Enemy e = (Enemy)obj.get(i);
				if(e.isAlive() && !immune) {
					if(getBounds().intersects(e.getBoundsTop())) {
						e.setAlive(false);
					}else if(getBoundsRight().intersects(e.getBox())) {
						if(big) {
							big = false;
							immune = true;
						}else {
							respawn();
							setLifes(getLifes()-1);
						}
						
					}else if(getBoundsLeft().intersects(e.getBox())) {
						if(big) {
							big = false;
							immune = true;
						}else {
							respawn();
							setLifes(getLifes()-1);
						}
					}else if(getBoundsTop().intersects(e.getBox())) {
						if(big) {
							big = false;
							immune = true;
						}else {
							respawn();
							setLifes(getLifes()-1);
						}
					}
				}
			}
			
			//bullet collision
			if(bullet.isAlive() ) {
				if(obj.get(i).getType() == ObjectType.OBSTACLE && bullet.getBounds().intersects(obj.get(i).getBounds())) {
					bullet.setAlive(false);
				}else if(obj.get(i).getType() == ObjectType.ENTITY){
					Enemy e = (Enemy)obj.get(i);
					if(e.isAlive() && bullet.getBounds().intersects(e.getBox())) {
						e.setAlive(false);
					}
					
				}
			}
			
			
		}
	}
	
	public void restart() {
		velX = 0;
		velY = 0;
		left = false;
		right = false;
		up = false;
		big = true;
		falling = true;
		jumping = false;
		respawn();
		setLifes(3);
	}
	
	public void respawn() {
		setX(spawn.x);
		setY(spawn.y);
	}
	
	public void shoot() {
		if(!bullet.isAlive()) {
			if(right) {
				bullet.setX(getX()+50);
				bullet.setY(getY()+20);
				bullet.setVelX(10.0f);
				bullet.setStart(new Point(bullet.getX(), bullet.getY()));
				bullet.setAlive(true);
			}else if(left) {
				bullet.setX(getX());
				bullet.setY(getY()+20);
				bullet.setVelX(-10.0f);
				bullet.setStart(new Point(bullet.getX(), bullet.getY()));
				bullet.setAlive(true);	
			}
			
			
		}
	}
	
	public Rectangle getBox() {
		return new Rectangle(getX(), getY(), getWidth(), getHeight());
	}
	
	public Rectangle getBounds() {
		return new Rectangle(getX()+(getWidth()/2)-((getWidth()/2)/2), getY()+(getHeight()/2), getWidth()/2, getHeight()/2);
	}
	
	public Rectangle getBoundsTop() {
		return new Rectangle(getX()+(getWidth()/2)-((getWidth()/2)/2), getY(), getWidth()/2, getHeight()/2);
	}
	
	public Rectangle getBoundsRight() {
		return new Rectangle(getX()+getWidth()-5, getY()+5, 5, getHeight()-20);
	}
	
	public Rectangle getBoundsLeft() {
		return new Rectangle(getX(), getY()+5, 5, getHeight()-20);
	}
	

	
	public int getLifes() {
		return lifes;
	}


	public void setLifes(int lifes) {
		this.lifes = lifes;
	}


	public boolean Left() {
		return left;
	}

	public void setLeft(boolean left) {
		this.left = left;
	}

	public boolean Right() {
		return right;
	}

	public void setRight(boolean right) {
		this.right = right;
	}

	public boolean Up() {
		return up;
	}

	public void setUp(boolean up) {
		this.up = up;
	}


	public Bullet getBullet() {
		return bullet;
	}
	
	
	
}
