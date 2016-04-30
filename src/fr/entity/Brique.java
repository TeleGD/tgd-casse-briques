package fr.entity;

import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.StateBasedGame;

import fr.util.Rectangle;

public class Brique extends Entity implements Rectangle{
	
	public int life;
	
	public Brique(int x, int y){
		this.x=x;
		this.y=y;
		this.width=64;
		this.height=32;
	}
	
	@Override
	public void render(GameContainer container, StateBasedGame game, Graphics g) throws SlickException {
		g.setColor(Color.blue);
		g.fillRect((float)x,(float)y,(float)width,(float)height);
	}

	@Override
	public void update(GameContainer container, StateBasedGame game, int delta) throws SlickException {
		// TODO Auto-generated method stub
		
	}

}
