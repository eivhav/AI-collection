package project1;

/*
 * Data structure that holds a variable and their x,y coordinates. 
 * Used by VCproblem to input the GraphicsVC class. 
 * GraphicsVC class iterates through them all, and prints each variable. 
 */


public class VarPoint {
	
	double xPos; 
	double yPos; 
	Variable var; 
	
	public VarPoint(double xPos, double yPos, Variable var){
		this.var = var; 
		this.xPos = xPos; 
		this.yPos= yPos; 
		
	}

	public double getxPos() {
		return xPos;
	}

	public void setxPos(double xPos) {
		this.xPos = xPos;
	}

	public double getyPos() {
		return yPos;
	}

	public void setyPos(double yPos) {
		this.yPos = yPos;
	}

	public Variable getVar() {
		return var;
	}

	public void setVar(Variable var) {
		this.var = var;
	}

	
}
