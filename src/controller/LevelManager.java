package controller;

import java.awt.Color;
import java.awt.Point;
import java.util.ArrayList;

import game.GameLoop;
import objects.ObjectType;
import objects.entities.Boss;
import objects.entities.Enemy;
import objects.entities.Player;
import objects.entities.StaticEnemy;
import objects.items.EnergyDrink;
import objects.items.Life;
import objects.items.Pipe;
import objects.level.Level;
import objects.misc.Trigger;
import objects.obstacles.Block;
import view.GamePanel;
import view.LevelItem;
import view.MainWindow;

public class LevelManager {

	private Level level;
	private MainWindow mw;

	
	private ArrayList<Level> levelList;
	
	private int maxH;
	
	public LevelManager(MainWindow mw) {
		this.mw = mw;

		maxH = mw.getMaxHeight();
		
		levelList = new ArrayList<Level>();
		
		initLevel1();
		initLevel2();
		initLevel3();
		initLevel4();
		
//		setLevel(0);
//		gPanel.setCurrentLevel(0);
		
	}

	public void initLevel1() {
		String victory = "Du hast es geschafft, der Kopf ist wieder gesund!";
		level = new Level(new Point(400, 400), victory, "kopf");
		level.add(new Block(0, maxH-40, 3400, 40, Color.GREEN));
		level.add(new Block(300, maxH-300, 400, 50, Color.BLUE));
		
		level.add(new StaticEnemy(1000, maxH-600, 100, 100));
		
		level.add(new Block(0, maxH-40-720, 100, 720, Color.GREEN));
		level.add(new Block(1000, maxH-40-300-101, 100, 300, Color.YELLOW));
		level.add(new Block(1400, maxH-280-40, 100, 280, Color.RED));
		
		level.add(new Life(500, 600, 50, 50, Color.RED));
		level.add(new Pipe(800, 600, 60, 20, Color.GREEN));
		level.add(new EnergyDrink(1200, maxH-40-60, 20, 40, Color.YELLOW));
		level.add(new Enemy(1200, maxH-40-100, 80, 80));
		
		//boss
		level.add(new Block(2000, maxH-40-1000, 100, 800, Color.GREEN));
		
		level.addTrigger(new Trigger(2100, maxH-40-200, 100, 200, Color.GREEN,ObjectType.BLOCKTRIGGER));
		
		level.add(new Block(2000, 0, 1400, 100, Color.GREEN));
		level.add(new Block(3000, 0, 100, maxH, Color.GREEN));
		
		level.add(new Boss(2775, maxH-50-500, 200, 200));
		
		level.addTrigger(new Trigger(1700, 0, 100, maxH, Color.GREEN,ObjectType.QUESTTRIGGER));
		
		///
		levelList.add(level);
	}
	
	public void initLevel2() {
		String victory = "Du hast es geschafft, das Herz ist wieder gesund!";
		level = new Level(new Point(100, 100), victory, "herz");
		level.add(new Block(0, maxH-40, 900, 40, Color.GREEN));
		level.add(new Block(600, maxH-500,200, 50, Color.BLUE));
		level.add(new Block(3100, maxH-300,150, 50, Color.BLUE));
		level.add(new Block(300, maxH-300,70, 40, Color.BLUE));
		level.add(new Block(2100, maxH-300,150, 50, Color.BLUE));
		level.add(new Block(5200, maxH-200,40, 40, Color.BLUE));
		level.add(new Block(4200, maxH-400,100, 50, Color.BLUE));
		level.add(new Block(5500, maxH-500,40, 40, Color.BLUE));
		level.add(new Block(0, maxH-40-720, 100, 720, Color.GREEN));
		level.add(new Block(1100, maxH-40-300-101, 100, 300, Color.YELLOW));
		level.add(new Block(1600, maxH-280-40, 100, 280, Color.RED));
		level.add(new Block(2600, maxH-280-1200, 100, 280, Color.RED));
		level.add(new Block(3600, maxH-280-40, 100, 280, Color.RED));
		level.add(new Block(1600, maxH-40, 400, 40, Color.GREEN));
		level.add(new Block(2300, maxH-40, 600, 40, Color.GREEN));
		level.add(new Block(3500, maxH-40, 800, 40, Color.GREEN));
		level.add(new Block(4500, maxH-40, 500, 40, Color.GREEN));
		level.add(new Block(6000, maxH-40, 1500, 40, Color.GREEN));
		level.add(new Block(4600, maxH-40-200-101, 100, 300, Color.YELLOW));
		level.add(new Block(2500, maxH-40-300-101, 100, 300, Color.YELLOW));
		
		
		level.add(new Life(700,maxH-40-60, 20, 20, Color.RED));
		level.add(new Pipe(700, maxH-40-500, 20, 20, Color.GREEN));
		level.add(new EnergyDrink(1900, maxH-40-100, 20, 20, Color.YELLOW));
		level.add(new Life(5230,100, 20, 20, Color.RED));
		level.add(new Pipe(3200, maxH-40-360, 20, 20, Color.GREEN));
		level.add(new EnergyDrink(4250, maxH-400-100, 20, 20, Color.YELLOW));
		level.add(new Enemy(800, maxH-40-100, 80, 80));
		level.add(new Enemy(1900, maxH-40-100, 60, 60));
		level.add(new Enemy(3700, maxH-100-100, 40, 40));
		level.add(new Enemy(2600, maxH-300-100, 60, 60));
		level.add(new Enemy(4650, maxH-40-100, 60, 60));
		level.add(new Enemy(3300, maxH-400-100, 60, 60));
		level.add(new Enemy(1700, maxH-400-100, 40, 40));
		
		//boss
		level.add(new Block(6000, maxH-40-1000, 100, 800, Color.GREEN));
		
		level.addTrigger(new Trigger(6100, maxH-40-200, 100, 200, Color.GREEN,ObjectType.BLOCKTRIGGER));
		
		level.add(new Block(6000, 0, 1400, 100, Color.GREEN));
		level.add(new Block(7000, 0, 100, maxH, Color.GREEN));
		
		level.add(new Boss(6800, maxH-50-300, 200, 200));
		
		level.addTrigger(new Trigger(5550, 0, 100, maxH-500, Color.GREEN,ObjectType.QUESTTRIGGER));
		
		levelList.add(level);
	}
	
	public void initLevel3() {
		String victory = "";
		level = new Level(new Point(400, 400), victory, "leber");
		level.add(new Block(0, maxH-40, 3400, 40, Color.GREEN));
		level.add(new Block(300, maxH-500, 400, 50, Color.BLUE));
		level.add(new Block(0, maxH-40-720, 100, 720, Color.GREEN));
		level.add(new Block(1000, maxH-40-300-101, 100, 300, Color.YELLOW));
		level.add(new Block(1400, maxH-280-40, 100, 280, Color.RED));
		
		level.add(new Life(500, 600, 20, 20, Color.RED));
		level.add(new Pipe(800, 600, 20, 20, Color.GREEN));
		level.add(new Enemy(1200, maxH-40-100, 80, 80));
		
		//boss
		level.add(new Block(2000, maxH-40-1100, 100, 900, Color.GREEN));
		level.add(new Block(2000, 0, 1400, 100, Color.GREEN));
		level.add(new Block(3000, 0, 100, maxH, Color.GREEN));
		
		level.add(new Boss(2775, maxH-50-500, 200, 200));
		
		level.addTrigger(new Trigger(1700, 0, 100, maxH, Color.GREEN, ObjectType.QUESTTRIGGER));
		
		levelList.add(level);
	}
	
	public void initLevel4() {
		String victory = "";
		level = new Level(new Point(400, 400), victory, "darm");
		level.add(new Block(0, maxH-40, 3400, 40, Color.GREEN));
		level.add(new Block(300, maxH-500, 400, 50, Color.BLUE));
		level.add(new Block(0, maxH-40-720, 100, 720, Color.GREEN));
		level.add(new Block(1000, maxH-40-300-101, 100, 300, Color.YELLOW));
		level.add(new Block(1400, maxH-280-40, 100, 280, Color.RED));
		
		level.add(new Life(500, 600, 50, 50, Color.RED));
		level.add(new Pipe(800, 600, 60, 20, Color.GREEN));
		level.add(new Enemy(1200, maxH-40-100, 80, 80));
		
		//boss
		level.add(new Block(2000, maxH-40-1100, 100, 900, Color.GREEN));
		level.add(new Block(2000, 0, 1400, 100, Color.GREEN));
		level.add(new Block(3000, 0, 100, maxH, Color.GREEN));
		
		level.add(new Boss(2775, maxH-50-500, 200, 200));
		
		level.addTrigger(new Trigger(1700, 0, 100, maxH, Color.GREEN, ObjectType.QUESTTRIGGER));
		
		levelList.add(level);
	}
//	public void setLevel(int num) {
//		gLoop.SetLevel(levelList.get(num));
//		gPanel.SetLevel(levelList.get(num));
//	}
	
	public Level getLevel(int num) {
		return levelList.get(num);
	}

	public ArrayList<Level> getLevelList() {
		return levelList;
	}
	
}
