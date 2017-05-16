package project1;


import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;

import javax.swing.JComponent;

@SuppressWarnings("serial")
public class GraphicBoard extends JComponent { 
	
	// This class defines the graphic of the map. 
	
	private Gridstate rot; 
	private Color obstacle = new Color(202, 0, 0); 
	private Color unseen = new Color(238, 238, 238); 
	private Color open = new Color(164, 209, 255); 
	private Color closed = new Color(189, 189, 223);    
	private Color inPath = new Color(0, 157, 79); 
	private Color lookingAt = new Color(173, 199, 0); 
	private Color start = new Color(255, 201, 14);  
	private int panelYsize;
	
	
	public GraphicBoard(Gridstate rot, int panelXsize, int panelYsize){
		this.rot = rot; 
		this.panelYsize= panelYsize; 
	}
		
	
	public void paint(Graphics g) {
		
		Graphics2D g2d = (Graphics2D) g;
		Gridstate node = rot; 
		int posY = panelYsize - 22; 
		int count = 0; 
		
		while(node != null) { //Iterates through each column
			
			int posX = 0;  	
			while(node != null){ //Iterates through each row
				
				// Sets the color of the square, based on: obstacle, unseen, open, closed, startpoint or goalpoint 
				
				if(node.isObstacle()){
					g2d.setColor(obstacle);}
				else{
					if(node.isUnseen()){
						g2d.setColor(unseen);
					}
					if(node.isOpen()){
						g2d.setColor(open); 
					}
					else if(node.isClosed()){
						g2d.setColor(closed);
					}
					if(node.isInPath()){
						g2d.setColor(lookingAt);
					}
					if(node.isLookingAt()){
						g2d.setColor(lookingAt);
					}
					if(node.isInFinalPath()){
						g2d.setColor(inPath);
					}
					if(node.isStartState()){
						g2d.setColor(start);
					}	
				}
				
				if(node.isGoalState()){
					g2d.setColor(inPath);
				}
		
				g2d.fill(new Rectangle(posX,posY,22,22));
				
				// Sets letter based on weather it is start/goal/path/open/closed -marked node
				g2d.setColor(Color.black); 
				if(node.isStartState()){
					g2d.drawString("S", posX +14, posY -22);}
				else if(node.isGoalState()){
					g2d.drawString("G", posX +14, posY -22);} 	
			
				
				//Creates a grid around the cell
				g2d.setColor(Color.gray); 
				g2d.drawLine(posX, posY, posX +22, posY); 
				g2d.drawLine(posX, posY, posX, posY +22); 
				
				posX = posX + 22; 
				node = node.getWest(); 
			}
			
			count++; 	
			posY = posY - 22;   	
			
			Gridstate tempNode = rot;   
			for(int i = 0; i < count; i++){
				tempNode = tempNode.getSouth(); 
			}
			node = tempNode; 
			}			
		}		 
}


