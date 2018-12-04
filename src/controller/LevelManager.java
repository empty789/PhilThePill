package controller;

import java.awt.Color;

import java.awt.Point;

import game.GameLoop;
import objects.entities.Enemy;
import objects.items.Life;
import objects.level.Level;
import objects.misc.Trigger;
import objects.obstacles.Block;
import view.GamePanel;
import view.MainWindow;

public class LevelManager {

	private Level level;
	private MainWindow mw;
	private GameLoop gLoop;
	private GamePanel gPanel;
	
	private int maxW;
	private int maxH;
	
	public LevelManager(MainWindow mw, GameLoop gLoop, GamePanel gPanel) {
		this.mw = mw;
		this.gLoop = gLoop;
		this.gPanel = gPanel;
		
		maxW = mw.getMaxWidth();
		maxH = mw.getMaxHeight();
		
		level = new Level(new Point(400, 400));
		level.add(new Block(0, maxH-40, 2000, 40, Color.GREEN));
		level.add(new Block(300, maxH-300, 400, 50, Color.BLUE));
		level.add(new Block(0, maxH-40-720, 100, 720, Color.GREEN));
		level.add(new Block(1000, maxH-40-300-101, 100, 300, Color.YELLOW));
		level.add(new Block(1400, maxH-280-40, 100, 280, Color.RED));
		
		level.add(new Life(500, 600, 20, 20, Color.RED));
		level.add(new Enemy(1200, maxH-40-100, 80, 80));
		
		level.addTrigger(new Trigger(1700, 0, 100, maxH));
		
		//todo: lm in jede klasse, getters hinzufügen
		gLoop.SetLevel(level);
		gPanel.SetLevel(level);
	}
}
