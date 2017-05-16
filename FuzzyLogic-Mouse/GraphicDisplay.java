package oving4;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.util.ArrayList;

import javax.swing.JComponent;

public class GraphicDisplay extends JComponent {
	
	ArrayList<Mouse> mouses = new ArrayList<Mouse>(); 
	int range; 
	int rangeAngle; 
	double pi = Math.PI; 
	
	public GraphicDisplay(ArrayList<Mouse> mouses,int range){
		this.mouses = mouses; 
		this.range = range; 
	}
	
	
	public void paint(Graphics g){
		Graphics2D g2d = (Graphics2D) g;
		g2d.setColor(new Color(225, 225, 200)); 
		g2d.fillRect(15, 15, 600, 600); 
		
		for(int j = 0; j < mouses.size(); j++){
			drawMouse(mouses.get(j), g2d);	
		}
		for(int i = 0; i < mouses.size(); i++){
			drawRangeZone(mouses.get(i), g2d); 	
		}
	}
	
	public void repaint(Graphics g){
		paint(g); 
	}
	
	private void drawRangeZone(Mouse mouse, Graphics g){
		Graphics g2d = g; 	
						
		if(mouse.isAlive && mouse.getOponment() != null){
			g2d.setColor(Color.red);      		
			g2d.drawOval(mouse.getxPos() - (range/2), mouse.getyPos() - (range/2), range, range);}
		else if(mouse.isAlive && mouse.getOponment() == null){
			g2d.setColor(Color.green);      		
			g2d.drawOval(mouse.getxPos() - (range/2), mouse.getyPos() - (range/2), range, range);}
	}
	
	private void drawMouse(Mouse mouse, Graphics g){
		Graphics g2d = g; 
		if(mouse.isAlive){
			g2d.setColor(choiceColor(mouse)); 
			g2d.fillOval(mouse.getxPos() -15, mouse.getyPos() -15, 30, 30); 
			g2d.setColor(Color.DARK_GRAY); 
			int[] nosepos = getNoseOrTailPos(mouse, "nose");  
			g2d.fillOval(nosepos[0], nosepos[1], 10, 10); 
			g2d.setColor(Color.white);
			int[] tailpos = getNoseOrTailPos(mouse, "tail"); 
			g2d.fillOval(tailpos[0], tailpos[1], 8, 8); 
			String number = "" + mouse.getNumber(); 
			g2d.setColor(Color.black); 
			g2d.drawString(number, mouse.getxPos() -3, mouse.getyPos() +5); 
			
			 
		}
	}
	
	
	private int[] getNoseOrTailPos(Mouse mouse, String type){	
		int[] returnArray = new int[2]; 
		double pi = Math.PI; 
		int dx = (int) (15 * ((Math.sin((mouse.getDirection() * 2 *pi/360))))); 
		int dy = (int) (15 * ((Math.cos((mouse.getDirection() * 2 *pi/360))))); 
		if(type.equals("nose")){
			returnArray[0] = mouse.getxPos() + dx - 5;  
			returnArray[1] = mouse.getyPos() - dy - 5;  
		}
		else if(type.equals("tail")){
			returnArray[0] = mouse.getxPos() - dx -4;  
			returnArray[1] = mouse.getyPos() + dy -4;  
		}
		return returnArray; 		
	}
		
		
	private Color choiceColor(Mouse mouse){ 
		int health = mouse.getHealth();  
		Color returnColor = new Color(0,0,0); 
		if(health < 10)
			returnColor = new Color(152, 12, 19); 
		else if(health < 20)
			returnColor = new Color(213, 17, 27); 
		else if(health < 30)
			returnColor = new Color(255, 83, 0); 
		else if(health < 40)
			returnColor = new Color(255, 179, 0); 
		else if(health < 50)
			returnColor = new Color(253, 222, 2); 
		else if(health < 60)
			returnColor = new Color(196, 252, 3); 
		else if(health < 70)
			returnColor = new Color(139, 186, 69); 
		else if(health < 80)
			returnColor = new Color(66, 189, 115); 
		else if(health < 90)
			returnColor = new Color(67, 188, 160); 
		else if(health <= 100)
			returnColor = new Color(67, 146, 188); 
		
		return returnColor; 
	}
	

		
	
	
	
	


	
	
	
	

}
