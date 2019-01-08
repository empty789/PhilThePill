package objects;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;

public class Object {

	private int x, y, width, height;
	private ObjectType type;
	private Color color;
	private BufferedImage image;
	boolean alive;
	
	public Object() {
		x  = 0;
		y = 0;
		width = 0;
		height = 0;
		type = ObjectType.UNDEFINED;
		color = Color.BLACK;
		image = null;
		alive = true;
	}
	
	public Rectangle getBounds() {
		return new Rectangle(getX(), getY(), getWidth(), getHeight());
	}
	
	public Rectangle getBoundsTop() {
		return new Rectangle(0,0,0,0);
	}
	
	public Rectangle getBoundsRight() {
		return new Rectangle(0,0,0,0);
	}
	
	public Rectangle getBoundsLeft() {
		return new Rectangle(0,0,0,0);
	}
	
	public void render(Graphics g) {
		
		if(image != null) {
			g.drawImage(image, x, y, width, height, null);
		}else {
			g.setColor(color);
			g.fillRect(x, y, width, height);
		}
	}
	
	public int getX() {
		return x;
	}
	public void setX(int x) {
		this.x = x;
	}
	public int getY() {
		return y;
	}
	public void setY(int y) {
		this.y = y;
	}
	public int getWidth() {
		return width;
	}
	public void setWidth(int width) {
		this.width = width;
	}
	public int getHeight() {
		return height;
	}
	public void setHeight(int height) {
		this.height = height;
	}
	public ObjectType getType() {
		return type;
	}
	public void setType(ObjectType type) {
		this.type = type;
	}
	public Color getColor() {
		return color;
	}
	public void setColor(Color color) {
		this.color = color;
	}
	public void setPosition(Point p) {
		x = p.x;
		y= p.y;
	}

	public void setImage(BufferedImage image) {
		this.image = image;
	}

	public BufferedImage getImage() {
		return image;
	}
	public boolean isAlive() {
		return alive;
	}

	public void setAlive(boolean alive) {
		this.alive = alive;
	}
	
}
