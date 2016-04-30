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
	private boolean lastKeyPressed;
	private int stillPressed;
	private int comp = 120;
	
	public Player(){
		this.x=336;
		this.y=535;
		this.height=32;
		this.width=128;
		isMoving = true;
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
		double var = this.width - this.width*multi; 
		this.x = this.x + var/2;
		this.width = this.width*multi;
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
		if (keyPressedLeft)  { speedX = -0.5; }
		if (keyPressedRight) {	speedX = 0.5;  }
		
		switch(stillPressed){
		case 1 :
			keyPressedLeft = true;
			break;
		case 2 :
			keyPressedRight = true;
			break;
		}
		
		if ( !(x <= 0 && speedX == -0.5) && !(x>=800-this.width && speedX == 0.5)){
			moveX(delta);
		}
		
		if ( comp > 0 ){
			comp --;
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
		}
			
	}

	

}
