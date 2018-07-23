package fr.ltdg;

import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.event.KeyEvent;

/*
 * Framework permet de controler le jeu (Game.java).
 */

public class Framework extends Canvas{
	
	public static int frameWidth;
	public static int frameHeight;
	
	public static final long secInNanosec = 1000000000L;
	public static final long milisecInNanosec = 1000000L;
	
	public final long GAME_UPDATE_PERIOD = secInNanosec / Settings.GAME_FPS;

	//Toute les états du jeu
	public static enum GameState{
		STARTING,
		VISUALIZING,
		MAIN_MENU,
		OPTIONS,
		PLAY
	}
	
	//Etat actuel du jeu
	public static GameState gameState;
	
	//Temps
	private long gameTime;
	
	private long lastTime;
	
	private Game game;
	
	public Framework() {
		super();
		
		gameState = GameState.VISUALIZING;
		
		Thread gameThread = new Thread() {
			public void run() {
				GameLoop();
			}
		};
	}
	
	/*
	 * Fonction pour charger les variables et les différents objets
	 */
	public void Initialize() {
		
	}
	
	/*
	 * Fonction pour charger les différents fichiers(Images, sons...)
	 */
	public void LoadContent() {
		
	}
	
	private void GameLoop() {
		
		long visualizingTime = 0, lastVisualizingTime = System.nanoTime();
		
		long beginTime, timeTaken, timeLeft;
		
		while(true) {
			beginTime = System.nanoTime();
			
			switch(gameState) {
				case VISUALIZING:
					if(getWidth() > 1 && visualizingTime > secInNanosec) {
						frameWidth = getWidth();
						frameHeight = getHeight();
						
						gameState = GameState.STARTING;
					} else {
						visualizingTime += System.nanoTime() - lastVisualizingTime;
						lastVisualizingTime = System.nanoTime();
					}
				break;
				case STARTING:
					Initialize();
					LoadContent();
					
					gameState = GameState.MAIN_MENU;
				break;
				case MAIN_MENU:
					
				break;
				case OPTIONS:
					
				break;
				case PLAY:
					
				break;
			}
			
			repaint();
			
			timeTaken = System.nanoTime() - beginTime;
            timeLeft = (GAME_UPDATE_PERIOD - timeTaken) / milisecInNanosec; 
            if (timeLeft < 10) 
                timeLeft = 10; 
            try {
                 Thread.sleep(timeLeft);
            } catch (InterruptedException ex) { }
            
		}
	}
	
	public void Draw(Graphics2D g) {
		
	}
	
    private Point mousePosition()
    {
        try
        {
            Point mp = this.getMousePosition();
            
            if(mp != null)
                return this.getMousePosition();
            else
                return new Point(0, 0);
        }
        catch (Exception e)
        {
            return new Point(0, 0);
        }
    }

	@Override
	public void keyReleasedFramework(KeyEvent e) {
		// TODO Auto-generated method stub
		
	}

}
