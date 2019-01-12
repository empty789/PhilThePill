package controller;

import java.awt.Font;
import java.awt.FontFormatException;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;

import javax.imageio.ImageIO;

import objects.Object;
import objects.ObjectType;
import objects.entities.Enemy;
import objects.entities.StaticEnemy;


public class ResourceManager {

	public LevelManager lm;
	public BufferedImage menu, manual, pause,victory, complete, gameover, heart, head, liver, stom, overview,
							aright, awrong, quiz, teacher, energy, life, pipe, boss1, boss1_shoot, enemy1_r, enemy1_l, enemy2_r, enemy2_l, enemy3_r, enemy3_l, bubble;
	public ArrayList<BufferedImage> bgList;
	
	public ResourceManager(LevelManager lm) throws IOException {
		this.lm = lm;
		

		loadImages();
	}
	
	
	private void loadImages() throws IOException {
		//level
		 bgList = new ArrayList<BufferedImage>();
		bgList.add(ImageIO.read(ResourceManager.class.getResource("/image/bg/level/background.png")));
		bgList.add(ImageIO.read(ResourceManager.class.getResource("/image/bg/level/background2.png")));
		bgList.add(ImageIO.read(ResourceManager.class.getResource("/image/bg/level/background3.png")));
		bgList.add(ImageIO.read(ResourceManager.class.getResource("/image/bg/level/background4.png")));
		
		//bg
		victory = ImageIO.read(ResourceManager.class.getResource("/image/bg/victory.png"));
		gameover = ImageIO.read(ResourceManager.class.getResource("/image/bg/gameover.png"));
		complete = ImageIO.read(ResourceManager.class.getResource("/image/bg/complete.png"));
		
		//menu
		menu = ImageIO.read(ResourceManager.class.getResource("/image/bg/menu/menu.png"));
		manual = ImageIO.read(ResourceManager.class.getResource("/image/bg/menu/manual.png"));
		pause = ImageIO.read(ResourceManager.class.getResource("/image/bg/menu/pause.png"));
		
		//overview
		overview = ImageIO.read(ResourceManager.class.getResource("/image/bg/overview/overview.png"));
		heart = ImageIO.read(ResourceManager.class.getResource("/image/bg/overview/heart.png"));
		head = ImageIO.read(ResourceManager.class.getResource("/image/bg/overview/head.png"));
		liver = ImageIO.read(ResourceManager.class.getResource("/image/bg/overview/liver.png"));
		stom = ImageIO.read(ResourceManager.class.getResource("/image/bg/overview/stom.png"));
		
		//quiz
		awrong = ImageIO.read(ResourceManager.class.getResource("/image/bg/quiz/awrong.png"));
		aright = ImageIO.read(ResourceManager.class.getResource("/image/bg/quiz/aright.png"));
		quiz = ImageIO.read(ResourceManager.class.getResource("/image/bg/quiz/quiz.png"));
		teacher = ImageIO.read(ResourceManager.class.getResource("/image/bg/quiz/teacher.png"));
		
		//items
		energy = ImageIO.read(ResourceManager.class.getResource("/image/items/energy.png"));
		life = ImageIO.read(ResourceManager.class.getResource("/image/items/life.png"));
		pipe = ImageIO.read(ResourceManager.class.getResource("/image/items/pipe.png"));
		
		//boss
		boss1 = ImageIO.read(ResourceManager.class.getResource("/image/boss/boss1.png"));
		boss1_shoot = ImageIO.read(ResourceManager.class.getResource("/image/boss/boss1_shoot.png"));
		
		//enemy
		enemy1_r = ImageIO.read(ResourceManager.class.getResource("/image/enemy/enemy1_r.png"));
		enemy1_l = ImageIO.read(ResourceManager.class.getResource("/image/enemy/enemy1_l.png"));
		enemy2_r = ImageIO.read(ResourceManager.class.getResource("/image/enemy/enemy2_r.png"));
		enemy2_l = ImageIO.read(ResourceManager.class.getResource("/image/enemy/enemy2_l.png"));
		enemy3_r = ImageIO.read(ResourceManager.class.getResource("/image/enemy/enemy3_r.png"));
		enemy3_l = ImageIO.read(ResourceManager.class.getResource("/image/enemy/enemy3_l.png"));
		
		//misc
		bubble = ImageIO.read(ResourceManager.class.getResource("/image/misc/bubble.png"));
		loadItemImages();
	}
	
	public void loadItemImages() {
		for(int i = 0; i < lm.getLevelList().size(); i++) {
			ArrayList<Object> obj = lm.getLevel(i).getObjects();
			//bg
			lm.getLevel(i).setBg(bgList.get(i));
			
			//items
			for(int j = 0; j < obj.size(); j++) {
				if(obj.get(j).getType() == ObjectType.LIFE) {
					obj.get(j).setImage(life);
				}else if(obj.get(j).getType() == ObjectType.PIPE) {
					obj.get(j).setImage(pipe);
				}else if(obj.get(j).getType() == ObjectType.ENERGYDRINK) {
					obj.get(j).setImage(energy);
				}else if(obj.get(j).getType() == ObjectType.ENTITY) {
					Enemy e = (Enemy)obj.get(j);
					e.setImage(enemy1_r,enemy1_l);
				}else if(obj.get(j).getType() == ObjectType.ENTITYSTATIC) {
					StaticEnemy e = (StaticEnemy)obj.get(j);
					e.setImage(enemy3_r,enemy3_l);
				}
			}
			
			//boss
			lm.getLevel(i).getBoss().setImage(boss1, boss1_shoot);
			
			
		}
		
	}
}
