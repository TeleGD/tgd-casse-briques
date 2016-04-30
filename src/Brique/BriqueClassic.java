package Brique;

import fr.entity.Brique;


public class BriqueClassic extends Brique {
	
	public BriqueClassic(int x, int y) {
		super(x, y,true);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void action() {
		// TODO Auto-generated method stub
		if (this.getColliding())
		{
			loseLife();
		}
	}

	@Override
	public void loseLife() {
		// TODO Auto-generated method stub
		this.setLife(this.getLife()-1);
	}

	
	
}
