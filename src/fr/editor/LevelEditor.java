package fr.editor;
import java.awt.Font;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import org.newdawn.slick.Color; 
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.TrueTypeFont;
import org.newdawn.slick.state.StateBasedGame;

import Brique.BriqueClassic;
import fr.entity.Brique;
import fr.entity.Entity;
import fr.menus.MainMenu;
import fr.parser.WriteFile;

public class LevelEditor extends Entity{
	private static final int barHorizontalHeight=60;
	private ArrayList<Brique> briques=new ArrayList<Brique>();
	private ArrayList<Brique> menuBriques=new ArrayList<Brique>();
	private Brique briqueSelectionne;
	private int oldBriqueX,oldBriqueY;
	private int couleurId;
	private Brique lastBrique;
	private boolean sauvegarder=false;
	private String nomFichier="";
	public LevelEditor()
	{
		width=800;
		height=600;
		
		for(int i=0;i<4;i++)
		{
			BriqueClassic b=new BriqueClassic(70*i,600-barHorizontalHeight/2-16,false);
			b.setColor(Brique.couleurs[0]);
			b.setLife(i+1);
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
		
		if(briqueSelectionne!=null)
		{
			briqueSelectionne.render(arg0, arg1, arg2);
		}
		if(sauvegarder)
		{
			arg2.setColor(Color.red);
			arg2.fillRoundRect(200, 200, 400, 200,20,20);
			arg2.setColor(Color.black);
			arg2.fillRoundRect(202, 202, 396, 196,20,20);
			arg2.setColor(Color.white);


			Font titre1Font = new Font("Kalinga", Font.BOLD, 15);
			TrueTypeFont font1 = new TrueTypeFont(titre1Font, false);
			arg2.setFont(font1);
			arg2.drawString("Sauvegarder le niveau", 300, 220);
			
			titre1Font = new Font("Kalinga", Font.BOLD, 12);
		    font1 = new TrueTypeFont(titre1Font, false);
			arg2.setFont(font1);
			arg2.drawString("Entrer le nom du niveau: "+nomFichier, 300, 280);
			arg2.drawString("Appuyer sur entrer pour enregistrer le niveau", 260, 300);
			
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
			briqueSelectionne=new BriqueClassic(briqueSelectionne);
			briques.add(lastBrique);
		}
	
	}
	
	public void mousePressed(int button, int oldx,int oldy){
		
		if(!yapasdebriques(oldx,oldy))return;
		for(int i=0;i<menuBriques.size();i++)
		{
			if(menuBriques.get(i).getX()<oldx  && menuBriques.get(i).getX()+menuBriques.get(i).getWidth()>oldx
				&& menuBriques.get(i).getY()<oldy  && menuBriques.get(i).getY()+menuBriques.get(i).getHeight()>oldy)
			{
				briqueSelectionne=new BriqueClassic(menuBriques.get(i));
				oldBriqueX=(int)briqueSelectionne.getX();
				oldBriqueY=(int)briqueSelectionne.getY();
				
			}
		}
		for(int i=0;i<briques.size();i++)
		{
			if(briques.get(i).getX()<oldx  && briques.get(i).getX()+briques.get(i).getWidth()>oldx
				&& briques.get(i).getY()<oldy  && briques.get(i).getY()+briques.get(i).getHeight()>oldy)
			{
				briqueSelectionne=briques.get(i);
				oldBriqueX=(int)briqueSelectionne.getX();
				oldBriqueY=(int)briqueSelectionne.getY();
				
			}
		}
		
	}
	
	
	public void mouseMoved(int oldx, int oldy, int newx, int newy) {

		if(briqueSelectionne!=null && yapasdebriques(newx,newy) && newy<=370)
		{
			briqueSelectionne.setX(newx);
			briqueSelectionne.setY(newy);
			briqueSelectionne.setX(((int)briqueSelectionne.getX()/64)*64);
			briqueSelectionne.setY(((int)briqueSelectionne.getY()/32)*32);
		}
		
	}

	private boolean yapasdebriques(int newx, int newy) {

		for(Brique b:briques)
		{
			if(b.getX()==(newx/64)*64 && b.getY()==(newy/32)*32)return false;
		}
		return true;
	}

	public void mouseDragged(int oldx,int  oldy, int newx,int  newy){
		mousePressed(0,newx,newy);
	}
	public void keyPressed(int key, char c) {

		if(sauvegarder)
		{
			
			if(key==Input.KEY_BACK){

				if(nomFichier.length()>0)nomFichier=nomFichier.substring(0, nomFichier.length()-1);
			}else if(key==Input.KEY_ENTER)
			{
				enregistrerNiveau();
			}
			else if(key!=Input.KEY_SPACE) nomFichier+=c;
			
		}
		
		
		if(key==Input.KEY_UP){
			couleurId++;
			if(couleurId==Brique.couleurs.length)couleurId=0;
		}
		else if(key==Input.KEY_DOWN){
			couleurId--;
			if(couleurId==-1)couleurId=Brique.couleurs.length-1;

		}
		else if(key==Input.KEY_S)
		{
			sauvegarder=true;
			
		}
		
		couleurId=couleurId%Brique.couleurs.length;
		for(int i=0;i<menuBriques.size();i++)
		{
			menuBriques.get(i).setColor(Brique.couleurs[couleurId]);
		}
		
		
	
	}

	private void enregistrerNiveau() {
		
		ArrayList<String> textLines=new ArrayList<String>();
		for(Brique b:briques)
		{
			textLines.add(b.briqueToString());
		}
		
		WriteFile file=new WriteFile("levels"+File.separator+nomFichier+".txt");
		try {
			file.writeToFile(textLines);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
}
