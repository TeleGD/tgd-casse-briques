package pages;

import games.casseBriques.Editor;
import java.util.Arrays;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.state.StateBasedGame;
import org.newdawn.slick.state.transition.FadeInTransition;
import org.newdawn.slick.state.transition.FadeOutTransition;

import app.AppMenu;
import app.elements.MenuItem;

import games.casseBriques.World;
import games.casseBriques.menus.LevelSelectorMenu;

public class Choice extends AppMenu {

	public Choice(int ID) {
		super(ID);
	}

	@Override
	public void init(GameContainer container, StateBasedGame game) {
		super.initSize(container, game, 600, 400);
		super.init(container, game);
		this.setTitle("Casse-briques");
		this.setSubtitle("Si seulement Jimmy avait su...");
		this.setMenu(Arrays.asList(new MenuItem[] {
			new MenuItem("Campagne") {
				public void itemSelected() {
					World world = (World) game.getState(3);
					world.campaign = true;
					world.currentCampaignLevel = 1;
					world.currentLevel = "niveau1";
					world.load(world.currentLevel, false);
					game.enterState(5 /* MissionMenu */, new FadeOutTransition(), new FadeInTransition());
				}
			},
			new MenuItem("Niveaux intégrés") {
				public void itemSelected() {
					LevelSelectorMenu levelSelectorMenu = (LevelSelectorMenu) game.getState(6);
					levelSelectorMenu.reload(container, game, false);
					game.enterState(6 /* LevelSelectorMenu */, new FadeOutTransition(), new FadeInTransition());
				}
			},
			new MenuItem("Niveaux personnalisés") {
				public void itemSelected() {
					LevelSelectorMenu levelSelectorMenu = (LevelSelectorMenu) game.getState(6);
					levelSelectorMenu.reload(container, game, true);
					game.enterState(6 /* LevelSelectorMenu */, new FadeOutTransition(), new FadeInTransition());
				}
			},
			new MenuItem("Editeur") {
				public void itemSelected() {
					Editor editor = (Editor) game.getState(7);
					editor.reload();
					game.enterState(7 /* Editor */, new FadeOutTransition(), new FadeInTransition());
				}
			},
			new MenuItem("Retour") {
				public void itemSelected() {
					game.enterState(0 /* Welcome */, new FadeOutTransition(), new FadeInTransition());
				}
			}
		}));
		this.setHint("BY TELEGAME DESIGN");
	}

}
