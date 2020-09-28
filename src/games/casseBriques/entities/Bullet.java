package games.casseBriques.entities;

import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.state.StateBasedGame;

import games.casseBriques.World;
import games.casseBriques.util.Collisions;
import games.casseBriques.util.Movable;
import games.casseBriques.util.Rectangle;

public class Bullet extends Movable implements Rectangle{

	private World world;

	public Bullet(World world,int x,int y){
		this.world = world;
		this.x=x;
		this.y=y;
		this.width=4;
		this.height=12;
		speedY = -0.3;
		speedX = 0;
		setMoving(true);
	}

	@Override
	public void render(GameContainer container, StateBasedGame game, Graphics g) {
		g.setColor(Color.red);
		g.fillRoundRect((float)x, (float)y, (float)width, (float)height,2,2);
	}

	@Override
	public void update(GameContainer container, StateBasedGame game, int delta) {
		for (Brique brique: world.getBriques())
		{
			if (!Collisions.colBrique(this, brique)) {
				continue;
			}
			this.world.remove(this);
			if (brique.getColliding()) {
				continue;
			}
			brique.setColliding(true);
			brique.update(container, game, delta);
			break;
		}
		this.moveY(delta);
	}

}
