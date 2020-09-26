package fr.main;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import org.lwjgl.input.Mouse;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
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
import fr.menus.GameOverMenu;
import fr.menus.LevelSelectorMenu;
import fr.menus.PauseMenu;
import fr.parser.ReadFile;

public class World extends BasicGameState{

	private static ArrayList<Brique> briques;
	private static ArrayList<Bullet> bullets;
	private static ArrayList<Bonus> bonus;
	private static Player player;
	private static Player player2;
	private static ArrayList<Ball> balls;
    public static enum mode {CAMPAIGN, MULTI, CUSTOM};
    public static mode gameMode;
    public static int currentCampaignLevel;

    private static Image background;

	public static int ID = 0;

	private static GameContainer container;
	private static StateBasedGame game;


	@Override
	public void init(GameContainer arg0, StateBasedGame arg1) throws SlickException {
		background = new Image("img"+File.separator+"background"+File.separator+"fond5.png");
		player=new Player();
		player2 = new Player();
		player2.setY(85);
		balls=new ArrayList<Ball>();
		bonus=new ArrayList<Bonus>();
		balls.add(new Ball());
		Bonus.chargerImageBonus();
		container = arg0;
		game = arg1;
		briques = new ArrayList<Brique>();
		bullets=new ArrayList<Bullet>();
		currentCampaignLevel = 1;
		if(new File("res"+File.separator+"levels"+File.separator+"niveau1.txt").exists())
		{
			ReadFile file=new ReadFile("res"+File.separator+"levels"+File.separator+"niveau1.txt");
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
		arg2.drawImage(background, 0, 0);
		player.render(arg0, arg1, arg2);
		if (gameMode == mode.MULTI)
			player2.render(arg0, arg1, arg2);
	    //arg2.drawString(Mouse.getX()+", "+Mouse.getY(), 10, 10);

		for (int i = 0; i < balls.size(); i++) {
			balls.get(i).render(arg0, arg1, arg2);
		}
		for (int i = 0; i < bonus.size(); i++) {
			bonus.get(i).render(arg0, arg1, arg2);
		}
		for(int i = 0; i < briques.size(); i++)
		{
			briques.get(i).render(arg0, arg1, arg2);
		}

		for (int i = 0; i < bullets.size(); i++) {
			bullets.get(i).render(arg0, arg1, arg2);
		}
		arg2.drawString("Lives : "+player.getLife(), 700, 580);
		if (gameMode == mode.CAMPAIGN)
			arg2.drawString("Level : "+currentCampaignLevel, 50, 580);

		if (areDestroyed(briques)) {
			if (gameMode == mode.CAMPAIGN && currentCampaignLevel < 5) {
				currentCampaignLevel++;
				reload();
			} else {
				game.enterState(LevelSelectorMenu.ID);
			}
		}

	}

	private boolean areDestroyed(ArrayList<Brique> br) {
		for(int i=0;i<briques.size();i++)
		{
			if( briques.get(i).getLife()>0)return false;
		}
		return true;
	}

	@Override
	public void update(GameContainer arg0, StateBasedGame arg1, int arg2) throws SlickException {
		player.update(arg0, arg1, arg2);
		if (gameMode == mode.MULTI)
			player2.update(arg0, arg1, arg2);
		for (int i = 0; i < balls.size(); i++) {
			balls.get(i).update(arg0, arg1, arg2);
		}
		for (int i = 0; i < bonus.size(); i++) {
			bonus.get(i).update(arg0, arg1, arg2);
		}
		for(int i=0;i<briques.size();i++)
		{
			briques.get(i).update(arg0, arg1, arg2);
		}

		for (int i=0;i<bullets.size();i++)
		{
			bullets.get(i).update(arg0, arg1, arg2);
		}

		if (player.getLife() == 0) {
			game.enterState(GameOverMenu.ID, new FadeOutTransition(), new FadeInTransition());
		}

		if (areDestroyed(briques)) {
			if (gameMode == mode.CAMPAIGN && currentCampaignLevel < 2) {
				currentCampaignLevel++;
				reload();
			} else {
				game.enterState(LevelSelectorMenu.ID);
			}
		}

		if(balls.size()==0){
			player.setLife(player.getLife()-1);
			balls.add(new Ball());
			player.setHasBall(true);
		}

	}

	@Override
	public int getID() {
		return ID;
	}

	public void keyReleased(int key, char c) {
		player.keyReleased(key, c);
	}


	public void keyPressed(int key, char c) {
		player.keyPressed(key, c);
		if(key == Input.KEY_ESCAPE){
			game.enterState(PauseMenu.ID, new FadeOutTransition(),
					new FadeInTransition());
		}
	}

	public static Player getPlayer() {
		return player;
	}

	public static void setPlayer(Player playerP) {
		player = playerP;
	}

	public static void destroy(Bullet b)
	{
		bullets.remove(b);
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
		bullets.add(b);
	}

	public static void removeBrique(Brique b){
		briques.remove(b);
	}


	public static void reload(String niveau)
	{
		if(new File("res"+File.separator+"levels"+File.separator+niveau).exists())
		{

			player=new Player();
			balls=new ArrayList<Ball>();
			balls.add(new Ball());
			briques = new ArrayList<Brique>();
			bullets=new ArrayList<Bullet>();

			ReadFile file=new ReadFile("res"+File.separator+"levels"+File.separator+niveau);
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

			if (gameMode == mode.CAMPAIGN) {
				try {
					background = new Image("img"+File.separator+"background"+File.separator+"fond"+currentCampaignLevel+".png");
				} catch (SlickException e) {
					e.printStackTrace();
				}
			}

		}
	}

	public static void reload() {
		if (gameMode == mode.CAMPAIGN) {
			reload("niveau"+currentCampaignLevel+".txt");
		} else if (gameMode == mode.MULTI) {
			reload("multi.txt");
		}
	}

	public static ArrayList<Bullet> getBullets() {
		// TODO Auto-generated method stub
		return bullets;
	}

}
