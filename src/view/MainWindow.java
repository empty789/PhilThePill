package view;

import java.awt.Color;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;

import javax.swing.JFrame;

import controller.InputManager;
import controller.LevelManager;
import controller.QuestionManager;
import controller.ResourceManager;
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

	
	private int maxWidth;
	private int maxHeight;
	
	
	private GamePanel gPanel;
	private GameLoop gLoop;
	private Player player;
	private InputManager input;
	private Camera cam;
	private LevelManager lm;
	private QuestionManager qm;
	private ResourceManager resM;
	
	public MainWindow() {
		init();
	}
	
	
	public void init() {
		maxWidth = getToolkit().getScreenSize().width;
		maxHeight = getToolkit().getScreenSize().height;
		
		cam = new Camera(this, 0, 0);
		player = new Player();
		try {
			qm = new QuestionManager();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		lm = new LevelManager(this);
		try {
			resM = new ResourceManager(lm);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		gPanel = new GamePanel(this, cam, player, qm, lm, resM);
		
		gLoop = new GameLoop(this, gPanel, player, cam, lm);
		
		
		

		
		input = new InputManager(player, gPanel, lm);
		gPanel.setDoubleBuffered(true);
		gPanel.addMouseListener(input);
		gPanel.addMouseMotionListener(input);
		gPanel.addKeyListener(input);
		
		
		
		
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
