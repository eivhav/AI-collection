package project1;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.util.ArrayList;

/*
 * The Graphics class of the Nonogram solution. 
 * The paint method iterates through the grid, and if either the row or column variable is set, 
 * the value for that cell is painted. 
 */
@SuppressWarnings("serial")
public class GraphicNG extends GraphicCSP {
	
	private int[] dim; 
	private int currentStateID; 
	private Color undecided = new Color(249, 176, 180); 
	private Color zero = new Color(220, 220, 220); 
	private Color one = new Color(80, 80, 80);
	private ArrayList<Constraint> constr = new ArrayList<Constraint>(); 
	
	
	public GraphicNG(ArrayList<Constraint> constr, int[] dim){ 
		this.constr = constr;
		this.dim = dim; 
	}
	
	
	public void paint(Graphics g) {
		
		Graphics2D g2d = (Graphics2D) g;
		int posY = 22 ;  
		
		for(int y = 0; y < dim[1]; y++ ){ //Iterates through each column
			
			int posX = 22;  	
			for(int x = 0; x < dim[0]; x++ ){ //Iterates through each row
				
				// Sets the color of the square, based on: obstacle, unseen, open, closed, startpoint or goalpoint 
				
				Constraint constraint = constr.get((x*dim[1]) + y);
				char c = ' '; 
				
				if(constraint.getVarsInvolved().get(0).getDomains().size() != 0 && constraint.getVarsInvolved().get(1).getDomains().size() != 0){
					if(constraint.getVarsInvolved().get(0).getDomain(currentStateID).size() == 1){
						c = constraint.getFinalNGChar(0); 	
					}
					else if(constraint.getVarsInvolved().get(1).getDomain(currentStateID).size() == 1){ 
						c = constraint.getFinalNGChar(1); 
					}
				}
				if(c == ' '){
					g2d.setColor(undecided);
				}
				if(c == '1'){
					g2d.setColor(one);
				}
				if(c == '0'){
				g2d.setColor(zero);
				}
				
				g2d.fill(new Rectangle(posX,posY,22,22));
				g2d.setColor(Color.gray); 
				g2d.drawLine(posX, posY, posX +22, posY); 
				g2d.drawLine(posX, posY, posX, posY +22); 	
					
				posX = posX + 22; 
			}	 
			posY = posY + 22; 
		}			
	}	
	
	public void setStateID(int stateID) {
		currentStateID = stateID; 
	}

}
