import java.awt.DisplayMode;
import java.awt.GraphicsEnvironment;

import javax.swing.JFrame;
import javax.swing.JOptionPane;

import org.newdawn.slick.AppGameContainer;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.StateBasedGame;

import app.AppLoader;

public final class Main {

	public static final void main(String[] arguments) throws SlickException {
		String title = "Casse-briques";
		int width = 800;
		int height = 600;
		boolean fullscreen = false;
		String request = "Voulez-vous jouer en plein écran ?";
		String[] options = new String[] {
			"Oui",
			"Non"
		};
		JFrame frame = new JFrame();
		frame.setIconImage(AppLoader.loadIcon("/images/icon.png").getImage());
		int returnValue = JOptionPane.showOptionDialog(
			frame,
			request,
			title,
			JOptionPane.YES_NO_OPTION,
			JOptionPane.QUESTION_MESSAGE,
			null,
			options,
			options[0]
		);
		frame.dispose();
		if (returnValue == -1) {
			return;
		}
		if (returnValue == 0) {
			DisplayMode display = GraphicsEnvironment.getLocalGraphicsEnvironment().getDefaultScreenDevice().getDisplayMode();
			width = display.getWidth();
			height = display.getHeight();
			fullscreen = true;
		}
		StateBasedGame game = new StateBasedGame(title) {

			@Override
			public void initStatesList(GameContainer container) {
				this.addState(new games.casseBriques.menus.WelcomeMenu(8));
				this.addState(new games.casseBriques.menus.MainMenu(4));
				this.addState(new games.casseBriques.menus.ConfirmMenu(1));
				this.addState(new games.casseBriques.menus.CreditsMenu(2));
				this.addState(new games.casseBriques.menus.GameOverMenu(3));
				this.addState(new pages.Pause(6));
				this.addState(new games.casseBriques.menus.ScoresMenu(7));
				this.addState(new games.casseBriques.menus.MissionMenu(5));
				this.addState(new games.casseBriques.menus.LevelSelectorMenu(10));
				this.addState(new games.casseBriques.Editor(9));
				this.addState(new games.casseBriques.World(0));
			}

		};
		AppGameContainer container = new AppGameContainer(game, width, height, fullscreen);
		container.setTargetFrameRate(60);
		container.setVSync(true);
		container.setShowFPS(false);
		container.setIcon(AppLoader.resolve("/images/icon.png"));
		container.start();
	}

}
