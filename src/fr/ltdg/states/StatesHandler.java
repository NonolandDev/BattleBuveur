package fr.ltdg.states;

import java.awt.Graphics;
import java.util.ArrayList;

import fr.ltdg.GameFramework;
import fr.ltdg.states.State.GameState;

public class StatesHandler {
	
	private ArrayList<State> states = new ArrayList();
	
	private GameState currentState;
	
	public void init(GameFramework gf) {
		for(State s : states) {
			s.init(gf);
			//gf.addKeyListener(s);
		}
	}
	
	public void render(GameFramework gf, Graphics g, float interpolation) {
		for(State s : states) {
			if(s.getIDState() == this.currentState) s.render(gf, g, interpolation);
		}
	}
	
	public void update(GameFramework gf) {
		for(State s : states) {
			if(s.getIDState() == this.currentState) {
				//gf.addKeyListener(s);
				s.update(gf);
				//gf.removeKeyListener(s);
			}
		}
	}
	
	public void addState(State state) {
		states.add(state);
	}

	public void setCurrentState(GameState currentState) {
		this.currentState = currentState;
	}

}
