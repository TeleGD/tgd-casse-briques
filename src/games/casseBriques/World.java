package games.casseBriques;

import java.io.BufferedReader;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

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

public class World extends BasicGameState{

	private int ID;
	private int state;

	private List<Ball> balls;
	private List<Bonus> bonuses;
	private List<Brique> briques;
	private List<Bullet> bullets;
	private Set<Ball> ballsToAdd;
	private Set<Bonus> bonusesToAdd;
	// private Set<Brique> briquesToAdd;
	private Set<Bullet> bulletsToAdd;
	private Set<Ball> ballsToRemove;
	private Set<Bonus> bonusesToRemove;
	private Set<Brique> briquesToRemove;
	private Set<Bullet> bulletsToRemove;
	private Player player;
	private Player player2;
    public enum mode {CAMPAIGN, MULTI, CUSTOM};
    public mode gameMode;
    public int currentCampaignLevel;
	public String currentLevel;

    private Image background;

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

		for (Ball ball: this.balls) {
			ball.render(arg0, arg1, arg2);
		}
		for (Bonus bonus: this.bonuses) {
			bonus.render(arg0, arg1, arg2);
		}
		for (Brique brique: this.briques) {
			brique.render(arg0, arg1, arg2);
		}
		for (Bullet bullet: this.bullets) {
			bullet.render(arg0, arg1, arg2);
		}
		arg2.drawString("Lives : "+player.getLife(), 700, 580);
		if (gameMode == mode.CAMPAIGN)
			arg2.drawString("Level : "+currentCampaignLevel, 50, 580);
	}

	private boolean areBriquesDestroyed() {
		for(int i=0;i<briques.size();i++)
		{
			if( briques.get(i).getLife()>0)return false;
		}
		return true;
	}

	@Override
	public void update(GameContainer arg0, StateBasedGame arg1, int arg2) {
		/* Méthode exécutée environ 60 fois par seconde */
		Input input = arg0.getInput();
		if (input.isKeyDown(Input.KEY_ESCAPE)) {
			this.setState(1);
			arg1.enterState(6 /* Pause */, new FadeOutTransition(), new FadeInTransition());
		}
		player.update(arg0, arg1, arg2);
		if (gameMode == mode.MULTI)
			player2.update(arg0, arg1, arg2);
		for (Brique brique: this.briques) {
			brique.setColliding(false);
		}
		for (Ball ball: this.balls) {
			ball.update(arg0, arg1, arg2);
		}
		for (Bonus bonus: this.bonuses) {
			bonus.update(arg0, arg1, arg2);
		}
		for (Brique brique: this.briques) {
			brique.update(arg0, arg1, arg2);
		}
		for (Bullet bullet: this.bullets) {
			bullet.update(arg0, arg1, arg2);
		}
		for (Ball ball: this.ballsToRemove) {
			balls.remove(ball);
		}
		for (Bonus bonus: this.bonusesToRemove) {
			bonuses.remove(bonus);
		}
		for (Brique brique: this.briquesToRemove) {
			brique.lastWhisper();
			briques.remove(brique);
		}
		for (Bullet bullet: this.bulletsToRemove) {
			bullets.remove(bullet);
		}
		this.ballsToRemove.clear();
		this.bonusesToRemove.clear();
		this.briquesToRemove.clear();
		this.bulletsToRemove.clear();
		for (Ball ball: this.ballsToAdd) {
			balls.add(ball);
		}
		for (Bonus bonus: this.bonusesToAdd) {
			bonuses.add(bonus);
		}
		// for (Brique brique: this.briquesToAdd) {
		// 	briques.add(brique);
		// }
		for (Bullet bullet: this.bulletsToAdd) {
			bullets.add(bullet);
		}
		this.ballsToAdd.clear();
		this.bonusesToAdd.clear();
		// this.briquesToAdd.clear();
		this.bulletsToAdd.clear();
		if (player.getLife() == 0) {
			arg1.enterState(3 /* GameOverMenu */, new FadeOutTransition(), new FadeInTransition());
		}

		if (areBriquesDestroyed()) {
			if (gameMode == mode.CAMPAIGN && currentCampaignLevel < 5) {
				currentCampaignLevel++;
				load("niveau" + currentCampaignLevel, false);
			} else {
				arg1.enterState(4 /* MainMenu */, new FadeOutTransition(), new FadeInTransition());
			}
		}

		if(balls.size()==0){
			player.setLife(player.getLife()-1);
			balls.add(new Ball(this));
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

	public void add(Ball ball) {
		this.ballsToAdd.add(ball);
	}

	public void add(Bonus bonus) {
		this.bonusesToAdd.add(bonus);
	}

	// public void add(Brique brique) {
	// 	this.briquesToAdd.add(brique);
	// }

	public void add(Bullet bullet){
		this.bulletsToAdd.add(bullet);
	}

	public void remove(Ball ball) {
		this.ballsToRemove.add(ball);
	}

	public void remove(Bonus bonus) {
		this.bonusesToRemove.add(bonus);
	}

	public void remove(Bullet bullet) {
		this.bulletsToRemove.add(bullet);
	}

	public void remove(Brique brique) {
		this.briquesToRemove.add(brique);
	}

	public int getScore() {
		// TODO Auto-generated method stub
		return 0;
	}

	public Player getPlayer() {
		return player;
	}

	public List<Ball> getBalls(){
		return this.balls;
	}

	// public List<Bonus> getBonuses(){
	// 	return this.bonuses;
	// }

	public List<Brique> getBriques(){
		return this.briques;
	}

	public List<Bullet> getBullets() {
		return this.bullets;
	}

	public void load(String niveau, boolean custom)
	{
		if (gameMode == mode.CAMPAIGN) {
			background = AppLoader.loadPicture("/images/casseBriques/background/fond"+currentCampaignLevel+".png");
		} else {
			background = AppLoader.loadPicture("/images/casseBriques/background/fond5.png");
		}
		player=new Player(this);
		if (gameMode == mode.MULTI) {
			player2 = new Player(this);
			player2.setY(85);
		} else {
			player2 = null;
		}
		this.balls = new ArrayList<Ball>();
		this.balls.add(new Ball(this));
		this.bonuses = new ArrayList<Bonus>();
		this.briques = new ArrayList<Brique>();
		this.bullets = new ArrayList<Bullet>();
		this.ballsToAdd = new HashSet<Ball>();
		this.bonusesToAdd = new HashSet<Bonus>();
		// this.briquesToAdd = new HashSet<Brique>();
		this.bulletsToAdd = new HashSet<Bullet>();
		this.ballsToRemove = new HashSet<Ball>();
		this.bonusesToRemove = new HashSet<Bonus>();
		this.briquesToRemove = new HashSet<Brique>();
		this.bulletsToRemove = new HashSet<Bullet>();
		String level;
		if (custom) {
			level = AppLoader.restoreData("/casseBriques/levels/" + niveau + ".txt");
		} else {
			level = AppLoader.loadData("/data/casseBriques/levels/" + niveau + ".txt");
		}
		BufferedReader reader = new BufferedReader(new StringReader(level));
		String line;
		try {
			while ((line = reader.readLine()) != null) {
				Brique b=Brique.StringToBrique(this, line);
				if (b.getX() >= 0 && b.getX() <= 720 && b.getY() >= 0 && b.getY() <= 352) {
					briques.add(b);
				}
			}
			reader.close();
		} catch (Exception error) {}
	}

}
