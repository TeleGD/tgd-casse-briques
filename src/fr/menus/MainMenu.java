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

import fr.main.Editor;
import fr.main.World;

public class MainMenu extends BasicGameState {

	public static int ID = 4;

	static TrueTypeFont font1;

	private String nom = "Menu Principal";
	private String[] items = { "Campagne", "Multijoueur", "Niveaux Custom", "Editeur", "Quitter" };

	public int nbrOption = items.length;

	public String[] getItems() {
		return this.items;
	}

	//private Image background;

	static GameContainer container;
	static StateBasedGame game;

	int selection = 0;

	public void init(GameContainer container, StateBasedGame game)
			throws SlickException {
		this.container = container;
		// container.setShowFPS(false);
		this.game = game;

		//background = new Image("sprites/0001.png");

		Font titre1Font = new Font("Kalinga", Font.BOLD, 12);
		font1 = new TrueTypeFont(titre1Font, false);

	}

	public void update(GameContainer container, StateBasedGame game, int delta)
			throws SlickException {
		
		if (mouseOverSelection()) {
			int x = Mouse.getX();
			int y = 600-Mouse.getY();
			selection = (y-360)/30;
		}
		
	}

	private boolean mouseOverSelection() {
		int x = Mouse.getX();
		int y = 600-Mouse.getY();
		return (   x>550
				&& x<750
				&& y>360
				&& y<360+nbrOption*30);
	}

	public void render(GameContainer container, StateBasedGame game, Graphics g)
			throws SlickException {
		//g.drawImage(background, 0, 0);

		g.setColor(Color.red);
		g.setFont(font1);
		g.drawString(this.nom, 550, 320);

		g.setColor(Color.white);

		for (int i = 0; i < nbrOption; i++) {
			g.drawString(this.items[i], 560, 360 + 30 * i);
		}
		
		g.drawString(">>", 540, 360 + 30 * selection);

			
	}

	@Override
	public void keyPressed(int key, char c) {
		switch (key) {
		case Input.KEY_DOWN:
			if (selection < nbrOption - 1)
				selection++;
			else
				selection = 0;
			break;
		case Input.KEY_UP:
			if (selection > 0)
				selection--;
			else
				selection = nbrOption - 1;
			break;
		case Input.KEY_ENTER:
			execOption();
			break;

		case Input.KEY_ESCAPE:
			game.enterState(ConfirmMenu.ID, new FadeOutTransition(),
					new FadeInTransition());
			break;
		case Input.KEY_C:
			game.enterState(CreditsMenu.ID, new FadeOutTransition(),
					new FadeInTransition());
			break;
		case Input.KEY_M:
			game.enterState(MissionMenu.ID, new FadeOutTransition(),
					new FadeInTransition());
			break;
		}
	}

	public void execOption() {
		switch (selection) {
		case 0:
			//World.gameMode = World.mode.CAMPAIGN;
			game.enterState(MissionMenu.ID, new FadeOutTransition(),
					new FadeInTransition());
			break;

		case 1:
			//World.gameMode = World.mode.MULTI;
			game.enterState(World.ID, new FadeOutTransition(),
					new FadeInTransition());
			break;
		
		case 2:
			LevelSelectorMenu.reload();
			game.enterState(LevelSelectorMenu.ID, new FadeOutTransition(),
					new FadeInTransition());
			break;
		 
		case 3:
			game.enterState(Editor.ID, new FadeOutTransition(),
					new FadeInTransition());
			break;
		case 4:
			game.enterState(ConfirmMenu.ID, new FadeOutTransition(),
					new FadeInTransition());
			break;
		}
	}
	
	public void mousePressed(int button, int oldx,int oldy){
		if (mouseOverSelection())
			execOption();
	}
	
	public int getID() {
		return ID;
	}


}
