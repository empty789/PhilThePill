package view;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.util.ArrayList;

import javax.swing.JPanel;

import game.GameState;
import objects.Object;
import objects.ObjectType;
import objects.entities.Player;
import objects.level.Level;
import objects.misc.Bullet;

public class GamePanel extends JPanel{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	
	private MainWindow mw;
	private Camera cam;
	private Player player;
	private Level level;
	private GameState state, lastState;
	
	private ArrayList<MenuItem> menuList;
	private Font menuFont, uiFont;
	
	private int maxW;
	private int maxH;
	
	public GamePanel(MainWindow mw, Camera cam, Player player) {
		this.mw = mw;
		this.cam = cam;
		this.player = player;
		init();
	}
	
	public void init() {
		//maxW = MainWindow.WIDTH;
		//maxH = MainWindow.HEIGHT;
		maxW = mw.getMaxWidth();
		maxH = mw.getMaxHeight();
		
		//setPreferredSize(new Dimension(MainWindow.WIDTH,MainWindow.HEIGHT));
		setPreferredSize(new Dimension(maxW, maxH));
		state = GameState.MENU;
		menuFont = new Font("Arial", Font.BOLD, 60);
		uiFont = new Font("Arial", Font.BOLD, 30);
		
		//Menu
		menuList = new ArrayList<MenuItem>();
		menuList.add(new MenuItem(maxW/2-150, 100, 300, 100, menuFont, Color.WHITE, "Start", "START"));
		menuList.add(new MenuItem(maxW/2-150, 225, 300, 100, menuFont, Color.WHITE, "Exit", "EXIT"));
		
		//gameover menu
		menuList.add(new MenuItem(maxW/2-150, 225, 300, 100, menuFont, Color.WHITE, "To Menu", "REPLAY"));
	}
	
	public void SetLevel(Level level) {
		  this.level = level;
	  }
	
	public Level getLevel() {
		return level;
	}
	
	public void setState(GameState state) {
		this.state = state;
	}
	
	public GameState getState() {
		return state;
	}
	
	public GameState getLastState() {
		return lastState;
	}
	
	public void resetMenu() {
		for(int i = 0; i < menuList.size(); i++) {
			menuList.get(i).setVisible(false);
		}
	}
	
	public ArrayList<MenuItem> getMenu() {
		return menuList;
	}
	@Override
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		
		int visibleX = player.getX()-maxW/2;
		int visibleY = 0;
		Rectangle visibleScreen = new Rectangle(visibleX, visibleY, maxW, maxH);
		
		Graphics2D g2d = (Graphics2D) g;
		g.setColor(Color.BLUE.darker());
		//g.fillRect(0, 0, MainWindow.WIDTH, MainWindow.HEIGHT);
		g.fillRect(0, 0, maxW, maxH);
		
		if(state == GameState.RUNNING) {
			lastState = GameState.RUNNING;
			/////////////////////////////////
			if(player.getX()>maxW-maxW/2) {
				g2d.translate(cam.getX(), cam.getY());
				visibleScreen = new Rectangle(visibleX, visibleY, maxW, maxH);
			}else {
				visibleScreen = new Rectangle(0, 0, maxW, maxH);
			}
			
			player.render(g);
			
			
			//draw all visible objects
			for(int i = 0; i < level.getObjects().size(); i++) {
				Object obj = level.getObjects().get(i);
				
				if(visibleScreen.intersects(obj.getBounds())) {
					//dont render trigger
					if(obj.getType() != (ObjectType.QUESTTRIGGER))
						obj.render(g);
				}
	
			}
			//draw player bullets
			Bullet bullet = player.getBullet();
			if(visibleScreen.intersects(bullet.getBounds()) && bullet.isAlive()) {
				bullet.render(g);
			}
			
			
			//interface
			g.setColor(Color.WHITE);
			g.setFont(uiFont);
			if(player.getX()>maxW-maxW/2)
				g.drawString("Life: x"+player.getLifes(), visibleX+50, 50);
			else
				g.drawString("Life: x"+player.getLifes(), 50, 50);
			
			
			if(player.getX()>maxW-maxW/2)
				g2d.translate(-cam.getX(), -cam.getY());
			////////////////////////////////////
		}else if(state == GameState.MENU) {
			menuList.get(0).render(g);
			menuList.get(1).render(g);
			
		}else if(state == GameState.GAMEOVER) {
			g.setFont(menuFont);
			g.setColor(Color.WHITE);
			g.drawString("GAMEOVER", maxW/2-150, 100);
			menuList.get(2).render(g);
		}else if(state == GameState.MINIGAME) {
			lastState = GameState.MINIGAME;
			g.setFont(menuFont);
			g.setColor(Color.WHITE);
			g.drawString("QUIZ", maxW/2-150, 100);

		}
	}
	
	public void draw() {
		repaint();
	}

}
