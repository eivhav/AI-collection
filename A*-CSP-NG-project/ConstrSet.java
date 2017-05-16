package project1;


/*
 * Data structure that holds a constraint, the two variables involved and their x,y coordinates. 
 * Used by VCproblem to input the GraphicsVC class. 
 * GraphicsVC class iterates through them all, and prints each variable and the lines between them.  
 */

public class ConstrSet {
	
	Variable var1; 
	Variable var2; 
	Constraint constr; 
	double var1Xpos;
	double var1Ypos;
	double var2Xpos;
	double var2Ypos;  
	
	
	public ConstrSet(Constraint constr, Variable var1, Variable var2, double var1Xpos, double var1Ypos, double var2Xpos, double var2Ypos){
		this.var1 = var1; 
		this.var2 = var2; 
		this.constr = constr; 
		this.var1Xpos = var1Xpos;  
		this.var1Ypos = var1Ypos; 
		this.var2Xpos = var2Xpos;  
		this.var2Ypos = var2Ypos;
	}
	



	public Variable getVar1() {
		return var1;
	}

	public void setVar1(Variable var1) {
		this.var1 = var1;
	}

	public Variable getVar2() {
		return var2;
	}

	public void setVar2(Variable var2) {
		this.var2 = var2;
	}

	public Constraint getConstr() {
		return constr;
	}

	public void setConstr(Constraint constr) {
		this.constr = constr;
	}

	public int getVar1Xpos() {
		return (int) var1Xpos;
	}

	public void setVar1Xpos(double var1Xpos) {
		this.var1Xpos = var1Xpos;
	}

	public int getVar1Ypos() {
		return (int) var1Ypos;
	}
	
	public void setVar1Ypos(double var1Ypos) {
		this.var1Ypos = var1Ypos;
	}

	public int getVar2Xpos() {
		return (int) var2Xpos;
	}

	public void setVar2Xpos(double var2Xpos) {
		this.var2Xpos = var2Xpos;
	}

	public int getVar2Ypos() {
		return (int) var2Ypos;
	}

	public void setVar2Ypos(double var2Ypos) {
		this.var2Ypos = var2Ypos;
	}
	
	
	
}