package games.casseBriques.bricks;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.state.StateBasedGame;

import app.AppLoader;

import games.casseBriques.entities.Bonus;
import games.casseBriques.entities.Brique;

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
		if(images[0]==null)images[0]=AppLoader.loadPicture("/images/casseBriques/brique/normal1.png");
		if(images[1]==null)images[1]=AppLoader.loadPicture("/images/casseBriques/brique/normal2.png");
		if(images[2]==null)images[2]=AppLoader.loadPicture("/images/casseBriques/brique/normal3.png");
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
	public void render(GameContainer container, StateBasedGame game, Graphics g) {

		int i=0;
		if(getLife()==4 || getLife()==3)i=0;
		else if(getLife()==2)i=1;
		else if(getLife()==1)i=2;
		g.drawImage(images[i],(float)x,(float)y,mettreAjourCouleur());
	}

	@Override
	public void action() {
		// TODO Auto-generated method stub
		if (this.getColliding())
		{
			loseLife();
		}
		super.action();
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

}
