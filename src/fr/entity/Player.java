package fr.entity;

import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.StateBasedGame;

import fr.util.Movable;
import fr.util.Rectangle;

public class Player extends Movable implements Rectangle {

	private boolean keyPressedLeft;
	private boolean keyPressedRight;
	private boolean keyPressedSpace;
	private boolean lastKeyPressed;
	private int stillPressed;
	private int comp = 120;
	private int compTir;
	private int life;
	private boolean modePistolet;
	
	public Player(){
		this.x=336;
		this.y=535;
		this.height=32;
		this.width=128;
		isMoving = true;
		accelX =0.5;
		life = 3;
		modePistolet=false;
		compTir=0;
	}
	
	public boolean getModePistolet(){
		return this.modePistolet;
	}
	
	public void setModePistolet(boolean modePistolet){
		this.modePistolet=modePistolet;
	}
	@Override
	public double getY() {
		return this.y;
	}

	@Override
	public double getX() {
		return this.x;
	}

	@Override
	public double getWidth() {
		return this.width;
	}

	@Override
	public double getHeight() {
		return this.height;
	}
	
	public void modify ( double multi, int time ){
		if (this.width*multi<32||this.width*multi>800){
			if(this.width*multi<32){
				this.width = 32;
			} else{
				this.width = 800;
			}
		} else {
			double var = this.width - this.width*multi;
			this.x = this.x + var/2;
			this.width = this.width*multi;
		}
		
		if ( x <= 0 ){ x = 0;}
		if ( x >= 800 - this.width ){ x = 800 - this.width; }
		comp = time;
		
	}

	@Override
	public void render(GameContainer container, StateBasedGame game, Graphics g) throws SlickException {
		g.setColor(Color.yellow);
		g.fillRect((float)x, (float)y, (float)width, (float)height);
	}

	@Override
	public void update(GameContainer container, StateBasedGame game, int delta) throws SlickException {
		
		stillPressed = 0;
		if (lastKeyPressed) {
			if(keyPressedLeft ){
				stillPressed = 1;
			}
			keyPressedLeft = false;
		} else {
			if(keyPressedRight ){
				stillPressed = 2;
			}
			keyPressedRight = false;
		}
		if (keyPressedLeft)  { speedX = -0.5*accelX; }
		if (keyPressedRight) {	speedX = 0.5*accelX;  }
		
		switch(stillPressed){
		case 1 :
			keyPressedLeft = true;
			break;
		case 2 :
			keyPressedRight = true;
			break;
		}
		
		if ( !(x <= 0 && speedX <=0) && !(x>=800-this.width && speedX >= 0)){

			moveX(delta);
		}
		
		if ( comp > 0 ){
			comp --;
		}
		if ( compTir > 0 ){
			compTir --;
			keyPressedSpace=false;
		}
	}

	public void keyReleased(int key, char c) {
		switch (key) {
		case Input.KEY_LEFT:
			speedX = 0;
			keyPressedLeft = false;
			lastKeyPressed = true;
			break;
		case Input.KEY_RIGHT:
			speedX = 0;
			keyPressedRight = false;
			lastKeyPressed = false;
			break;
		}
	}

	public void keyPressed(int key, char c) {
		switch (key) {
		
		case Input.KEY_LEFT:
			keyPressedLeft = true;
			lastKeyPressed = false;
			break;
		case Input.KEY_RIGHT:
			keyPressedRight = true;
			lastKeyPressed = true;
			break;
		case Input.KEY_SPACE:
			if (compTir==0&&modePistolet){
				keyPressedSpace=true;
				new Bullet();
				compTir=60;
			}
			break;
		}
			
	}
	
	public void setLife(int life){
		this.life = life;
	}
	
	public int getLife(){
		return this.life;
	}



	

}
