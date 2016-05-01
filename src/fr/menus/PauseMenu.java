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


public class PauseMenu extends BasicGameState {
	private String nom = "Pause";
	private String[] items = { "Continuer", "Quitter" };

	public int nbrOption = items.length;

	public static int ID = 6;
	
	static TrueTypeFont font1;

	public String[] getItems() {
		return this.items;
	}
	
	private Image background;

	static StateBasedGame game;

	static GameContainer container;
	int selection = 0;

	public PauseMenu() {

	}

	@Override
	public void init(GameContainer container, StateBasedGame game)
			throws SlickException {
		this.container = container;
		container.setShowFPS(false);
		this.game = game;
		//background = new Image("Images/background2.png");
		
		Font titre1Font = new Font("Kalinga", Font.BOLD, 26);
    	font1 = new TrueTypeFont(titre1Font, false);
    	
    	
		
	}
	
	private boolean mouseOverSelection() {
		int x = Mouse.getX();
		int y = 600-Mouse.getY();
		return (   x>300
				&& x<550
				&& y>280
				&& y<280+nbrOption*50);
	}

	public void update(GameContainer container, StateBasedGame game, int delta)
			throws SlickException {

		if (mouseOverSelection()) {
			int x = Mouse.getX();
			int y = 600-Mouse.getY();
			selection = (y-280)/50;
		}
	}

	@Override
	public void render(GameContainer container, StateBasedGame game, Graphics g)
			throws SlickException {

		//g.drawImage(background, 0, 0);

		g.setColor(Color.blue);
		g.setFont(font1);

		g.drawString(this.nom, 200, 200);

		g.setColor(Color.white);

		for (int i = 0; i < nbrOption; i++) {
			g.drawString(this.items[i], 300, 280 + 50 * i);
		}

		g.drawString(">>", 230, 280 + 50 * selection);

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
			game.enterState(World.ID, new FadeOutTransition(), new FadeInTransition());
			break;

		}
	}
	
	public void mousePressed(int button, int oldx,int oldy){
		if (mouseOverSelection())
			execOption();
	}

	public void execOption() {

		switch (selection) {
		case 0:
			game.enterState(World.ID, new FadeOutTransition(), new FadeInTransition());
			break;
		case 1:
			game.enterState(MainMenu.ID, new FadeOutTransition(), new FadeInTransition());
			//World.MMenu.loop(); //(music)
			break;
		}
	}

	public int getID() {
		return ID;
	}

}
