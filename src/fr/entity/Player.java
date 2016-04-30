package fr.entity;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.StateBasedGame;

import fr.util.Movable;
import fr.util.Rectangle;

public class Player extends Movable implements Rectangle{
	
	private double width=128;
	private double height=32;
	private boolean rightPress,leftPress,droitegauche;
	private int compt=0;
	
	@Override
	public void render(GameContainer container, StateBasedGame game, Graphics g) throws SlickException {
		g.fillRect((float)x, (float)y, (float)width, (float)height);
	}

	@Override
	public void update(GameContainer container, StateBasedGame game, int delta) throws SlickException {
		this.x=400+200*Math.sin(compt);
		compt+=1;
	}

public void keyReleased(int key, char c) {
		
		switch (key){	
			case Input.KEY_LEFT:
				leftPress=false;
			break;
			
			case Input.KEY_RIGHT:
				rightPress=false;
			break;
		}
		
		
	}

	public void keyPressed(int key, char c) {
		switch (key){
		case Input.KEY_LEFT:
			leftPress=true;
			droitegauche=false;
		break;
		case Input.KEY_RIGHT:
			rightPress=true;
			droitegauche=true;
		break;

		}
		
	}
	
	public Player(int X,int Y){
		this.x=X;
		this.y=Y;
	}

}
