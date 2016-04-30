package fr.entity;

import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.StateBasedGame;

import Brique.BriqueClassic;
import Brique.BriqueTp;
import fr.util.Rectangle;

public abstract class Brique extends Entity implements Rectangle{
	
	private int life;
	protected Color color;
	private boolean colliding;
	private boolean hard;
	private boolean dead = false;
	private boolean rand;
	private Color[] couleurs={Color.red,Color.blue,Color.green,Color.yellow,Color.orange,Color.cyan};
	
	public Brique(int x, int y, boolean h, boolean random,int life){
		this.x=x;
		this.y=y;
		this.width=64;
		this.height=32;
		this.rand = random;
		this.color =couleurs[(int)(Math.random()*couleurs.length)];
		if (random)
		{
			setLife((int) (Math.random()*4+1));
		}
		else
		{
			setLife(life);
		}
		this.colliding = false;
		this.hard = h;		

		//this.color = new Color(0,0,255-135/4*life);
	}
	
	public Brique()
	{
		
	}
	public Brique(Brique b)
	{
		this.x = b.x;
		this.y = b.y;
		this.width = 64;
		this.height = 32;
		if (b.rand)
		{
			this.life = (int) (Math.random()*4+1);
		}
		else
		{
			this.life = b.life;
		}
		this.colliding = false;
		this.hard = b.hard;
		this.color = new Color(0,0,255-135/4*life);
	}
	
	@Override
	public void render(GameContainer container, StateBasedGame game, Graphics g) throws SlickException {
		g.setColor(color);
		g.fillRect((float)x,(float)y,(float)width,(float)height);
	}

	@Override
	public void update(GameContainer container, StateBasedGame game, int delta) throws SlickException {
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
		int red = (int)(this.color.getRed()-135/4*life);
		int green = (int)(this.color.getGreen()-135/4*life);
		int blue = (int)(this.color.getBlue()-135/4*life);
		this.color = new Color(red,green,blue);
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
		this.color = c;
	}
	
	public Color getColor()
	{
		return this.color;
	}
	
	public abstract void action();
	public abstract void loseLife();
	
	public String briqueToString(){
		if(this instanceof BriqueClassic)
		{
			return  "BriqueClassic "+x+" "+y+" "+color.getBlue()+" "+color.getRed()+" "+color.getGreen()+" "+life+" "+ hard;
		}else if(this instanceof BriqueTp)
		{
			return  "BriqueTp "+x+" "+y+" "+color.getBlue()+" "+color.getRed()+" "+color.getGreen()+" "+life+" "+ hard;
		}
		return null;
		
		
	}
	
   public static Brique StringToBrique(String s){
		String t[]=s.split(" ");
		Brique b;
		if(t[0].equals("BriqueClassic"))
		{
			b=new BriqueClassic();
		}else 
			b=new BriqueTp();
		b.setX(Double.parseDouble(t[1]));
		b.setY(Double.parseDouble(t[2]));
		b.setColor(new Color(Integer.parseInt(t[3]),Integer.parseInt(t[4]),Integer.parseInt(t[5])));
		b.setLife(Integer.parseInt(t[6]));
		b.setHard(Boolean.parseBoolean(t[7]));

		return b;
		
	}
}
