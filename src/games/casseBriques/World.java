package games.casseBriques;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.Input;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;
import org.newdawn.slick.state.transition.FadeInTransition;
import org.newdawn.slick.state.transition.FadeOutTransition;

import app.AppLoader;

import games.casseBriques.entities.Ball;
import games.casseBriques.entities.Bonus;
import games.casseBriques.entities.Brique;
import games.casseBriques.entities.Bullet;
import games.casseBriques.entities.Player;
import games.casseBriques.parser.ReadFile;

public class World extends BasicGameState{

	private int ID;
	private int state;

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

	private static GameContainer container;
	private static StateBasedGame game;

	public World(int ID) {
		this.ID = ID;
	}

	@Override
	public int getID() {
		return this.ID;
	}

	@Override
	public void init(GameContainer arg0, StateBasedGame arg1) {
		/* Méthode exécutée une unique fois au chargement du programme */
		background = AppLoader.loadPicture("/images/casseBriques/background/fond5.png");
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
		if(new File("res"+File.separator+"data"+File.separator+"casseBriques"+File.separator+"levels"+File.separator+"niveau1.txt").exists())
		{
			ReadFile file=new ReadFile("res"+File.separator+"data"+File.separator+"casseBriques"+File.separator+"levels"+File.separator+"niveau1.txt");
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
	public void enter(GameContainer container, StateBasedGame game) {
		/* Méthode exécutée à l'apparition de la page */
		if (this.state == 0) {
			this.play(container, game);
		} else if (this.state == 2) {
			this.resume(container, game);
		}
	}

	@Override
	public void leave(GameContainer container, StateBasedGame game) {
		/* Méthode exécutée à la disparition de la page */
		if (this.state == 1) {
			this.pause(container, game);
		} else if (this.state == 3) {
			this.stop(container, game);
			this.state = 0; // TODO: remove
		}
	}

	@Override
	public void render(GameContainer arg0, StateBasedGame arg1, Graphics arg2) {
		/* Méthode exécutée environ 60 fois par seconde */
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
				game.enterState(10 /* LevelSelectorMenu */);
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
	public void update(GameContainer arg0, StateBasedGame arg1, int arg2) {
		/* Méthode exécutée environ 60 fois par seconde */
		Input input = container.getInput();
		if (input.isKeyDown(Input.KEY_ESCAPE)) {
			this.setState(1);
			game.enterState(2, new FadeOutTransition(), new FadeInTransition());
		}
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
			game.enterState(3 /* GameOverMenu */, new FadeOutTransition(), new FadeInTransition());
		}

		if (areDestroyed(briques)) {
			if (gameMode == mode.CAMPAIGN && currentCampaignLevel < 2) {
				currentCampaignLevel++;
				reload();
			} else {
				game.enterState(10 /* LevelSelectorMenu */);
			}
		}

		if(balls.size()==0){
			player.setLife(player.getLife()-1);
			balls.add(new Ball());
			player.setHasBall(true);
		}

	}

	public void play(GameContainer container, StateBasedGame game) {
		/* Méthode exécutée une unique fois au début du jeu */
	}

	public void pause(GameContainer container, StateBasedGame game) {
		/* Méthode exécutée lors de la mise en pause du jeu */
	}

	public void resume(GameContainer container, StateBasedGame game) {
		/* Méthode exécutée lors de la reprise du jeu */
	}

	public void stop(GameContainer container, StateBasedGame game) {
		/* Méthode exécutée une unique fois à la fin du jeu */
	}

	public void setState(int state) {
		this.state = state;
	}

	public int getState() {
		return this.state;
	}

	public void keyReleased(int key, char c) {
		player.keyReleased(key, c);
	}

	public void keyPressed(int key, char c) {
		player.keyPressed(key, c);
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
		if(new File("res"+File.separator+"data"+File.separator+"casseBriques"+File.separator+"levels"+File.separator+niveau).exists())
		{

			player=new Player();
			balls=new ArrayList<Ball>();
			balls.add(new Ball());
			briques = new ArrayList<Brique>();
			bullets=new ArrayList<Bullet>();

			ReadFile file=new ReadFile("res"+File.separator+"data"+File.separator+"casseBriques"+File.separator+"levels"+File.separator+niveau);
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
				background = AppLoader.loadPicture("/images/casseBriques/background/fond"+currentCampaignLevel+".png");
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
