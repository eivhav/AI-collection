package project1;

import java.util.ArrayList;

/*
 * Class that contains constraint entered by the user. 
 * Extends the constraint class
 * 
 */

public class CmplxConstraint extends Constraint {
	
	
	private int number;  
	private String description; 
	private ArrayList<Variable> varsInvolved = new ArrayList<Variable>(); 
	private ArrayList<String> varsInvolvedByNames = new ArrayList<String>(); 
	private int comparPos = 0; 
	private String comparSign = ""; 
	Expression left; 
	Expression right; 
	
	
	
	// Iniate and build expression data structure
	public CmplxConstraint(int number, String description, ArrayList<Variable> allVars){
		
		this.setNumber(number);  
		this.setDescription(description); 
		ArrayList<String> varNames = readVars(description); 
		
		for(int i = 0; i < varNames.size(); i++){
			for(int j = 0; j < allVars.size(); j++){
				if(varNames.get(i).equalsIgnoreCase(allVars.get(j).getName())){
					varsInvolved.add(allVars.get(j)); 
					varsInvolvedByNames.add(allVars.get(j).getName()); 
					allVars.get(j).getConstraints().add(this); 	
				}
			}
		}
		
		String leftSideOfConstr = description.substring(0,comparPos); 
		String rightSideOfConstr = description.substring((comparPos+comparSign.length()), description.length()); 
		left = new ExpressionBuilder(leftSideOfConstr).variables(varsInvolvedByNames).build();
		right = new ExpressionBuilder(rightSideOfConstr).variables(varsInvolvedByNames).build();
	}
	
	
	// test the constraint
	public boolean testConstraint(){
		ArrayList<Integer> testValues = new ArrayList<Integer>(); 
		for(int i = 0; i < varsInvolved.size(); i++){
			testValues.add(varsInvolved.get(i).getTestValue()); 
		}
		
		left.setVariables(varsInvolvedByNames, testValues); 
		right.setVariables(varsInvolvedByNames, testValues); 
		boolean constraintSataisfied = false; 
		
		double leftEval = left.evaluate(); 
		double rightEval = right.evaluate(); 	
		
		if((leftEval == rightEval) && (comparSign.equals("=") || comparSign.equals("<=") || comparSign.equals(">="))){
			constraintSataisfied = true; 
		}
		else if((leftEval < rightEval) && (comparSign.equals("<") || comparSign.equals("<="))){
			constraintSataisfied = true;  	
		}
		else if((leftEval > rightEval) && (comparSign.equals(">") || comparSign.equals(">="))){
			constraintSataisfied = true; 
		}
		else if((leftEval != rightEval) && (comparSign.equals("!="))){
			constraintSataisfied = true; 
		}
		return constraintSataisfied; 
	}
	
	
		
	// read the input string and find the position of the comparator sign. 
	public ArrayList<String> readVars(String descr){
		
		String varName = "";  
		ArrayList<String> results = new ArrayList<String>(); 
		boolean wordFound = false; 
		for(int i = 0;  i < descr.length(); i++){
			char c = descr.charAt(i); 
			
			if(c == '!' || c == '=' || c == '<' || c== '>'){
				if(comparPos <1){
					comparPos = i; 
				}
				comparSign = comparSign + c; 
				if(wordFound){
					results.add(varName); 
					varName=""; 
					wordFound = false; 
				}
			}
			else if(!Character.isLetter(c) && !wordFound){ 
			}
			else if(Character.isLetter(c) && !wordFound){
				varName= varName + c; 
				wordFound = true; 
				
			}
			else if(Character.isLetterOrDigit(c) && wordFound){
				varName = varName + c; 
			}
			else if(!Character.isLetterOrDigit(c) && wordFound){  
				results.add(varName); 
				varName=""; 
				wordFound = false; 
			}	
			
			if((i == descr.length()-1) && wordFound){
				results.add(varName); 
			}
			
		}
		return results; 
	}
	
	
	
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
	
	public ArrayList<String> getVarsInvolvedByNames(){
		return varsInvolvedByNames; 		
	}

}
