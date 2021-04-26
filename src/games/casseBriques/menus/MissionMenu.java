package games.casseBriques.menus;

import java.util.ArrayList;

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

public class MissionMenu extends BasicGameState {

	private int ID;

	Font font5;
	Font font6;

	public String[] txt1 = new String[2];
	public String[] txt2 = new String[2];
	public String[] txt3 = new String[2];
	public ArrayList<String> textList;

	private String nom = "Mission :";

	GameContainer container;
	StateBasedGame game;

	public ArrayList<String> generateText(int lineSize) {
		ArrayList<String> res = new ArrayList<String>();
		String text = txt1[0]+txt2[0]+txt3[0];
		int lastSpaceIndex = 0;
		int chunkStart = 0;
		String tmp = "";

		for (int i = 0; i < text.length(); i++) {
			char c = text.charAt(i);

			if (c == ' ') {
				lastSpaceIndex = i;
			}

			if (i - chunkStart == lineSize) {
				tmp = text.substring(chunkStart, lastSpaceIndex);
				res.add(tmp);
				chunkStart = lastSpaceIndex;
			}
		}

		tmp = text.substring(chunkStart, text.length());
		res.add(tmp);

		return res;
	}

	public MissionMenu(int ID) {
		this.ID = ID;
	}

	@Override
	public int getID() {
		return this.ID;
	}

	private boolean mouseOverSelection() {
		int x = Mouse.getX();
		int y = 600-Mouse.getY();
		return (   x>72
				&& x<275
				&& y>280+12*13
				&& y<280+13*13);
	}

	public void init(GameContainer container, StateBasedGame game) {
		this.container = container;
		this.game = game;

		font5 = AppLoader.loadFont("/fonts/press-start-2p.ttf", AppFont.BOLD, 40);

		font6 = AppLoader.loadFont("/fonts/vt323.ttf", AppFont.BOLD, 24);

		txt1[0]=" Un matin de 1976, sur le chemin de l'école, le petit Jimmy fit une rencontre qui allait changer sa vie.";
        txt2[0]=" Cependant, ce jeu ne parle pas de cette rencontre. Il ne parle pas non plus de Jimmy. En realité, ce jeu parle d'un problème bien plus important :";
        txt3[0]=" la menace que representent les ouvriers extrémistes travaillant dans le BTP. Votre mission est de détruire leurs constructions machiavéliques afin de sauver l'humanité de la domination des parpaings.";
		textList = generateText(40);
	}

	public void update(GameContainer container, StateBasedGame game, int delta) {}

	public void render(GameContainer container, StateBasedGame game, Graphics g) {
		g.setColor(Color.red);
		g.setFont(font5);

		g.drawString(this.nom, 200, 30);

		g.setColor(Color.white);
		g.setFont(font6);
		int lines = textList.size();

		for (int j = 0; j < lines; j++) {
			g.drawString(textList.get(j), 200, 103 + 30 * j);
		}

		g.drawString("Continuer", 300, 103 + (lines + 1) * 30);
		g.drawString(">>", 273, 103 + (lines + 1) * 30);
	}

	public void mousePressed(int button, int oldx,int oldy){
		if (mouseOverSelection())
			game.enterState(3 /* World */);
	}

	@Override
	public void keyPressed(int key, char c) {
		switch (key) {
		case Input.KEY_ENTER:
			game.enterState(3 /* World */, new FadeOutTransition(),
					new FadeInTransition());
			break;
		case Input.KEY_ESCAPE:
			game.enterState(1 /* Choice */, new FadeOutTransition(),
					new FadeInTransition());
			break;
		}
	}

}
