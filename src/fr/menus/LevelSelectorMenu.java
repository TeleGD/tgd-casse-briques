package fr.menus;
import java.awt.Font;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

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

public class LevelSelectorMenu extends BasicGameState {

	public static int ID = 10;

	static TrueTypeFont font1;

	private String nom = "Selection du niveau";

	private static String[] items;

	private boolean popup = false;

	public static int nbrOption;

	public String[] getItems() {
		return this.items;
	}

	//private Image background;

	static GameContainer container;
	static StateBasedGame game;

	static int selection = 0;

	static int selectionPopup = 0;


	public static void reload() {
		items = (new File("res/levels")).list(); // TODO make multiplatform
		nbrOption = items.length;
		for (int i = 0; i < items.length; i++)
			if (items[i].endsWith(".txt"))
				items[i] = items[i].substring(0, items[i].length()-4);
		selection = 0;
	}

	public void init(GameContainer container, StateBasedGame game)
			throws SlickException {
		this.container = container;
		// container.setShowFPS(false);
		this.game = game;

		//background = new Image("sprites/0001.png");

		Font titre1Font = new Font("Kalinga", Font.BOLD, 12);
		font1 = new TrueTypeFont(titre1Font, false);

		reload();

	}

	public void update(GameContainer container, StateBasedGame game, int delta)
			throws SlickException {


		if (mouseOverSelection() && !popup) {
			int y = 600-Mouse.getY();
			selection = (y-160)/30;
		} else if (mouseOverPopup()) {
			int y = 600-Mouse.getY();
			selectionPopup = (y-280)/30;
		}

	}

	private boolean mouseOverSelection() {
		int x = Mouse.getX();
		int y = 600-Mouse.getY();
		return (   x>250
				&& x<450
				&& y>160
				&& y<160+nbrOption*30);
	}

	private boolean mouseOverPopup() {
		int x = Mouse.getX();
		int y = 600-Mouse.getY();
		return (   x>290
				&& x<490
				&& y>280
				&& y<280+3*30);
	}

	public void render(GameContainer container, StateBasedGame game, Graphics g)
			throws SlickException {
		//g.drawImage(background, 0, 0);

		g.setColor(Color.red);
		g.setFont(font1);
		g.drawString(this.nom, 250, 120);

		g.setColor(Color.white);

		for (int i = 0; i < nbrOption; i++) {
			g.drawString(this.items[i], 260, 160 + 30 * i);
		}

		g.drawString(">>", 240, 160 + 30 * selection);

		if (popup) {

			g.setColor(Color.red);
			g.fillRoundRect(200, 200, 400, 200,20,20);
			g.setColor(Color.black);
			g.fillRoundRect(202, 202, 396, 196,20,20);
			g.setColor(Color.white);


			Font titre1Font = new Font("Kalinga", Font.BOLD, 15);
			TrueTypeFont font1 = new TrueTypeFont(titre1Font, false);
			g.setFont(font1);
			g.drawString("Que faire sur "+items[selection]+ " ?", 300, 220);

			titre1Font = new Font("Kalinga", Font.BOLD, 12);
		    font1 = new TrueTypeFont(titre1Font, false);
			g.setFont(font1);
			g.drawString("Jouer", 300, 280);
			g.drawString("Modifier", 300, 310);
			g.drawString("Spprimer", 300, 340);

			g.drawString(">>", 280, 280+30*selectionPopup);

		}


	}

	@Override
	public void keyPressed(int key, char c) {
		switch (key) {
		case Input.KEY_DOWN:
			if (!popup) {
				if (selection < nbrOption - 1)
					selection++;
				else
					selection = 0;
			} else {
				if (selectionPopup < 2)
					selectionPopup++;
				else
					selectionPopup = 0;
			}
			break;
		case Input.KEY_UP:
			if (!popup) {
				if (selection > 0)
					selection--;
				else
					selection = nbrOption - 1;
			} else {
				if (selectionPopup > 0)
					selectionPopup--;
				else
					selectionPopup = 2;
			}
			break;
		case Input.KEY_ENTER:
			if (!popup) {
				popup = true;
				selectionPopup = 0;
			} else {
				popup = false;
				execOption();
			}
			break;

		case Input.KEY_ESCAPE:
			if (!popup)
				game.enterState(MainMenu.ID, new FadeOutTransition(),
					new FadeInTransition());
			else {
				popup = false;
				selectionPopup = 0;
			}
			break;
		}
	}

	public void execOption() {
		switch (selectionPopup) {
		case 0:
			World.gameMode = World.mode.CUSTOM;
			World.reload(items[selection]+".txt");
			game.enterState(World.ID, new FadeOutTransition(),
					new FadeInTransition());
			break;
		case 1:
			Editor.reload(items[selection]+".txt");
			game.enterState(Editor.ID, new FadeOutTransition(),
					new FadeInTransition());
			break;

		case 2:
			try {
				Files.delete(Paths.get("levels"+File.separator+items[selection]+".txt"));
			} catch (IOException e) {
				e.printStackTrace();
			}
			reload();
			break;
		}
	}

	public void mousePressed(int button, int oldx,int oldy){
		if (mouseOverSelection() && !popup) {
			popup = true;
			selectionPopup = 0;
		} else if (mouseOverPopup()) {
			popup = false;
			execOption();
		}
	}

	public int getID() {
		return ID;
	}




}
