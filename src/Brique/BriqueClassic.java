package Brique;

import org.newdawn.slick.Color;

import fr.entity.Bonus;
import fr.entity.Brique;


public class BriqueClassic extends Brique {
	
	private String[] liste;
	private int type;
	
	public BriqueClassic(int x, int y, boolean random) {
		super(x, y,true, random,1); //Brique à vie random
		type = (int) (Math.random()*(Bonus.lesTypes.length + 1));
		// TODO Auto-generated constructor stub
	}
	
	public BriqueClassic(Brique b)
	{
		super(b);
	}

	public BriqueClassic() {
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
			createBonus();
			this.setDead(true);
		}
	}

	@Override
	public void loseLife() {
		// TODO Auto-generated method stub
		double coef;
		
		if(getLife()==0)
		{
			coef=1;
		}else{
			this.setLife(this.getLife()-1);
		}
		mettreAjourCouleur();
	}

	public void createBonus(){
		if(liste==null)return;
		if(type != liste.length){
			Bonus bonus = new Bonus(this.getX(),this.getY(),Bonus.lesTypes[type]);
		}
	}
	
}