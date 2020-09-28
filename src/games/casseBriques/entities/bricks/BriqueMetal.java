package games.casseBriques.entities.bricks;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.state.StateBasedGame;

import app.AppLoader;

import games.casseBriques.World;
import games.casseBriques.entities.Brique;

public class BriqueMetal extends Brique{

	private static  Image image;

	public BriqueMetal(World world, int x, int y, boolean random) {
		super(world, x, y,true, false,-1);
		chargerImage();
	}

	@Override
	public void render(GameContainer container, StateBasedGame game, Graphics g) {
		g.drawImage(image,(float)x,(float)y,mettreAjourCouleur());
	}

	@Override
	public void update(GameContainer container, StateBasedGame game, int delta) {}

	private void chargerImage() {
		if(image==null)image=AppLoader.loadPicture("/images/casseBriques/brique/incassable.png");
	}

	public BriqueMetal(Brique b)
	{
		super(b);
		chargerImage();
	}

	public BriqueMetal(World world) {
		super(world);
		chargerImage();
	}

}
