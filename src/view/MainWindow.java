package view;

import java.awt.Color;
import java.util.ArrayList;

import javax.swing.JFrame;

import controller.InputManager;
import controller.LevelManager;
import game.GameLoop;
import objects.Object;
import objects.entities.Player;
import objects.obstacles.Block;

public class MainWindow extends JFrame{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	public static final String title = "Phil the Pill";
	public static final int WIDTH = 1280;
	public static final int HEIGHT = 720;
	
	private int maxWidth;
	private int maxHeight;
	
	
	private GamePanel gPanel;
	private GameLoop gLoop;
	private Player player;
	private InputManager input;
	private Camera cam;
	private LevelManager lm;
	
	public MainWindow() {
		init();
	}
	
	
	public void init() {
		maxWidth = getToolkit().getScreenSize().width;
		maxHeight = getToolkit().getScreenSize().height;
		
		cam = new Camera(this, 0, 0);
		player = new Player();
		gPanel = new GamePanel(this, cam, player);
		
		
		
		
		
		input = new InputManager(player, gPanel);
		gPanel.setDoubleBuffered(true);
		gPanel.addMouseListener(input);
		gPanel.addMouseMotionListener(input);
		gPanel.addKeyListener(input);
		
		gLoop = new GameLoop(this, gPanel, player, cam);
		
		lm = new LevelManager(this, gLoop, gPanel);
		
		setTitle(title);
		setContentPane(gPanel);
		
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setResizable(true);
		//setLocationRelativeTo(null);
		
		gPanel.setFocusable(true);
		gPanel.requestFocus();
		setExtendedState(JFrame.MAXIMIZED_BOTH); 
		setUndecorated(true);
		setVisible(true);
		pack();
		
	}


	public int getMaxWidth() {
		return maxWidth;
	}


	public int getMaxHeight() {
		return maxHeight;
	}
	
	
}
