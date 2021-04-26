package games.casseBriques.menus;

import java.io.BufferedReader;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

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

import games.casseBriques.Editor;
import games.casseBriques.World;

public class LevelSelectorMenu extends BasicGameState {

	private int ID;

	Font font1;

	private String nom = "Selection du niveau";

	private String[] items;

	private boolean popup = false;

	public int nbrOption;

	public String[] getItems() {
		return this.items;
	}

	//private Image background;

	GameContainer container;
	StateBasedGame game;

	int selection = 0;

	int selectionPopup = 0;

	private boolean custom;

	public void reload(boolean custom) {
		this.custom = custom;
		String levels;
		if (custom) {
			levels = AppLoader.restoreData("/casseBriques/levels.txt");
		} else {
			levels = AppLoader.loadData("/data/casseBriques/levels.txt");
		}
		BufferedReader reader = new BufferedReader(new StringReader(levels));
		List<String> items = new ArrayList<String>();
		String line;
		try {
			while ((line = reader.readLine()) != null) {
				items.add(line);
			}
			reader.close();
		} catch (Exception error) {}
		this.nbrOption = items.size();
		this.items = items.toArray(new String[0]);
		this.selection = 0;
		this.selectionPopup = 0;
	}

	public LevelSelectorMenu(int ID) {
		this.ID = ID;
	}

	@Override
	public int getID() {
		return this.ID;
	}

	public void init(GameContainer container, StateBasedGame game) {
		this.container = container;
		// container.setShowFPS(false);
		this.game = game;

		//background = new Image("sprites/0001.png");

		font1 = AppLoader.loadFont("Kalinga", AppFont.BOLD, 12); // TODO: trouver une fonte équivalente
	}

	public void update(GameContainer container, StateBasedGame game, int delta) {
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

	public void render(GameContainer container, StateBasedGame game, Graphics g) {
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

			Font font1 = AppLoader.loadFont("Kalinga", AppFont.BOLD, 15); // TODO: trouver une fonte équivalente
			g.setFont(font1);
			g.drawString("Que faire sur "+items[selection]+ " ?", 300, 220);

			font1 = AppLoader.loadFont("Kalinga", AppFont.BOLD, 12); // TODO: trouver une fonte équivalente
			g.setFont(font1);
			g.drawString("Jouer", 300, 280);
			if (this.custom) {
				g.drawString("Modifier", 300, 310);
				g.drawString("Spprimer", 300, 340);
			}

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
			} else if (this.custom) {
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
			} else if (this.custom) {
				if (selectionPopup > 0)
					selectionPopup--;
				else
					selectionPopup = 2;
			}
			break;
		case Input.KEY_ENTER:
			if (popup || !this.custom) {
				popup = false;
				execOption();
			} else {
				popup = true;
				selectionPopup = 0;
			}
			break;

		case Input.KEY_ESCAPE:
			if (!popup)
				game.enterState(4 /* MainMenu */, new FadeOutTransition(),
					new FadeInTransition());
			else {
				popup = false;
				selectionPopup = 0;
			}
			break;
		}
	}

	public void execOption() {
		World world = (World) game.getState(0);
		Editor editor = (Editor) game.getState(9);
		if (!this.custom) {
			selectionPopup = 0;
		}
		switch (selectionPopup) {
		case 0:
			world.gameMode = World.mode.CUSTOM;
			world.currentLevel = items[selection];
			world.load(world.currentLevel, this.custom);
			game.enterState(0 /* World */, new FadeOutTransition(),
					new FadeInTransition());
			break;
		case 1:
			editor.reload(items[selection]);
			game.enterState(9 /* Editor */, new FadeOutTransition(),
					new FadeInTransition());
			break;

		case 2:
			String item = this.items[this.selection];
			List<String> items = new ArrayList<String>(Arrays.asList(this.items));
			items.remove(item);
			AppLoader.saveData("/casseBriques/levels.txt", String.join("\n", items));
			AppLoader.saveData("/casseBriques/levels/" + item + ".txt", null);
			reload(true);
			if (this.nbrOption == 0) {
				game.enterState(4 /* MainMenu */, new FadeOutTransition(), new FadeInTransition());
			}
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

}
