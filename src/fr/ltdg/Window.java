package fr.ltdg;

import javax.swing.JFrame;

public class Window extends JFrame{
	
	
	
	public Window() {
		setTitle(Settings.TITLE);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		setUndecorated(true);
		
		if(Settings.FULLSCREEN) {
			setExtendedState(MAXIMIZED_BOTH);
		} else {
			setSize(Settings.WIDTH, Settings.HEIGHT);
			setLocationRelativeTo(null);
			setResizable(false);
		}
		
		setContentPane(new GameFramework());
		
		setVisible(true);
	}

}
