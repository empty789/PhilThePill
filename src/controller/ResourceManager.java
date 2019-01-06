package controller;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.ArrayList;

import javax.imageio.ImageIO;

import objects.Object;
import objects.ObjectType;
import objects.entities.Player;

public class ResourceManager {

	public LevelManager lm;
	public BufferedImage menu, manual, pause,victory, complete, gameover, heart, head, liver, stom, overview,
							aright, awrong, quiz, teacher, energy, life, pipe;
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
		
		loadItemImages();
	}
	
	public void loadItemImages() {
		for(int i = 0; i < lm.getLevelList().size(); i++) {
			ArrayList<Object> obj = lm.getLevel(i).getObjects();
			lm.getLevel(i).setBg(bgList.get(i));
			
			
			for(int j = 0; j < obj.size(); j++) {
				if(obj.get(j).getType() == ObjectType.LIFE) {
					obj.get(j).setImage(life);
					System.out.println("life");
				}else if(obj.get(j).getType() == ObjectType.PIPE) {
					obj.get(j).setImage(pipe);
				}else if(obj.get(j).getType() == ObjectType.ENERGYDRINK) {
					obj.get(j).setImage(energy);
				}
			}
		}
		
	}
}
