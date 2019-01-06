package objects.level;

import java.awt.Point;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

import objects.Object;
import objects.ObjectType;
import objects.entities.Boss;
import objects.entities.Enemy;
import objects.items.Life;
import objects.misc.Trigger;
import objects.obstacles.Block;

public class Level {


	private BufferedImage bg;
	private ArrayList<Object> objList, defaultObj;
	private ArrayList<Trigger> triggerList, defaultTrigger;
	private Point startPoint;
	private String vicMsg;
	private String topic;

	
	public Level(Point startPoint, String vicMsg, String topic) {
		objList = new ArrayList<Object>();
		defaultObj = new ArrayList<Object>();
		triggerList = new ArrayList<Trigger>();
		defaultTrigger = new ArrayList<Trigger>();
		this.startPoint = startPoint;
		this.vicMsg = vicMsg;
		this.topic = topic;
		
	}
	
	
	
	public BufferedImage getBg() {
		return bg;
	}



	public void setBg(BufferedImage bg) {
		this.bg = bg;
	}



	public Point getStartPoint() {
		return startPoint;
	}
	
	public String getTopic() {
		return topic;
	}

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
	
	public Boss getBoss() {
		Boss res = null;
		for(int i = 0; i < objList.size(); i++) {
			if(objList.get(i).getType() == ObjectType.BOSS) {
				res = (Boss) objList.get(i);
			}
		}
		return res;
	}
	
	
	

	public String getVicMsg() {
		return vicMsg;
	}

	public void resetLevel() {
		objList = defaultObj;
	}
}
