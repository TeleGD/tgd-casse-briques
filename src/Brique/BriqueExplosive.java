package Brique;

import java.util.ArrayList;

import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.StateBasedGame;

import fr.entity.Brique;
import fr.main.World;

public class BriqueExplosive extends Brique {
	int rangeX;
	int rangeY;
	int damage;
	
	public BriqueExplosive(int x, int y, boolean random){
		super(x, y,true, random,1);
		this.rangeX = 60;
		this.rangeY = 60;
		this.damage = 1;
	}
	public BriqueExplosive(Brique b)
	{
		super(b);
	}

	public BriqueExplosive() {
		// TODO Auto-generated constructor stub
	}
	@Override
	public void action() {
		// TODO Auto-generated method stub
		if (this.getColliding())
		{
			loseLife();
		}

		if (this.getLife()==0)
		{
			explose();
			this.setDead(true);
		}
	}

	@Override
	public void render(GameContainer container, StateBasedGame game, Graphics g) throws SlickException {
		g.setColor(color);
		g.fillRect((float)x,(float)y,(float)width,(float)height);
		g.setColor(Color.orange);
		g.fillRoundRect((float)(x+35), (float)(y+10),(float) width/4,(float) height/4, 20,20);
	}
	@Override
	public void loseLife() {
		// TODO Auto-generated method stub
		this.setLife(this.getLife()-1);
	}
	
	public void explose(){
		ArrayList<Brique> briques = World.getBriques();
		for(Brique b : briques){
			if((b.getX()>this.getX()-rangeX) && (b.getX()<this.getX()+rangeX) && (b.getY()>this.getY()-rangeY) && (b.getX()<this.getY()-rangeY)){
				b.setLife(b.getLife()-damage);
			}
		}
	}

}
