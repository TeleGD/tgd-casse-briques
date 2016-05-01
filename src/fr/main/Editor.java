package fr.main;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;
import org.newdawn.slick.state.transition.FadeInTransition;
import org.newdawn.slick.state.transition.FadeOutTransition;

import fr.editor.LevelEditor;
import fr.menus.MainMenu;

public class Editor extends BasicGameState{
	
	
	public static int ID = 9;
	public static LevelEditor editor;
	private static GameContainer container;
	private static StateBasedGame game;
	
	@Override
	public void init(GameContainer arg0, StateBasedGame arg1) throws SlickException {
		editor=new LevelEditor();
		container = arg0;
		game = arg1;
	}

	@Override
	public void render(GameContainer arg0, StateBasedGame arg1, Graphics arg2) throws SlickException {
		editor.render(arg0, arg1, arg2);
		
	}

	@Override
	public void update(GameContainer arg0, StateBasedGame arg1, int arg2) throws SlickException {
		editor.update(arg0, arg1, arg2);
	}

	@Override
	public int getID() {
		return ID;
	}
	
	public void keyReleased(int key, char c) {
		//Player.keyReleased(key, c);
	}


	public void keyPressed(int key, char c) {
		//Player.keyPressed(key, c);
		if(key == Input.KEY_ESCAPE){
			game.enterState(MainMenu.ID, new FadeOutTransition(),
					new FadeInTransition());
		}
		

		editor.keyPressed(key,c);
	}
	

	
	public void mouseDragged(int oldx,int  oldy, int newx,int  newy){
		editor.mouseDragged(oldx,oldy, newx,  newy);
	}
	public void mouseReleased(int button, int x,int y){
		editor.mouseReleased(button, x,  y);
	}
	public void mousePressed(int button, int oldx,int oldy){
		editor.mousePressed(button, oldx, oldy);
		
	}

	
}
