package fr.main;

import java.util.ArrayList;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;
import org.newdawn.slick.state.transition.FadeInTransition;
import org.newdawn.slick.state.transition.FadeOutTransition;

import fr.entity.Ball;
import fr.entity.Brique;
import fr.entity.Player;
import fr.menus.PauseMenu;

public class World extends BasicGameState{
	
	private static ArrayList<Brique> briques;
	private static Player Player;
	private static Ball Balls;
	
	public static int ID = 0;
	
	private static GameContainer container;
	private static StateBasedGame game;
	
	@Override
	public void init(GameContainer arg0, StateBasedGame arg1) throws SlickException {
		Player=new Player();
		Balls=new Ball();
		container = arg0;
		game = arg1;
	}

	@Override
	public void render(GameContainer arg0, StateBasedGame arg1, Graphics arg2) throws SlickException {
		Player.render(arg0, arg1, arg2);
		Balls.render(arg0, arg1, arg2);
		
	}

	@Override
	public void update(GameContainer arg0, StateBasedGame arg1, int arg2) throws SlickException {
		Player.update(arg0, arg1, arg2);
		Balls.update(arg0, arg1, arg2);
	}

	@Override
	public int getID() {
		return ID;
	}
	
	public void keyReleased(int key, char c) {
		Player.keyReleased(key, c);
	}


	public void keyPressed(int key, char c) {
		Player.keyPressed(key, c);
		if(key == Input.KEY_ESCAPE){
			game.enterState(PauseMenu.ID, new FadeOutTransition(),
					new FadeInTransition());
		}
	}
	
	public static Player getPlayer() {
		return Player;
	}

	public static void setPlayer(Player playerP) {
		Player = playerP;
	}

	public static int getScore() {
		// TODO Auto-generated method stub
		return 0;
	}
	
	public static ArrayList<Brique> getBriques(){
		return briques;
	}
	
	public static void addBrique(Brique b){
		briques.add(b);
	}

	public static void removeBrique(Brique b){
		briques.remove(b);
	}
}
