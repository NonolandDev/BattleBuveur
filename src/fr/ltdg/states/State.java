package fr.ltdg.states;

import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import fr.ltdg.GameFramework;
import fr.ltdg.Settings;

public abstract class State implements KeyListener, MouseListener{

	public static enum GameState{MAIN_MENU}
	
	protected GameState id;
	
	public State(GameState id) {
		this.id = id;
	}
	
	//Fonction basique d'un jeu
	public abstract void init(GameFramework gf);
	public abstract void render(GameFramework gf, Graphics g, float interpolation);
	public abstract void update(GameFramework gf);
	
	//Fonction pour le clavier
	public abstract void KeyPressed(KeyEvent e);
	public abstract void KeyReleased(KeyEvent e);
	public abstract void KeyTyped(KeyEvent e);
	
	//Fonction pour la souris
	public abstract void MouseClicked(MouseEvent e);
	public abstract void MousePressed(MouseEvent e);
	public abstract void MouseReleased(MouseEvent e);
	public abstract void MouseEntered(MouseEvent e);
	public abstract void MouseExited(MouseEvent e);
	
	public GameState getIDState() {
		return this.id;
	}
	
	@Override
	public void keyPressed(KeyEvent e) {
		KeyPressed(e);
	}

	@Override
	public void keyReleased(KeyEvent e) {
		if(e.getKeyCode() == KeyEvent.VK_F3) {
			if(Settings.RENDER_FPS) Settings.RENDER_FPS = false;
			else Settings.RENDER_FPS = true;
		} else if (e.getKeyCode() == KeyEvent.VK_ESCAPE) {
			System.exit(0);
		}
		
		KeyReleased(e);
	}

	@Override
	public void keyTyped(KeyEvent e) {
		KeyTyped(e);
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		MouseClicked(e);
	}

	@Override
	public void mouseEntered(MouseEvent e) {
		MouseEntered(e);
	}

	@Override
	public void mouseExited(MouseEvent e) {
		MouseExited(e);
	}

	@Override
	public void mousePressed(MouseEvent e) {
		MousePressed(e);
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		MouseReleased(e);
	}
	
}
