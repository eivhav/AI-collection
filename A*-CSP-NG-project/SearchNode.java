package project1;

import java.util.ArrayList;

public class SearchNode {

	
	private State state; 
	private double H; 
	private double G; 
	private double movementCost; 
	private String status; 
	private SearchNode parent; 
	private ArrayList<SearchNode> children = new  ArrayList<SearchNode>(); 
	private ArrayList<SearchNode> allNodes;   
	
	
	public SearchNode(ArrayList<SearchNode> allNodes, State state){ 
		this.allNodes = allNodes; 
		allNodes.add(this); 
		this.state = state; 
		this.H = state.calcHeuristic();  
		this.movementCost = state.getMovementCost(); 
	}
	
	
	
	public void generateChildren(){
		ArrayList<State> childStates = state.getNeighbours(); 
		for(int i = 0; i < childStates.size(); i++){
			SearchNode childNode = new SearchNode(allNodes, childStates.get(i));  
			children.add(childNode); 
		}	
	}
	
	
	
	public SearchNode stateAllreadyDisc(State state){
		for(int i = 0; i < allNodes.size(); i++){
			if(allNodes.get(i).getState().getStateID() == state.getStateID()){
				return allNodes.get(i); 
			}
		}
		return null; 
	}
	
	
	public double getF(){
		return H + G; 
	}

	
	public void setStatus(String status) {
		this.status = status; 
		if(status.equals("OPEN")){
			state.setOpen(); 
			state.setnotClosed(); 
		}
		else if(status.equals("CLOSED")){
			state.setClosed(); 
			state.setNotOpen(); 
		}
	}
	
	
	
	
	
	public State getState() {
		return state;
	}

	public void setState(State state) {
		this.state = state;
	}

	public double getH() {
		return H;
	}

	public void setH(double H) {
		this.H = H;
	}

	public double getG() {
		return G;
	}

	public void setG(double G) {
		this.G = G;
	}

	public String getStatus() {
		return status;
	}
	
	public void setInpath(boolean b){
		state.setInPath(b);
	}
	
	public void setInfinalPath(){
		state.setInFinalPath();
	}

	public SearchNode getParent() {
		return parent;
	}

	public void setParent(SearchNode parent) {
		this.parent = parent;		 
	}

	public ArrayList<SearchNode> getChildren() {
		return children;
	}

	public void setChildren(ArrayList<SearchNode> children) {
		this.children = children;
	}

	public boolean isGoalState() {
		return state.isGoalState(); 
	}

	public double getMovementCost() {
		return movementCost;
	}

	public void setMovementCost(double movementCost) {
		this.movementCost = movementCost;
	}
	
 
}
