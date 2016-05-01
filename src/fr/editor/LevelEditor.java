package fr.editor;
import java.awt.Font;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;

import org.newdawn.slick.Color; 
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.TrueTypeFont;
import org.newdawn.slick.state.StateBasedGame;

import Brique.BriqueClassic;
import Brique.BriqueExplosive;
import Brique.BriqueMetal;
import Brique.BriqueTp;
import fr.entity.Brique;
import fr.entity.Entity;
import fr.menus.MainMenu;
import fr.parser.WriteFile;

public class LevelEditor extends Entity{
	private static final int barHorizontalHeight=100;
	private ArrayList<Brique> briques=new ArrayList<Brique>();
	private ArrayList<Brique> menuBriques=new ArrayList<Brique>();
	private Brique briqueSelectionne;
	private int oldBriqueX,oldBriqueY;
	private int couleurId;
	private boolean sauvegarder=false;
	private String nomFichier="";
	private boolean sauvegarderSucces;
	private boolean selectionmenu;
	private boolean debut=true;
	private int indexSelection=-1;
	private boolean gommeActive=false;
	public LevelEditor()
	{
		width=800;
		height=600;
		
		for(int i=0;i<4;i++)
		{
			BriqueClassic b=new BriqueClassic(70*i,600-barHorizontalHeight/2-35,false);
			b.setColor(Brique.getCouleurs()[0]);
			b.setLife(i+1);
			menuBriques.add(b);
		}
		for(int i=0;i<4;i++)
		{
			BriqueExplosive b=new BriqueExplosive(70*i,600-barHorizontalHeight/2,false);
			b.setColor(Brique.getCouleurs()[0]);
			b.setLife(i+1);
			menuBriques.add(b);
		}
		
		for(int i=0;i<1;i++)
		{
			BriqueMetal b=new BriqueMetal(300+70*i,600-barHorizontalHeight/2,false);
			b.setColor(Color.darkGray);
			b.setLife(i+1);
			menuBriques.add(b);
		}
		
		
	}

	public void update(GameContainer arg0, StateBasedGame arg1, int arg2) {
		// TODO Auto-generated method stub
		
	}

	public void render(GameContainer arg0, StateBasedGame arg1, Graphics arg2) throws SlickException {
		// TODO Auto-generated method stub
		
		
		if(debut)
		{

			Font titre1Font = new Font("Kalinga", Font.BOLD, 20);
			TrueTypeFont font1 = new TrueTypeFont(titre1Font, false);
			arg2.setFont(font1);
			arg2.drawString("Cliquer sur le carré et cliquer ou vous souhaitez le placer !", 100, 170);
			arg2.drawString("       Touche haut ou bas pour changer de couleur", 100, 220);
			arg2.drawString("Appuyez sur S pour sauvegarder", 200, 270);

		}
		
		arg2.setColor(Color.red);
		arg2.fillRect(0f,  (float)height-barHorizontalHeight, (float)width, 2);
		

		arg2.setColor(Color.white);
		arg2.fillRect(0f,  384, (float)width, 1);
		
		
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
		
		if(indexSelection!=-1)
		{
			arg2.setColor(Color.white);
			arg2.fillRect((float)menuBriques.get(indexSelection).getX(),(float)menuBriques.get(indexSelection).getY(),
					(float)menuBriques.get(indexSelection).getWidth(),2f);
			arg2.fillRect((float)menuBriques.get(indexSelection).getX(),(float)menuBriques.get(indexSelection).getY()+(float)menuBriques.get(indexSelection).getHeight()-2,
					(float)menuBriques.get(indexSelection).getWidth(),2f);
			arg2.fillRect((float)menuBriques.get(indexSelection).getX(),(float)menuBriques.get(indexSelection).getY(),2,
					(float)menuBriques.get(indexSelection).getHeight());
			arg2.fillRect((float)menuBriques.get(indexSelection).getX()+(float)menuBriques.get(indexSelection).getWidth()-2,(float)menuBriques.get(indexSelection).getY(),2,
					(float)menuBriques.get(indexSelection).getHeight());
			
			
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
			arg2.drawString("Appuyer sur entrer pour enregistrer le niveau", 260, 310);
			
			if(sauvegarderSucces)
			{
				arg2.setColor(Color.green);
				arg2.drawString("Sauvegarder avec succès ! (je crois...)", 280, 340);
			}
			
		}
		
		arg2.setColor(Color.red);
		arg2.fillOval(400, (float)height-barHorizontalHeight+barHorizontalHeight/4,  barHorizontalHeight/4, barHorizontalHeight/4);
		if(gommeActive)arg2.setColor(Color.green);
		else arg2.setColor(Color.black);
		arg2.fillOval(402, (float)height-barHorizontalHeight+barHorizontalHeight/4+2, barHorizontalHeight/4-4, barHorizontalHeight/4-4);
		
		
	}

	public void mouseReleased(int button, int x,int y){
		
		if(!gommeActive && briqueSelectionne!=null)
		{
			if(y<=384)
			{
				briqueSelectionne.setX(((int)briqueSelectionne.getX()/64)*64+12);
				briqueSelectionne.setY(((int)briqueSelectionne.getY()/32)*32);
			}else {
				briqueSelectionne.setX(oldBriqueX);
				briqueSelectionne.setY(oldBriqueY);
			}
			if(!selectionmenu){
				briques.add(briqueSelectionne);
				briqueSelectionne=copie(briqueSelectionne);
			}
			
		}
	
		Brique b=yapasdebriques(x,y);
		if(gommeActive && b!=null )briques.remove(b);
		
	}
	
	public void mousePressed(int button, int oldx,int oldy){
		debut=false;
		if(oldx>600 && oldy> (float)height-barHorizontalHeight+barHorizontalHeight/4 &&  oldx<600+barHorizontalHeight/2&& oldy< (float)height-barHorizontalHeight+barHorizontalHeight/4+ barHorizontalHeight/2)gommeActive=!gommeActive;
		
		if(!gommeActive)
		{
			
			selectionmenu=false;
			for(int i=0;i<menuBriques.size();i++)
			{
				if(menuBriques.get(i).getX()<oldx  && menuBriques.get(i).getX()+menuBriques.get(i).getWidth()>oldx
					&& menuBriques.get(i).getY()<oldy  && menuBriques.get(i).getY()+menuBriques.get(i).getHeight()>oldy)
				{
					briqueSelectionne=copie(menuBriques.get(i));
					oldBriqueX=(int)briqueSelectionne.getX();
					oldBriqueY=(int)briqueSelectionne.getY();
					selectionmenu=true;
					indexSelection=i;
				}
			}
		}
		
		
		
		
	}
	
	
	private Brique copie(Brique brique) {

		if(brique instanceof BriqueClassic)return new BriqueClassic(brique);
		else if(brique instanceof BriqueExplosive)return new BriqueExplosive(brique);
		else if(brique instanceof BriqueMetal)return new BriqueMetal(brique);
		else if(brique instanceof BriqueTp)return new BriqueTp(brique);
		return null;
	}

	public void mouseMoved(int oldx, int oldy, int newx, int newy) {

		if(!gommeActive && briqueSelectionne!=null && yapasdebriques(newx,newy)==null  && newx<=800-12 && newy<=384)
		{
			briqueSelectionne.setX(newx);
			briqueSelectionne.setY(newy);
			briqueSelectionne.setX(((int)briqueSelectionne.getX()/64)*64+12);
			briqueSelectionne.setY(((int)briqueSelectionne.getY()/32)*32);
		}else if(gommeActive){
			
		}
		
	}

	private Brique yapasdebriques(int newx, int newy) {

		for(Brique b:briques)
		{
			if(b.getX()==((newx)/64)*64+12 && b.getY()==(newy/32)*32)return b;
		}
		return null;
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
			else if(key!=Input.KEY_SPACE && ((int)c)!=0){
				System.out.println("char : "+c);
				nomFichier+=c;
			}
			
		}
		
		
		if(key==Input.KEY_UP){
			couleurId++;
			if(couleurId==Brique.getCouleurs().length)couleurId=0;
		}
		else if(key==Input.KEY_DOWN){
			couleurId--;
			if(couleurId==-1)couleurId=Brique.getCouleurs().length-1;

		}
		else if(key==Input.KEY_S)
		{
			sauvegarder=true;
			
		}
		
		couleurId=couleurId%Brique.getCouleurs().length;
		for(int i=0;i<menuBriques.size();i++)
		{
			menuBriques.get(i).setColor(Brique.getCouleurs()[couleurId]);
		}
		if(briqueSelectionne!=null)
		{
			briqueSelectionne.setColor(Brique.getCouleurs()[couleurId]);
		}
		
	
	}

	private void enregistrerNiveau() {
		
		ArrayList<String> textLines=new ArrayList<String>();
		for(Brique b:briques)
		{
			textLines.add(b.briqueToString());
		}
		
		try {
			Files.delete(Paths.get("levels"+File.separator+nomFichier+".txt"));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		WriteFile file = new WriteFile("levels"+File.separator+nomFichier+".txt",false);
		try {
			file.writeToFile(textLines);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		sauvegarderSucces=true;
	}
	
	public void mouseWheelMoved(int newValue){
		couleurId++;
		couleurId=couleurId%Brique.getCouleurs().length;
		for(int i=0;i<menuBriques.size();i++)
		{
			menuBriques.get(i).setColor(Brique.getCouleurs()[couleurId]);
		}
		if(briqueSelectionne!=null)
		{
			briqueSelectionne.setColor(Brique.getCouleurs()[couleurId]);
		}
		
	
	}
	
}
