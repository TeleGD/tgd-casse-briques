package fr.entity;

import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.StateBasedGame;

import fr.main.World;
import fr.util.Circle;
import fr.util.Movable;
import fr.util.Collisions;

public class Ball extends Movable implements Circle{
    
    private enum styleEnum {BASIC,LCURVE,RCURVE};
    private styleEnum style;
    private double rayonCourbe;
    private boolean sticky;
    private double vect1,vect2;//utile en cas de collision avec le player
    private double speedNorm=3.5;
    private double speedNormAct;
    
    public Ball(){
        x = 400;
        y = 300;
        width = 32;
        height = 32;
        style = styleEnum.BASIC;
        speedY = -0.3;
        speedX=0.2;
    }
    
    @Override
    public int getRadius() {
        return (int)(width/2);
    }

    @Override
    public void render(GameContainer container, StateBasedGame game, Graphics g) throws SlickException {
        g.setColor(Color.pink);
        g.fillOval((float)x, (float)y, (float)width, (float)width);
        
    }

    public double speedNorm(){
        return Math.sqrt(speedX*speedX+speedY*speedY);
    }
    
    @Override
    public void update(GameContainer container, StateBasedGame game, int delta) throws SlickException {
        setMoving(speedNorm()>0);
        
        //Detection de collisions avec les murs :
        if(x<=0){
            speedX *= -1;
        }else if(x>(800-getWidth())){
            speedX *= -1;
        }
        
        if(y<=0){
            speedY *= -1;
        }else if(y>=(600-height/2)){
            World.getPlayer().setLife(World.getPlayer().getLife()-1);
            x = 400;
            y = 300;
            speedX = 0;
            speedY = -0.1;
        }
        
        if (fr.util.Collisions.colPlayer(this,World.getPlayer())){
        	this.speedX=this.x-World.getPlayer().getX();
        	this.speedY=-World.getPlayer().getHeight();
        }
        
        
        //Detection de collisions avec les briques : 
        /*for(Brique b:World.getBriques()){
            b.setColliding(isColliding(b));
            if(isColliding(b)){
                if(b.getHard()){
                    //On touche un brique dure
                    if((x<=b.getX()) || (x>=(b.getX()+b.getWidth()))){
                        //Contact effectue sur un des cotes.
                        this.speedX *= -1;
                    }else{
                        this.speedY *= -1;
                    }    
                }
                
                //Ici on renvoie a la brique de quel cote on la percute.
                if(x<=b.getX()){
                //Touche a gauche    
                }else if(x>=(b.getX()+b.getWidth())){
                //Touche a droite    
                }else if(y<=b.getY()){
                //Touche en haut    
                }else if(y>=b.getY()+b.getHeight()){
                //Touche en bas    
                }
            }
        }*/
        
        //Rectification de la norme de la vitesse
        this.speedNormAct=Math.sqrt(this.speedX*this.speedX+this.speedY*this.speedY);
    	this.speedX=this.speedX*this.speedNorm/this.speedNormAct;
    	this.speedY=this.speedY*this.speedNorm/this.speedNormAct;
        moveX(delta);
        moveY(delta);
        
    }
    
    public boolean isColliding(Brique b){
        return Collisions.isCollisionCircleRect(this, b);
    }
    
    public boolean isColliding(Player p){
        return Collisions.isCollisionCircleRect(this, p);
    }

    public boolean getSticky(){
        return sticky;
    }
    
    public void setSticky(boolean value){
        sticky = value;
    }
    
  
      
    
}