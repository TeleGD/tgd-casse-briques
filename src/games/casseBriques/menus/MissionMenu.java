package games.casseBriques.menus;

import java.util.ArrayList;
import java.util.Random;

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
	public int numMessage;

	private String nom = "Mission :";

	//private Image background;
	GameContainer container;
	StateBasedGame game;

	public ArrayList<String> generateText(int lineSize) {
		ArrayList<String> res = new ArrayList<String>();
		Random r = new Random();

		int numMission = r.nextInt(2);
		numMessage = numMission;
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
		container.setShowFPS(false);
		this.game = game;
		//background = new Image("sprites/0005.png");

		font5 = AppLoader.loadFont("Courant", AppFont.BOLD, 18); // TODO: trouver une fonte équivalente

		font6 = AppLoader.loadFont("Courant", AppFont.BOLD, 13); // TODO: trouver une fonte équivalente

		txt1[0]="Un matin de 1976, sur le chemin de l'ecole, le petit Jimmy fit une rencontre qui allait changer sa vie.";
        txt2[0]=" Cependant, ce jeu ne parle pas de cette rencontre. Il ne parle pas non plus de Jimmy. En realite, ce jeu parle d'un probleme bien plus important :";
        txt3[0]=" la menace que representent les ouvriers extremistes travaillant dans le BTP. Votre mission est de detruire leurs constructions machiaveliques afin de sauver l'humanite de la domination des parpaings.";
		textList = generateText(40);
	}

	public void update(GameContainer container, StateBasedGame game, int delta) {}

	public void render(GameContainer container, StateBasedGame game, Graphics g) {
		//g.drawImage(background, 0, 0);

		g.setColor(Color.red);
		g.setFont(font5);

		g.drawString(this.nom, 90, 235);

		g.setColor(Color.white);
		g.setFont(font6);

		for (int j = 0; j < textList.size(); j++) {
			g.drawString(textList.get(j), 75, 258 + 13 * j);
		}

		int lines = 12;
		g.drawString(">>  Continuer", 75, 255 + 25 + lines * 13);
	}

	public void reset() {}

	public void mousePressed(int button, int oldx,int oldy){
		if (mouseOverSelection())
			//World.reset();
			game.enterState(0 /* World */);
	}

	@Override
	public void keyPressed(int key, char c) {
		switch (key) {
		case Input.KEY_ENTER:
			game.enterState(0 /* World */, new FadeOutTransition(),
					new FadeInTransition());
			//World.reset();
			break;
		case Input.KEY_ESCAPE:
			game.enterState(4 /* MainMenu */, new FadeOutTransition(),
					new FadeInTransition());
			break;
		}
	}

}
