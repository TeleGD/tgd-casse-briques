package fr.menus;

import java.awt.Font;

import org.lwjgl.input.Mouse;
import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.TrueTypeFont;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;
import org.newdawn.slick.state.transition.FadeInTransition;
import org.newdawn.slick.state.transition.FadeOutTransition;

import fr.main.World;
import fr.menus.MainMenu;
import fr.main.Main;
import fr.main.World.mode;


public class GameOverMenu extends BasicGameState {

	public static int ID = 3;

	static TrueTypeFont font3;
	static TrueTypeFont font4;

	private String nom = "GAME OVER";
	private String[] items = { "Rejouer", "Retour au menu"};

	public int nbrOption = items.length;

	public String[] getItems() {
		return this.items;
	}

	//private Image background;

	static StateBasedGame game;

	static GameContainer container;
	int selection = 0;
	private static boolean firstTime;

	private boolean mouseOverSelection() {
		int x = Mouse.getX();
		int y = 600-Mouse.getY();
		return (   x>300
				&& x<550
				&& y>280
				&& y<280+nbrOption*50);
	}

	public void init(GameContainer container, StateBasedGame game)
			throws SlickException {
		this.game = game;
		this.container = container;
		container.setShowFPS(false);

		//background = new Image("Images/background2.png");

		Font titre3Font = new Font("Goudy Stout", Font.BOLD, 30);
    	font3 = new TrueTypeFont(titre3Font, false);

    	Font titre4Font = new Font("Kristen ITC", Font.BOLD, 20);
		font4 = new TrueTypeFont(titre4Font, false);

		firstTime = true;

	}

	public void update(GameContainer container, StateBasedGame game, int delta)
			throws SlickException {
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

	public void render(GameContainer container, StateBasedGame game, Graphics g)
			throws SlickException {
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

	public static void reset() {
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
			game.enterState(MainMenu.ID, new FadeOutTransition(), new FadeInTransition());
			break;
		case Input.KEY_C:
			game.enterState(CreditsMenu.ID, new FadeOutTransition(), new FadeInTransition());
			break;
		}
	}

	public void execOption() {

		switch (selection) {
		case 0:
			//World.reset();
			if (World.gameMode == mode.MULTI) {
				World.reload();
				game.enterState(World.ID, new FadeOutTransition(), new FadeInTransition());
			} else if (World.gameMode == mode.CAMPAIGN) {
				World.currentCampaignLevel = 1;
				World.reload();
				game.enterState(World.ID, new FadeOutTransition(), new FadeInTransition());
			} else if (World.gameMode == mode.CUSTOM) {
				game.enterState(LevelSelectorMenu.ID, new FadeOutTransition(), new FadeInTransition());
			}
			break;
		case 1:
			game.enterState(MainMenu.ID, new FadeOutTransition(), new FadeInTransition());
			break;

		}
	}

	public int getID() {
		return ID;
	}

}
