package Brique;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.StateBasedGame;

import fr.entity.Brique;

public class BriqueTp extends Brique{
	
	private Brique link;
	
	public BriqueTp(int x, int y, boolean h, Brique b) {
		super(x, y, h);
		// TODO Auto-generated constructor stub
		this.link = b;

	}

	@Override
	public void update(GameContainer container, StateBasedGame game, int delta) throws SlickException {

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
