package fr.editor;

import java.util.ArrayList;

import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.StateBasedGame;

import Brique.BriqueClassic;
import fr.entity.Brique;
import fr.entity.Entity;

public class LevelEditor extends Entity{
	private static final int barHorizontalHeight=60;
	private ArrayList<Brique> briques=new ArrayList<Brique>();
	private ArrayList<Brique> menuBriques=new ArrayList<Brique>();
	private Brique briqueSelectionne;
	private int oldBriqueX,oldBriqueY;
	
	private Color[] couleurs={Color.red,Color.blue,Color.green,Color.yellow,Color.orange,Color.cyan,Color.gray};
	private int couleurId;
	private Brique lastBrique;
	public LevelEditor()
	{
		width=800;
		height=600;
		
		for(int i=0;i<3;i++)
		{
			BriqueClassic b=new BriqueClassic(70*i,600-barHorizontalHeight/2-16,false);
			b.setLife((i+1));
			menuBriques.add(b);
		}
		
		
	}

	public void update(GameContainer arg0, StateBasedGame arg1, int arg2) {
		// TODO Auto-generated method stub
		
	}

	public void render(GameContainer arg0, StateBasedGame arg1, Graphics arg2) throws SlickException {
		// TODO Auto-generated method stub
		arg2.setColor(Color.red);
		arg2.fillRect(0f,  (float)height-barHorizontalHeight, (float)width, 2);
		for(int i=0;i<briques.size();i++)
		{
			briques.get(i).render(arg0, arg1, arg2);
		}
		for(int i=0;i<menuBriques.size();i++)
		{
			menuBriques.get(i).render(arg0, arg1, arg2);
		}
		
	}

	public void mouseReleased(int button, int x,int y){
		
		if(briqueSelectionne!=null)
		{
			if(y<=370)
			{
				briqueSelectionne.setX(((int)briqueSelectionne.getX()/64)*64);
				briqueSelectionne.setY(((int)briqueSelectionne.getY()/32)*32);
			}else {
				briqueSelectionne.setX(oldBriqueX);
				briqueSelectionne.setY(oldBriqueY);
			}
			lastBrique=briqueSelectionne;
			briques.add(lastBrique);
		}
	
		briqueSelectionne=null;
	}
	
	public void mousePressed(int button, int oldx,int oldy){
		
		for(int i=0;i<menuBriques.size();i++)
		{
			if(menuBriques.get(i).getX()<oldx  && menuBriques.get(i).getX()+menuBriques.get(i).getWidth()>oldx
				&& menuBriques.get(i).getY()<oldy  && menuBriques.get(i).getY()+menuBriques.get(i).getHeight()>oldy)
			{
				//briqueSelectionne=new BriqueClassic(menuBriques.get(i));
				oldBriqueX=(int)briqueSelectionne.getX();
				oldBriqueY=(int)briqueSelectionne.getY();
				
			}
		}
		
	}
	
	
	public void mouseDragged(int oldx, int oldy, int newx, int newy) {

		if(briqueSelectionne!=null)
		{
			briqueSelectionne.setX(newx);
			briqueSelectionne.setY(newy);
			briqueSelectionne.setX(((int)briqueSelectionne.getX()/64)*64);
			briqueSelectionne.setY(((int)briqueSelectionne.getY()/32)*32);
		}
		
	}

	public void keyPressed(int key, char c) {

		if(key==Input.KEY_UP)couleurId++;
		else if(key==Input.KEY_DOWN)couleurId--;
		
		couleurId=couleurId%couleurs.length;
		lastBrique.setColor(couleurs[couleurId]);
	
	}
	
}
