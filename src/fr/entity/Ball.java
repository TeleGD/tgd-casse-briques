package fr.entity;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.StateBasedGame;

import fr.util.Circle;
import fr.util.Movable;

public class Ball extends Movable implements Circle{
	
	public int radius=16;
	
	@Override
	public int getRadius() {
		// TODO Auto-generated method stub
		return radius;
	}

	@Override
	public void render(GameContainer container, StateBasedGame game, Graphics g) throws SlickException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void update(GameContainer container, StateBasedGame game, int delta) throws SlickException {
		// TODO Auto-generated method stub
		
	}
	
	public Ball(double X,double Y){
		this.x=X;
		this.y=Y;
	}
}
