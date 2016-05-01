package Brique;

import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.StateBasedGame;

import fr.entity.Bonus;
import fr.entity.Brique;


public class BriqueClassic extends Brique {
	
	private String[] liste;
	private int type;
	private int indexImage;
	private static Image[] images=new Image[3];
	public BriqueClassic(int x, int y, boolean random) {
		super(x, y,true, random,1); //Brique à vie random
		
		type = (int) (Math.random()*(Bonus.lesTypes.length + 1));
		chargerImage();
		// TODO Auto-generated constructor stub
	}
	
	private void chargerImage() {
		
		try {
			if(images[0]==null)images[0]=new Image("img/brique/Normal1.png");
			if(images[1]==null)images[1]=new Image("img/brique/Normal2.png");
			if(images[2]==null)images[2]=new Image("img/brique/Normal3.png");
		} catch (SlickException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		indexImage=2;
	}

	public BriqueClassic(Brique b)
	{
		super(b);
		chargerImage();
	}

	public BriqueClassic() {
		chargerImage();
	}
	
	@Override
	public void render(GameContainer container, StateBasedGame game, Graphics g) throws SlickException {
		
		
		g.drawImage(images[indexImage],(float)x,(float)y,mettreAjourCouleur());
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