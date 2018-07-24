package fr.ltdg;

import java.awt.Color;
import java.awt.Graphics;

import javax.swing.JFrame;
import javax.swing.JPanel;

import fr.ltdg.states.MainMenu;
import fr.ltdg.states.State.GameState;
import fr.ltdg.states.StatesHandler;

/*
 * Cette class est le coeur même du jeu
 */

public class GameFramework extends JPanel{
	
	private JFrame window;
	
	private int fps = 60;
	private int frameCount = 0; 
	
	public boolean pause = false;
	
	private float interpolation;
	
	//States
	public StatesHandler statesHandler;
	
	private MainMenu mainMenu = new MainMenu(GameState.MAIN_MENU);
	
	public GameFramework(JFrame window) {
		this.window = window;
		
		Thread game = new Thread() {
			public void run() {
				gameLoop();
			}
		};
		game.start();
	}
	
	public void initGame() {
		statesHandler = new StatesHandler();
		
		statesHandler.addState(mainMenu);
		
		statesHandler.init(this);
		
		statesHandler.setCurrentState(GameState.MAIN_MENU);
		
		window.addKeyListener(mainMenu);
		addMouseListener(mainMenu);
		
		//Vérification de l'existence potentiel d'un ancien compte
		
	}
	
	public void renderGame(float interpolation) {
		this.interpolation = interpolation;
		
		repaint();
	}
	
	//True update loop
	public void updateGame() {
		statesHandler.update(this);
	}
	
	//True render loop
	public void paintComponent(Graphics g) {
		g.setColor(getBackground());
		g.fillRect(0, 0, getWidth(), getHeight());
		
		if(Settings.RENDER_FPS) {
			g.setColor(Color.black);
			g.drawString("fps: " + fps, 0,10);
		}
		
		frameCount++;
		
		statesHandler.render(this, g, interpolation);
	}
	
	public void gameLoop() {
		
		final double GAME_HERTZ = 30.0;
		final double TIME_BETWEEN_UPDATES = 1000000000 / Settings.GAME_FPS;
		
		final double TARGET_TIME_BETWEEN_RENDERS = 1000000000 / Settings.GAME_FPS;
		
		final int MAX_UPDATES_BEFORE_RENDER = 1;
		
		double lastUpdateTime = System.nanoTime();
		
		double lastRenderTime = System.nanoTime();
		
		int lastSecondTime = (int) (lastUpdateTime / 1000000000);
		
		initGame();
		
		while(true) {
			double now = System.nanoTime();
			int updateCount = 0;
			
			if(!pause) {
				while(now - lastUpdateTime > TIME_BETWEEN_UPDATES && updateCount < MAX_UPDATES_BEFORE_RENDER) {
					//Update Game 
					updateGame();
					
					lastUpdateTime += TIME_BETWEEN_UPDATES;
					updateCount++;
				}
				
				if(now - lastUpdateTime > TIME_BETWEEN_UPDATES) {
					lastUpdateTime = now - TIME_BETWEEN_UPDATES;
				}
				
				float interpolation = Math.min(1.0f, (float) ((now - lastUpdateTime) / TIME_BETWEEN_UPDATES));
				//Render game
				renderGame(interpolation);
				
				lastRenderTime = now;
				
				int thisSecond = (int) (lastUpdateTime / 1000000000);
				if(thisSecond > lastSecondTime) {
					fps = frameCount;
					frameCount = 0;
					lastSecondTime = thisSecond;
				}
				
				while(now - lastRenderTime < TARGET_TIME_BETWEEN_RENDERS && now - lastUpdateTime < TIME_BETWEEN_UPDATES) {
					Thread.yield();
					
					try {Thread.sleep(1);} catch(Exception e) {}
					
					now = System.nanoTime();
				}
				
			}
		}
		
	}

}
