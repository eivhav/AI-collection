package project1;

import java.util.ArrayList;



public class NGConstraint extends Constraint {
	
	
	private int number;  
	private String description = "NGram1Compare";  
	private ArrayList<Variable> varsInvolved; 
	private int rowNo; 
	private int colNo; 
	private int[] dim; 
	
	
	public NGConstraint(int number, int[] dim, int rowNo, int colNo, ArrayList<Variable> allVars){
		this.number = number; 
		this.rowNo = rowNo; 
		this.colNo = colNo; 
		this.dim = dim; 
		this.varsInvolved = new ArrayList<Variable>(); 
		varsInvolved.add(allVars.get(rowNo));  
		varsInvolved.add(allVars.get(dim[1] + colNo)); 
		
		for(int i = 0; i < varsInvolved.size(); i++){
			varsInvolved.get(i).getConstraints().add(this); 
		} 
	}
	
	
	
	public boolean testConstraint(){
		
		int rowVarValue = varsInvolved.get(0).getTestValue(); 
		int colVarValue = varsInvolved.get(1).getTestValue(); 
		
		String rowString = getStringValue(rowVarValue, dim[0]); 
		String colString = getStringValue(colVarValue, dim[1]);
		
		if(rowString.charAt(colNo) == colString.charAt(rowNo)){ // shuld be the other way arround
			return true; 
		}
		else{
			return false; 
		}
	}
	
	
	public String getStringValue(int number, int dimSize){
		String s = Integer.toBinaryString(number); 
		while(s.length() < dimSize){
			String p = "0";
			s = p + s; 
		}
		return s; 	
	}

	
	
	public char getFinalNGChar(int varNo){
		int rowVarValue = varsInvolved.get(varNo).getTestValue(); 
		String rowString = getStringValue(rowVarValue, dim[0]);
		char ret = rowString.charAt(colNo); 
		return ret; 
		
	}
	
	
	
	public String getTestString(String s, int pos){
		
		String part1 ="";  
		if(pos > 0){
			part1 = s.substring(0, pos);  
		}
		String part2=""; 
		if(pos < s.length()-1){
			part2 = s.substring(pos+1); 
		}
		
		String r = part1 + '[' +s.charAt(pos) +']' + part2;  
		
		return r; 
	}
	
	
	
	
	
	
	
	public void printConstr(int stateID){
		System.out.print("Constr:" + number); 
			Variable varRow = varsInvolved.get(0); 
			Variable varCol = varsInvolved.get(1); 
			
			String sRow = getStringValue(varRow.getDomain(stateID).get(0), dim[0]) ; 
			String sCol = getStringValue(varCol.getDomain(stateID).get(0), dim[1]) ;		
			
			System.out.print(" Var"+ varRow.getName() +":{"+sRow.charAt(rowNo) + "} " +":{"+ varRow.getDomain(stateID).get(0) + "}");
			System.out.print(" Var"+ varCol.getName() +":{"+sCol.charAt(colNo) + "} " +":{"+ varCol.getDomain(stateID).get(0) + "}");
			System.out.print(" rowNo " + rowNo + " colNo: " + colNo);
		
		System.out.println(); 
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
	


	
	
	

}

