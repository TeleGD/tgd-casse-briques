package Brique;

import java.util.ArrayList;

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

	@Override
	public void action() {
		// TODO Auto-generated method stub
		if (this.getColliding())
		{
			loseLife();
			if (this.getLife()==0)
			{
				explose();
				this.setDead(true);
			}
		}
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
