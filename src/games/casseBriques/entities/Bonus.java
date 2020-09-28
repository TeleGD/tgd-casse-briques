package games.casseBriques.entities;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.state.StateBasedGame;

import app.AppLoader;

import games.casseBriques.World;
import games.casseBriques.util.Collisions;
import games.casseBriques.util.Movable;

public class Bonus  extends Movable implements games.casseBriques.util.Circle{
	private World world;
	protected String type;
	public static String[] lesTypes={"agrandir","agrandirBalle","accelerer","retrecir","rondfusee","ralentir","multiballe"};

	private static Image[] image=new Image[lesTypes.length];
	private int indexImage;
	public Bonus(World world, double x, double y, String type){
		this.world = world;
		this.x=x;
		this.y=y;
		this.width=16;
		this.type=type;
		isMoving = true;
		this.speedY=0.1;//tester differentes vitesses?
		for(int i=0;i<lesTypes.length;i++)
		{
			if(type==lesTypes[i]) {
				image[i]=AppLoader.loadPicture("/images/casseBriques/bonus/"+lesTypes[i]+".png");
				indexImage=i;
			}
		}
	}

	@Override
	public void render(GameContainer container, StateBasedGame game, Graphics g) {

		g.drawImage(image[indexImage],(float)x,(float)y);
	}

	public boolean collisionPlayer(){
		return Collisions.colPlayer(this, world.getPlayer());
	}

	public boolean collisionGround(){
		return this.y>=616;
	}

	public boolean mustBeDeleted(){
		return (collisionPlayer()||collisionGround());
	}

	@Override
	public void update(GameContainer container, StateBasedGame game, int delta) {
		if(mustBeDeleted()){
			if (collisionPlayer()){
				switch (type){
				// TODO faire les differents bonus
				case "agrandirBalle":
					for (int i = 0; i <world.getBalls().size(); i++) {
						if(world.getBalls().get(i).getRadius()<128)
							world.getBalls().get(i).setWidth(world.getBalls().get(i).getWidth()*2);;
					};break;
				case "retrecirBalle":
					for (int i = 0; i < world.getBalls().size(); i++) {
						world.getBalls().get(i).setWidth(world.getBalls().get(i).getWidth()/2);;
					};break;
				case "multiballe":

					int h=world.getBalls().size();
					for(int i=0;i<h;i++)
					{
						for (int j = 0; j < 2; j++) {
							world.add(new Ball(this.world,(int)(world.getBalls().get(i).getX()),(int) world.getBalls().get(i).getY()));
						}
					}

					break;
				case "rondfusee":
					world.getPlayer().setModePistolet(true);break;
				case "pistoletoff":world.getPlayer().setModePistolet(false);
				case "pistolet":world.getPlayer().setModePistolet(true);
				case "accelerer":world.getPlayer().setAccelX(world.getPlayer().getAccelX()*2);break;
				case "ralentir":world.getPlayer().setAccelX(world.getPlayer().getAccelX()*0.5);break;
				case "retrecir":world.getPlayer().modify(0.5, 200);break;
				case "agrandir":world.getPlayer().modify(2, 200);break;
				default:break;
				}
			}
			world.remove(this);
		}
		moveY(delta);
	}

	@Override
	public int getRadius() {
		return (int) this.width;
	}

}
