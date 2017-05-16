package project1;

import java.util.ArrayList;

/* 
 * Constraint used in VC coloring and suduko.
 * Inherits from constrain class. 
 */

public class NotEqConstraint extends Constraint {
	
	private int number;  
	private String description = "noteqCompare";  
	private ArrayList<Variable> varsInvolved;  
	
	
	public NotEqConstraint(int number, ArrayList<Variable> varaiblesInvolved){
		this.varsInvolved = varaiblesInvolved; 
		this.number = number; 
		for(int i = 0; i < varaiblesInvolved.size(); i++){
			varaiblesInvolved.get(i).getConstraints().add(this); 
		}
	}
	
	
	
	// Test if all the elements in the list are unique. 
	public boolean testConstraint(){
		for(int i = 0; i < varsInvolved.size()-1; i++){
			for(int j = i+1; j < varsInvolved.size(); j++ ){
				if(varsInvolved.get(i).getTestValue() == varsInvolved.get(j).getTestValue()){ 
					return false;  
				}
			}
		}	 
		return true;  
	}
	
	
	
	
	// returns all variables in a constraint without a given variable. 
	public ArrayList<Variable> getVarsInvovledWithoutFocalVar(Variable focalVar){
		ArrayList<Variable> returnList = new ArrayList<Variable>(); 
		for(int i = 0; i < varsInvolved.size(); i++){
			if(varsInvolved.get(i) != focalVar){
				returnList.add(varsInvolved.get(i)); 
			}
		}
		return returnList; 	
	}
	



	public int getNumber() {
		return number;
	}

	public void setNumber(int number) {
		this.number = number;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}	
	
	public ArrayList<Variable> getVarsInvolved() {
		return varsInvolved;
	}
	

}
