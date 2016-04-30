package fr.main;

import java.util.ArrayList;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;

import fr.entity.Player;

public class World extends BasicGameState{
	
	
	private static Player Player;
	
	@Override
	public void init(GameContainer arg0, StateBasedGame arg1) throws SlickException {
		Player=new Player();
	}

	@Override
	public void render(GameContainer arg0, StateBasedGame arg1, Graphics arg2) throws SlickException {
		Player.render(arg0, arg1, arg2);
		
	}

	@Override
	public void update(GameContainer arg0, StateBasedGame arg1, int arg2) throws SlickException {
		Player.update(arg0, arg1, arg2);
	}

	@Override
	public int getID() {
		// Aucune idée de ca que ça fait ^^
		return 0;
	}
	
	public void keyReleased(int key, char c) {
		Player.keyReleased(key, c);
	}


	public void keyPressed(int key, char c) {
		Player.keyPressed(key, c);
		if(key == Input.KEY_ESCAPE){
			System.exit(0);
		}
	}
	
	public static Player getPlayer() {
		return Player;
	}

	public static void setPlayer(Player playerP) {
		Player = playerP;
	}
	
}
