package fr.entity;
import java.awt.*;

import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.StateBasedGame;

import fr.util.Rectangle;

public abstract class Brique extends Entity implements Rectangle{
	
	private int life;
	private int color;
	private boolean colliding;
	private boolean hard;
	private boolean dead = false;
	
	public Brique(int x, int y, boolean h, boolean random,int life){
		this.x=x;
		this.y=y;
		this.width=64;
		this.height=32;
		if (random)
		{
			this.life = (int) (Math.random()*3+1);
		}
		else
		{
			this.life = life;
		}
		this.color = 255-(life-1)*30;
		this.colliding = false;
		this.hard = h;
	}
	
	@Override
	public void render(GameContainer container, StateBasedGame game, Graphics g) throws SlickException {
		g.setColor(new Color(0,0,this.color));
		g.fillRect((float)x,(float)y,(float)width,(float)height);
	}

	@Override
	public void update(GameContainer container, StateBasedGame game, int delta) throws SlickException {
		// TODO Auto-generated method stub
		action();
	}

	public void onHit()
	{
		// TODO
	}
	
	public int getLife()
	{
		return this.life;
	}
	
	public void setDead(boolean d)
	{
		this.dead = d;
	}
	
	public boolean getDead()
	{
		return this.dead;
	}
	
	public void setLife(int life)
	{
		this.life = life;
	}
	
	public boolean getColliding()
	{
		return this.colliding;
	}
	
	public void setColliding(boolean c)
	{
		this.colliding = c;
	}
	
	public boolean getHard()
	{
		return this.hard;
	}
	
	public void setHard(boolean h)
	{
		this.hard = h;
	}
	
	
	public abstract void action();
	public abstract void loseLife();
	
}
