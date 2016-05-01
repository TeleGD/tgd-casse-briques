package Brique;

import fr.entity.Bonus;
import fr.entity.Brique;


public class BriqueMetal extends Brique{
	
	private String[] liste;
	private int type;

	
	public BriqueMetal(int x, int y, boolean random) {
		super(x, y,true, false,-1); //Brique à vie random
		type = (int) (Math.random()*(Bonus.lesTypes.length + 1));
		// TODO Auto-generated constructor stub
	}
	
	public BriqueMetal(Brique b)
	{
		super(b);
	}

	@Override
	public void action() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void loseLife() {
		// TODO Auto-generated method stub
		
	}
}
