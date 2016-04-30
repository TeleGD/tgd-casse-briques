package Brique;

import fr.entity.Brique;


public class BriqueClassic extends Brique {
	
	public BriqueClassic(int x, int y, boolean random) {
		super(x, y,true, random,1); //Brique à vie random
		// TODO Auto-generated constructor stub
	}

	@Override
	public void action() {
		// TODO Auto-generated method stub
		if (this.getColliding())
		{
			loseLife();
			if (this.getLife()==0)
			{
				this.setDead(true);
			}
		}
	}

	@Override
	public void loseLife() {
		// TODO Auto-generated method stub
		this.setLife(this.getLife()-1);
	}

	
	
}
