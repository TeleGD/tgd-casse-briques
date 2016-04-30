package fr.entity;

import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.StateBasedGame;

import fr.util.Movable;
import fr.util.Rectangle;

public class Player extends Movable implements Rectangle {

	private int compt=0;
	
	public Player(){
		this.x=100;
		this.y=100;
		this.height=32;
		this.width=128;
	}
	
	@Override
	public double getY() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public double getX() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public double getWidth() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public double getHeight() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public void render(GameContainer container, StateBasedGame game, Graphics g) throws SlickException {
		g.setColor(Color.green);
		g.fillRect((float)x, (float)y, (float)width, (float)height);
	}

	@Override
	public void update(GameContainer container, StateBasedGame game, int delta) throws SlickException {
		this.x=300+200*Math.sin(compt);
		compt+=1;
		
	}

	public void keyReleased(int key, char c) {
		// TODO Auto-generated method stub
		
	}

	public void keyPressed(int key, char c) {
		// TODO Auto-generated method stub
		
	}

	

}
