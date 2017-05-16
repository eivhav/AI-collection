package project1;

import java.util.ArrayList;


/*
 * Superclass state. Works as an interface, and if any of the methods are reached,
 * an error occurs and it then needs to be fixed. 
 */

public class State {
	
	private double stateID;
	
	public boolean isGoalState() {
		System.out.println("Superclass state reached1"); 
		return false;
	}
	
	public int getStateID() {
		System.out.println("Superclass state reached3"); 
		return (int) stateID; 
	}
	
	public ArrayList<State> getNeighbours(){
		System.out.println("Superclass state reached4"); 
		return new ArrayList<State>(); 
	}
	
	public double calcHeuristic(){
		System.out.println("Superclass state reached5"); 
		return 0; 
	}

	public int getMovementCost() {
		System.out.println("Superclass state reached6"); 
		return 0;
	}

	public void setParent(State state) {
		System.out.println("Superclass state reached7"); 
	}

	public void setOpen() {
		System.out.println("Superclass state reached8"); 
	}
	
	public void setnotClosed() {
		System.out.println("Superclass state reached9"); 
	}

	public void setClosed() {
		System.out.println("Superclass state reached10"); 
	}
	
	public void setNotOpen(){
		System.out.println("Superclass state reached11"); 
	}

	public void setInPath(boolean b) {
		//System.out.println("Superclass state reached12"); 
	}
	
	public void setLookingAt(boolean v){
		System.out.println("Superclass state reached13"); 
	}

	public void setInFinalPath() {
		//System.out.println("Superclass state reached14"); 		
	}
	
}
	
	
	