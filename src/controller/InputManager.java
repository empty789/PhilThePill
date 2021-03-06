package controller;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.util.ArrayList;

import javax.swing.event.MouseInputListener;

import game.GameState;
import objects.entities.Player;
import view.GamePanel;
import view.LevelItem;
import view.MenuItem;

public class InputManager implements KeyListener, MouseInputListener{

	private Player player;
	private GamePanel gPanel;
	private LevelManager lvlManager;
	private int keys;
	
	public InputManager(Player player, GamePanel gPanel, LevelManager lvlManager) {
		this.player = player;
		this.gPanel = gPanel;
		this.lvlManager = lvlManager;
		keys = 0;
	}
	
	public void click(MouseEvent e) {
		ArrayList<MenuItem> items = gPanel.getMenu();
		ArrayList<MenuItem> answers = gPanel.getAnswerList();
		ArrayList<LevelItem> lvls = gPanel.getLvlMenuList();
		int mX = e.getX();
		int mY = e.getY();
		
		if(gPanel.getState() == GameState.MENU || gPanel.getState() == GameState.PAUSE || gPanel.getState() == GameState.VICTORY || gPanel.getState() == GameState.ARIGHT || gPanel.getState() == GameState.AWRONG || gPanel.getState() == GameState.MINIGAME || gPanel.getState() == GameState.GAMEOVER || gPanel.getState() == GameState.MANUAL|| gPanel.getState() == GameState.GAMEWON) {
			for(int i = 0; i < items.size(); i++) {
				if(items.get(i).getBounds().contains(mX, mY) && items.get(i).isVisible() == true) {
					if(items.get(i).getAction().equals("STARTBUTTON")) {
						gPanel.setState(GameState.LEVELOVERVIEW);
						player.restart();
					}else if(items.get(i).getAction().equals("EXITBUTTON")) {
						System.exit(0);
					}else if(items.get(i).getAction().equals("REPLAYBUTTON")) {
						gPanel.setState(GameState.MENU);
						gPanel.restartGame();
					}else if(items.get(i).getAction().equals("MANUALBUTTON")) {
						gPanel.setState(GameState.MANUAL);
					}else if(items.get(i).getAction().equals("VICTORYBUTTON")) {
						gPanel.setCurrentLevel(gPanel.getCurrentLevel()+1);
						gPanel.setState(GameState.LEVELOVERVIEW);
						keys = 0;
					}else if(items.get(i).getAction().equals("PIPEJOKER")) {
						gPanel.ShowHint();
					}else if(items.get(i).getAction().equals("RIGHTBUTTON") || items.get(i).getAction().equals("WRONGBUTTON")) {
						gPanel.setShowHint(false);
						gPanel.resetAnswerColor();
						player.resetMovement();
						keys = 0;
						gPanel.setState(GameState.RUNNING);
						if(items.get(i).getAction().equals("WRONGBUTTON"))
							
							player.setLifes(player.getLifes()-1);
							
							
							
					}else if(items.get(i).getAction().equals("MANUALBACKBUTTON")) {
						gPanel.setState(gPanel.getLastState());
					}
	
				}
			}
		}
		
		if(gPanel.getState() == GameState.MINIGAME) {
			for(int i = 0; i < answers.size(); i++) {
				if(answers.get(i).getBounds().contains(mX, mY) && answers.get(i).isVisible() == true) {
					if(answers.get(i).getAction().equals("RIGHT")) {
						gPanel.setState(GameState.ARIGHT);
					}else if(answers.get(i).getAction().equals("WRONG")) {
						gPanel.setState(GameState.AWRONG);
					}
				}
			}
		}
		
		if(gPanel.getState() == GameState.LEVELOVERVIEW) {
			for(int i = 0; i < lvls.size(); i++) {
				if(lvls.get(i).getBounds().contains(mX, mY) && lvls.get(i).isVisible() == true) {
					if(lvls.get(i).getAction().equals("KOPF") && lvls.get(i).isActive()) {
						gPanel.setState(GameState.RUNNING);
						//gPanel.setCurrentLevel(0);
					}else if(lvls.get(i).getAction().equals("HERZ") && lvls.get(i).isActive()) {
						gPanel.setState(GameState.RUNNING);
						//gPanel.setCurrentLevel(1);
					}
					else if(lvls.get(i).getAction().equals("LEBER") && lvls.get(i).isActive()) {
						gPanel.setState(GameState.RUNNING);
						//gPanel.setCurrentLevel(2);
					}
					else if(lvls.get(i).getAction().equals("DARM") && lvls.get(i).isActive()) {
						gPanel.setState(GameState.RUNNING);
						//gPanel.setCurrentLevel(3);
					}
				}
			}
		}
	}
	
	
	@Override
	public void mouseClicked(MouseEvent e) {	
		
		click(e);
		
	}

	@Override
	public void mouseEntered(MouseEvent e) {
	}

	@Override
	public void mouseExited(MouseEvent e) {
	}

	@Override
	public void mousePressed(MouseEvent e) {
		click(e);
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		click(e);
	}

	@Override
	public void mouseDragged(MouseEvent e) {
		click(e);
	}

	@Override
	public void mouseMoved(MouseEvent e) {
		ArrayList<MenuItem> items = gPanel.getMenu();
		ArrayList<MenuItem> answers = gPanel.getAnswerList();
		ArrayList<LevelItem> lvls = gPanel.getLvlMenuList();
		
		int mX = e.getX();
		int mY = e.getY();

		for(int i = 0; i < items.size(); i++) {
			if(items.get(i).getBounds().contains(mX, mY) ) {
				items.get(i).setHover(true);
			}else {
				items.get(i).setHover(false);
			}
		}
	
		for(int i = 0; i < answers.size(); i++) {
			if(answers.get(i).getBounds().contains(mX, mY) ) {
				answers.get(i).setHover(true);
			}else {
				answers.get(i).setHover(false);
			}
		}
		
		for(int i = 0; i < lvls.size(); i++) {
			if(lvls.get(i).getBounds().contains(mX, mY) ) {
				lvls.get(i).setHover(true);
			}else {
				lvls.get(i).setHover(false);
			}
		}
		
	}


	@Override
	public void keyPressed(KeyEvent e) {

		if(gPanel.getState() == GameState.RUNNING) {

			if(e.getKeyCode() == 39) {
				if(!player.Right()) {
					keys++;
				}
				player.setLeft(false);
				player.setRight(true);
				
				
			}
			if(e.getKeyCode() == 37) {
				if(!player.Left()) {
					keys++;
				}
				player.setRight(false);
				player.setLeft(true);
			}
			if(e.getKeyCode() == 38) {
				player.setUp(true);
			}
			if(e.getKeyCode() == 32) {
				player.shoot();
			}
			
		}
		if(e.getKeyCode() == 27 && gPanel.getState() != GameState.PAUSE && gPanel.getState() != GameState.MENU && gPanel.getState() != GameState.MANUAL) {
			gPanel.setState(GameState.PAUSE);
		}else if(e.getKeyCode() == 27 && gPanel.getState() == GameState.PAUSE){
			gPanel.setState(gPanel.getLastState());
		}
	}

	@Override
	public void keyReleased(KeyEvent e) {
		if(gPanel.getState() == GameState.RUNNING) {

			if(e.getKeyCode() == 39) {
				player.setRight(false);
				keys--;
				if(keys != 0) {
					player.setLeft(true);
				}
			}
			if(e.getKeyCode() == 37) {
				player.setLeft(false);
				keys--;
				if(keys != 0) {
					player.setRight(true);
				}
			}
			if(e.getKeyCode() == 38) {
				player.setUp(false);
			}
		}
	}

	@Override
	public void keyTyped(KeyEvent e) {

		
	}

}
