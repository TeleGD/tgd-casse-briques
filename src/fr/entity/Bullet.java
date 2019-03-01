package fr.entity;

import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.StateBasedGame;

import fr.main.World;
import fr.util.Movable;
import fr.util.Rectangle;

public class Bullet extends Movable implements Rectangle{


	public Bullet(int x,int y){
		this.x=x;
		this.y=y;
		this.width=4;
		this.height=12;
		speedY = -0.3;
		speedX = 0;
		setMoving(true);
	}


	@Override
	public void render(GameContainer container, StateBasedGame game, Graphics g) throws SlickException {
		g.setColor(Color.red);
		g.fillRoundRect((float)x, (float)y, (float)width, (float)height,2,2);

	}


	@Override
	public void update(GameContainer container, StateBasedGame game, int delta) throws SlickException {
		// TODO Auto-generated method stub
		for (Brique b:World.getBriques())
		{
			if (fr.util.Collisions.colBrique(this, b))
			{
				b.setLife(b.getLife()-1);
				World.getBullets().remove(this);
			}
		}
		System.out.println("touche");
		moveY(delta);
	}


}
