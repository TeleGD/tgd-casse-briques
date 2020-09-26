package games.casseBriques.bricks;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.state.StateBasedGame;

import games.casseBriques.entities.Brique;

public class BriqueTp extends Brique{

	private Brique link;

	public BriqueTp(int x, int y, boolean h, Brique b) {
		super(x, y, h, false, 0);
		// TODO Auto-generated constructor stub
		this.link = b;

	}

	public BriqueTp(Brique b)
	{
		super(b);
	}
	public BriqueTp() {
		super();
		// TODO Auto-generated constructor stub
	}

	@Override
	public void update(GameContainer container, StateBasedGame game, int delta) {

		// TODO Auto-generated method stub
		telep();
	}

	public void telep()
	{

	}

	@Override
	public void action() {
		// TODO Auto-generated method stub
		telep();
	}

	@Override
	public void loseLife() {
		// TODO Auto-generated method stub

	}

}
