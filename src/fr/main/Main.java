package fr.main;

import org.newdawn.slick.AppGameContainer;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.StateBasedGame;

import fr.main.World;
import fr.menus.ConfirmMenu;
import fr.menus.CreditsMenu;
import fr.menus.GameOverMenu;
import fr.menus.MainMenu;
import fr.menus.MissionMenu;
import fr.menus.PauseMenu;
import fr.menus.ScoresMenu;
import fr.menus.WelcomeMenu;


public class Main extends StateBasedGame {
	

	public static void main(String[] args) throws SlickException {
		AppGameContainer app = new AppGameContainer(new Main(),800, 600, false);
		app.setTargetFrameRate(60);
		app.setVSync(true);
		app.setShowFPS(true);
		app.start();
	}
	

	public Main() {
		super("Ouep");
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
		addState(new World());
		
	}


}
