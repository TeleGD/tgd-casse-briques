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
	private double perturbation;
	
	public Ball(){
		this.x=300;
		this.y=300;
		this.width=32;
		style = styleEnum.BASIC;
		perturbation = 0;
	}
	
	@Override
	public int getRadius() {
		return (int)width;
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
		
		//Deplacement de la brique :
		switch(style){
		case BASIC:
			speedX += delta*accelX;
			speedY += delta*accelY;
			break;
		case LCURVE:
			perturbation = 10;//Norme du vecteur perturbateur.
			speedX += perturbation*(speedY/speedNorm());
			speedY += perturbation*(speedX/speedNorm());
			break;
		case RCURVE:
			perturbation = -10;//Norme du vecteur perturbateur.
			speedX += perturbation*(speedY/speedNorm());
			speedY += perturbation*(speedX/speedNorm());
			break;
		default:
			break;
		}
		x += delta*speedX;
		y += delta*speedY;

		setMoving(speedNorm()>0);
		
		//Detection de collision : 
		for(Brique b:World.getBriques()){
			b.setColliding(isColliding(b));
			if(isColliding(b)){
				if(b.getHard()){
					//On touche un brique dure
					if((this.x<=b.getX()) || (this.x>=(b.getX()+b.getWidth()))){
						//Contact effectue sur un des cotes.
						this.speedX *= -1;
					}else{
						this.speedY *= -1;
					}	
				}
				
				//Ici on renvoie a la brique de quel cote on la percute.
				if(this.x<=b.getX()){
				//Touche a gauche	
				}else if(this.x>=(b.getX()+b.getWidth())){
				//Touche a droite	
				}else if(this.y<=b.getY()){
				//Touche en haut	
				}else if(this.y>=b.getY()+b.getHeight()){
				//Touche en bas	
				}
			}
		}
	}
	
	public boolean isColliding(Brique b){
		return Collisions.isCollisionCircleRect(this, b);
	}

}
