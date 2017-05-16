package project1;


/* 
 * Class that inherits from he state superclass.
 * One of many types that can be run through the Astar system. 
 */
	
import java.util.ArrayList;
	
	public class Gridstate extends State {
		
		private int stateID;
		private boolean obstacle = false; 
		private boolean thisStartState = false; 
		private boolean thisGoalState = false; 
		private boolean inPath = false; 
		private boolean inFinalPath = false; 
		private boolean unseen = true; 
		private int xPos; 
		private int yPos; 
		private boolean open = false; 
		private boolean closed = false; 
		private int movementCost; 
		private boolean lookingAt = false; 
		String heurType = "manhatten"; 
		
		Gridstate north = null; 
		Gridstate east = null; 
		Gridstate south = null; 
		Gridstate west = null; 
		Gridstate bestParent; 
		Gridstate goalState; 

		
		public Gridstate(int xPos, int yPos, String heurType){
			this.xPos = xPos;  
			this.yPos = yPos;   
			setStateID( (xPos*100) + yPos); 
			this.movementCost = 1; 
			this.heurType = heurType; 
		}
		
		
		
		// return each neighbor in the grid, if not obstacle or wall. 
		public ArrayList<State> getNeighbours(){	
			
			ArrayList<State> neighbours = new ArrayList<State>(); 
			if(north != null && !north.isObstacle()){
				neighbours.add(north); }
			if(south != null && !south.isObstacle()){
				neighbours.add(south);} 
			if(east != null && !east.isObstacle()){
				neighbours.add(east); }
			if(west != null && !west.isObstacle()){
				neighbours.add(west);}
				
			return neighbours; 
	
		}
		
		
	
		// set the relations in between the cells.  
		public void assaignRelations(int xGridSize, int yGridSize, ArrayList<Gridstate> allNodes) {
			if(xPos != 0){
				east = allNodes.get(xPos -1 +(yPos * xGridSize)); }
			if(xPos != xGridSize -1){
				west = allNodes.get(xPos +1 +(yPos * xGridSize)); }
			if(yPos != 0){
				north = allNodes.get(xPos +((yPos-1) * (xGridSize))); }
			if(yPos != yGridSize -1){
				south = allNodes.get(xPos +((yPos+1) * (xGridSize))); }		
		}
		
		
		
		// returns the heuristic value for this state
		public double calcHeuristic(){
			if(heurType.equals("euc")){
				return getEucHeuristic((Gridstate) goalState); 
			}
			else {
				return getManhattenHeuristic((Gridstate) goalState);
			}
		}
		
	
		//The method calculates the heuristic value for each node based upon the Manhattan distance to the goal node. 
		public double getManhattenHeuristic(Gridstate goalNode){ 
			double D = 1;
			double dx = this.xPos - goalNode.getxPos(); 
			double dy = this.yPos - goalNode.getyPos(); 
			dx = Math.sqrt(dx * dx); 
			dy = Math.sqrt(dy * dy);
			return D * (dx + dy); 
		}
		
		
		//The method calculates the heuristic value for each node based upon the Euculidian distance to the goal node. 
		public double getEucHeuristic(Gridstate goalNode){ 
			double dx = this.xPos - goalNode.getxPos(); 
			double dy = this.yPos - goalNode.getyPos(); 
			return Math.sqrt((dx * dx) + (dy * dy)); 
		}
		
	
	
	
		//Regular getters and setters
		
	
		public int getyPos() {
			return yPos;
		}
	
		public int getxPos(){ 
			return xPos;
		}
	
		public boolean isObstacle() {
			return this.obstacle; 
		}
		
		public void setObstacle(){
			this.obstacle = true; 
		}
		
		public boolean isUnseen() {
			return unseen; 
		}
	
		public Gridstate getWest() {
			return west; 
		}
	
		public boolean isOpen() {
			return open; 
		}
	
		public boolean isClosed() {
			return closed; 
		}
	
		public boolean isInPath() {
			return inPath; 
		}
	
		public Gridstate getSouth() {
			return south; 
		}
	
		public void setOpen() {
			this.open = true; 	
		}
		
		public boolean isGoalState(){
			return thisGoalState; 
		}
		
		public void setIsGoalState(){
			thisGoalState = true; 
		}
		
		public void setStartState() {
			this.thisStartState = true; 
		}
		
		public boolean isStartState() {
			return this.thisStartState;
		}
	
		public int getMovementCost() {
			return movementCost;
		}
	
		public void setMovementCost(int movementCost) {
			this.movementCost = movementCost;
		}
		
		public void setnotClosed() {
			this.closed = false; 
		}
		
		public void setClosed() {
			this.closed = true; 
		}
		
		public void setNotOpen() {
			this.open = false; 
		}
		
		public void setInPath(boolean b) {
			this.inPath = b; 	
		}
	
		public int getStateID() {
			return stateID;
		}
	
		public void setStateID(int d) {
			this.stateID = d;
		}
	
		public boolean isLookingAt() {
			return lookingAt;
		}
	
		public void setLookingAt(boolean v){
			this.lookingAt = v; 	
		}
	
		public boolean isInFinalPath() {
			return inFinalPath;
		}
	
		public void setInFinalPath() {
			this.inFinalPath = true;
		}
		
		public Gridstate getGoalState() {
			return goalState;
		}

		public void setGoalState(Gridstate goalState) {
			this.goalState = goalState;
		}
		
		
	}
	
