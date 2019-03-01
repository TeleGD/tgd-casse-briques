package fr.entity;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.StateBasedGame;
import org.newdawn.slick.state.transition.FadeInTransition;
import org.newdawn.slick.state.transition.FadeOutTransition;

import fr.main.World;
import fr.menus.GameOverMenu;
import fr.util.Movable;
import fr.util.Rectangle;

public class Player extends Movable implements Rectangle {

	private boolean keyPressedLeft;
	private boolean keyPressedRight;
	private boolean keyPressedSpace;
	private boolean lastKeyPressed;
	private boolean hasBall=true;
	private int stillPressed;
	private int comp = 120;
	private int compTir;
	private int life;
	private boolean modePistolet;
	private Image raquette;
	private Image raquette64;
	private Image raquette32;
	private Image raquette256;
	private Image raquette512;
	private Image raquette600;

	public Player(){
		this.x=336;
		this.y=535;
		this.height=32;
		this.width=128;
		isMoving = true;
		accelX =2;
		life = 3;
		modePistolet=false;
		compTir=0;
		try {
			raquette = new Image("img/raquette/raquette.png");
			raquette64 = new Image("img/raquette/raquette64.png");
			raquette600 = new Image("img/raquette/raquette600.png");
			raquette32 = new Image("img/raquette/raquette32.png");
			raquette256 = new Image("img/raquette/raquette256.png");
			raquette512 = new Image("img/raquette/raquette512.png");
		} catch ( SlickException e){
			e.printStackTrace();
		}
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

	public Image getImage(){
		if ( this.width == 32 ){
			return raquette32;
		} else if ( this.width == 64 ){
			return raquette64;
		} else if ( this.width == 256 ){
			return raquette256;
		} else if ( this.width == 512 ){
			return raquette512;
		} else if ( this.width >= 600 ){
			return raquette600;
		} else {
			return raquette;
		}
	}

	@Override
	public void render(GameContainer container, StateBasedGame game, Graphics g) throws SlickException {
		g.drawImage(getImage(),(float)(this.x),(float)(this.y));
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

		if (life == 0) {
			game.enterState(GameOverMenu.ID, new FadeOutTransition(), new FadeInTransition());
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
			hasBall=false;
			if (compTir==0 && modePistolet){
				keyPressedSpace=true;
				World.getBullets().add(new Bullet((int)x,(int)y));
				World.getBullets().add(new Bullet((int)(x+width),(int)y));
				compTir=10;
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

	public boolean hasBall(){
		return hasBall;
	}

	public void setHasBall(boolean b) {

		hasBall=b;

	}






}
