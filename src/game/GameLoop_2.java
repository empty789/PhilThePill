package game;

import java.util.ArrayList;

import objects.entities.Enemy;
import objects.entities.Player;
import objects.level.Level;
import view.Camera;
import view.GamePanel;

public class GameLoop_2 implements Runnable{
	
	private Thread thread;
	  private boolean running = false;
	  
	  public int frames;
	  public int lastFrames;
	  public int ticks;
	
	  private GamePanel gPanel;
		private Player player;
		private Camera cam;
		private Level level;
		
	  public static final int TICKSPERS = 60;
	  public static final boolean ISFRAMECAPPED = true;
	  
	  
	  public GameLoop_2(GamePanel gPanel, Player player, Camera cam) {
		  this.gPanel = gPanel;
			this.player = player;
			this.cam = cam;
			start();
	  }
	  
	
	  public void SetLevel(Level level) {
		  this.level = level;
	  }
	  
	  public void draw() {
		  gPanel.resetButtons();
		  frames++;
    	  gPanel.draw();
	  }
	  
	public void tick(){
		if(gPanel.getState() == GameState.RUNNING) {
			player.tick(level.getObjects());
			player.move();
	    	cam.tick(player);
	    	
	    	Level lvl = gPanel.getLevel();
			ArrayList<Enemy> enemys = lvl.getEnemys();
			
			for(int i = 0; i < enemys.size(); i++) {
				enemys.get(i).move();
				enemys.get(i).tick(level.getObjects());
			}
		}
		
		if(player.getLifes() <=0) {
			gPanel.setState(GameState.GAMEOVER);
		}
		
		
	  }

	  public synchronized void start(){
	      if(running) return;
	      running = true;
	      thread = new Thread(this, "Thread");
	      thread.start();
	  }

	  public synchronized void stop(){
	      if(!running) return;
	      running = false;
	      try {
	          System.exit(1);
	          thread.join();
	      } catch (InterruptedException e) {
	          e.printStackTrace();
	      }
	  }

	  public void init(){

	  }

	  public void run() {
	      init();
	      //Tick counter variable
	      long lastTime = System.nanoTime();
	      //Nanoseconds per Tick
	      double nsPerTick = 1000000000D/TICKSPERS;
	      frames = 0;
	      ticks = 0;
	      long fpsTimer = System.currentTimeMillis();
	      double delta = 0;
	      boolean shouldRender;
	      while(running){
	          shouldRender = !ISFRAMECAPPED;
	          long now = System.nanoTime();
	          delta += (now - lastTime) / nsPerTick;
	          lastTime = now;
	          //if it should tick it does this
	          while(delta >= 1 ){
	              ticks++;
	              tick();
	              delta -= 1;
	              shouldRender = true;
	          }
	          if (shouldRender){
	        	  draw();
	          }
	          if (fpsTimer < System.currentTimeMillis() - 1000){
	              System.out.println(ticks +" ticks, "+ frames+ " frames");
	              ticks = 0;
	              lastFrames = frames;
	              frames = 0;
	              fpsTimer = System.currentTimeMillis();
	          }
	      }
	  }
}
