package games.casseBriques.entities;

import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.state.StateBasedGame;

import games.casseBriques.World;
import games.casseBriques.bricks.BriqueClassic;
import games.casseBriques.bricks.BriqueExplosive;
import games.casseBriques.bricks.BriqueMetal;
import games.casseBriques.bricks.BriqueTp;
import games.casseBriques.util.Rectangle;

public abstract class Brique extends Entity implements Rectangle{

	private int life;
	private boolean colliding;
	private boolean hard;
	private boolean dead = false;
	private boolean rand;
	private Color couleurOriginal;
	private static Color[] couleurs={Color.red,Color.blue,Color.green,Color.yellow,Color.orange,Color.cyan,Color.magenta,Color.pink,Color.white};

	public Brique(int x, int y, boolean h, boolean random,int life){
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

	public Brique()
	{
		this.width=64;
		this.height=32;
	}
	public Brique(Brique b)
	{
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

	@Override
	public void render(GameContainer container, StateBasedGame game, Graphics g) {
		g.setColor(Color.black);
		g.fillRect((float)x+2,(float)y+2,(float)width-4,(float)height-4);
		g.setColor(mettreAjourCouleur());
		g.fillRect((float)x,(float)y,(float)width,(float)height);
	}

	@Override
	public void update(GameContainer container, StateBasedGame game, int delta) {
		// TODO Auto-generated method stub
		action();
	}

	public void onHit()
	{
		// TODO
	}

	public int getLife()
	{
		return this.life;
	}

	public void setDead(boolean d)
	{
		this.dead = d;
	}

	public boolean getDead()
	{
		return this.dead;
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

	public  void action(){
		if (this.getLife()==0)
		{
			this.setDead(true);
			World.destroy(this);
		}
	}
	public abstract void loseLife();

	public String briqueToString(){
		if(this instanceof BriqueClassic)
		{
			return  "BriqueClassic "+x+" "+y+" "+couleurOriginal.getRed()+" "+couleurOriginal.getGreen()+" "+couleurOriginal.getBlue()+" "+life+" "+ hard;
		}else if(this instanceof BriqueTp)
		{
			return  "BriqueTp "+x+" "+y+" "+couleurOriginal.getRed()+" "+couleurOriginal.getGreen()+" "+couleurOriginal.getBlue()+" "+life+" "+ hard;
		}else if(this instanceof BriqueExplosive)
		{
			return  "BriqueExplosive "+x+" "+y+" "+couleurOriginal.getRed()+" "+couleurOriginal.getGreen()+" "+couleurOriginal.getBlue()+" "+life+" "+ hard;
		}else if(this instanceof BriqueMetal)
		{
			return  "BriqueMetal "+x+" "+y+" "+couleurOriginal.getRed()+" "+couleurOriginal.getGreen()+" "+couleurOriginal.getBlue()+" "+life+" "+ hard;
		}
		return null;

	}

   public static Brique StringToBrique(String s){
		String t[]=s.split(" ");
		Brique b;
		if(t[0].equals("BriqueClassic"))
		{
			b=new BriqueClassic();
		}else if(t[0].equals("BriqueExplosive"))
		{
			b=new BriqueExplosive();
		}else if(t[0].equals("BriqueMetal"))
		{
			b=new BriqueMetal();
		}else
			b=new BriqueTp();

		b.setX(Double.parseDouble(t[1]));
		b.setY(Double.parseDouble(t[2]));

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

	double sqrt= Math.sqrt(life);
	int red = (int)(this.couleurOriginal.getRed()/sqrt);
	int green = (int)(this.couleurOriginal.getGreen()/sqrt);
	int blue = (int)(this.couleurOriginal.getBlue()/sqrt);
	return  new Color(red,green,blue);
}

public void lastWhisper() {
        int bonusOuPas = (int) (Math.random()*12+1);
        int choixBonus = (int) (Math.random()*Bonus.lesTypes.length);
        if (bonusOuPas==1){
                World.addBonus(new Bonus(this.x,this.y,Bonus.lesTypes[choixBonus]));
        }

   }

}
