package games.casseBriques.entities;

import org.newdawn.slick.Color;

import games.casseBriques.Entity;
import games.casseBriques.World;
import games.casseBriques.entities.bricks.BriqueClassic;
import games.casseBriques.entities.bricks.BriqueExplosive;
import games.casseBriques.entities.bricks.BriqueMetal;
import games.casseBriques.util.Rectangle;

public abstract class Brique extends Entity implements Rectangle{

	protected World world;
	private int life;
	private boolean colliding;
	private boolean hard;
	private boolean rand;
	private Color couleurOriginal;
	private static Color[] couleurs={Color.red,Color.blue,Color.green,Color.yellow,Color.orange,Color.cyan,Color.magenta,Color.pink,Color.white};

	public Brique(World world, int x, int y, boolean h, boolean random,int life){
		this.world = world;
		this.x=x;
		this.y=y;
		this.width=64;
		this.height=32;
		this.rand = random;
		this.couleurOriginal=getCouleurs()[(int)(Math.random()*getCouleurs().length)];
		if (random)
		{
			this.life=((int) (Math.random()*4+1));
		}
		else
		{
			this.life=(life);
		}
		this.colliding = false;
		this.hard = h;

		//this.color = new Color(0,0,255-135/4*life);
	}

	public Brique(World world)
	{
		this.world = world;
		this.width=64;
		this.height=32;
	}
	public Brique(Brique b)
	{
		this.world = b.world;
		this.x = b.x;
		this.y = b.y;
		this.width = 64;
		this.height = 32;
		this.couleurOriginal=b.couleurOriginal;
		if (b.rand)
		{
			this.life=((int) (Math.random()*4+1));
		}
		else
		{
			this.life=(b.life);
		}
		this.colliding = false;
		this.hard = b.hard;
	}

	public int getLife()
	{
		return this.life;
	}

	public void setLife(int life)
	{
		this.life = life;
	}

	public boolean getColliding()
	{
		return this.colliding;
	}

	public void setColliding(boolean c)
	{
		this.colliding = c;
	}

	public boolean getHard()
	{
		return this.hard;
	}

	public void setHard(boolean h)
	{
		this.hard = h;
	}

	public void setColor(Color c)
	{
		this.couleurOriginal=c;
	}

	public Color getColor()
	{
		return this.couleurOriginal;
	}

	public String briqueToString(){
		if (this instanceof BriqueExplosive) {
			return  "BriqueExplosive "+(int)x+" "+(int)y+" "+couleurOriginal.getRed()+" "+couleurOriginal.getGreen()+" "+couleurOriginal.getBlue()+" "+life+" "+ hard;
		}
		if (this instanceof BriqueMetal) {
			return  "BriqueMetal "+(int)x+" "+(int)y+" "+couleurOriginal.getRed()+" "+couleurOriginal.getGreen()+" "+couleurOriginal.getBlue()+" "+life+" "+ hard;
		}
		return  "BriqueClassic "+(int)x+" "+(int)y+" "+couleurOriginal.getRed()+" "+couleurOriginal.getGreen()+" "+couleurOriginal.getBlue()+" "+life+" "+ hard;
	}

   public static Brique StringToBrique(World world, String s){
		String t[]=s.split(" ");
		Brique b;
		if(t[0].equals("BriqueExplosive"))
		{
			b=new BriqueExplosive(world);
		}else if(t[0].equals("BriqueMetal"))
		{
			b=new BriqueMetal(world);
		}else
			b=new BriqueClassic(world);

		b.setX(Integer.parseInt(t[1]));
		b.setY(Integer.parseInt(t[2]));

		int a=Integer.parseInt(t[3]);
		int c=Integer.parseInt(t[4]);
		int d=Integer.parseInt(t[5]);

		b.setColor(new Color(a,c,d));
		b.setLife(Integer.parseInt(t[6]));
		b.setHard(Boolean.parseBoolean(t[7]));
		return b;
	}

   public static Color[] getCouleurs() {
		return couleurs;
	}

public static void setCouleurs(Color[] couleurs) {
	Brique.couleurs = couleurs;
}

public Color mettreAjourCouleur()
{
	double sqrt= Math.sqrt(Math.abs(life));
	int red = (int)(this.couleurOriginal.getRed()/sqrt);
	int green = (int)(this.couleurOriginal.getGreen()/sqrt);
	int blue = (int)(this.couleurOriginal.getBlue()/sqrt);
	return  new Color(red,green,blue);
}

public void lastWhisper() {
        int bonusOuPas = (int) (Math.random()*12+1);
        int choixBonus = (int) (Math.random()*Bonus.lesTypes.length);
        if (bonusOuPas==1){
                world.add(new Bonus(this.world,this.x,this.y,Bonus.lesTypes[choixBonus]));
        }

   }

}
