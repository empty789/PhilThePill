package objects.level;

import java.awt.Point;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

import objects.Object;
import objects.ObjectType;
import objects.entities.Enemy;
import objects.items.Life;
import objects.misc.Trigger;
import objects.obstacles.Block;

public class Level {


	BufferedImage bg;
	ArrayList<Object> objList, defaultObj;
	ArrayList<Trigger> triggerList, defaultTrigger;
	Point startPoint;
	
	public Level(Point startPoint) {
		objList = new ArrayList<Object>();
		defaultObj = new ArrayList<Object>();
		triggerList = new ArrayList<Trigger>();
		defaultTrigger = new ArrayList<Trigger>();
		this.startPoint = startPoint;
		
	}
	
//	public void render(Graphics g) {
//		if(bg != null) {
//			g.drawImage(bg, 0, 0, MainWindow.WIDTH, MainWindow.HEIGHT, null);
//		}else {
//			g.setColor(Color.GREEN.brighter().brighter());
//			g.fillRect(0, 0, MainWindow.WIDTH, MainWindow.HEIGHT);
//			
//		}
//		
//		
//	}
	
	public void add(Object obj) {
		objList.add(obj);
		Object dObject = null;
		if(obj.getType() == ObjectType.OBSTACLE) {
			Block block = new Block(obj.getX(), obj.getY(), obj.getWidth(), obj.getHeight(), obj.getColor());
			dObject = block;
		}else if(obj.getType() == ObjectType.LIFE) {
			Life live = new Life(obj.getX(), obj.getY(), obj.getWidth(), obj.getHeight(), obj.getColor());
			dObject = live;
		}else if(obj.getType() == ObjectType.ENTITY) {
			Enemy enemy = new Enemy(obj.getX(), obj.getY(), obj.getWidth(), obj.getHeight());
			dObject = enemy;
		}
		 
		defaultObj.add(dObject);

	}
	public void addTrigger(Trigger obj) {
		triggerList.add(obj);
	}
	
	
	
	public ArrayList<Trigger> getTriggerList() {
		return triggerList;
	}

	public ArrayList<Object> getObjects(){
		return objList;
	}
	
	public ArrayList<Enemy> getEnemys(){
		ArrayList<Enemy> eList = new ArrayList<Enemy>();
		
		for(int i = 0; i < objList.size(); i++) {
			if(objList.get(i).getType() == ObjectType.ENTITY) {
				eList.add((Enemy) objList.get(i));
			}
		}
		
		return eList;
	}
	
	public void resetLevel() {
		objList = defaultObj;
	}
}
