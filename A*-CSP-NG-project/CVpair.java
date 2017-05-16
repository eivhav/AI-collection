package project1;

/*
 * Data structure that holds a constraint and a Variable. 
 * Used by CSPstate to load up the reviseQueue.
 *  
 */


public class CVpair {
	
	private Constraint constraint; 
	private Variable variable; 
	
	public CVpair(Constraint constr, Variable var){
		this.setConstraint(constr); 
		this.setVariable(var); 
	}

	
	public Constraint getConstraint() {
		return constraint;
	}

	public void setConstraint(Constraint constraint) {
		this.constraint = constraint;
	}

	public Variable getVariable() {
		return variable;
	}

	public void setVariable(Variable variable) {
		this.variable = variable;
	}
	
	
	
}
