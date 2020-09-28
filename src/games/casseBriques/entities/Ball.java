package games.casseBriques.entities;

import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.state.StateBasedGame;

import games.casseBriques.World;
import games.casseBriques.util.Circle;
import games.casseBriques.util.Movable;

public class Ball extends Movable implements Circle{

    private World world;
    private boolean sticky;
    private double speedNorm=0.3;
    private double speedNormAct;
    private int compt;

    public Ball(World world){
        this.world = world;
    	x=world.getPlayer().getX()+world.getPlayer().getWidth()/2-width/2;
        y = world.getPlayer().getY()-16;
        width = 16;
        height = 16;
        speedY = -0.3;
        speedX=(Math.random()*4 -2) /10;
    }
    public Ball(World world,int x,int y){
        this.world = world;
    	this.x=x;
    	this.y=y;
        width = 16;
        height = 16;
        speedY = -0.3;
        speedX=(Math.random()*4 -2) /10;
    }

    @Override
    public int getRadius() {
        return (int)(width/2);
    }

    @Override
    public void render(GameContainer container, StateBasedGame game, Graphics g) {
        g.setColor(Color.pink);
        g.fillOval((float)x, (float)y, (float)width, (float)width);
    }

    public double speedNorm(){
        return Math.sqrt(speedX*speedX+speedY*speedY);
    }

    @Override
    public void update(GameContainer container, StateBasedGame game, int delta) {
        setMoving(speedNorm()>0);

        if(world.getPlayer().hasBall()){
        	x=world.getPlayer().getX()+world.getPlayer().getWidth()/2-width/2;
        	return;
        }

        //Detection de collisions avec les murs :
        if(x<=0){
            speedX *= -1;
            x=1;
        }else if(x>(800-getWidth())){
            speedX *= -1;
            x=800-getWidth()-1;
        }

        if(y<=0){
            speedY *= -1;
            y=1;
        }else if(y>=(600-height/2)){
            world.remove(this);
        	x=world.getPlayer().getX()+world.getPlayer().getWidth()/2-width/2;
            y = world.getPlayer().getY()-16;
            speedY = -0.3;
            speedX=(Math.random()*4 -2) /10;
        }

        if (games.casseBriques.util.Collisions.colPlayer(this,world.getPlayer())){
        	this.speedX=this.x-(world.getPlayer().getX()+world.getPlayer().getWidth()/2);//a modifier
        	this.speedY=-world.getPlayer().getHeight();
        }

        //Detection de collisions avec les briques :
        for(Brique b:world.getBriques()){
            	  if(games.casseBriques.util.Collisions.colBrique(this,b)){
	            	if(b.getHard()){
	            		//On touche une brique dure.
	                	if((x+width/2>b.getX()) && (x+width/2<=(b.getX()+b.getWidth()))){
	                		//Contact effectue par la verticale.
	                		if(y<b.getY())y=b.getY()-height-1;
	                		else y=b.getY()+b.getHeight()+1;
	                        speedY = -1*speedY;
	                    }else if((y+height/2>b.getY())&& (y+height/2<(b.getY()+b.getHeight())))
	                    {
	                    	//Contact effectue par l'horizontale
	                		if(x<b.getX())x=b.getX()-1-width;
	                		else x=b.getX()+b.getWidth()+1;
	                        speedX = -1*speedX;
	                    }
	                	b.setColliding(true);
	                }
	            }
        }

        //Rectification de la norme de la vitesse
        this.speedNormAct=Math.sqrt(this.speedX*this.speedX+this.speedY*this.speedY);
    	this.speedX=this.speedX*this.speedNorm/this.speedNormAct;
    	this.speedY=this.speedY*this.speedNorm/this.speedNormAct;

        moveX(delta);
        moveY(delta);
    }

    public boolean getSticky(){
        return sticky;
    }

    public void setSticky(boolean value){
        sticky = value;
    }

}
