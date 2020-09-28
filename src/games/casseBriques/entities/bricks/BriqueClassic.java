package games.casseBriques.entities.bricks;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.state.StateBasedGame;

import app.AppLoader;

import games.casseBriques.World;
import games.casseBriques.entities.Brique;

public class BriqueClassic extends Brique {

	private static Image[] images=new Image[3];

	public BriqueClassic(World world, int x, int y, boolean random) {
		super(world, x, y,true, random,1); //Brique à vie random
		chargerImage();
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

	public BriqueClassic(World world) {
		super(world);
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
	public void update(GameContainer container, StateBasedGame game, int delta) {
		if (!this.getColliding()) {
			return;
		}
		this.setLife(this.getLife() - 1);
		if (this.getLife() <= 0) {
			this.world.remove(this);
		}
	}

}
