package games.casseBriques.menus;

import org.lwjgl.input.Mouse;
import org.newdawn.slick.Color;
import org.newdawn.slick.Font;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Input;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;
import org.newdawn.slick.state.transition.FadeInTransition;
import org.newdawn.slick.state.transition.FadeOutTransition;

import app.AppFont;
import app.AppLoader;

import games.casseBriques.World;

public class GameOverMenu extends BasicGameState {

	private int ID;

	Font font3;
	Font font4;

	private String nom = "GAME OVER";
	private String[] items = { "Rejouer", "Retour au menu"};

	public int nbrOption = items.length;

	public String[] getItems() {
		return this.items;
	}

	//private Image background;

	StateBasedGame game;

	GameContainer container;
	int selection = 0;
	private boolean firstTime;

	public GameOverMenu(int ID) {
		this.ID = ID;
	}

	@Override
	public int getID() {
		return this.ID;
	}

	private boolean mouseOverSelection() {
		int x = Mouse.getX();
		int y = 600-Mouse.getY();
		return (   x>300
				&& x<550
				&& y>280
				&& y<280+nbrOption*50);
	}

	public void init(GameContainer container, StateBasedGame game) {
		this.game = game;
		this.container = container;
		container.setShowFPS(false);

		//background = new Image("Images/background2.png");

		font3 = AppLoader.loadFont("Goudy Stout", AppFont.BOLD, 30); // TODO: trouver une fonte équivalente

    	font4 = AppLoader.loadFont("Kristen ITC", AppFont.BOLD, 20); // TODO: trouver une fonte équivalente

		firstTime = true;
	}

	public void update(GameContainer container, StateBasedGame game, int delta) {
		/*if (firstTime) {
			items[3] = "Score : "+World.getScore();
			firstTime = false;
		}*/

		if (mouseOverSelection()) {
			int x = Mouse.getX();
			int y = 600-Mouse.getY();
			selection = (y-280)/50;
		}
	}

	public void render(GameContainer container, StateBasedGame game, Graphics g) {
		//g.drawImage(background, 0, 0);

		g.setColor(Color.red);
		g.setFont(font3);

		g.drawString(this.nom, 200, 200);

		g.setColor(Color.white);
		g.setFont(font4);

		for (int i = 0; i < nbrOption; i++) {
			g.drawString(this.items[i], 300, 280 + 50 * i);
		}

		g.drawString(">>", 230, 280 + 50 * selection);
	}

	public void reset() {
		firstTime = true;
	}

	public void mousePressed(int button, int oldx,int oldy){
		if (mouseOverSelection())
			execOption();
	}

	public void keyPressed(int key, char c) {
		switch (key) {
		case Input.KEY_DOWN:
		case Input.KEY_UP:
			selection = 1-selection;
			break;
		case Input.KEY_ENTER:
			execOption();
			break;
		case Input.KEY_ESCAPE:
			game.enterState(4 /* MainMenu */, new FadeOutTransition(), new FadeInTransition());
			break;
		case Input.KEY_C:
			game.enterState(2 /* CreditsMenu */, new FadeOutTransition(), new FadeInTransition());
			break;
		}
	}

	public void execOption() {
		World world = (World) game.getState(0);
		switch (selection) {
		case 0:
			if (world.gameMode == World.mode.CAMPAIGN) {
				world.currentCampaignLevel = 1;
			}
			world.load(world.currentLevel);
			game.enterState(0 /* World */, new FadeOutTransition(), new FadeInTransition());
			break;
		case 1:
			game.enterState(4 /* MainMenu */, new FadeOutTransition(), new FadeInTransition());
			break;
		}
	}

}
