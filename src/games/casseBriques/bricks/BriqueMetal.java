package games.casseBriques.bricks;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.state.StateBasedGame;

import app.AppLoader;

import games.casseBriques.entities.Bonus;
import games.casseBriques.entities.Brique;

public class BriqueMetal extends Brique{

	private String[] liste;
	private int type;
	private static  Image image;

	public BriqueMetal(int x, int y, boolean random) {
		super(x, y,true, false,-1); //Brique à vie random
		type = (int) (Math.random()*(Bonus.lesTypes.length + 1));
		// TODO Auto-generated constructor stub
		chargerImage();
		setLife(-1);
	}

	@Override
	public void render(GameContainer container, StateBasedGame game, Graphics g) {

		g.drawImage(image,(float)x,(float)y,mettreAjourCouleur());
	}

	private void chargerImage() {
		image=AppLoader.loadPicture("/images/casseBriques/brique/incassable.png");
	}

	public BriqueMetal(Brique b)
	{
		super(b);

		chargerImage();
	}

	public BriqueMetal() {
		chargerImage();
		// TODO Auto-generated constructor stub
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
