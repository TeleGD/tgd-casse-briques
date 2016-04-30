package fr.world;

import java.util.ArrayList;

import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Input;
import org.newdawn.slick.Music;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;

import fr.entity.Player;


public class World extends BasicGameState{
	
	public enum direction {HAUT,DROITE,BAS,GAUCHE};
	private static Player player;
	public static int ID=0;
	private static GameContainer container;
	private static StateBasedGame game;
	
	private static int score = 0;
	
	@Override
	public void init(GameContainer container, StateBasedGame game) throws SlickException {

		player = new Player(400,300);
		World.container = container;
		World.game = game;
	}
	

	@Override
	public void render(GameContainer container, StateBasedGame game, Graphics g) throws SlickException {
		player.render(container, game, g);
		g.setColor(Color.white);
	}

	@Override
	public void update(GameContainer container, StateBasedGame game, int delta) throws SlickException {
		player.update(container, game, delta);
	}
	
	public void keyReleased(int key, char c) {
		player.keyReleased(key, c);
	}


	public void keyPressed(int key, char c) {
		player.keyPressed(key, c);
	}
	
	
	@Override
	public int getID() {
		return ID;
	}
	
	public static int getScore() {
		return score;
	}

	public static void setScore(int scoreP) {
		score = scoreP;
	}
	
}
