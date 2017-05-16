package project1;

import java.util.ArrayList;


/*
 * The Constraint superclass. All constrains that are used in the CSP system extends this class.
 * The methods below specifies which methods each constraint subclass must/can implement. 
 */

public class Constraint {
	
	
	private int number;  
	private String description; 
	private ArrayList<Variable> varsInvolved; 
	private ArrayList<String> varsInvolvedByNames; 
	
	
	public boolean testConstraint(){
		System.out.println("Superclass constraint reached1"); 
		return false; 
	}
	

	public int getNumber() {
		System.out.println("Superclass constraint reached2"); 
		return number;
	}



	public void setNumber(int number) {
		System.out.println("Superclass constraint reached3"); 
		this.number = number;
	}



	public String getDescription() {
		System.out.println("Superclass constraint reached4"); 
		return description;
	}



	public void setDescription(String description) {
		System.out.println("Superclass constraint reached5"); 
		this.description = description;
	}



	public ArrayList<Variable> getVarsInvolved() {
		System.out.println("Superclass constraint reached6"); 
		return varsInvolved;
	}



	public void setVarsInvolved(ArrayList<Variable> varsInvolved) {
		System.out.println("Superclass constraint reached7"); 
		this.varsInvolved = varsInvolved;
	}



	public ArrayList<String> getVarsInvolvedByNames() {
		System.out.println("Superclass constraint reached8"); 
		return varsInvolvedByNames;
	}



	public void setVarsInvolvedByNames(ArrayList<String> varsInvolvedByNames) {
		System.out.println("Superclass constraint reached9"); 
		this.varsInvolvedByNames = varsInvolvedByNames;
	}	
	
	public ArrayList<Variable> getVarsInvovledWithoutFocalVar(Variable focalVar){
		ArrayList<Variable> returnList = new ArrayList<Variable>(); 
		System.out.println("Superclass constraint reached10"); 
		return returnList; 
		
	}


	public void printConstr(int stateID) {
		System.out.println("Superclass constraint reached11"); 
		
	}


	public boolean testConstraintwithPrint() {
		System.out.println("Superclass constraint reached12"); 
		return false;
	}
	
	public char getFinalNGChar(int varNo){
		System.out.println("Superclass constraint reached13"); 
		return ' '; 
		
	}
		
	

}
