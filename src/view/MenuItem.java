package view;

import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;

public class MenuItem {

	
	private int x, y, width, height;
	private boolean hover, visible;
	private Font font;
	private Color c;
	private String title;
	private String action;
	private BufferedImage icon;
	private int ticks;

	
	public MenuItem(int x, int y, int width, int height, Font font, Color c, String title, String action) {
		this.x = x;
		this.y = y;
		this.width = width;
		this.height = height;
		this.font = font;
		this.c = c;
		this.title = title;
		this.action = action;
		this.icon = null;
		this.visible = false;
		this.hover = false;
		ticks = 0;
	}

	public MenuItem(int x, int y, int width, int height, Font font, Color c, String title, String action, BufferedImage icon) {
		this.x = x;
		this.y = y;
		this.width = width;
		this.height = height;
		this.font = font;
		this.c = c;
		this.title = title;
		this.action = action;
		this.icon = icon;
		this.visible = false;
		this.hover = false;
		ticks = 0;
	}
	
	public void setX(int x) {
		this.x = x;
	}


	public void setY(int y) {
		this.y = y;
	}


	public int getX() {
		return x;
	}


	public int getY() {
		return y;
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
	
	public String getAction() {
		return action;
	}
	
	public Rectangle getBounds() {
		return new Rectangle (x, y, width, height);
	}
	
	
	public void render(Graphics g) {
		g.setFont(font);
		g.setColor(c);
		if(icon != null) {

			if(hover) {
				g.drawImage(icon, x-width/4, y-height/4, (int)(width*1.5), (int)(height*1.5), null);
			}else {
				
				if(ticks >= 125) {
					g.drawImage(icon, x, y, width, height, null);
					ticks = 0;
				}else if(ticks < 100) {
					g.drawImage(icon, x, y, width, height, null);
				}else if(ticks >= 100) {
					g.drawImage(icon, x-width/20, y-height/20, (int)(width*1.1), (int)(height*1.1), null);
				}
				
				
				ticks++;
			}
		}else {
			if(hover) {
				g.fillRect(x, y, width, height);
				g.setColor(Color.BLACK);
			}else {
				
				g.drawRect(x, y, width, height);
				
			}
			drawCenteredString(g, title, getBounds(), font);
		}
		
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


	@Override
	public String toString() {
		return "MenuItem [action=" + action + "]";
	}
	
	
	
}
