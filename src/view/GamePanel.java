package view;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.util.ArrayList;
import java.util.Random;

import javax.swing.JPanel;

import controller.QuestionManager;
import game.GameState;
import objects.Object;
import objects.ObjectType;
import objects.entities.Player;
import objects.level.Level;
import objects.misc.Bullet;
import util.Question;

public class GamePanel extends JPanel{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	
	private MainWindow mw;
	private Camera cam;
	private Player player;
	private Level level;
	private QuestionManager qm;
	private GameState state, lastState;
	
	private Question q;
	private String qText, a1Text, a2Text, a3Text;
	
	private ArrayList<MenuItem> menuList;
	private ArrayList<MenuItem> answerList;
	private Font menuFont, uiFont, answerFont;
	
	private int maxW;
	private int maxH;
	
	private Random r;
	
	public GamePanel(MainWindow mw, Camera cam, Player player, QuestionManager qm) {
		this.qm = qm;
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
		
		r = new Random();
		//setPreferredSize(new Dimension(MainWindow.WIDTH,MainWindow.HEIGHT));
		setPreferredSize(new Dimension(maxW, maxH));
		state = GameState.MENU;
		//state= GameState.MINIGAME;
		menuFont = new Font("Arial", Font.BOLD, 60);
		uiFont = new Font("Arial", Font.BOLD, 30);
		answerFont = new Font("Arial", Font.ITALIC, 20);
		
		//Menu
		menuList = new ArrayList<MenuItem>();
		menuList.add(new MenuItem(maxW/2-150, 100, 300, 100, menuFont, Color.WHITE, "Start", "START"));
		menuList.add(new MenuItem(maxW/2-150, 225, 300, 100, menuFont, Color.WHITE, "Manual", "MANUAL"));
		menuList.add(new MenuItem(maxW/2-150, 350, 300, 100, menuFont, Color.WHITE, "Exit", "EXIT"));
		
		//gameover menu
		menuList.add(new MenuItem(maxW/2-150, 225, 300, 100, menuFont, Color.WHITE, "To Menu", "REPLAY"));
		
		
		
		//answer menu
		answerList = new ArrayList<MenuItem>();
		setNextQuestion();
		
		
		
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
	
	public void resetButtons() {
		for(int i = 0; i < menuList.size(); i++) {
			menuList.get(i).setVisible(false);
		}
		for(int i = 0; i < answerList.size(); i++) {
			answerList.get(i).setVisible(false);
		}
	}
	
	public ArrayList<MenuItem> getMenu() {
		return menuList;
	}
	
	public ArrayList<MenuItem> getAnswerList() {
		return answerList;
	}
	
	public void setNextQuestion() {
		q = qm.nextQuestion();
		qText = q.getQuestion();
		ArrayList<String> answers = q.getAnswers();
		
		boolean foundIt = false;
		for(int i = 0; i < 3; i++) {
			int rand = r.nextInt(answers.size());
			if(i == 0) {
				a1Text = q.getAnswers().get(rand);
			}else if(i == 1) {
				a2Text = q.getAnswers().get(rand);
			}else if(i == 2) {
				a3Text = q.getAnswers().get(rand);
			}
			
			
			if(rand == 0 && foundIt == false) {
				foundIt = true;
				answerList.add(new MenuItem((int) (maxW*0.2), (int) (maxH*0.4)+i*50, 30, 30, uiFont, Color.WHITE, "x", "RIGHT"));
			}else {
				answerList.add(new MenuItem((int) (maxW*0.2), (int) (maxH*0.4)+i*50, 30, 30, uiFont, Color.WHITE, "x", "WRONG"));
			}
			

			answers.remove(rand);
			
		}
			

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
			menuList.get(2).render(g);
			
		}else if(state == GameState.GAMEOVER) {
			g.setFont(menuFont);
			g.setColor(Color.WHITE);
			g.drawString("GAMEOVER", maxW/2-150, 100);
			menuList.get(2).render(g);
		}else if(state == GameState.MINIGAME) {
			lastState = GameState.MINIGAME;
			g.setFont(menuFont);
			g.setColor(Color.WHITE);
			//g.drawString("QUIZ", maxW/2-150, 100);
			drawCenteredString(g, "QUIZ", new Rectangle(0, 0, maxW, (int) (maxH*0.2)), menuFont);
			g.setFont(uiFont);
			
			//g.drawString(qText, maxW/10, 200);
			drawCenteredString(g, qText, new Rectangle(0, (int) (maxH*0.1), maxW, (int) (maxH*0.4)), uiFont);

			answerList.get(0).render(g);
			answerList.get(1).render(g);
			answerList.get(2).render(g);
			g.setColor(Color.WHITE);
			g.setFont(answerFont);
			g.drawString(a1Text,(int) (maxW*0.2)+40, (int) (maxH*0.4)+23);
			g.drawString(a2Text,(int) (maxW*0.2)+40, (int) (maxH*0.4)+23+50);
			g.drawString(a3Text,(int) (maxW*0.2)+40, (int) (maxH*0.4)+23+100);
		}else if(state == GameState.ARIGHT) {
			g.setColor(Color.WHITE);
			drawCenteredString(g, "RICHTIG JUHUUUUUUUUUUUUU!!!!!!!", new Rectangle(0, 0, maxW, maxH), menuFont);
		}else if(state == GameState.AWRONG) {
			g.setColor(Color.WHITE);
			drawCenteredString(g, "FALSCH, WARUM STUDIERST DU EIGENTLICH?!?", new Rectangle(0, 0, maxW, maxH), menuFont);
		}
	}
	
	public void draw() {
		repaint();
	}
	
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
