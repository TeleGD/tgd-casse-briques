package games.casseBriques.menus;

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

	private Font font3;
	private Font font4;

	private String nom = "GAME OVER";
	private String[] items = { "Rejouer", "Retour au menu"};

	public int nbrOption = items.length;

	public String[] getItems() {
		return this.items;
	}

	StateBasedGame game;

	GameContainer container;
	int selection = 0;

	public GameOverMenu(int ID) {
		this.ID = ID;
	}

	@Override
	public int getID() {
		return this.ID;
	}

	public void init(GameContainer container, StateBasedGame game) {
		this.game = game;
		this.container = container;

		font3 = AppLoader.loadFont("/fonts/press-start-2p.ttf", AppFont.BOLD, 40);

    	font4 = AppLoader.loadFont("/fonts/vt323.ttf", AppFont.BOLD, 24);
	}

	public void update(GameContainer container, StateBasedGame game, int delta) {}

	public void render(GameContainer container, StateBasedGame game, Graphics g) {
		g.setColor(Color.red);
		g.setFont(font3);

		g.drawString(this.nom, 200, 200);

		g.setColor(Color.white);
		g.setFont(font4);

		for (int i = 0; i < nbrOption; i++) {
			g.drawString(this.items[i], 300, 273 + 30 * i);
		}

		g.drawString(">>", 273, 273 + 30 * selection);
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
			game.enterState(1 /* Choice */, new FadeOutTransition(), new FadeInTransition());
			break;
		}
	}

	public void execOption() {
		World world = (World) game.getState(3);
		switch (selection) {
		case 0:
			if (world.campaign) {
				world.currentCampaignLevel = 1;
			}
			world.load(world.currentLevel, world.custom);
			game.enterState(3 /* World */, new FadeOutTransition(), new FadeInTransition());
			break;
		case 1:
			game.enterState(1 /* Choice */, new FadeOutTransition(), new FadeInTransition());
			break;
		}
	}

}
