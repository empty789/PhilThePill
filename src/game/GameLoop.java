package game;

import java.awt.Color;
import java.awt.Point;
import java.util.ArrayList;

import controller.LevelManager;
import objects.ObjectType;
import objects.entities.Boss;
import objects.entities.Enemy;
import objects.entities.Player;
import objects.level.Level;
import objects.misc.AdvancedBullet;
import objects.misc.Trigger;
import objects.obstacles.Block;
import view.Camera;
import view.GamePanel;
import view.MainWindow;

public class GameLoop implements Runnable{
	
	private boolean running;
	
	private MainWindow mw;
	private GamePanel gPanel;
	private Player player;
	private Camera cam;
	private LevelManager lm;
	private Level level;
	
	public GameLoop  (MainWindow mw, GamePanel gPanel, Player player, Camera cam, LevelManager lm) {
		this.mw = mw;
		this.gPanel = gPanel;
		this.player = player;
		this.cam = cam;
		this.lm = lm;
		start();
	  }
	
	private void start() {
        if(running) return;
        running = true;
        new Thread(this, "SidescrollerMain-Thread").start();
    }

    private void stop() {
        if(!running) return;
        running = false;
    }

    private void tick() {
    	//System.out.println(gPanel.getState());
    	//main tick method while game is running
    	level = gPanel.getLevel();
    	
    	if(gPanel.getState() == GameState.RUNNING) {
			player.tick(level.getObjects());
			player.move();
	    	cam.tick(player);
	    	
	    	Level lvl = gPanel.getLevel();
			ArrayList<Enemy> enemys = lvl.getEnemys();
			ArrayList<Trigger> trigger = lvl.getTriggerList();
			Boss b = lvl.getBoss();
			
			//enemys
			for(int i = 0; i < enemys.size(); i++) {
				enemys.get(i).move();
				enemys.get(i).tick(level.getObjects());
			}
			
			//trigger
			for(int i = 0; i < trigger.size(); i++) {
				Trigger tr = trigger.get(i);
				if(tr.isAlive()) {
					if(player.getBox().intersects(tr.getBounds())) {
						if(tr.getType() == ObjectType.QUESTTRIGGER) {
							gPanel.setNextQuestion();
							gPanel.setState(GameState.MINIGAME);
							tr.setAlive(false);
						}else if(tr.getType() == ObjectType.BLOCKTRIGGER) {
							Block block = new Block(tr.getX()-tr.getWidth(), tr.getY(), tr.getWidth(), tr.getHeight(), tr.getColor());
							level.add(block);
							if(player.getBounds().intersects(block.getBounds())) {
								player.setX(player.getX()+block.getWidth()/2);
							}
							level.addTrigger(new Trigger(tr.getX()-tr.getWidth()*2, tr.getY()+tr.getHeight()-10, tr.getWidth(), 10, Color.RED, ObjectType.TELEPORTTRIGGER, new Point(tr.getX()+tr.getWidth()/2, tr.getY())));
							tr.setAlive(false);
						}else if(tr.getType() == ObjectType.TELEPORTTRIGGER) {
							player.setPosition(tr.getTpPoint());
						}
					}
				}
			}
			
			//boss
			b.move();
			b.tick(level.getObjects(), player);
			ArrayList<AdvancedBullet> bulletList = b.getBulletList();
			for(int i = 0; i < bulletList.size(); i++) {
				bulletList.get(i).move();
			}
			
			if(b.getHitpoints() <= 0) {
				b.setAlive(true);
				gPanel.setState(GameState.VICTORY);
			}
			
		}
		
    	//world collision check
    	if(player.getY() >= mw.getMaxHeight()+100) {
			if(player.getLifes() >= 1) {
				
				player.respawn();
				player.setLifes(player.getLifes()-1);
			}
			
		}
    	
    	//gameover check
		if(player.getLifes() <=0) {
			gPanel.setState(GameState.GAMEOVER);
		}
		
		//wincondition
		if(gPanel.getCurrentLevel() > lm.getLevelList().size()) {
			gPanel.setState(GameState.GAMEWON);
		}
    }
    
    @Override
    public void run() {
        double target = 60.0;
        double nsPerTick = 1000000000.0 / target;
        long lastTime = System.nanoTime();
        long timer = System.currentTimeMillis();
        double unprocessed = 0.0;
        int fps = 0;
        int tps = 0;
        boolean canRender = false;
        
        while (running) {
            long now = System.nanoTime();
            unprocessed += (now - lastTime) / nsPerTick;
            lastTime = now;
            
            if(unprocessed >= 1.0){
            	tick();
                unprocessed--;
                tps++;
                canRender = true;
            }else {
            	canRender = false;
            }
            
            try {
                Thread.sleep(1);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            
            if(canRender){
                //render
            	draw();
                fps++;
            }
            
            if(System.currentTimeMillis() - 1000 > timer){
                timer += 1000;
                //System.out.printf("FPS: %d | TPS: %d\n", fps, tps);
                fps = 0;
                tps = 0;
            }
            
        }
        
        System.exit(0);
    }
	
	  public void SetLevel(Level level) {
		  this.level = level;
	  }
	  
	  public void draw() {
		  gPanel.resetButtons();
    	  gPanel.draw();
	  }
}
