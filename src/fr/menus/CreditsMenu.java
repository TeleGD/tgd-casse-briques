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


public class CreditsMenu extends BasicGameState {

	public static int ID = 2;
	
	static TrueTypeFont font5;
	static TrueTypeFont font6;

	private String nom = "Credits :";
	private String[] items = {
			"dev1",
			"dev2",
			"dev3",
			"dev4",
			"dev5",
			"dev6",
			"dev7",
			"dev8",
			"",
			"Club TeleGame Design,",
			"      Telecom Nancy",
			"",
			" Retour Menu" };

	public int nbrOption = items.length;

	public String[] getItems() {
		return this.items;
	}

	//private Image background;
	
	static GameContainer container;
	static StateBasedGame game;
	int selection = nbrOption - 1;

	public void init(GameContainer container, StateBasedGame game)
			throws SlickException {
		this.container = container;
		container.setShowFPS(false);
		this.game = game;
		//background = new Image("sprites/0004.png");
		
    	Font titre5Font = new Font("Courant", Font.BOLD, 18);
    	font5 = new TrueTypeFont(titre5Font, false);
		
		Font titre6Font = new Font("Courant", Font.BOLD, 13);
    	font6 = new TrueTypeFont(titre6Font, false);
	}
	
	private boolean mouseOverSelection() {
		int x = Mouse.getX();
		int y = 600-Mouse.getY();
		return (   x>238
				&& x<438
				&& y>390 + 13 * (nbrOption-1)
				&& y<390 + 13 * nbrOption);
	}

	public void update(GameContainer container, StateBasedGame game, int delta)
			throws SlickException {
		
	}

	public void render(GameContainer container, StateBasedGame game, Graphics g)
			throws SlickException {
		
		//g.drawImage(background, 0, 0);

		g.setColor(Color.red);
		g.setFont(font5);

		g.drawString(this.nom, 255, 365);

		g.setColor(Color.white);
		g.setFont(font6);

		for (int i = 0; i < nbrOption; i++) {
			g.drawString(this.items[i], 238, 390 + 13 * i);
		}
		
		g.drawString(">>", 225, 390 + 13 * selection);
		
	}

	public void mousePressed(int button, int oldx,int oldy){
		if (mouseOverSelection())
			game.enterState(MainMenu.ID);
	}
	
	@Override
	public void keyPressed(int key, char c) {
		switch (key) {
		case Input.KEY_ENTER: case Input.KEY_SPACE:
			game.enterState(MainMenu.ID);
			break;
		case Input.KEY_ESCAPE:
			game.enterState(MainMenu.ID);
			break;
		}
	}


	public int getID() {
		return ID;
	}
}
