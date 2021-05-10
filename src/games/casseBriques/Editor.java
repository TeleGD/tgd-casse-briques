package games.casseBriques;

import java.io.BufferedReader;
import java.io.StringReader;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Input;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;
import org.newdawn.slick.state.transition.FadeInTransition;
import org.newdawn.slick.state.transition.FadeOutTransition;

import app.AppLoader;

import games.casseBriques.editor.LevelEditor;
import games.casseBriques.entities.Brique;

public class Editor extends BasicGameState{

	private int ID;
	public LevelEditor editor;
	private StateBasedGame game;

	public Editor(int ID) {
		this.ID = ID;
	}

	@Override
	public int getID() {
		return this.ID;
	}

	@Override
	public void init(GameContainer arg0, StateBasedGame arg1) {
		editor=new LevelEditor();
		game = arg1;
	}

	@Override
	public void render(GameContainer arg0, StateBasedGame arg1, Graphics arg2) {
		editor.render(arg0, arg1, arg2);
	}

	@Override
	public void update(GameContainer arg0, StateBasedGame arg1, int arg2) {}

	public void keyReleased(int key, char c) {
		//Player.keyReleased(key, c);
	}

	public void keyPressed(int key, char c) {
		//Player.keyPressed(key, c);
		if(key == Input.KEY_ESCAPE && !this.editor.getSauvegarder()){
			game.enterState(1 /* Choice */, new FadeOutTransition(),
					new FadeInTransition());
		}

		editor.keyPressed(key,c);
	}

	public void mouseDragged(int oldx,int  oldy, int newx,int  newy){
		editor.mouseDragged(oldx,oldy, newx,  newy);
	}

	public void mouseMoved(int oldx,int  oldy, int newx,int  newy){
		editor.mouseMoved(oldx,oldy, newx,  newy);
	}

	public void mouseReleased(int button, int x,int y){
		editor.mouseReleased(button, x,  y);
	}

	public void mousePressed(int button, int oldx,int oldy){
		editor.mousePressed(button, oldx, oldy);
	}

	public void mouseWheelMoved(int newValue){
		editor.mouseWheelMoved(newValue);
	}

	public void reload(String niveau) {
		editor.reload(niveau);
		String level = AppLoader.restoreData("/casseBriques/levels/" + niveau + ".txt");
		if (level == null) {
			level = "";
		}
		BufferedReader reader = new BufferedReader(new StringReader(level));
		String line;
		try {
			while ((line = reader.readLine()) != null) {
				Brique b=Brique.StringToBrique(null, line);
				if(b.getY()<400){
					editor.addBrique(b);
				}
			}
			reader.close();
		} catch (Exception error) {}
	}

	public void reload() {
		editor.reload("");
	}

}
