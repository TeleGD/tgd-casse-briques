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

public class ConfirmMenu extends BasicGameState {

	Font font9;
	Font font6;

	private int ID;

	private String nom = "Etes-vous sûr(e)? Tous les scores seront perdus";
	private String[] items = { "Non", "Oui" };

	public int nbrOption = items.length;

	public String[] getItems() {
		return this.items;
	}

	//private Image background;

	StateBasedGame game;

	GameContainer container;
	int selection = 0;

	public ConfirmMenu(int ID) {
		this.ID = ID;
	}

	@Override
	public int getID() {
		return this.ID;
	}

	public void init(GameContainer container, StateBasedGame game) {
		this.game = game;
		this.container = container;
		container.setShowFPS(false);

		//background = new Image("Images/background2.png");

		font9 = AppLoader.loadFont("Courant", AppFont.BOLD, 22); // TODO: trouver une fonte équivalente

    	font6 = AppLoader.loadFont("Courant", AppFont.BOLD, 16); // TODO: trouver une fonte équivalente
	}

	private boolean mouseOverSelection() {
		int x = Mouse.getX();
		int y = 600-Mouse.getY();
		return (   x>300
				&& x<550
				&& y>280
				&& y<280+nbrOption*50);
	}

	public void update(GameContainer container, StateBasedGame game, int delta) {
		if (mouseOverSelection()) {
			int x = Mouse.getX();
			int y = 600-Mouse.getY();
			selection = (y-280)/50;
		}
	}

	public void render(GameContainer container, StateBasedGame game, Graphics g) {
		//g.drawImage(background, 0, 0);

		g.setColor(Color.white);
		g.setFont(font9);

		g.drawString(this.nom, 100, 200);

		g.setFont(font6);

		for (int i = 0; i < nbrOption; i++) {
			g.drawString(this.items[i], 300, 280 + 50 * i);
		}

		g.drawString(">>", 230, 280 + 50 * selection);
	}

	public void mousePressed(int button, int oldx,int oldy){
		if (mouseOverSelection())
			execOption();
	}

	public void keyPressed(int key, char c) {
		switch (key) {
		case Input.KEY_DOWN: case Input.KEY_S:
			if (selection < nbrOption - 1)
				selection++;
			else
				selection = 0;
			break;
		case Input.KEY_UP: case Input.KEY_Z:
			if (selection > 0)
				selection--;
			else
				selection = nbrOption - 1;
			break;
		case Input.KEY_ENTER: case Input.KEY_SPACE:
			execOption();
			break;
		case Input.KEY_ESCAPE:
			game.enterState(4 /* MainMenu */);
			break;
		}
	}

	public void execOption() {
		switch (selection) {
		case 0:
			game.enterState(4 /* MainMenu */, new FadeOutTransition(), new FadeInTransition());
			break;
		case 1:
			container.exit();
			break;
		}
	}

}
