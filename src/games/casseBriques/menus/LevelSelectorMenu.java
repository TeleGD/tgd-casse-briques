package games.casseBriques.menus;

import java.io.BufferedReader;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.List;

import org.newdawn.slick.Color;
import org.newdawn.slick.Font;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Input;
import org.newdawn.slick.state.StateBasedGame;
import org.newdawn.slick.state.transition.FadeInTransition;
import org.newdawn.slick.state.transition.FadeOutTransition;

import app.AppFont;
import app.AppLoader;
import app.AppMenu;
import app.elements.MenuItem;

import games.casseBriques.Editor;
import games.casseBriques.World;

public class LevelSelectorMenu extends AppMenu {

	private Font font1;
	private Font font2;
	private boolean custom;
	private MenuItem selectedItem = null;
	private int selectedSubItem = 0;

	public LevelSelectorMenu(int ID) {
		super(ID);
	}

	@Override
	public void init(GameContainer container, StateBasedGame game) {
		super.initSize(container, game, 600, 400);
		super.init(container, game);
		this.setTitle("Selection");
		this.setHint("SELECT A LEVEL");

		font1 = AppLoader.loadFont("/fonts/vt323.ttf", AppFont.BOLD, 30);

		font2 = AppLoader.loadFont("/fonts/vt323.ttf", AppFont.BOLD, 24);
	}

	public void reload(GameContainer container, StateBasedGame game, boolean custom) {
		this.custom = custom;
		String levels;
		if (custom) {
			levels = AppLoader.restoreData("/casseBriques/levels.txt");
			if (levels == null) {
				levels = "";
			}
		} else {
			levels = AppLoader.loadData("/data/casseBriques/levels.txt");
		}
		BufferedReader reader = new BufferedReader(new StringReader(levels));
		List<MenuItem> items = new ArrayList<MenuItem>();
		String line;
		try {
			while ((line = reader.readLine()) != null) {
				items.add(new MenuItem(line) {
					public void itemSelected() {
						if (!LevelSelectorMenu.this.custom || LevelSelectorMenu.this.selectedItem != null) {
							World world = (World) game.getState(3);
							world.campaign = false;
							world.currentLevel = this.getContent();
							world.load(world.currentLevel, LevelSelectorMenu.this.custom);
							game.enterState(3 /* World */, new FadeOutTransition(), new FadeInTransition());
						} else {
							LevelSelectorMenu.this.selectedItem = this;
						}
					}
				});
			}
			reader.close();
		} catch (Exception error) {}
		items.add(new MenuItem("Retour") {
			public void itemSelected() {
				game.enterState(1 /* Choice */, new FadeOutTransition(), new FadeInTransition());
			}
		});
		this.setSubtitle(custom ? "Niveaux personnalisés" : "Niveaux intégrés");
		this.setMenu(items);
		this.selectedItem = null;
		this.selectedSubItem = 0;
	}

	public void update(GameContainer container, StateBasedGame game, int delta) {
		if (this.selectedItem == null) {
			super.update(container, game, delta);
		} else {
			Input input = container.getInput();
			boolean BUTTON_A = input.isKeyPressed(Input.KEY_ENTER);
			boolean BUTTON_B = input.isKeyPressed(Input.KEY_ESCAPE);
			boolean KEY_UP = input.isKeyPressed(Input.KEY_UP);
			boolean KEY_DOWN = input.isKeyPressed(Input.KEY_DOWN);
			boolean BUTTON_UP = KEY_UP && !KEY_DOWN;
			boolean BUTTON_DOWN = KEY_DOWN && !KEY_UP;
			if (BUTTON_A) {
				if (this.selectedSubItem == 0) {
					this.selectedItem.itemSelected();
				} else if (this.selectedSubItem == 1) {
					Editor editor = (Editor) game.getState(7);
					editor.reload(this.selectedItem.getContent());
					game.enterState(7 /* Editor */, new FadeOutTransition(), new FadeInTransition());
				} else if (this.selectedSubItem == 2) {
					List<String> items = new ArrayList<String>();
					List<MenuItem> menu = this.getMenu();
					for (int i = 0, li = menu.size() - 1; i < li; ++i) {
						MenuItem item = menu.get(i);
						if (item != this.selectedItem) {
							items.add(item.getContent());
						}
					}
					AppLoader.saveData("/casseBriques/levels.txt", String.join("\n", items));
					AppLoader.saveData("/casseBriques/levels/" + this.selectedItem.getContent() + ".txt", null);
					this.reload(container, game, true);
				} else {
					this.selectedItem = null;
				}
			}
			if (BUTTON_B) {
				this.selectedItem = null;
			}
			if (BUTTON_UP) {
				if (this.selectedSubItem > 0) {
					this.selectedSubItem--;
				} else {
					this.selectedSubItem = 3;
				}
			}
			if (BUTTON_DOWN) {
				if (this.selectedSubItem < 3) {
					this.selectedSubItem++;
				} else {
					this.selectedSubItem = 0;
				}
			}
		}
	}

	public void render(GameContainer container, StateBasedGame game, Graphics g) {
		super.render(container, game, g);
		if (this.selectedItem != null) {
			g.setColor(Color.red);
			g.fillRoundRect(200, 200, 400, 220,20,20);
			g.setColor(Color.black);
			g.fillRoundRect(202, 202, 396, 216,20,20);
			g.setColor(Color.red);
			g.setFont(font1);
			g.drawString("Que faire sur " + this.selectedItem.getContent() + " ?", 300, 220);
			g.setColor(Color.white);
			g.setFont(font2);
			g.drawString("Jouer", 300, 283);
			g.drawString("Modifier", 300, 313);
			g.drawString("Supprimer", 300, 343);
			g.drawString("Retour", 300, 373);
			g.drawString(">>", 273, 283+30*this.selectedSubItem);
		}
	}

}
