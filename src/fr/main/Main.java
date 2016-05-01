package fr.main;

import org.newdawn.slick.AppGameContainer;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.StateBasedGame;

import fr.main.World;
import fr.menus.*;


public class Main extends StateBasedGame {
	

	public static void main(String[] args) throws SlickException {
		AppGameContainer app = new AppGameContainer(new Main(),800, 600, false);
		app.setTargetFrameRate(60);
		app.setVSync(true);
		app.setShowFPS(true);
		app.start();
	}
	

	public Main() {
		//super("LE CACHE BRIQUE CH'EST UN CHEU DE PORTOUGESH");
		super("L'action de casser des parpaings est un amusement caracteristique de la population portugaise");
	}



	@Override
	public void initStatesList(GameContainer container) throws SlickException {
		
		addState(new WelcomeMenu());
		addState(new MainMenu());
		addState(new ConfirmMenu());
		addState(new CreditsMenu());
		addState(new GameOverMenu());
		//addState(new HelpMenu());
		addState(new PauseMenu());
		addState(new ScoresMenu());
		addState(new MissionMenu());
		addState(new LevelSelectorMenu());
		addState(new Editor());
		addState(new World());
		
	}


}
