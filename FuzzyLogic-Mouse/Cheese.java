package oving4;

import java.awt.Graphics;
import java.util.ArrayList;
import java.util.Random;

import javax.swing.JFrame;

public class Cheese {
	
	static int numberOfMouses = 10;
	static double timeIntervall = 100; 
	static int range = 100; 
	static int viewAngle = 40; 
	static int stepValueNormal = 10; 
	static int stepValueFast = 17; 
	static int stepValueSlow = 7; 
	static int numberOfattacks = 0; 
	static int numberOfstandby = 0;
	static int numberOfescapes = 0;
	
	static ArrayList<Mouse> mouses = new ArrayList<Mouse>(); 
	static ArrayList<ArrayList<Mouse>> oponents = new ArrayList<ArrayList<Mouse>>(); 
	
	
	public void generateMouses(int numberOfMouses){
		for(int i = 0; i < numberOfMouses; i++){
			Mouse mouse = new Mouse(); 
			mouse.setNumber(i); 
			mouses.add(mouse); 
		}
	}
	
	
	public static void setOponents(){
		for(int i = 0; i < mouses.size(); i++){
			mouses.get(i).findAndSetOponment(mouses, range, viewAngle); 
			if(mouses.get(i).getOponment() != null) {
				ArrayList<Mouse> opPair = new ArrayList<Mouse>(); 
				opPair.add(mouses.get(i)); 
				opPair.add(mouses.get(i).getOponment()); 
				boolean isInList = false; 
				for (int j = 0; j < oponents.size(); j++){
					if(mouses.get(i) == oponents.get(j).get(1) || mouses.get(i) == oponents.get(j).get(0) )
						isInList = true; 
				}
				if(!isInList)
					oponents.add(opPair);
			}
		}		
	}
	
	public static boolean checkOponents(){
		boolean tempreturn = true; 
		for(int i = 0; i < oponents.size(); i++){
			if(oponents.get(i).get(0).getOponment() == null || oponents.get(i).get(1).getOponment() == null){
				tempreturn = false; 
				break; 
			}
		}
		return tempreturn; 
	}
	
	public static void printOponents(){
		for(int i = 0; i < oponents.size(); i++){
			System.out.print(i + " : Mouse1: " + oponents.get(i).get(0).getNumber() + " op: " + oponents.get(i).get(0).getOponment().getNumber() + "  Mouse2: " + oponents.get(i).get(1).getNumber() + " op: " + oponents.get(i).get(1).getOponment().getNumber() + "\n"); 
		}
	}
	
	public static void moveMouses(int stepValue){
		for(int i = 0; i < mouses.size(); i++){
			
				mouses.get(i).moveByDirection(stepValue, true); 
		}
	}
	
	
	public static void clearOponents(){
		for(int i = 0; i < mouses.size(); i++){
			mouses.get(i).setOponment(null);  
		}
		oponents = new ArrayList<ArrayList<Mouse>>();
	}
	
	
	public static void action(){
		for(int i = 0; i < oponents.size(); i++){
			Random rnd = new Random(); 
			int no = rnd.nextInt(2); 
			Mouse attacker = oponents.get(i).get(no); 
			attacker.turnToOpnent(attacker.getOponment());  
			FuzzyLogic logic = new FuzzyLogic(attacker, attacker.getOponment()); 
			String action = logic.getAction();  
			if(action.equals("attack")){
				numberOfattacks++; 
				attacker.attack();}
			else if(action.equals("standby")){
				numberOfstandby++; 
				attacker.ignore(stepValueNormal); }
			else if(action.equals("escape")){
				numberOfescapes++; 
				attacker.flee(range); 
			}		
		}
	}
	
	public static void removeDead(){
		for(int i = 0; i < mouses.size(); i++){
			if(mouses.get(i).getHealth() <= 0)
				mouses.remove(i);
		}
	}
	
	public static void generateRandomMouses(){
		for(int i = 0; i < numberOfMouses; i++){
			//Random rnd = new Random(); 
			int health = 100;  
			Random rnd2 = new Random(); 
			int xPos = rnd2.nextInt(300); 
			Random rnd3 = new Random(); 
			int yPos = rnd3.nextInt(500); 
			Random rnd4 = new Random(); 
			int dir = rnd4.nextInt(360); 
			
			Mouse mouse = new Mouse(); 
			mouse.setNumber(i); 
			mouse.setHealth(health); 
			mouse.setxPos(xPos);
			mouse.setyPos(yPos); 
			mouse.setDirection(dir); 
			mouses.add(mouse); 
		}
	}
	
	
	public static void main(String[] args){
		
		generateRandomMouses(); 
		
	    JFrame frame = new JFrame();
	    GraphicDisplay GD = new GraphicDisplay(mouses, range); 
	    frame.add(GD); 
	    frame.setSize(650, 650);
	    frame.setVisible(true);
	    
	    double time = System.currentTimeMillis(); 
	    boolean running = true; 
	    
	    while(running)
	    	
	    	if((System.currentTimeMillis() -timeIntervall ) > time){
	    		time = System.currentTimeMillis(); 
	    		clearOponents(); 
	    		moveMouses(stepValueNormal);
	    		if(mouses.size() == 3 ){
	    			mouses.get(1).followMouse(mouses.get(2), stepValueSlow); }
	    		else if(mouses.size() < 3 && mouses.size() > 1){
	    			mouses.get(0).followMouse(mouses.get(1), stepValueSlow); }
	    		else if(mouses.size() == 1){
	    			running = false;} 
	    		setOponents(); 
	    		printOponents(); 
	    		action(); 
	    		removeDead(); 
	    		System.out.print("numberofAttacs: " + numberOfattacks + "\n" + "numberOfstandby: " +  numberOfstandby + "\n" + "numberOfescapes : " + numberOfescapes + "\n"  + "\n");  
	    		GD.repaint(); 
	    		
	    }
	}
	    
	    

	
	
	}
	
	
	
	

