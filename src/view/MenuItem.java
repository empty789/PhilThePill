package view;

import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Rectangle;

public class MenuItem {

	
	private int x, y, width, height;
	private boolean hover, visible;
	private Font font;
	private Color c;
	private String title;
	private String action;

	
	public MenuItem(int x, int y, int width, int height, Font font, Color c, String title, String action) {
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
		if(hover) {
			g.fillRect(x, y, width, height);
			g.setColor(Color.BLACK);
		}else {
			
			g.drawRect(x, y, width, height);
			
		}
		drawCenteredString(g, title, getBounds(), font);
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
