package games.casseBriques.entities.bricks;

import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.state.StateBasedGame;

import games.casseBriques.World;
import games.casseBriques.entities.Brique;

public class BriqueExplosive extends Brique {

	public BriqueExplosive(World world, int x, int y, boolean random){
		super(world, x, y,true, random,1);
	}

	public BriqueExplosive(Brique b)
	{
		super(b);
	}

	public BriqueExplosive(World world) {
		super(world);
	}

	@Override
	public void render(GameContainer container, StateBasedGame game, Graphics g) {
		g.setColor(mettreAjourCouleur());
		g.fillRect((float)x,(float)y,(float)width,(float)height);
		g.setColor(Color.orange);
		g.fillRoundRect((float)(x+35), (float)(y+10),(float) width/4,(float) height/4, 20,20);
	}

	@Override
	public void update(GameContainer container, StateBasedGame game, int delta) {
		if (!this.getColliding()) {
			return;
		}
		this.setLife(this.getLife() - 1);
		if (this.getLife() <= 0) {
			this.world.remove(this);
			for (Brique brique: this.world.getBriques()) {
				if (brique == this) {
					continue;
				}
				if (brique.getX() < this.getX() - 64) {
					continue;
				}
				if (brique.getX() > this.getX() + 64) {
					continue;
				}
				if (brique.getY() < this.getY() - 32) {
					continue;
				}
				if (brique.getY() > this.getY() + 32) {
					continue;
				}
				if (brique.getColliding()) {
					continue;
				}
				brique.setColliding(true);
				brique.update(container, game, delta);
			}
		}
	}

}
