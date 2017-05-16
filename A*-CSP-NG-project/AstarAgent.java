package project1;

import java.util.ArrayList;

import javax.swing.JFrame;


/*
 * The AstarAgent search class. Used by the different problem classes during run.  
 * Returns a state when invoking method run. 
 * 
 */
public class AstarAgent {
	

	private State startState;   
	public ArrayList<SearchNode> OPEN = new ArrayList<SearchNode>(); 
	public ArrayList<SearchNode>  CLOSED = new ArrayList<SearchNode>(); 
	private int simSpeed; 
	JFrame frame; 
	private ArrayList<SearchNode> allNodes = new  ArrayList<SearchNode>();
	private String searchMethod; 
	private State finalState; 
	private int solutionPathLength = 0; 
	private boolean showSolutionPath = false; 
	private Toolbox utils = new Toolbox(); 
	
	
	
	public AstarAgent(JFrame frame, State startstate){
		this.frame = frame; 
		this.startState = startstate; 
	}
	
	
	
	// starts the search by the selected method
	public void runAgent(String type){
		this.searchMethod = type; 
		setFinalState(Search()); 
	}
	
	
	
	// Finds the goal state and the shortest path by intelligent search.   
	// Can also be set to search by DFS, BFS and Dijkstra 
	// Returns the goal state
	public State Search(){
		
		SearchNode startNode = new SearchNode(allNodes, startState); 
		startNode.setG(0); 
		startNode.setStatus("OPEN"); 
		OPEN.add(startNode); 
		SearchNode current = startNode;
		
		while(OPEN.size() > 0 ){ 		
			
			current = OPENpop(searchMethod); 
			current.getState().setLookingAt(true); 
			setSolutionPath(current, false);
			System.out.println("Moving to state: "+current.getState().getStateID()+"   H:"+current.getH() +" G:" + current.getG()+" F:" + current.getF()); 
			CLOSED.add(current); 
			
			
			if(current.isGoalState()){
				System.out.println("GoalState found!. Search is complete..."); 
				utils.printSpaceAndLine(); 
				setSolutionPath(current, true);
				printStats(); 
				return current.getState();  	
			}
			else{
				
				current.generateChildren(); 
				ArrayList<SearchNode> children = current.getChildren();  
				for(int i =0; i < children.size(); i++){
					
					SearchNode child = children.get(i); 
					if(child.stateAllreadyDisc(child.getState()) != null){
						child = child.stateAllreadyDisc(child.getState()); 
					}
					
					if(!isInList(OPEN, child) && !isInList(CLOSED, child)){
						attachaAndeEval(child, current);  
						OPEN.add(child); 
						child.setStatus("OPEN"); 
						
					}
					else if(current.getG() + child.getMovementCost() < child.getG() && searchMethod.equals("Astar")){
						attachaAndeEval(child, current); 
						if(isInList(CLOSED, child)){
							propagatePathImprovemnts(child); 
						}	
					}
				}		
			}
			
			if(frame != null){ 
				frame.repaint(); 
				waitForDelay();
				current.setStatus("CLOSED");
				removeSolutionPath(current); 
				current.getState().setLookingAt(false);
			}
			
			utils.printSpaceAndLine(); 
		}
		
		return null; 
	}
	
	

	
	
	// Decides and invokes preference pop from OPEN list  
	private SearchNode OPENpop(String type){
		SearchNode current = null; 
		if(type.equals("Astar")){
			current = bestPOP(OPEN); //sorted pick
		}
		else if(type.equals("BFS")){
			current = OPEN.remove(0);
		}
		else if(type.equals("DFS")){
			current = OPEN.remove(OPEN.size()-1); 
		}
		else if(type.equals("Djikstra")){
			current = bestDjikstraPOP(OPEN);
		}
		return current; 
	}
	
	
	
	// Goes thru the specified list and pops the best node by:   
	// lowest f-value --> lowest h-value --> last added 
	private SearchNode bestPOP(ArrayList<SearchNode> list){
		SearchNode bestNode = list.get(list.size()-1);  
		ArrayList<Integer> bestPos = new ArrayList<Integer>();  
		bestPos.add(list.size()-1); 
		for(int i = list.size()-2; i >= 0; i--){	
			if(list.get(i).getF() < bestNode.getF()){
				bestNode = list.get(i); 
				bestPos = new ArrayList<Integer>(); 	
			}
			if(list.get(i).getF() == bestNode.getF()){
				bestPos.add(i); 
			}
		}
		SearchNode bestHNode = list.get(bestPos.get(0)); 
		for(int i = 1; i < bestPos.size(); i++){
			if(list.get(bestPos.get(i)).getH() < bestHNode.getH()){
				bestHNode = list.get(bestPos.get(i)); 
			}
		}
		list.remove(bestHNode); 
		return bestHNode; 
			
	}
		
	
	// pops the node form OPEN by g-value. 	
	private SearchNode bestDjikstraPOP(ArrayList<SearchNode> list) {
		SearchNode bestNode = list.get(list.size()-1);   
		for(int i = list.size()-2; i >= 0; i--){	
			if(list.get(i).getG() < bestNode.getG()){
				bestNode = list.get(i); 
			}
		}			
		list.remove(bestNode); 
		return bestNode; 
	}

		
	
	// Checks if a node is in a specific list 
	private boolean isInList(ArrayList<SearchNode> list, SearchNode node){ 
		for(int i = 0; i < list.size(); i++){
			if(list.get(i) == node){
				return true; 
			}
		}
		return false; 
	}
	
	
		
		
	// Evaluates and sets parent to node
	public void attachaAndeEval(SearchNode child, SearchNode parent){
		child.setParent(parent); 
		child.setG(parent.getG() + child.getMovementCost()); 
	
	}
	
	
	
	// Propagates the newly discovered g-value to all children where parents has been given a better g-value 
	private void propagatePathImprovemnts(SearchNode node){
		for(int i = 0; i < node.getChildren().size(); i++){
			SearchNode child = node.getChildren().get(i); 
			if((node.getG() + child.getMovementCost()) < child.getG()){
				child.setParent(node); 
				child.setG(node.getG() + child.getMovementCost()); 
				propagatePathImprovemnts(child); 		
			}		
		}
	}
	
	
	
	
	// Sets the inPath field for all nodes from this node to start 
	private void setSolutionPath(SearchNode endNode, boolean finalPath){
		if((showSolutionPath && frame != null) || finalPath){
			SearchNode node = endNode; 
			int pathLength = 0; 
			while(node.getState() != startState && node != null){
				if(finalPath){
					node.setInfinalPath(); 
				}
				else {
					node.setInpath(true);
				}
				node = node.getParent(); 
				pathLength++; 
			}
			setSolutionPathLength(pathLength); 
		}
	}
	
	


	// Removes the inPath field for all nodes from this node to start
	private void removeSolutionPath(SearchNode endNode){
		if(showSolutionPath){
			SearchNode node = endNode;
			while(node.getState() != startState && node != null){
				node.setInpath(false); 
				node = node.getParent(); 
			}
		}
	}
	


	// print stats for search. 
	public void printStats(){

		System.out.println("Search Agent Statistics: "+searchMethod);  
		System.out.println(); 
		System.out.println("Total nodes generated       :"+ (OPEN.size() + CLOSED.size())); 
		System.out.println("   nodes popped from Agenda :"+ CLOSED.size());
		System.out.println("   nodes remaining in OPEN  :"+ OPEN.size()); 
		System.out.println(); 
		System.out.println("Solution Path length        :"+solutionPathLength);  
		System.out.println();
		System.out.println("--------------------------------------------------");
		
	}
	
	
	
	// holds the system for a given time. 
	public void waitForDelay(){
		long timeNow = System.currentTimeMillis();  	
    	while(System.currentTimeMillis() < timeNow +simSpeed){
    	}
    }
	
	

	
	// Ordinary setters and getters
	
	public int getSimSpeed() {
		return simSpeed;
	}

	public void setSimSpeed(int simSpeed) {
		this.simSpeed = simSpeed;
	}
	
	
	public State getFinalState() {
		return finalState;
	}

	public void setFinalState(State finalState) {
		this.finalState = finalState;
	}

	public int getSolutionPathLength() {
		return solutionPathLength;
	}

	public void setSolutionPathLength(int solutionPathLength) {
		this.solutionPathLength = solutionPathLength;
	}

	public void setShowSolutionPath(boolean b) {
		this.showSolutionPath = b;
	}

	

}
