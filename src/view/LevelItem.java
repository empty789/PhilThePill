package view;

import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;

import objects.entities.Player;

public class LevelItem {

	
	private int x, y, width, height;
	private boolean hover, visible, active;
	private Font font;
	private Color c;
	private String title;
	private String action;
	private BufferedImage phil;
	private int tick;

	
	public LevelItem(int x, int y, int width, int height, Font font, Color c, String title, String action) {
		this.x = x;
		this.y = y;
		this.width = width;
		this.height = height;
		this.font = font;
		this.c = c;
		this.title = title;
		this.action = action;
		this.visible = false;
		this.hover = false;
		tick = 0;
		try {
			phil = ImageIO.read(Player.class.getResource("/image/player/0.png"));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	
	public void setX(int x) {
		this.x = x;
	}


	public void setY(int y) {
		this.y = y;
	}


	public boolean isVisible() {
		return visible;
	}

	public void setVisible(boolean visible) {
		this.visible = visible;
	}

	public boolean isHover() {
		return hover;
	}
	
	public void setHover(boolean hover) {
		this.hover = hover;
	}
	
	public boolean isActive() {
		return active;
	}


	public void setActive(boolean active) {
		this.active = active;
	}


	public String getAction() {
		return action;
	}
	
	public Rectangle getBounds() {
		return new Rectangle (x, y, width, height);
	}
	
	public void render(Graphics g) {

		g.setFont(font);
		g.setColor(c);
		if(hover && active) {
			g.fillRect(x, y, width, height);
			g.setColor(Color.BLACK);
			
			if(active) {
				g.drawImage(phil, x+(width/2)-25, y, 50, 100, null);
				if(tick >=60) {
					g.setColor(c);
					g.drawRect(x-2, y-2, width+4, height+4);
					g.drawRect(x-3, y-3, width+6, height+6);
				}
				if(tick >=120)
					tick = 0;
				
				tick++;
				
			}
			
		}else {
			
			g.drawRect(x, y, width, height);
			if(active) {
				g.drawImage(phil, x+(width/2)-25, y, 50, 100, null);
				if(tick >=60) {
					g.setColor(c);
					g.drawRect(x-2, y-2, width+4, height+4);
					g.drawRect(x-3, y-3, width+6, height+6);
				}
				if(tick >=120)
					tick = 0;
				
				tick++;
				
			}
			
			
		}
		if(hover && active)
			g.setColor(Color.BLACK);
		else
			g.setColor(c);
		
		drawCenteredString(g, title, new Rectangle(x, y+height/3, width, height), font);
		visible = true;
	}
	
	//from https://stackoverflow.com/a/27740330
	public void drawCenteredString(Graphics g, String text, Rectangle rect, Font font) {
	    // Get the FontMetrics
	    FontMetrics metrics = g.getFontMetrics(font);
	    // Determine the X coordinate for the text
	    int x = rect.x + (rect.width - metrics.stringWidth(text)) / 2;
	    // Determine the Y coordinate for the text (note we add the ascent, as in java 2d 0 is top of the screen)
	    int y = rect.y + ((rect.height - metrics.getHeight()) / 2) + metrics.getAscent();
	    // Set the font
	    g.setFont(font);
	    // Draw the String
	    g.drawString(text, x, y);
	}
	
}
