package games.casseBriques.editor;

import java.io.BufferedReader;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.List;

import org.newdawn.slick.Color;
import org.newdawn.slick.Font;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Input;
import org.newdawn.slick.state.StateBasedGame;

import app.AppFont;
import app.AppLoader;

import games.casseBriques.entities.Brique;
import games.casseBriques.entities.bricks.BriqueClassic;
import games.casseBriques.entities.bricks.BriqueExplosive;
import games.casseBriques.entities.bricks.BriqueMetal;

public class LevelEditor {

	private int width=800;
	private int height=600;
	private int barHorizontalHeight=100;
	private ArrayList<Brique> briques=new ArrayList<Brique>();
	private ArrayList<Brique> menuBriques=new ArrayList<Brique>();
	private Brique briqueSelectionne;
	private int couleurId;
	private boolean sauvegarder=false;
	private String nomFichier="";
	private boolean sauvegarderSucces;
	private boolean debut=true;
	private int indexSelection=-1;
	private boolean gommeActive=false;
	private boolean frolleSauvegarde;

	public LevelEditor()
	{
		for(int i=0;i<4;i++)
		{
			BriqueClassic b=new BriqueClassic(null,70*i,600-barHorizontalHeight/2-35,false);
			b.setColor(Brique.getCouleurs()[0]);
			b.setLife(i+1);
			menuBriques.add(b);
		}
		for(int i=0;i<4;i++)
		{
			BriqueExplosive b=new BriqueExplosive(null,70*i,600-barHorizontalHeight/2,false);
			b.setColor(Brique.getCouleurs()[0]);
			b.setLife(i+1);
			menuBriques.add(b);
		}
		BriqueMetal b=new BriqueMetal(null,300,600-barHorizontalHeight/2,false);
		b.setColor(Color.darkGray);
		b.setLife(-1);
		menuBriques.add(b);
	}

	public void render(GameContainer arg0, StateBasedGame arg1, Graphics arg2) {
		// TODO Auto-generated method stub

		if(debut)
		{
			Font font1 = AppLoader.loadFont("/fonts/vt323.ttf", AppFont.BOLD, 24);
			arg2.setFont(font1);
			arg2.setColor(Color.red);
			arg2.drawString("Cliquez sur le carré et cliquez où vous souhaitez le placer !", 75, 143);
			arg2.drawString("Touchez haut ou bas pour changer de couleur", 150, 173);
			arg2.drawString("Appuyez sur S pour sauvegarder", 225, 203);
		}

		arg2.setColor(Color.red);
		arg2.fillRect(0f,  (float)height-barHorizontalHeight, (float)width, 2);
		arg2.setColor(Color.white);
		arg2.fillRect(15f,  384, (float)width-31, 1);
		arg2.fillRect(15,  0,1, 384);
		arg2.fillRect((float)width-16,  0,1, 384);

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
			arg2.fillRoundRect(200, 100, 400, 250,20,20);
			arg2.setColor(Color.black);
			arg2.fillRoundRect(202, 102, 396, 246,20,20);
			arg2.setColor(Color.red);
			Font font1 = AppLoader.loadFont("/fonts/vt323.ttf", AppFont.BOLD, 30);
			arg2.setFont(font1);
			arg2.drawString("Sauvegarder le niveau", 270, 120);
			arg2.setColor(Color.white);
			font1 = AppLoader.loadFont("/fonts/vt323.ttf", AppFont.BOLD, 24);
			arg2.setFont(font1);
			arg2.drawString("Entrez le nom du niveau :", 240, 183);
			arg2.drawString(nomFichier, 240, 213);
			arg2.drawString("Appuyez sur entrée pour", 240, 273);
			arg2.drawString("enregistrer le niveau", 240, 303);

			if(sauvegarderSucces)
			{
				arg2.setColor(Color.green);
				arg2.drawString("Sauvegardé avec succès ! (je crois)", 210, 423);
			}
		}
		Font font1 = AppLoader.loadFont("/fonts/vt323.ttf", AppFont.BOLD, 24);
	    if(frolleSauvegarde)arg2.setColor(Color.red);
	    else arg2.setColor(Color.orange);
		arg2.setFont(font1);
		arg2.drawString("SAUVEGARDER", 650, 540);

		arg2.setColor(Color.red);
		arg2.fillOval(310, (float)height-barHorizontalHeight+barHorizontalHeight/7,  barHorizontalHeight/4, barHorizontalHeight/4);
		if(gommeActive)arg2.setColor(Color.white);
		else arg2.setColor(Color.black);
		arg2.fillOval(312, (float)height-barHorizontalHeight+barHorizontalHeight/7+2, barHorizontalHeight/4-4, barHorizontalHeight/4-4);
	}

	public void mouseReleased(int button, int x,int y){}

	public void mousePressed(int button, int oldx,int oldy){
		debut=false;

		if(oldx>310 && oldy> (float)height-barHorizontalHeight+barHorizontalHeight/7 &&  oldx<310+barHorizontalHeight/4 && oldy< (float)height-barHorizontalHeight+barHorizontalHeight/7+ barHorizontalHeight/4)
		{
			gommeActive=!gommeActive;
			briqueSelectionne=null;
		}
		else
		{
			for(int i=0;i<menuBriques.size();i++)
			{
				if(menuBriques.get(i).getX()<oldx  && menuBriques.get(i).getX()+menuBriques.get(i).getWidth()>oldx
					&& menuBriques.get(i).getY()<oldy  && menuBriques.get(i).getY()+menuBriques.get(i).getHeight()>oldy)
				{
					briqueSelectionne=copie(menuBriques.get(i));
					indexSelection=i;
					gommeActive=false;
				}
			}

			if(briqueSelectionne!=null)
			{
				if(oldx>=16 && oldx<784 && oldy>=0 && oldy<384 && recupererBrique(oldx, oldy)==null)
				{
					briqueSelectionne.setX((oldx-16)/64*64+16);
					briqueSelectionne.setY(oldy/32*32);
					briques.add(briqueSelectionne);
					briqueSelectionne=copie(briqueSelectionne);
				}else {
					briqueSelectionne.setX(-112);
					briqueSelectionne.setY(-64);
				}
			}

			if(gommeActive)
			{
				Brique b=recupererBrique(oldx,oldy);
				if( b!=null )briques.remove(b);
			}

			if(frolleSauvegarde){
				sauvegarder=true;
			}
		}
	}

	private Brique copie(Brique brique) {
		if(brique instanceof BriqueExplosive)return new BriqueExplosive(brique);
		else if(brique instanceof BriqueMetal)return new BriqueMetal(brique);
		else return new BriqueClassic(brique);
	}

	public void mouseMoved(int oldx, int oldy, int newx, int newy) {
		if(newx>650 && newx<750 && newy>=540 && newy<=560){
			frolleSauvegarde=true;
		}else
			frolleSauvegarde=false;

		if(briqueSelectionne!=null)
		{
			if(newx>=16 && newx<784 && newy>=0 && newy<384 && recupererBrique(newx,newy)==null)
			{
				briqueSelectionne.setX((newx-16)/64*64+16);
				briqueSelectionne.setY((newy/32)*32);
			} else {
				briqueSelectionne.setX(-112);
				briqueSelectionne.setY(-64);
			}
		}
	}

	private Brique recupererBrique(int newx, int newy) {
		for(Brique b:briques)
		{
			if(b.getX()==(newx-16)/64*64+16 && b.getY()==newy/32*32)return b;
		}
		return null;
	}

	public void mouseDragged(int oldx,int  oldy, int newx,int  newy){
		mouseMoved(oldx,oldy,newx,newy);
		mousePressed(0,newx,newy);
	}
	public void keyPressed(int key, char c) {
		if(sauvegarder)
		{
			if(key == Input.KEY_ESCAPE){
				sauvegarder=false;
			}else if(key==Input.KEY_BACK){
				if(nomFichier.length()>0)nomFichier=nomFichier.substring(0, nomFichier.length()-1);
			}else if(key==Input.KEY_ENTER)
			{
				enregistrerNiveau();
			}
			else if(key!=Input.KEY_SPACE && ((int)c)!=0&&c!='.'){
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
		String levels = AppLoader.restoreData("/casseBriques/levels.txt");
		if (levels == null) {
			levels = "";
		}
		BufferedReader reader = new BufferedReader(new StringReader(levels));
		List<String> items = new ArrayList<String>();
		String line;
		try {
			while ((line = reader.readLine()) != null) {
				items.add(line);
			}
			reader.close();
		} catch (Exception error) {}
		int i = 0;
		int li = items.size();
		while (i < li && items.get(i).compareTo(nomFichier) < 0) {
			++i;
		}
		if (i == li || !items.get(i).equals(nomFichier)) {
			items.add(i, nomFichier);
		}
		AppLoader.saveData("/casseBriques/levels.txt", String.join("\n", items));
		ArrayList<String> textLines=new ArrayList<String>();
		for(Brique b:briques)
		{
			textLines.add(b.briqueToString());
		}
		AppLoader.saveData("/casseBriques/levels/" + nomFichier + ".txt", String.join("\n", textLines));
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

	public void removeAllBriques() {
		briques.removeAll(briques);
	}

	public void addBrique(Brique b) {
		briques.add(b);
	}

	public void reload(String nomFichier) {
		briques.removeAll(briques);
		sauvegarderSucces=false;
		sauvegarder=false;
		briqueSelectionne=null;
		debut=true;
		this.nomFichier=nomFichier;
	}

	public boolean getSauvegarder() {
		return this.sauvegarder;
	}

}
