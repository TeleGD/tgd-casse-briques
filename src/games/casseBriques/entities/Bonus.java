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
	protected String type;
	public static String[] lesTypes={"agrandir","agrandirBalle","accelerer","retrecir","rondfusee","ralentir","multiballe"};

	private static Image[] image=new Image[lesTypes.length];
	private int indexImage;
	public Bonus(double x, double y, String type){
		this.x=x;
		this.y=y;
		this.width=16;
		this.type=type;
		isMoving = true;
		this.speedY=0.1;//tester differentes vitesses?
		for(int i=0;i<lesTypes.length;i++)
		{
			if(type==lesTypes[i])indexImage=i;
		}

	}

	public static void chargerImageBonus()
	{
		for(int i=0;i<lesTypes.length;i++)
		{
			System.out.println("bonus:"+lesTypes[i]);
			image[i]=AppLoader.loadPicture("/images/casseBriques/bonus/"+lesTypes[i]+".png");
		}
	}

	@Override
	public void render(GameContainer container, StateBasedGame game, Graphics g) {

		g.drawImage(image[indexImage],(float)x,(float)y);

	}

	public boolean collisionPlayer(){
		return Collisions.colPlayer(this, World.getPlayer());
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
					for (int i = 0; i <World.getBalls().size(); i++) {
						if(World.getBalls().get(i).getRadius()<128)
							World.getBalls().get(i).setWidth(World.getBalls().get(i).getWidth()*2);;
					};break;
				case "retrecirBalle":
					for (int i = 0; i < World.getBalls().size(); i++) {
						World.getBalls().get(i).setWidth(World.getBalls().get(i).getWidth()/2);;
					};break;
				case "multiballe":

					int h=World.getBalls().size();
					for(int i=0;i<h;i++)
					{
						for (int j = 0; j < 2; j++) {
							World.addBall(new Ball((int)(World.getBalls().get(i).getX()),(int) World.getBalls().get(i).getY()));
						}
					}

					break;
				case "rondfusee":
					World.getPlayer().setModePistolet(true);break;
				case "pistoletoff":World.getPlayer().setModePistolet(false);
				case "pistolet":World.getPlayer().setModePistolet(true);
				case "accelerer":World.getPlayer().setAccelX(World.getPlayer().getAccelX()*2);break;
				case "ralentir":World.getPlayer().setAccelX(World.getPlayer().getAccelX()*0.5);break;
				case "retrecir":World.getPlayer().modify(0.5, 200);break;
				case "agrandir":World.getPlayer().modify(2, 200);break;
				default:break;
				}
			}
			World.destroy(this);
		}
		moveY(delta);
	}

	@Override
	public int getRadius() {

		return (int) this.width;
	}

}
