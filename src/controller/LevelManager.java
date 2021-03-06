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
import objects.obstacles.TimedBlock;
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
		
		
	}

	public void initLevel1() {
		//korbi
		String victory = "Du hast es geschafft, der Kopf ist wieder gesund!";
		
		level = new Level(new Point(300, 400), victory, "kopf");
		level.add(new Block(600, maxH-40, 2700, 40, Color.GREEN));
		level.add(new Block(300, maxH-400, 50, 50, Color.BLUE));
		level.add(new Block(0, maxH-720, 100, 720, Color.GREEN));
		level.add(new Block(1200, maxH-441, 100, 300, Color.YELLOW));
		level.add(new Block(1400, maxH-280-40, 100, 280, Color.RED));
		level.add(new Block(600, maxH-60, 20, 40, Color.GREEN));
		level.add(new Block(800, maxH-400, 50, 50, Color.BLUE));
		
		
		level.add(new Block(2200, maxH-441, 100, 300, Color.YELLOW));
		level.add(new Block(2800, maxH-441, 100, 300, Color.YELLOW));
		level.add(new Block(1900, maxH-400, 50, 50, Color.BLUE));
		level.add(new Block(2400, maxH-800, 50, 50, Color.BLUE));
		level.add(new Block(2750, maxH-950, 500, 50, Color.BLUE));
		level.add(new Block(3050, maxH-1351, 50, 300, Color.YELLOW));
		level.add(new Block(2600, maxH-280-40, 100, 280, Color.RED));
		level.add(new Block(3200, maxH-280-40, 100, 280, Color.RED));
		
		
		level.add(new Block(3775, maxH-40, 50, 40, Color.GREEN));
		level.add(new TimedBlock(3600, maxH-40, 400, 40, Color.GREEN, true, 4));
		level.add(new TimedBlock(3600, maxH-540, 400, 40, Color.GREEN, true, 4));
		level.add(new Block(3600, maxH-570, 1, 1, Color.GREEN));
		level.add(new Block(4000, maxH-570, 1, 1, Color.GREEN));
		level.add(new Block(3600, maxH-70, 1, 1, Color.GREEN));
		level.add(new Block(4000, maxH-70, 1, 1, Color.GREEN));
		level.add(new Block(4575, maxH-40, 50, 40, Color.GREEN));
		level.add(new TimedBlock(4400, maxH-40, 400, 40, Color.GREEN, true, 4));
		level.add(new TimedBlock(4400, maxH-540, 400, 40, Color.GREEN, true, 4));
		level.add(new Block(4399, maxH-70, 1, 1, Color.GREEN));
		level.add(new Block(4399, maxH-570, 1, 1, Color.GREEN));
		level.add(new Block(4799, maxH-70, 1, 1, Color.GREEN));
		level.add(new Block(4799, maxH-570, 1, 1, Color.GREEN));
		
		
		level.add(new Block(5200, maxH-40, 460, 40, Color.GREEN));
		level.add(new Block(5900, maxH-40, 150, 40, Color.GREEN));
		level.add(new Block(6800, maxH-40, 150, 40, Color.GREEN));
		level.add(new Block(5530, maxH-441, 50, 300, Color.YELLOW));
		level.add(new Block(5580, maxH-241, 30, 100, Color.YELLOW));
		level.add(new Block(5945, maxH-441, 50, 300, Color.YELLOW));
		level.add(new Block(5200, maxH-400, 50, 50, Color.BLUE));
		level.add(new Block(5530, maxH-700, 50, 50, Color.BLUE));
		level.add(new Block(5945, maxH-850, 50, 50, Color.BLUE));
		level.add(new Block(6300, maxH-280-40, 100, 280, Color.RED));
		level.add(new Block(6410, maxH-960, 50, 50, Color.BLUE));
		
		
		
		
		level.add(new Block(7000, maxH-400, 50, 50, Color.BLUE));
		
		level.add(new Block(7000, maxH-700, 50, 50, Color.BLUE));
		level.add(new Block(7500, maxH-700, 50, 50, Color.BLUE));
		
		
		
		level.add(new Block(7900, maxH-40, 1500, 40, Color.GREEN));
		
		
		
		level.add(new Life(500, maxH-600, 60, 50, Color.RED));
		level.add(new Life(2800, maxH-1000, 60, 50, Color.RED));
		level.add(new Life(1700, maxH-600, 60, 50, Color.RED));
		
		level.add(new Pipe(800, 600, 80, 50, Color.GREEN));
		level.add(new Pipe(800, 600, 80, 50, Color.GREEN));
		
		level.add(new EnergyDrink(1200, maxH-40-60, 30, 60, Color.YELLOW));
		level.add(new EnergyDrink(3250, maxH-390, 30, 60, Color.YELLOW));
		level.add(new EnergyDrink(6650, maxH-1000, 30, 60, Color.YELLOW));
		
		level.add(new StaticEnemy(1800, maxH-900, 100, 100));
		//level.add(new StaticEnemy(6800, maxH-900, 100, 100));
		
		level.add(new Enemy(1200, maxH-40-100, 100, 100, 3, false));
		level.add(new Enemy(2000, maxH-40-100, 100, 100, 3, false));
		level.add(new Enemy(3000, maxH-40-100, 100, 100, 3, false));
		level.add(new Enemy(3000, maxH-40-100, 100, 100, 4, true));
		
		level.add(new Enemy(3850, maxH-110, 50, 50,3, false));
		level.add(new Enemy(4650, maxH-110, 50, 50, 3, false));
		
		
		level.add(new Enemy(3850, maxH-10-600, 50, 50, 3, false));
		level.add(new Enemy(4650, maxH-10-600, 50, 50, 3, false));
		
		
		level.add(new Enemy(6000, maxH-40-100, 80, 80, 2, false));
		
		
		level.addTrigger(new Trigger(1500, 0, 100, maxH, Color.GREEN,ObjectType.QUESTTRIGGER));
		level.addTrigger(new Trigger(3700, 0, 100, maxH, Color.GREEN,ObjectType.QUESTTRIGGER));
		level.addTrigger(new Trigger(7950, 0, 100, maxH, Color.GREEN,ObjectType.QUESTTRIGGER));
		level.addTrigger(new Trigger(3000, 0, 100, maxH, Color.GREEN,ObjectType.QUESTTRIGGER));
		
		//boss
		level.add(new Block(8000, maxH-40-1000, 100, 800, Color.GREEN));
		
		level.addTrigger(new Trigger(8100, maxH-40-200, 100, 200, Color.GREEN,ObjectType.BLOCKTRIGGER));
		
		level.add(new Block(8000, 0, 1400, 100, Color.GREEN));
		level.add(new Block(9000, 0, 100, maxH, Color.GREEN));
		
		level.add(new Boss(8775, maxH-50-500, 200, 200));
		
		
		
		levelList.add(level);
		
	}
	
	public void initLevel2() {
		//saj
		String victory = "Du hast es geschafft, das Herz ist wieder gesund!";
		level = new Level(new Point(100, 100), victory, "herz");
		level.add(new Block(0, maxH-40, 900, 40, Color.GREEN));
		level.add(new Block(3100, maxH-300,150, 50, Color.BLUE));
		level.add(new Block(300, maxH-300,70, 40, Color.BLUE));
		level.add(new Block(5200, maxH-200,40, 40, Color.BLUE));
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
		level.add(new TimedBlock(600, maxH-500, 200, 50, Color.GREEN, true, 2));
		level.add(new TimedBlock(2100, maxH-300, 150, 50, Color.GREEN, true, 2));
		level.add(new TimedBlock(4200, maxH-400, 100, 50, Color.GREEN, true, 2));
		
		
		level.add(new Life(700,maxH-40-60, 60, 50, Color.RED));
		level.add(new Pipe(700, maxH-40-570, 80, 50, Color.GREEN));
		level.add(new EnergyDrink(1900, maxH-40-100, 30, 60, Color.YELLOW));
		level.add(new Life(5230,100, 40, 40, Color.RED));
		level.add(new Pipe(3200, maxH-40-360, 80, 50, Color.GREEN));
		level.add(new EnergyDrink(4250, maxH-400-100, 30, 60, Color.YELLOW));
		level.add(new Enemy(800, maxH-40-100, 80, 80, 2, false));
		level.add(new Enemy(1900, maxH-40-100, 60, 60, 2, false));
		level.add(new Enemy(3700, maxH-100-100, 40, 40, 2, false));
		level.add(new Enemy(2600, maxH-300-100, 60, 60, 2, false));
		level.add(new Enemy(4650, maxH-40-100, 60, 60, 2, false));
		level.add(new Enemy(3300, maxH-400-100, 60, 60, 2, false));
		level.add(new Enemy(1700, maxH-400-100, 40, 40, 2, false));
		level.add(new StaticEnemy(2500, maxH-600, 100, 100));
		
		//boss
		level.add(new Block(6000, maxH-40-1000, 100, 800, Color.GREEN));
		
		level.addTrigger(new Trigger(6100, maxH-40-200, 100, 200, Color.GREEN,ObjectType.BLOCKTRIGGER));
		
		level.add(new Block(6000, 0, 1400, 100, Color.GREEN));
		level.add(new Block(7000, 0, 100, maxH, Color.GREEN));
		
		level.add(new Boss(6800, maxH-50-300, 200, 200));
		
		level.addTrigger(new Trigger(5550, 0, 100, maxH-500, Color.GREEN,ObjectType.QUESTTRIGGER));
		level.addTrigger(new Trigger(2200, 0, 100, maxH-500, Color.GREEN,ObjectType.QUESTTRIGGER));
		
		levelList.add(level);
	}
	
	public void initLevel3() {
		//tiffy
		String victory = "Du hast es geschafft, die Leber ist wieder gesund!";
		level = new Level(new Point(400, 400), victory, "leber");
		

		level.add(new StaticEnemy(20, maxH-40-820, 100, 100));
		level.add(new EnergyDrink(200, maxH-40-400, 30, 70, Color.YELLOW));
		level.add(new Block(0, maxH-40, 2000, 40, Color.GREEN));
		level.add(new Block(300, maxH-300, 400, 50, Color.BLUE));
		level.add(new Block(0, maxH-40-720, 100, 720, Color.GREEN));
		
		level.addTrigger(new Trigger(1050, 0, 100, maxH, Color.GREEN,ObjectType.QUESTTRIGGER));
		level.add(new Block(1000, maxH-40-300-101, 100, 300, Color.YELLOW));
		level.add(new Pipe(700, maxH-40-50, 80, 40, Color.GREEN));
		level.add(new Block(1400, maxH-280-40, 100, 280, Color.RED));
		level.add(new Pipe(1680, maxH-280-300, 80, 40, Color.GREEN));
		
		level.add(new Life(500, maxH-40-440, 30, 30, Color.RED));
		level.add(new Enemy(1200, maxH-40-100, 80, 80, 2, false));
		
		level.addTrigger(new Trigger(1700, 0, 100, maxH, Color.GREEN,ObjectType.QUESTTRIGGER));
		
		level.add(new TimedBlock(2050, maxH - 280, 75, 30, Color.BLACK, true, 2));
		level.add(new Life(2265, maxH-280-60, 30, 30, Color.RED));
		level.add(new TimedBlock(2250, maxH-280-20, 75, 30, Color.BLACK, false,1));
		level.add(new TimedBlock(2500, maxH-280-40, 75, 30, Color.BLACK, true, 2));
	
		
		level.add(new Block(2600, maxH-50, 1500, 50, Color.GREEN));
		level.add(new Block(2800, maxH-50-400, 80, 400, Color.WHITE));
		level.add(new Enemy(2850, maxH-50-350, 80, 80, 2, false));
		level.add(new Enemy(2850, maxH-50-100, 80, 80, 2, false));
		
		level.add(new Block(3500, maxH-50-200, 100, 200, Color.ORANGE));
		level.add(new Block(3600, maxH-50-400, 350, 100, Color.BLUE));
		level.add(new Block(3950, maxH-50-400, 50, 300, Color.BLUE));
		level.addTrigger(new Trigger(4000, 0, 100, maxH, Color.GREEN,ObjectType.QUESTTRIGGER));
		level.add(new Life(3700, maxH-40-300, 30, 30, Color.RED));
		
		level.add(new Block(4300, maxH-280-20, 400, 100, Color.BLACK));
		level.add(new Block(4600, maxH-500, 300, 100, Color.YELLOW));
		level.add(new Life(4650, maxH-280-20-40, 30, 30, Color.RED));
		
		level.add(new Block(5200, maxH-280-50, 100, 280, Color.BLUE));
		level.add(new Block(5750, maxH-280-50, 50, 50, Color.BLACK));
		level.add(new Life(5755, maxH-260, 30, 30, Color.RED));
		level.add(new Block(5200, maxH-50, 1000, 50, Color.GREEN));
		level.add(new Block(6000, maxH-450-50, 100, 450, Color.BLUE));
		level.add(new Enemy(5310, maxH-50-100, 80, 80,2, false));
		level.add(new Enemy(5750, maxH-50-100, 80, 80,2, true));
		
		level.add(new Block(6500, maxH-400, 200, 50, Color.BLUE));
		level.add(new EnergyDrink(6600, maxH-50-200, 30, 70, Color.YELLOW));
		level.add(new Block(6450, maxH-50, 500, 50, Color.GREEN));
		level.add(new Block(6800, maxH-320, 50, 50, Color.WHITE));
		
		level.add(new Block(7200, maxH-450, 250, 50, Color.RED));
		level.add(new Pipe(7300, maxH-450-50, 80, 40, Color.GREEN));
		
		level.add(new Block(7500, maxH-50, 600, 50, Color.GREEN));
		
		level.add(new StaticEnemy(7700, maxH-50-200, 200, 200));
		level.addTrigger(new Trigger(7900, 0, 100, maxH, Color.GREEN,ObjectType.QUESTTRIGGER));
		
		level.add(new Block(8000, maxH-50-100, 50, 100, Color.YELLOW));
		level.add(new Block(8000, maxH-50-1000, 50, 500, Color.YELLOW));
		
		level.add(new Block(8200, maxH-100, 200, 50, Color.BLUE));
		level.add(new Life(8300, maxH-170, 30, 30, Color.RED));
		level.add(new Block(8600, maxH-300, 100, 50, Color.ORANGE));	
		level.add(new Enemy(8850, maxH-300, 80, 80,2, false));
		level.add(new Block(8850, maxH-500, 50, 50, Color.WHITE));
		level.add(new Life(8900, maxH-800, 30, 30, Color.RED));
		
		level.add(new Block(9100, maxH-80, 1200, 80, Color.GREEN));
		level.add(new Block(9400, maxH-80-100, 50, 100, Color.BLACK));
		level.add(new EnergyDrink(9200, maxH-80-200, 30, 70, Color.YELLOW));
		level.add(new Block(10000, maxH-80-300, 100, 300, Color.CYAN));
		level.add(new Pipe(10020, maxH-80-400, 80, 40, Color.GREEN));
		level.add(new Enemy(9600, maxH-80-200, 80, 80,2, false));
		
		level.addTrigger(new Trigger(10050, 0, 100, maxH, Color.GREEN,ObjectType.QUESTTRIGGER));
		
		level.add(new Block(10110, maxH-50, 1500, 50, Color.GREEN));
		level.add(new Block(10250, maxH-50-300, 200, 80, Color.MAGENTA));
		level.add(new Life(10300, maxH-50-60, 30, 30, Color.RED));
		level.add(new Block(10450, maxH-50, 80, 230, Color.GREEN));
		
		level.add(new TimedBlock(10650, maxH-500, 400, 50, Color.GREEN, true, 2));
		level.add(new Pipe(10750, maxH-500-100, 80, 40, Color.GREEN));
		level.add(new Block(11000, maxH-50-200, 150, 50, Color.GRAY));
		
		level.add(new TimedBlock(11350, maxH-30, 400, 30, Color.GREEN, true, 2));
		level.add(new Block(11750, maxH-50, 1250, 50, Color.GREEN));
		//boss
				
				
				level.add(new Block(12000, maxH-40-1000, 100, 800, Color.GREEN));
				
				level.addTrigger(new Trigger(12100, maxH-40-200, 100, 200, Color.GREEN,ObjectType.BLOCKTRIGGER));
				
				level.add(new Block(12000, 0, 1400, 100, Color.GREEN));
				level.add(new Block(13000, 0, 100, maxH, Color.GREEN));
				
				level.add(new Boss(12800, maxH-50-300, 200, 200));
				
				
		
		levelList.add(level);
	}
	
	public void initLevel4() {
		//marc
		String victory = "Du hast es geschafft, der Darm ist wieder gesund!";
		level = new Level(new Point(400, 0), victory, "darm");
		//boden
		
		level.add(new TimedBlock(700, maxH-40, 1300, 40, Color.GREEN, false, 2));
		level.add(new Block(0, maxH-40, 900, 40, Color.GREEN));
		level.add(new Block(2000, maxH-40, 1100, 40, Color.GREEN));
		level.add(new Block(3100, maxH-40-60-300, 200, 60, Color.GREEN));
		level.add(new Block(3100, maxH-40-50-250, 50, 50, Color.GREEN));
		level.add(new Block(3300, maxH-40-120-300, 200, 60, Color.GREEN));
		level.add(new Block(3500, maxH-40-180-300, 200, 120, Color.GREEN));
		level.add(new Block(3700, maxH-40-240-300, 200, 60, Color.GREEN));
		level.add(new Block(3900, maxH-40-300-300, 200, 60, Color.GREEN));
		level.add(new Block(3100, maxH-40, 1000, 40, Color.GREEN));
		level.add(new Block(3100, maxH-40-60-300, 600, 60, Color.GREEN));
		level.add(new TimedBlock(4100, maxH-40-240-300, 200, 60, Color.GREEN, false, 1));
		level.add(new TimedBlock(4500, maxH-40-240-300, 600, 60, Color.GREEN, false, 1));
		level.add(new Block(4300, maxH-40-240-300, 200, 60, Color.GREEN));
		level.add(new Block(4500, maxH, 400, 60, Color.GREEN));
		level.add(new Block(5100, maxH-250, 120, 40, Color.RED));
		level.add(new Block(4900, maxH-40-240-300, 200, 60, Color.GREEN));
		level.add(new Block(5500, maxH-40-240-300, 200, 60, Color.GREEN));
		level.add(new Block(5900, maxH-40-240-550, 150, 70, Color.GREEN));
		level.add(new Block(6500, maxH-40, 1500, 40, Color.GREEN));
		
		//wand
		level.add(new Block(0, 0, 100, maxH, Color.GREEN));
		level.add(new Block(2000, maxH-40-300, 100, 300, Color.GREEN));
		level.add(new Block(3000, maxH-40-300, 100, 300, Color.GREEN));
		level.add(new TimedBlock(3660, maxH-40-300, 120, 300, Color.RED, false, 3));
		
		//bl�cke
		level.add(new Block(300, maxH-40-260,300, 40, Color.BLUE));
		level.add(new Block(1150, maxH-40-260,200, 40, Color.GREEN));
		level.add(new Block(1600, maxH-40-460,60, 40, Color.GREEN));
		level.add(new TimedBlock(1660, maxH-40-460, 80, 40, Color.GREEN, true, 1));
		level.add(new TimedBlock(1660, maxH-40, 80, 40, Color.GREEN, true, 2));
		level.add(new Block(1740, maxH-40-460,60, 40, Color.GREEN));
		level.add(new Block(4100, maxH-300,100, 300, Color.GREEN));
		level.add(new Block(6900, 0-100,100, 100, Color.GREEN));
		
		//items
		level.add(new Life(3300, maxH-40-70, 50, 50, Color.RED));
		level.add(new Pipe(3150, maxH-40-70, 80, 60, Color.GREEN));
		level.add(new Life(1680, maxH-40-70, 50, 50, Color.RED));
		level.add(new EnergyDrink(400, maxH-40-85, 25, 60, Color.YELLOW));
		level.add(new EnergyDrink(4700, maxH-65, 25, 60, Color.YELLOW));
		
		//enemys
		level.add(new Enemy(110, maxH-40-80, 80, 80, 3, false));
		level.add(new Enemy(1900, maxH-40-350, 80, 80, 3, false));
		level.add(new StaticEnemy(2500, maxH-40-860,70, 70));
		level.add(new StaticEnemy(3150, maxH-40-50-250, 50, 50));
		level.add(new Enemy(2500, maxH-40-85, 80, 80, 3, false));
		level.add(new Enemy(2300, maxH-40-350, 100, 100, 3, false));
		level.add(new Enemy(3760, maxH-40-300, 100, 100, 3, true));
		level.add(new StaticEnemy(4200, maxH-100,100, 100));
		level.add(new StaticEnemy(6000, maxH-200,200, 200));
		level.add(new Enemy(6900, maxH-40-100,100, 100, 5, true));
		level.add(new StaticEnemy(6950, maxH-40-500,50, 50));
		
		//questions
		level.addTrigger(new Trigger(4700, 0, 100, maxH, Color.GREEN,ObjectType.QUESTTRIGGER));
		level.addTrigger(new Trigger(7000, 0, 100, maxH, Color.GREEN,ObjectType.QUESTTRIGGER));
		//boss
				level.add(new Block(7000, maxH-40-1000, 100, 800, Color.GREEN));
				
				level.addTrigger(new Trigger(7100, maxH-40-200, 100, 200, Color.GREEN,ObjectType.BLOCKTRIGGER));
				
				level.add(new Block(7000, 0, 1400, 100, Color.GREEN));
				level.add(new Block(8000, 0, 100, maxH, Color.GREEN));
				
				level.add(new Boss(7800, maxH-50-300, 200, 200));
				
				level.add(new TimedBlock(7400, maxH-40-300, 50, 20, Color.YELLOW, true, 2));
				
				
		levelList.add(level);
		
	}

	
	public Level getLevel(int num) {
		if(num >= levelList.size())
			return levelList.get(levelList.size()-1);
		else
			return levelList.get(num);
	}

	public ArrayList<Level> getLevelList() {
		return levelList;
	}
	
	public void resetLevels() {
		levelList.clear();
		initLevel1();
		initLevel2();
		initLevel3();
		initLevel4();
	}
}
