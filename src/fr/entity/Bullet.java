package fr.entity;

import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.StateBasedGame;

import fr.main.World;
import fr.util.Circle;
import fr.util.Movable;

public class Bullet extends Ball implements Circle{
	
	private boolean touched;
	
	public Bullet(){
		this.x=300;
		this.y=300;
		this.width=32;
		touched = false;
	}
	@Override
	public int getRadius() {
		// TODO Auto-generated method stub
		return (int)width;
	}

	@Override
	public void render(GameContainer container, StateBasedGame game, Graphics g) throws SlickException {
		g.setColor(Color.red);
		g.fillOval((float)x, (float)y, (float)width, (float)width);

	}

	@Override
	public void update(GameContainer container, StateBasedGame game, int delta) throws SlickException {
		// TODO Auto-generated method stub
		speedY = 0.1;
		speedX = 0;
		for (Brique b:World.getBriques())
		{
			if (isColliding(b))
			{
				b.setLife(b.getLife()-1);
				setTouched(true);
			}
		}
	}
	
	public void setTouched(boolean t)
	{
		this.touched = t;
	}

}