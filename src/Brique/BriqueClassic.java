package Brique;

import org.newdawn.slick.Color;

import fr.entity.Brique;


public class BriqueClassic extends Brique {
	
	public BriqueClassic(int x, int y, boolean random) {
		super(x, y,true, random,1); //Brique à vie random
		// TODO Auto-generated constructor stub
	}
	
	public BriqueClassic(Brique b)
	{
		super(b);
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
		double coef = (getLife()-1)/getLife();
		int red = (int)(this.color.getRed()*coef);
		int green = (int)(this.color.getGreen()*coef);
		int blue = (int)(this.color.getBlue()*coef);
		this.color = new Color(red,green,blue);
		this.setLife(this.getLife()-1);
	}

	
	
}
