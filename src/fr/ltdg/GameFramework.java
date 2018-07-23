package fr.ltdg;

import java.awt.Color;
import java.awt.Graphics;

import javax.swing.JPanel;

/*
 * Cette class est le coeur même du jeu
 */

public class GameFramework extends JPanel{
	
	private int fps = 60;
	private int frameCount = 0; 
	
	public boolean pause = false;
	
	private float interpolation;
	
	public GameFramework() {
		Thread game = new Thread() {
			public void run() {
				gameLoop();
			}
		};
		game.start();
	}
	
	public void init() {
		
	}
	
	public void renderGame(float interpolation) {
		this.interpolation = interpolation;
		repaint();
	}
	
	//True update loop
	public void updateGame() {
		
	}
	
	//True render loop
	public void paintComponent(Graphics g) {
		g.setColor(getBackground());
		g.fillRect(0, 0, getWidth(), getHeight());
		
		g.setColor(Color.black);
		g.drawString("fps: " + fps, 0,15);
		
		frameCount++;
	}
	
	public void gameLoop() {
		
		final double GAME_HERTZ = 30.0;
		final double TIME_BETWEEN_UPDATES = 1000000000 / Settings.GAME_FPS;
		
		final double TARGET_TIME_BETWEEN_RENDERS = 1000000000 / Settings.GAME_FPS;
		
		final int MAX_UPDATES_BEFORE_RENDER = 1;
		
		double lastUpdateTime = System.nanoTime();
		
		double lastRenderTime = System.nanoTime();
		
		int lastSecondTime = (int) (lastUpdateTime / 1000000000);
		
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
					System.out.println(fps);
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
