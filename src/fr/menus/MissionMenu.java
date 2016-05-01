package fr.menus;

import java.awt.Font;
import java.util.ArrayList;
import java.util.Random;

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

public class MissionMenu extends BasicGameState {

	public static int ID = 5;

	static TrueTypeFont font5;
	static TrueTypeFont font6;

	public static String[] txt1 = new String[2];
	public static String[] txt2 = new String[2];
	public static String[] txt3 = new String[2];/*
	public static String[] planetName = { "Kepler-770-C", "Utopia", "Balmoran",
			"Sulituan", "Naeco", "Nihpuad" };
	public static String[] postName = { "g駭駻al 6 騁oiles en chef",
			"capitaine de section", "grand administrateur", "chef artilleur" };
	public static ArrayList<String> textList;
	public static int numMessage;*/

	private String nom = "Mission :";

	//private Image background;
	static GameContainer container;
	static StateBasedGame game;

	
	/*public static ArrayList<String> generateText(int lineSize) {
		ArrayList<String> res = new ArrayList<String>();
		Random r = new Random();

		int numMission = r.nextInt(2);
		numMessage = numMission;
		int numPlanet = r.nextInt(planetName.length);
		int numPost = r.nextInt(postName.length);

		String text = txt1[numMission] + planetName[numPlanet]
				+ txt2[numMission] + postName[numPost] + txt3[numMission];
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
	}*/
	
	private boolean mouseOverSelection() {
		int x = Mouse.getX();
		int y = 600-Mouse.getY();
		return (   x>72
				&& x<275
				&& y>280+12*13
				&& y<280+13*13);
	}

	public void init(GameContainer container, StateBasedGame game)
			throws SlickException {
		this.container = container;
		container.setShowFPS(false);
		this.game = game;
		//background = new Image("sprites/0005.png");

		Font titre5Font = new Font("Courant", Font.BOLD, 18);
		font5 = new TrueTypeFont(titre5Font, false);

		Font titre6Font = new Font("Courant", Font.BOLD, 13);
		font6 = new TrueTypeFont(titre6Font, false);

		txt1[0]="Un beau jour de 1976, alors qu'il rentrait de l'ecole, le petit Jimmy fit une rencontre qui allait changer sa vie a tout jamais.";
        txt2[0]="Cependant, ce jeu ne parle pas de cette rencontre. Il ne parle pas non plus de Jimmy. En realite, ce jeu parle d'un probleme bien plus important :";
        txt3[0]="la menace que representent les ouvriers travaillant dans le BTP. Votre mission est de detruire leurs constructions machiaveliques.";
/*
		txt1[0] = "Depuis plusieurs ann馥s, la plan鑼e ";
		txt2[0] = " vit dans la peur de la menace spatio-terroriste. Sur une des derni鑽es plan鑼es de la galaxie poss馘ant encore des arbres naturels fournissant l'oxyg鈩e n馗essaire � la vie, l'馗onomie inter-stellaire est gangr駭馥 par le trafic de v馮騁aux. Ces pirates de l'espace poss鐡ent leur propre arm馥 et agissent en bande organis馥, se livrant � toutes sortes d'activit駸 anti-馗ologiques de grande ampleur. Vous venez d'黎re nomm� au poste de ";
		txt3[0] = " � la t黎e de la brigade d'intervention et de pr騅ision des attaques. Prenez les devants de votre escadron et faites le m駭age dans cette arm馥 de cosmobrigands !";

		txt1[1] = "Des id饌listes sovi騁iques tentent de mettre en place une nouvelle r騅olution communiste ! Apr鑚 la migration de l'humanit� vers la plan鑼e ";
		txt2[1] = ", des groupuscules arm駸 ont tent� de prendre le pouvoir par la force. Gr稍e � la puissance du grand chef supr麥e, les camarades tentent d'envahir les bastions de l'ennemi capitaliste depuis leurs vaisseaux spatiaux. Si rien n'est fait pour les arr黎er, la face de ce nouveau monde pourrait changer � jamais ! En tant que ";
		txt3[1] = " de la contre-r騅olte capitaliste, vous avez la charge de tuer dans l'oeuf ce soul钁ement prol騁aire. Soyez fort, le sort de la plan鑼e est entre vos mains.";
 		
		textList = generateText(40);
		*/

	}

	public void update(GameContainer container, StateBasedGame game, int delta)
			throws SlickException {

	}

	public void render(GameContainer container, StateBasedGame game, Graphics g)
			throws SlickException {

		//g.drawImage(background, 0, 0);

		g.setColor(Color.red);
		g.setFont(font5);

		g.drawString(this.nom, 90, 235);

		g.setColor(Color.white);
		g.setFont(font6);

		/*for (int j = 0; j < textList.size(); j++) {
			g.drawString(textList.get(j), 75, 258 + 13 * j);
		}*/

		int lines = 12;
		g.drawString(">>  Continuer", 75, 255 + 25 + lines * 13);

	}

	public static void reset() {
		
	}

	public void mousePressed(int button, int oldx,int oldy){
		if (mouseOverSelection())
			//World.reset();
			game.enterState(World.ID);
	}
	
	@Override
	public void keyPressed(int key, char c) {
		switch (key) {
		case Input.KEY_ENTER:
			game.enterState(World.ID, new FadeOutTransition(),
					new FadeInTransition());
			//World.reset();
			break;
		case Input.KEY_ESCAPE:
			game.enterState(MainMenu.ID, new FadeOutTransition(),
					new FadeInTransition());
			break;
		}
	}

	public int getID() {
		return ID;
	}
}
