package game;

import java.util.ArrayList;

import objects.Object;
import objects.ObjectType;
import objects.entities.Enemy;
import objects.entities.Player;
import objects.level.Level;
import objects.misc.Trigger;
import view.Camera;
import view.GamePanel;
import view.MainWindow;

public class GameLoop implements Runnable{
	
	private boolean running;
	
	private MainWindow mw;
	private GamePanel gPanel;
	private Player player;
	private Camera cam;

	private Level level;
	
	public GameLoop  (MainWindow mw, GamePanel gPanel, Player player, Camera cam) {
		this.mw = mw;
		this.gPanel = gPanel;
		this.player = player;
		this.cam = cam;
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
    	if(gPanel.getState() == GameState.RUNNING) {
			player.tick(level.getObjects());
			player.move();
	    	cam.tick(player);
	    	
	    	Level lvl = gPanel.getLevel();
			ArrayList<Enemy> enemys = lvl.getEnemys();
			ArrayList<Trigger> trigger = lvl.getTriggerList();
			
			for(int i = 0; i < enemys.size(); i++) {
				enemys.get(i).move();
				enemys.get(i).tick(level.getObjects());
			}
			
			for(int i = 0; i < trigger.size(); i++) {
				Trigger tr = trigger.get(i);
				if(tr.isAlive()) {
					if(player.getBox().intersects(tr.getBounds()) && tr.getType() == ObjectType.QUESTTRIGGER) {
						gPanel.setState(GameState.MINIGAME);
						tr.setAlive(false);
					}
				}
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
                System.out.printf("FPS: %d | TPS: %d\n", fps, tps);
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
		  gPanel.resetMenu();
    	  gPanel.draw();
	  }
}
