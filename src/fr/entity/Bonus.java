package fr.entity;

import java.util.ArrayList;

import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.StateBasedGame;
import fr.util.*;
import fr.main.*;

import fr.util.Movable;

public class Bonus  extends Movable implements fr.util.Circle{ 
	protected String type;
	public static String[] lesTypes={"accelerer","ralentir","retraicir","agrandir","collant","pleinBalle","pistolet","agrandirBalle","retrecirBalle"};
	
	public Bonus(double x, double y, String type){
		this.x=x;
		this.y=y;
		this.width=16;
		this.type=type;
		isMoving = true;
		this.speedY=0.1;//tester differentes vitesses?
	}

	@Override
	public void render(GameContainer container, StateBasedGame game, Graphics g) throws SlickException {
		g.setColor(Color.pink);
		g.fillOval((float)x, (float)y, (float)width, (float)width);
		
	}

	public boolean collisionPlayer(){
		return Collisions.isCollisionCircleRect(this, World.getPlayer());
	}
	
	public boolean collisionGround(){
		return this.y>=616;
	}
	
	public boolean mustBeDeleted(){
		return (collisionPlayer()||collisionGround());
	}
	
	@Override
	public void update(GameContainer container, StateBasedGame game, int delta) throws SlickException {
		if(mustBeDeleted()){
			if (collisionPlayer()){
				switch (type){
				// TODO faire les differents bonus
				case "accelerer":World.getPlayer().setAccelX(World.getPlayer().getAccelX()*2);break;
				case "ralentir":World.getPlayer().setAccelX(World.getPlayer().getAccelX()*0.5);break;
				case "retraicir":World.getPlayer().modify(0.5, 200);break;
				case "agrandir":World.getPlayer().modify(2, 200);break;
				default:break;
				}
			}
			World.destroyBonus(this);
		}
		moveY(delta);
	}

	@Override
	public int getRadius() {

		return (int) this.width;
	}

}
