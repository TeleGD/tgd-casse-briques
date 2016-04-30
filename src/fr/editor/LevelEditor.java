package fr.editor;

import java.util.ArrayList;

import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.StateBasedGame;

import fr.entity.Brique;
import fr.entity.Entity;

public class LevelEditor extends Entity{
	private static final int barHorizontalHeight=60;
	private ArrayList<Brique> briques=new ArrayList<Brique>();
	private ArrayList<Brique> menuBriques=new ArrayList<Brique>();
	private Brique briqueSelectionne;
	
	public LevelEditor()
	{
		width=800;
		height=600;
		menuBriques.add(new Brique(5,600-barHorizontalHeight/2-16));
		menuBriques.add(new Brique(74,600-barHorizontalHeight/2-16));
		menuBriques.add(new Brique(143,600-barHorizontalHeight/2-16));
		menuBriques.add(new Brique(212,600-barHorizontalHeight/2-16));
		
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
		
		briqueSelectionne=null;
	}
	
	public void mousePressed(int button, int oldx,int oldy){
		for(int i=0;i<menuBriques.size();i++)
		{
			if(menuBriques.get(i).getX()<oldx  && menuBriques.get(i).getX()+menuBriques.get(i).getWidth()>oldx
				&& menuBriques.get(i).getY()<oldy  && menuBriques.get(i).getY()+menuBriques.get(i).getHeight()>oldy)
			{
				briqueSelectionne=menuBriques.get(i);
			}
		}
		
	}
	
	
	public void mouseDragged(int oldx, int oldy, int newx, int newy) {

		System.out.println("x="+oldx);
		System.out.println("y="+oldy);
		if(briqueSelectionne!=null)
		{
			briqueSelectionne.setX(newx-32);
			briqueSelectionne.setY(newy-16);
		}
		
	}
	
}
