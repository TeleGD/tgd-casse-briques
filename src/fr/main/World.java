package fr.main;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import org.lwjgl.input.Mouse;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;
import org.newdawn.slick.state.transition.FadeInTransition;
import org.newdawn.slick.state.transition.FadeOutTransition;

import fr.entity.Ball;
import fr.entity.Bonus;
import fr.entity.Brique;
import fr.entity.Bullet;
import fr.entity.Player;
import fr.menus.PauseMenu;
import fr.parser.ReadFile;

public class World extends BasicGameState{
	
	private static ArrayList<Brique> briques;
	private static ArrayList<Bullet> bullet;
	private static ArrayList<Bonus> bonus;
	private static Player Player;
	private static ArrayList<Ball> balls;
    public static enum mode {CAMPAIGN, MULTI, CUSTOM};
    public static mode gameMode;
	
	public static int ID = 0;
	
	private static GameContainer container;
	private static StateBasedGame game;
	
	@Override
	public void init(GameContainer arg0, StateBasedGame arg1) throws SlickException {
		Player=new Player();
		balls=new ArrayList<Ball>();
		balls.add(new Ball());
		container = arg0;
		game = arg1;
		briques = new ArrayList<Brique>();
		bullet=new ArrayList<Bullet>();
		if(new File("levels"+File.separator+"niveau1.txt").exists())
		{
			ReadFile file=new ReadFile("levels"+File.separator+"niveau1.txt");
		    ArrayList<String> texts;
			try {
				texts = file.readFromFile();
				for(String s:texts)
				{
					briques.add(Brique.StringToBrique(s));
				}
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		
	}

	@Override
	public void render(GameContainer arg0, StateBasedGame arg1, Graphics arg2) throws SlickException {
		Player.render(arg0, arg1, arg2);
	    arg2.drawString(Mouse.getY()+", "+Mouse.getY(), 10, 10);

		for (int i = 0; i < balls.size(); i++) {
			balls.get(i).render(arg0, arg1, arg2);
		}

		for(Brique b:briques)
		{
			if(!b.getDead()){
				b.render(arg0, arg1, arg2);
			}
		}

	}

	@Override
	public void update(GameContainer arg0, StateBasedGame arg1, int arg2) throws SlickException {
		Player.update(arg0, arg1, arg2);
		for (int i = 0; i < balls.size(); i++) {
			balls.get(i).update(arg0, arg1, arg2);
		}
		for(Brique b:briques)
		{
			if(!b.getDead()){
				b.update(arg0, arg1, arg2);
			}
		}
		
		for (Bullet b:bullet)
		{
			if (getTouched(b))
			{
				destroy(b);
			}
		}
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

	public static void destroy(Bullet b)
	{
		bullet.remove(b);
	}
	
	public static void destroy(Brique b)
	{
		b.lastWhisper();
		briques.remove(b);
	}
	
	public static void destroy(Bonus b){
		// TODO destruction du bonus
		bonus.remove(b);
	}
	
	public static int getScore() {
		// TODO Auto-generated method stub
		return 0;
	}
	
	public static ArrayList<Brique> getBriques(){
		return briques;
	}
	
	public static ArrayList<Bonus> getBonus(){
		return bonus;
	}
	
	public static ArrayList<Ball> getBalls(){
		return balls;
	}
	
	public static void addBrique(Brique b){
		briques.add(b);
	}
	
	public static void addBall(Ball b){
		balls.add(b);
	}
	
	public static void addBonus(Bonus b){
		bonus.add(b);
	}
	
	public static void addBullet(Bullet b)
	{
		bullet.add(b);
	}

	public static void removeBrique(Brique b){
		briques.remove(b);
	}
	
	public static boolean getTouched(Bullet b)
	{
		return getTouched(b);
	}
	
	public static void reload(String niveau)
	{
		if(new File("levels"+File.separator+niveau).exists())
		{
			ReadFile file=new ReadFile("levels"+File.separator+niveau);
		    ArrayList<String> texts;
			try {
				texts = file.readFromFile();
				briques.removeAll(briques);
				for(String s:texts)
				{
					Brique b=Brique.StringToBrique(s);
					if(b.getY()<400){
						briques.add(b);
					}
					
				}
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
}
