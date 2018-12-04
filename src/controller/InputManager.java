package controller;

import java.awt.Rectangle;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.util.ArrayList;

import javax.swing.event.MouseInputListener;

import game.GameState;
import objects.entities.Player;
import view.GamePanel;
import view.MenuItem;

public class InputManager implements KeyListener, MouseInputListener{

	private Player player;
	private GamePanel gPanel;
	private int keys;
	
	public InputManager(Player player, GamePanel gPanel) {
		this.player = player;
		this.gPanel = gPanel;
		keys = 0;
	}
	
	@Override
	public void mouseClicked(MouseEvent e) {	
		
		
		
	}

	@Override
	public void mouseEntered(MouseEvent e) {
	}

	@Override
	public void mouseExited(MouseEvent e) {
	}

	@Override
	public void mousePressed(MouseEvent e) {
		ArrayList<MenuItem> items = gPanel.getMenu();
		int mX = e.getX();
		int mY = e.getY();
		
		for(int i = 0; i < items.size(); i++) {
			if(items.get(i).getBounds().contains(mX, mY) && items.get(i).isVisible() == true) {
				if(items.get(i).getAction().equals("START")) {
					gPanel.setState(GameState.RUNNING);
				}else if(items.get(i).getAction().equals("EXIT")) {
					System.exit(0);
				}else if(items.get(i).getAction().equals("REPLAY")) {
					player.restart();
					gPanel.getLevel().resetLevel();
					gPanel.setState(GameState.MENU);
				}

			
			}
		}
	}

	@Override
	public void mouseReleased(MouseEvent e) {
	}

	@Override
	public void mouseDragged(MouseEvent arg0) {
	}

	@Override
	public void mouseMoved(MouseEvent e) {
		ArrayList<MenuItem> items = gPanel.getMenu();
		int mX = e.getX();
		int mY = e.getY();
		
		for(int i = 0; i < items.size(); i++) {
			if(items.get(i).getBounds().contains(mX, mY)) {
				items.get(i).setHover(true);
			}else {
				items.get(i).setHover(false);
			}
		}
	}


	@Override
	public void keyPressed(KeyEvent e) {
		//System.out.println(e.getKeyCode());
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
		if(e.getKeyCode() == 27 && gPanel.getState() != GameState.MENU) {
			gPanel.setState(GameState.MENU);
		}else if(e.getKeyCode() == 27 && gPanel.getState() == GameState.MENU){
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
