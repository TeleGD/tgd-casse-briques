package fr.util;

import fr.entity.Player;
import fr.main.World;

public class Collisions {
	
	static double distCC;
	
	public static boolean isCollisionRectRect(Rectangle rect1, Rectangle rect2){
		int marge = 0;
    	if (rect1.getX()+rect1.getWidth()-marge >= rect2.getX() && rect1.getX()+marge <= rect2.getX()+rect2.getWidth()){
    		if(rect1.getY()+rect1.getHeight()-marge >= rect2.getY() && rect1.getY()+marge <= rect2.getY()+rect2.getHeight()){
    			return true;
    		}else return false;
    	}else return false;
    }
	
	public static boolean isCollisionCircleCircle(Circle c1,Circle c2){
		int marge = 0;
		distCC = Geometry.distance(c1.getX(), c1.getY(), c2.getX(), c2.getY());
		return ((distCC+marge) > (c1.getRadius()+c2.getRadius()));
	}
	
	public static boolean isCollisionCircleRect(Circle c,Rectangle rect){
		double x1,x2,y1,y2;
		boolean res=false;
		x1=rect.getX();
		x2=rect.getX()+rect.getWidth();
		y1=rect.getY();
		y2=rect.getY()+rect.getHeight();
		for (int i =(int) x1; i < x2+1; i++) {
			res=res||(Geometry.distance(c.getX(), c.getY(),(double) i, y1) < c.getRadius());
			res=res||(Geometry.distance(c.getX(), c.getY(),(double) i, y2) < c.getRadius());
		}
		for (int j = (int)y1; j < y2+1; j++) {
			res=res||(Geometry.distance(c.getX(), c.getY(),x1, (double) j) < c.getRadius());
			res=res||(Geometry.distance(c.getX(), c.getY(),x2, (double) j) < c.getRadius());
			}
		
		return true;
	}
	/*marche pas
	public static boolean isCollisionCircleRect(Circle c, Rectangle rect){
		if(c.getX() <= rect.getX()){
			if(c.getY() <= rect.getY()){
				//marche pas
				return(Geometry.distance(c.getX(), c.getY(), rect.getX(), rect.getY()) < c.getRadius());
			}else if(c.getY() >= rect.getY()){
				return(Geometry.distance(c.getX(), c.getY(), rect.getX(), rect.getY()+rect.getHeight()) < c.getRadius());
			}else{
				return(rect.getX()-c.getX()< c.getRadius());
			}	
		}else if(c.getX() >= rect.getX()){
			if(c.getY() <= rect.getY()){
				return(Geometry.distance(c.getX(), c.getY(), rect.getX()+rect.getWidth(), rect.getY()) < c.getRadius());
			}else if(c.getY() >= rect.getY()){
				return(Geometry.distance(c.getX(), c.getY(), rect.getX()+rect.getWidth(), rect.getY()+rect.getHeight()) < c.getRadius());
			}else{
				return(c.getX()-rect.getX()< c.getRadius());
			}	
		}else{
			if(c.getY() <= rect.getY()){
				return(rect.getY()-c.getY() < c.getRadius());
			}else if(c.getY() >= rect.getY()){
				return(c.getY()-rect.getY() < c.getRadius());
			}else{
				return true;
			}	
		}

	}*/

	//Detection de collisions avec la raquette :
    public static boolean colPlayer(Movable object,Player player){
        	
		if (object.getY()+object.getWidth()<player.getY()){return false;}
		if(object.getX()+object.getWidth()<player.getX()){return false;}
		if(object.getX()+object.getWidth()>player.getX()+World.getPlayer().getWidth()){return false;}
		return true;
    }

}
