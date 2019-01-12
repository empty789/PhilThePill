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
import objects.items.EnergyDrink;
import objects.items.Life;
import objects.items.Pipe;
import objects.misc.AdvancedBullet;
import objects.misc.Bullet;
import objects.obstacles.TimedBlock;

public class Player extends Object {

	private static final int WIDTH = 50;
	private static final int HEIGHT = 100;
	
	private float velX, velY;
	private float gravity = 0.5f;
	private final float MAX_VEL = 10;

	
	private int lifes, pipes;
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
		pipes = 0;
		big = true;
		immune = false;
		immuneTime = 0; 
		bullet = new Bullet(ObjectType.PLAYER, 20, 700);
		images = new ArrayList<BufferedImage>(); // idle, left, l_fall, l_jump, right, r_fall, r_jump
		for(int i = 0; i < 9; i++) {
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
			if(velY > 2) {
				g.drawImage(images.get(7),getX(), getY(), getWidth(), getHeight(), null);
			}else if(velY < 0) {
				g.drawImage(images.get(8),getX(), getY(), getWidth(), getHeight(), null);
			}else {
				g.drawImage(images.get(0),getX(), getY(), getWidth(), getHeight(), null);
			}
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
			if(getBoundsTop().intersects(obj.get(i).getBounds()) && obj.get(i).isAlive() && (obj.get(i).getType() == ObjectType.OBSTACLE || obj.get(i).getType() == ObjectType.TIMEDOBSTACLE)) {
				setY(obj.get(i).getY()+obj.get(i).getHeight());
				velY = 0;

			}
			
			if(getBounds().intersects(obj.get(i).getBounds()) && obj.get(i).isAlive() && (obj.get(i).getType() == ObjectType.OBSTACLE || obj.get(i).getType() == ObjectType.TIMEDOBSTACLE) ) {
				if(obj.get(i).getType() == ObjectType.TIMEDOBSTACLE) {
					TimedBlock tBlock = (TimedBlock) obj.get(i);
					tBlock.setActivated(true);
					falling = true;
				}else {
					falling = false;
				}
				//work on this, workaround for flickering
				if(big)
					setY(obj.get(i).getY()-getHeight()+1);
				
				if(!big)
					setY(obj.get(i).getY()-getHeight());
				
				velY = 0;
				//falling = false;
				jumping = false;
				
			}else {
				if(left || right)
					falling = true;
				
				
				
			}
			
			if(getBoundsRight().intersects(obj.get(i).getBounds()) && obj.get(i).isAlive() && (obj.get(i).getType() == ObjectType.OBSTACLE || obj.get(i).getType() == ObjectType.TIMEDOBSTACLE) ) {
				setX(obj.get(i).getX()-getWidth());

			}
			
			if(getBoundsLeft().intersects(obj.get(i).getBounds()) && obj.get(i).isAlive() && (obj.get(i).getType() == ObjectType.OBSTACLE || obj.get(i).getType() == ObjectType.TIMEDOBSTACLE) ) {
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
			//pipe
			if(getBox().intersects(obj.get(i).getBounds()) && obj.get(i).getType() == ObjectType.PIPE) {
				Pipe p = (Pipe) obj.get(i);
				if(p.isAlive()) {
					setPipes(getPipes()+1);
					p.setAlive(false);
				}
			}
			//Energy
			if(getBox().intersects(obj.get(i).getBounds()) && obj.get(i).getType() == ObjectType.ENERGYDRINK) {
				EnergyDrink e = (EnergyDrink) obj.get(i);
				if(e.isAlive()) {
					big = true;
					setY(getY()+25);
					e.setAlive(false);
				}
			}
			
			//boss collision
			if(obj.get(i).getType() == ObjectType.BOSS){
				Boss b = (Boss)obj.get(i);
				if(getBounds().intersects(b.getBox()) && !immune) {
					getDamage();
				}
			}
			
			
			//enemy collision
			if(obj.get(i).getType() == ObjectType.ENTITY){

				if(obj.get(i).isAlive() && !immune) {
					if(getBounds().intersects(obj.get(i).getBoundsTop())) {
						obj.get(i).setAlive(false);
					}else if(getBoundsRight().intersects(obj.get(i).getBounds())) {
						getDamage();	
					}else if(getBoundsLeft().intersects(obj.get(i).getBounds())) {
						getDamage();
					}else if(getBoundsTop().intersects(obj.get(i).getBounds())) {
						getDamage();
					}
				}
			}else if(obj.get(i).getType() == ObjectType.ENTITYSTATIC){
				StaticEnemy e = (StaticEnemy)obj.get(i);
				if(getBounds().intersects(e.getBox()) && !immune) {
					getDamage();
				}
			}
			
			//bullet collision
			if(bullet.isAlive() ) {
				if(obj.get(i).getType() == ObjectType.OBSTACLE  && bullet.getBounds().intersects(obj.get(i).getBounds())) {
					bullet.setAlive(false);
					bullet.resetRange();
				}else if(obj.get(i).getType() == ObjectType.ENTITY){
					Enemy e = (Enemy)obj.get(i);
					if(e.isAlive() && bullet.getBounds().intersects(e.getBox())) {
						e.setAlive(false);
						bullet.setAlive(false);
						bullet.resetRange();

					}
					
				}else if(obj.get(i).getType() == ObjectType.BOSS){
					Boss b = (Boss)obj.get(i);
					if(b.isAlive() && bullet.getBounds().intersects(b.getBox())) {
						bullet.setAlive(false);
						bullet.resetRange();
						b.setHitpoints(b.getHitpoints()-bullet.getDmg());
					}
				}
			}
			
			
		}
	}
	
	public void restart() {
		big = true;
		respawn();
		setLifes(3);
	}
	
	public void respawn() {
		setPosition(spawn);
		//resetMovement();
		velX = 0;
		velY = 0;
		up = false;
		falling = true;
		jumping = false;
	}
	
	public void resetMovement() {
		velX = 0;
		velY = 0;
		left = false;
		right = false;
		up = false;
		falling = true;
		jumping = false;
	}
	
	public void shoot() {
		Point p = getCenterPosition();
		
		
		if(!bullet.isAlive()) {
			if(right) {

				bullet.setAlive(true);
				bullet.setX(getX()+50);
				bullet.setY(getY()+20);
				bullet.setVelX(Math.abs(bullet.getVelX()));
				bullet.setStart(new Point(bullet.getX(), bullet.getY()));
			}else if(left) {

				bullet.setAlive(true);
				bullet.setX(getX());
				bullet.setY(getY()+20);
				bullet.setVelX(Math.abs(bullet.getVelX())*-1);
				bullet.setStart(new Point(bullet.getX(), bullet.getY()));
			}
			

		}
	}
	
	public boolean getDamage() {
		if(big) {
			big = false;
			immune = true;
			falling = true;
			return false;
		}else {
			respawn();
			setLifes(getLifes()-1);
			return true;
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
	
	public Point getPosition() {
		return new Point(getX(), getY());
	}
	
	public Point getCenterPosition() {
		return new Point(getX()+getWidth()/2, getY()+getHeight()/2);
	}
	
	public int getLifes() {
		return lifes;
	}


	public void setLifes(int lifes) {
		this.lifes = lifes;
	}

	
	
	public int getPipes() {
		return pipes;
	}


	public void setPipes(int pipes) {
		this.pipes = pipes;
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


	public Point getSpawn() {
		return spawn;
	}


	public float getVelX() {
		return velX;
	}


	public void setSpawn(Point spawn) {
		this.spawn = spawn;
	}
	
	
	
}
