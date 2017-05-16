package project1;

import java.util.ArrayList;

/*
 * Holds the varaible information. 
 * 
 * 
 */

public class Variable {
	
	private String name; 
	private int finalValue; 
	private ArrayList<Integer> orgDomain = new ArrayList<Integer>(); 
	private ArrayList<ArrayList<Integer>> domains = new ArrayList<ArrayList<Integer>>();
	private ArrayList<Constraint> constraints = new ArrayList<Constraint>(); 
	private ArrayList<Integer> guessedAtStateID = new ArrayList<Integer>();  
	private int testValue; 
	
	
	public Variable(String name, ArrayList<Integer> orgDomain){
		this.name = name; 
		this.orgDomain = orgDomain; 
		
	}
	
	
	
	public ArrayList<Integer> copyDomain(ArrayList<Integer> domain){
		ArrayList<Integer> returnDomain = new ArrayList<Integer>(); 
		for(int i = 0; i < domain.size(); i++){
			int value = domain.get(i); 
			returnDomain.add(value); 
		}
		return returnDomain; 
	}
	
	
	
	public void setDomain(ArrayList<Integer> domain, int stateID) {
		if(stateID >= this.domains.size()){
			this.domains.add(copyDomain(domain));
		}
		else{
			this.domains.set(stateID, copyDomain(domain)); 
		}
		
	}
	
	
	
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public int getFinalValue() {
		return finalValue;
	}
	public void setFinalValue(int finalValue) {
		this.finalValue = finalValue;
	}
	public ArrayList<Integer> getOrgDomain() {
		return orgDomain;
	}
	public void setOrgDomain(ArrayList<Integer> orgDomain) {
		this.orgDomain = orgDomain;
	}
	public ArrayList<Integer> getDomain(int stateID) {
		return domains.get(stateID);
	}
	
	public ArrayList<ArrayList<Integer>> getDomains(){
		return domains; 
	}
	
	public ArrayList<Constraint> getConstraints() {
		return constraints;
	}
	
	public void setConstraints(ArrayList<Constraint> constraints) {
		this.constraints = constraints;
	}

	public int getTestValue() {
		return testValue;
	}

	public void setTestValue(int testValue) {
		this.testValue = testValue;
	}

	public ArrayList<Integer> getGuessedAtStatedID() {
		return guessedAtStateID;
	}

	public void setGuessedAtStatedID(ArrayList<Integer> guessedAtStatedID) {
		this.guessedAtStateID = guessedAtStatedID;
	}

	public void addGuess(int stateID) {
		this.guessedAtStateID.add(stateID); 
	}
	
	public boolean hasBeenGueesed(int stateID){
		boolean ret = false; 
		for(int i = 0; i < guessedAtStateID.size(); i++){
			if(guessedAtStateID.get(i) == stateID){
				ret = true; 
				break; 
			}
		}
		return ret; 
	}
	
	



		
	
	

}
