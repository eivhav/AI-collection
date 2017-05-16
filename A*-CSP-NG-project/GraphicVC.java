package project1;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Shape;
import java.awt.geom.Ellipse2D;
import java.util.ArrayList;





@SuppressWarnings("serial")
public class GraphicVC extends GraphicCSP{ 
		
		// This class defines the graphics 
		

		private Color edge = new Color(17, 17, 17);
		private Color lookingAt = new Color(163, 73, 164); 
		private ArrayList<Color> colorList = new ArrayList<Color>(); 
		private int panelXsize; 
		private int panelYsize;
		private int startXpoint = 0; 
		private int startYpoint = 0; 
		boolean printNumbers = false;
		private int currentStateID; 
		private ArrayList<VarPoint> varPoints = new  ArrayList<VarPoint>(); 
		private ArrayList<ConstrSet> constrSets = new ArrayList<ConstrSet>(); 
		
		
		
		public GraphicVC(int panelXsize, int panelYsize, ArrayList<VarPoint> varPoints, ArrayList<ConstrSet> constrSets){
			this.panelYsize = panelYsize; 
			this.panelXsize = panelXsize; 
			this.varPoints = varPoints;
			this.constrSets = constrSets; 
			calculateRatios(); 
			createColors(); 
		}
			
		
		public void paint(Graphics g) { 
			Graphics2D g2d = (Graphics2D) g;
			g2d.setColor(edge); 
			
			for(int i = 0; i < constrSets.size(); i++){
				ConstrSet c = constrSets.get(i);  
				g2d.drawLine(c.getVar1Xpos(), c.getVar1Ypos(), c.getVar2Xpos(), c.getVar2Ypos()); 
			}
			
			for(int i = 0; i< constrSets.size(); i++){
				Variable var1 = constrSets.get(i).getVar1(); 
				Variable var2 = constrSets.get(i).getVar2(); 
				
				ArrayList<ArrayList<Integer>> domains = new ArrayList<ArrayList<Integer>>();  
				domains.add(var1.getDomain(currentStateID)); 
				domains.add(var2.getDomain(currentStateID)); 
				
				double[] xpos = new double[2]; 
				xpos[0] = constrSets.get(i).getVar1Xpos(); 
				xpos[1] = constrSets.get(i).getVar2Xpos();
				
				double[] ypos = new double[2];
				ypos[0] = constrSets.get(i).getVar1Ypos(); 
				ypos[1] = constrSets.get(i).getVar2Ypos();
				
				
				for(int j = 0; j < domains.size(); j++){
					if(domains.get(j).size() != 0){
						if(domains.get(j).get(0) <= 9 && domains.get(j).size() == 1){
							g2d.setColor(colorList.get(domains.get(j).get(0)));
						}
						else{ 
							g2d.setColor(new Color(120, 120, 120));
						}
						Ellipse2D.Double circle1 = new Ellipse2D.Double(xpos[j]-5, ypos[j]-5, 10, 10);
						g2d.fill(circle1);	
						
						if(j == 0 && var1.hasBeenGueesed(currentStateID)){
							Shape circle = new Ellipse2D.Double(xpos[j]-10, ypos[j]-10, 20, 20); 
							g2d.fill(circle);	
						}
						else if(j == 1 && var2.hasBeenGueesed(currentStateID)){
							Ellipse2D.Double circle = new Ellipse2D.Double(xpos[j]-10, ypos[j]-10, 20, 20);
							g2d.fill(circle);	
						}
					}		
				}	
			}	
			if(printNumbers){
				for(int i = 0; i< constrSets.size(); i++){
					
					Variable var1 = constrSets.get(i).getVar1(); 
					Variable var2 = constrSets.get(i).getVar2(); 
					
					ArrayList<ArrayList<Integer>> domains = new ArrayList<ArrayList<Integer>>();  
					domains.add(var1.getDomain(currentStateID)); 
					domains.add(var2.getDomain(currentStateID)); 
					
					double[] xpos = new double[2]; 
					xpos[0] = constrSets.get(i).getVar1Xpos(); 
					xpos[1] = constrSets.get(i).getVar2Xpos();
					
					double[] ypos = new double[2];
					ypos[0] = constrSets.get(i).getVar1Ypos(); 
					ypos[1] = constrSets.get(i).getVar2Ypos();
					
					g2d.setColor(lookingAt); 
					String v1 = constrSets.get(i).getVar1().getName();
					String v2 = constrSets.get(i).getVar2().getName(); 
					g2d.drawString(v1, (int) xpos[0], (int) ypos[0]); 
					g2d.drawString(v2, (int) xpos[1], (int) ypos[1]); 
				}
			}
		}
		
	
		private void createColors(){
			Color pink = new Color(255, 102, 178); 
			Color orange = new Color(255, 153, 51); 
			Color yellow = new Color(255, 255, 51);
			Color lGreen = new Color(178, 255, 102);
			Color red = new Color(204, 0, 0);
			Color blue = new Color(0, 162, 232);
			Color dgreen = new Color(0, 204, 102);
			Color dBlue = new Color(102, 51, 0);
			Color brown = new Color(255, 102, 178);
			Color purple = new Color(153, 0, 153);
			
			colorList.add(pink); 
			colorList.add(orange); 
			colorList.add(yellow); 
			colorList.add(lGreen);
			colorList.add(red);
			colorList.add(blue);
			colorList.add(dgreen);
			colorList.add(dBlue);
			colorList.add(brown);
			colorList.add(purple);
			
		}
		
		
		
		public void calculateRatios() {
			double[] range = getRange(); 
			double xratio = ((double) panelXsize) / ((range[0] + Math.sqrt(range[2] *range[2])));  
			double yratio = ((double) panelYsize) / ((range[1] + Math.sqrt(range[3] *range[3]))); 
			startXpoint = (int) ((panelXsize/2) - (xratio*(range[0] + range[2])/2) + 15); 
			startYpoint = (int) ((panelYsize/2) - (yratio*(range[1] + range[3])/2) + 15); 
			for(int i = 0; i < varPoints.size(); i++){ 
				varPoints.get(i).setxPos((int) (varPoints.get(i).getxPos() * xratio) + startXpoint); 
				varPoints.get(i).setyPos((int) (varPoints.get(i).getyPos() * yratio) + startYpoint); 
			}
			for(int i = 0; i < constrSets.size(); i++){ 
				constrSets.get(i).setVar1Xpos((int) (constrSets.get(i).getVar1Xpos() * xratio) + startXpoint); 
				constrSets.get(i).setVar1Ypos((int) (constrSets.get(i).getVar1Ypos() * yratio) + startYpoint); 
				constrSets.get(i).setVar2Xpos((int) (constrSets.get(i).getVar2Xpos() * xratio) + startXpoint); 
				constrSets.get(i).setVar2Ypos((int) (constrSets.get(i).getVar2Ypos() * yratio) + startYpoint);  
			}
		}
		
		
		
		public double[] getRange(){
			double[] returnlist = new double[4]; 
			returnlist[0] = -100000;   
			returnlist[1] = -100000;
			returnlist[2] = 100000;  
			returnlist[3] = 100000;
			 
			for(int i = 0; i < varPoints.size(); i++){ 
				if(varPoints.get(i).getxPos() > returnlist[0]){
					returnlist[0] = varPoints.get(i).getxPos(); 
				}
				if(varPoints.get(i).getyPos() > returnlist[1]){
					returnlist[1] = varPoints.get(i).getyPos(); 
				}
				if(varPoints.get(i).getxPos() < returnlist[2]){
					returnlist[2] = varPoints.get(i).getxPos(); 
				}
				if(varPoints.get(i).getyPos() < returnlist[3]){
					returnlist[3] = varPoints.get(i).getyPos(); 
				}
			}
			return returnlist; 
		}
		
		
		
		
		
		public int getCurrentStateID() {
			return currentStateID;
		}

		public void setCurrentStateID(int currentStateID) {
			this.currentStateID = currentStateID;
		}	
		
		public void setStateID(int currentStateID){
			setCurrentStateID(currentStateID); 			
		}
		
		public void setPrintNumbers(boolean b){
			printNumbers = b; 
		}
	




}
