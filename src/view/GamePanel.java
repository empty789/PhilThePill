package view;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.RenderingHints;
import java.awt.font.TextAttribute;
import java.util.ArrayList;
import java.util.Map;
import java.util.Random;

import javax.swing.JPanel;

import controller.LevelManager;
import controller.QuestionManager;
import controller.ResourceManager;
import game.GameState;
import objects.Object;
import objects.ObjectType;
import objects.entities.Player;
import objects.entities.StaticEnemy;
import objects.level.Level;
import objects.misc.AdvancedBullet;
import objects.misc.Bullet;
import objects.misc.Trigger;
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
	private ArrayList<LevelItem> lvlMenuList;
	private Font menuFont, uiFont, answerFont, answerFont_del, vicFont;
	
	private LevelManager lm;
	private ResourceManager resM;
	
	private int maxW;
	private int maxH;
	
	private int currentLevel, rightAnswer, delAnswer;
	private boolean showHint;
	
	private Random r;
	
	public GamePanel(MainWindow mw, Camera cam, Player player, QuestionManager qm, LevelManager lm, ResourceManager resM) {
		this.qm = qm;
		this.mw = mw;
		this.cam = cam;
		this.player = player;
		this.lm = lm;
		this.resM = resM;
		init();
	}
	
	public void init() {
		maxW = mw.getMaxWidth();
		maxH = mw.getMaxHeight();
		
		currentLevel = 0;
		r = new Random();
		showHint = false;
		delAnswer = 99;
		
		setPreferredSize(new Dimension(maxW, maxH));
		state = GameState.MENU;


		menuFont = new Font("Arial", Font.BOLD, 60);
		uiFont = new Font("Arial", Font.BOLD, 30);

		vicFont = new Font("Arial", Font.BOLD, 24);
		answerFont = new Font("Arial", Font.ITALIC, 20);
		
		Font tempFont = new Font("Arial", Font.ITALIC, 20);
		Map attributes = tempFont.getAttributes();
		attributes.put(TextAttribute.STRIKETHROUGH, TextAttribute.STRIKETHROUGH_ON);
		answerFont_del = new Font(attributes);
		
		//Menu
		menuList = new ArrayList<MenuItem>();
		menuList.add(new MenuItem((int) (maxW*0.2), (int) (maxH*0.5), 300, 100, menuFont, Color.WHITE, "Start", "STARTBUTTON"));
		menuList.add(new MenuItem((int) (maxW*0.2), (int) (maxH*0.5)+125, 300, 100, menuFont, Color.WHITE, "Anleitung", "MANUALBUTTON"));
		menuList.add(new MenuItem((int) (maxW*0.2), (int) (maxH*0.5)+250, 300, 100, menuFont, Color.WHITE, "Beenden", "EXITBUTTON"));
		
		//gameover menu
		menuList.add(new MenuItem(maxW/2-150, (int) (maxH*0.8), 300, 100, menuFont, Color.WHITE, "Zum Menü", "REPLAYBUTTON"));
		
		//victorybtn
		menuList.add(new MenuItem((int) (maxW*0.15), (int) (maxH*0.75), 300, 100, menuFont, Color.WHITE, "Weiter", "VICTORYBUTTON"));
		
		//wrong answer btn
		menuList.add(new MenuItem((int) (maxW*0.8)-100, (int) (maxH*0.2), 300, 100, menuFont, Color.WHITE, "Weiter", "WRONGBUTTON"));
		
		//right answer btn
		menuList.add(new MenuItem((int) (maxW*0.8)-100, (int) (maxH*0.2), 300, 100, menuFont, Color.WHITE, "Weiter", "RIGHTBUTTON"));
		
		//joker pipe button (quiz)
		menuList.add(new MenuItem((int) (maxW*0.8)-100, (int) (maxH*0.2), 300, 100, menuFont, Color.WHITE, "pipe", "PIPEJOKER", resM.pipe));
		
		//answer menu
		answerList = new ArrayList<MenuItem>();
		
		
		//lvl overview list
		lvlMenuList = new ArrayList<LevelItem>();
		lvlMenuList.add(new LevelItem((int) (maxW*0.28),(int) (maxH*0.3), 150, 150, uiFont, Color.WHITE, "Kopf", "KOPF", resM.head));
		lvlMenuList.add(new LevelItem((int) (maxW*0.28)+225,(int) (maxH*0.3), 150, 150, uiFont, Color.WHITE, "Herz", "HERZ", resM.heart));
		lvlMenuList.add(new LevelItem((int) (maxW*0.28)+450,(int) (maxH*0.3), 150, 150, uiFont, Color.WHITE, "Leber", "LEBER", resM.liver));
		lvlMenuList.add(new LevelItem((int) (maxW*0.28)+675,(int) (maxH*0.3), 150, 150, uiFont, Color.WHITE, "Darm", "DARM", resM.stom));
		
		level = lm.getLevel(0);
		setCurrentLevel(0);
		
	}
	
	public void SetLevel(Level level) {
		  this.level = level;
		  
	}
	
	
	public int getCurrentLevel() {
		return currentLevel;
	}
	
	public void setCurrentLevel(int num) {
		
		currentLevel = num;
		if(num <= lm.getLevelList().size()) {
			level = lm.getLevel(num);
	
			if(num != 0)
				lvlMenuList.get(num-1).setDone(true);
			
			for(int i=0; i<lvlMenuList.size(); i++) {
				lvlMenuList.get(i).setActive(false);
			}
			
			lvlMenuList.get(num).setActive(true);
			player.setSpawn(level.getStartPoint());
			player.respawn();
		}
		
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
		for(int i = 0; i < lvlMenuList.size(); i++) {
			lvlMenuList.get(i).setVisible(false);
		}
	}
	
	public void resetAnswerColor() {
		for(int i = 0; i < answerList.size(); i++) {
			answerList.get(i).setC(Color.WHITE);;
		}
	}
	
	public ArrayList<MenuItem> getMenu() {
		return menuList;
	}
	
	public ArrayList<MenuItem> getAnswerList() {
		return answerList;
	}
	
	public ArrayList<LevelItem> getLvlMenuList() {
		return lvlMenuList;
	}
	
	public void setNextQuestion() {
		answerList.clear();
		q = qm.nextQuestion(level.getTopic());
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
				answerList.add(new MenuItem((int) (maxW*0.4), (int) (maxH*0.4)+i*50, 30, 30, uiFont, Color.WHITE, "x", "RIGHT"));
				rightAnswer = i;
			}else {
				answerList.add(new MenuItem((int) (maxW*0.4), (int) (maxH*0.4)+i*50, 30, 30, uiFont, Color.WHITE, "x", "WRONG"));
				
			}
			

			answers.remove(rand);
			
		}
			
		System.out.println(answerList);
	}
	
	
	
	public void ShowHint() {
		if(!showHint && player.getPipes() > 0) {
			showHint = true;
			player.setPipes(player.getPipes()-1);
			delAnswer = 99;
			int rand = r.nextInt(100);
			System.out.println(rand);


			if(rand >= 90) {
				answerList.get(rightAnswer).setC(Color.GREEN);
			}else if(rand >= 45) {
				answerList.get(rightAnswer).setC(Color.WHITE);
				setNextQuestion();
			}else if(rand < 45){
				int del = r.nextInt(2);
				
				while(del == rightAnswer) {
					del = r.nextInt(2);
				}
				delAnswer = del;
			}
		}
		
	}

	public void setShowHint(boolean b) {
		showHint = b;
	}

	@Override
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		
		int visibleX = player.getX()-maxW/2;
		int visibleY = 0;
		Rectangle visibleScreen = new Rectangle(visibleX, visibleY, maxW, maxH);
		
		Graphics2D g2d = (Graphics2D) g;
		
		g2d.setRenderingHint(
		        RenderingHints.KEY_TEXT_ANTIALIASING,
		        RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
		
		g.setColor(Color.BLUE.darker());
		

		g.fillRect(0, 0, maxW, maxH);
		if(currentLevel < lm.getLevelList().size()) {
				g.drawImage(resM.bgList.get(currentLevel), 0, 0, maxW, maxH, null);
		}
		
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
			
			
			ArrayList<Trigger> tl = level.getTriggerList();
			
			for(int i = 0; i < tl.size(); i++) {
				if(tl.get(i).getType() == ObjectType.TELEPORTTRIGGER) {
					tl.get(i).render(g);
				}
				
			}

			//draw all visible objects
			for(int i = 0; i < level.getObjects().size(); i++) {
				Object obj = level.getObjects().get(i);
				
				if(visibleScreen.intersects(obj.getBounds()) && obj.isAlive()) {
					obj.render(g);
				}
	
			}
			//draw player bullets
			Bullet bullet = player.getBullet();
			if(visibleScreen.intersects(bullet.getBounds()) && bullet.isAlive()) {
				bullet.render(g);
			}
			
			//draw boss bullets
			ArrayList<AdvancedBullet> bulletList = level.getBoss().getBulletList();
			for(int i = 0; i < bulletList.size(); i++) {
				if(visibleScreen.intersects(bulletList.get(i).getBounds()) && bulletList.get(i).isAlive()) {
					bulletList.get(i).render(g);
				}
			}
			
			//draw static enemys bullets
			ArrayList<StaticEnemy> staticEnemyList = level.getStaticEnemys();
			
			for(int i = 0; i < staticEnemyList.size(); i++) {
				ArrayList<AdvancedBullet> enemyBulletList = staticEnemyList.get(i).getBulletList();
				for(int j = 0; j < enemyBulletList.size(); j++) {
					if(visibleScreen.intersects(enemyBulletList.get(j).getBounds()) && enemyBulletList.get(j).isAlive()) {
						enemyBulletList.get(j).render(g);
					}
				}
			}

			
			
			//interface
			g.setColor(Color.WHITE);
			g.setFont(uiFont);
			if(player.getX()>maxW-maxW/2) {
				g.drawImage(resM.life, visibleX+50, (int) (maxH*0.05)-35, 50, 50, null);
				g.drawString(" x"+player.getLifes(), visibleX+105, (int) (maxH*0.05));
				g.drawImage(resM.pipe, visibleX+180, (int) (maxH*0.05)-35, 80, 50, null);
				g.drawString(" x"+player.getPipes(), visibleX+235, (int) (maxH*0.05));
			}else {
				g.drawImage(resM.life, 50, (int) (maxH*0.05)-35, 50, 50, null);
				g.drawString(" x"+player.getLifes(), 105, (int) (maxH*0.05));
				g.drawImage(resM.pipe, 180, (int) (maxH*0.05)-35, 80, 50, null);
				g.drawString(" x"+player.getPipes(), 235, (int) (maxH*0.05));
			}
			
			//boss hp
			if(visibleScreen.intersects(level.getBoss().getBounds())) {
				g.setColor(Color.BLACK);
				g.fillRect(visibleX - 304 + maxW/2, ((int) (maxH*0.1))-4, 608, 48);
				g.setColor(Color.RED);
				g.fillRect(visibleX - 300 + maxW/2, (int) (maxH*0.1), 6*level.getBoss().getHitpoints(), 40);
			}
			

			if(player.getX()>maxW-maxW/2)
				g2d.translate(-cam.getX(), -cam.getY());
			////////////////////////////////////
		}else if(state == GameState.MENU) {
			g.drawImage(resM.menu, 0, 0, maxW, maxH, null);
			menuList.get(0).render(g);
			menuList.get(1).setX((int) (maxW*0.2));
			menuList.get(1).render(g);
			menuList.get(2).setX((int) (maxW*0.2));
			menuList.get(2).render(g);
		}else if(state == GameState.PAUSE) {
			lastState = GameState.RUNNING;
			g.drawImage(resM.pause, 0, 0, maxW, maxH, null);
			menuList.get(1).setX((int) (maxW*0.3));
			menuList.get(1).render(g);
			menuList.get(2).setX((int) (maxW*0.3));
			menuList.get(2).render(g);
		}else if(state == GameState.MANUAL) {
			g.drawImage(resM.manual, 0, 0, maxW, maxH, null);
		}else if(state == GameState.GAMEOVER) {
			g.drawImage(resM.gameover, 0, 0, maxW, maxH, null);
			menuList.get(3).render(g);
		}else if(state == GameState.MINIGAME) {
			lastState = GameState.MINIGAME;
			g.drawImage(resM.quiz, 0, 0, maxW, maxH, null);
			g.setColor(Color.WHITE);
			g.setFont(uiFont);
			g.drawImage(resM.pipe, 180, (int) (maxH*0.05)-35, 80, 50, null);
			g.drawString(" x"+player.getPipes(), 235, (int) (maxH*0.05));
			g.setFont(menuFont);
			g.setColor(Color.WHITE);
			//g.drawString("QUIZ", maxW/2-150, 100);
			//drawCenteredString(g, "QUIZ", new Rectangle(0, 0, maxW, (int) (maxH*0.2)), menuFont);
			g.setFont(uiFont);
			
			//g.drawString(qText, maxW/10, 200);
			drawCenteredString(g, qText, new Rectangle(0, 0, maxW, (int) (maxH*0.3)), uiFont);

			answerList.get(0).render(g);
			answerList.get(1).render(g);
			answerList.get(2).render(g);
			//joker
			menuList.get(7).render(g);
			g.setColor(Color.WHITE);
			g.setFont(answerFont);
			
			if(delAnswer == 0 && showHint) {
				g.setFont(answerFont_del);
			}else {
				g.setFont(answerFont);
			}
			g.drawString(a1Text,(int) (maxW*0.4)+40, (int) (maxH*0.4)+23);
			
			if(delAnswer == 1 && showHint) {
				g.setFont(answerFont_del);
			}else {
				g.setFont(answerFont);
			}
			g.drawString(a2Text,(int) (maxW*0.4)+40, (int) (maxH*0.4)+23+50);
			
			if(delAnswer == 2 && showHint) {
				g.setFont(answerFont_del);
			}else {
				g.setFont(answerFont);
			}
			g.drawString(a3Text,(int) (maxW*0.4)+40, (int) (maxH*0.4)+23+100);
		}else if(state == GameState.ARIGHT) {
			lastState = GameState.ARIGHT;
//			g.setColor(Color.WHITE);
//			drawCenteredString(g, "RICHTIG JUHUUUUUUUUUUUUU!!!!!!!", new Rectangle(0, 0, maxW, maxH), menuFont);
			g.drawImage(resM.aright, 0, 0, maxW, maxH, null);
			menuList.get(6).render(g);
		}else if(state == GameState.AWRONG) {
			lastState = GameState.AWRONG;
//			g.setColor(Color.WHITE);
//			drawCenteredString(g, "FALSCH, WARUM STUDIERST DU EIGENTLICH?!? -1 Leben!", new Rectangle(0, 0, maxW, maxH), menuFont);
			g.drawImage(resM.awrong, 0, 0, maxW, maxH, null);
			menuList.get(5).render(g);
		}else if(state == GameState.LEVELOVERVIEW) {
			lastState = GameState.LEVELOVERVIEW;
			g.drawImage(resM.overview, 0, 0, maxW, maxH, null);
//			g.setFont(menuFont);
//			g.setColor(Color.WHITE);
//			drawCenteredString(g, "Level ï¿½bersicht", new Rectangle(0, 0, maxW, (int) (maxH*0.2)), menuFont);
			for(int i=0; i < lvlMenuList.size(); i++) {
				lvlMenuList.get(i).render(g);
			}
			
			
		}else if(state == GameState.VICTORY) {
			lastState = GameState.VICTORY;
			g.drawImage(resM.complete, 0, 0, maxW, maxH, null);
			g.setColor(Color.WHITE);
			//drawCenteredString(g, "Glï¿½ckwunsch!", new Rectangle(0, 0, maxW, (int) (maxH*0.2)), menuFont);
			g.setFont(vicFont);
			g.drawString(level.getVicMsg(), (int) (maxW*0.075), (int) (maxH*0.6));
			menuList.get(4).render(g);
		}else if(state == GameState.GAMEWON) {
			g.drawImage(resM.victory, 0, 0, maxW, maxH, null);
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
