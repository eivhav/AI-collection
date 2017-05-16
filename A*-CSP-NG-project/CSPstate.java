package project1;

import java.util.ArrayList;

public class CSPstate extends State{
	
	int stateID; 
	private ArrayList<Variable> CNETvariables;  
	private ArrayList<Constraint> CNETconstraints; 	
	private CSPstate parent; 
	private ArrayList<CVpair> reviseQueue = new ArrayList<CVpair>(); 
	private boolean constrSatisfied = false; 
	private boolean isGoalState = false; 
	private boolean isDeadEnd = false; 
	private Variable guessedVar; 
	private GraphicCSP graphics; 
	private boolean printDFL = false; 
	public CSPheuristic cspHeuristic; 
	
	
	
	// Initiate a new CSPstate. The stateID is set to the next free index in the domain list for one of the Variables. 
	// If the parent is null, copy orgDomain.
	// if the parent is not null, copy parent domain 
	public CSPstate(CSPstate parent, ArrayList<Variable> variables, ArrayList<Constraint> constraints, CSPheuristic cspHeuristic, GraphicCSP graphics){   
		this.CNETvariables = variables; 														
		this.CNETconstraints= constraints; 
		this.stateID = variables.get(0).getDomains().size(); 
		this.graphics = graphics; 
		this.parent = parent; 
		this.cspHeuristic = cspHeuristic; 
		System.out.println("    Creating CSPstate:" +  stateID); 
																														// get the state-id. Equals the size of the domains list. 
		if(parent != null){
			setStartDomains(parent.getStateID(), stateID); 																// if not the first state, set the domain as a copy of its parent.
			System.out.println("      parent: "+ parent.getStateID());
		}
		else{ 																											// Init the start state 
			for(int i = 0; i < variables.size(); i++){																	// for each variable
				Variable currentVar = variables.get(i); 																// this variable
				currentVar.getDomains().add(currentVar.copyDomain(currentVar.getOrgDomain())); 							// Set the first domain as a copy of org. domain 
			}
			System.out.println("      parent: "+ null) ;
		}			
	}
	
	
	
		
	
	// create and return neighbors. One for each value in the optimal variable. 
	public ArrayList<State> getNeighbours(){																			// Creates and returns neighbors for the optimal variable. 
		ArrayList<State> neigh = new ArrayList<State>(); 
		Variable optimalVar = cspHeuristic.getOptimalVariable(this); 
		
		if(optimalVar == null || isDeadEnd){
			return neigh; 
		}
		
		else{
			System.out.println("  Generating Neighbors for State:" + stateID);		   
			for(int i = 0; i < optimalVar.getDomain(stateID).size(); i++){												// for each value of optimal variable
				CSPstate newState = new CSPstate(this, CNETvariables, CNETconstraints, cspHeuristic, graphics); 						// create new neigh-state
				ArrayList<Integer> newDomain = new ArrayList<Integer>();  												// create a new domain.
				int value = optimalVar.getDomain(newState.getStateID()).get(i);  
				newDomain.add(value);   																				// add the i'th value of the optimal variable to the new domain
				optimalVar.setDomain(newDomain, newState.getStateID()); 												// Replace the full domain of the new state with the single(guessed) value. 			 
				newState.setGuessedVar(optimalVar); 
				optimalVar.addGuess(newState.getStateID()); 
				if(newState.getGuessedVar() != null){
					System.out.println("      assumption: "+ newState.getGuessedVar().getName() +":" + newState.getGuessedVar().getDomain(newState.getStateID()));
				}
				neigh.add(newState); 																					// Add to list of neighbors. 
			}
			System.out.println(); 
			return neigh; 
		}		
	}
	
	
	
	
	
	// Evaluate state. Run init/rerun and domain filtering loop. Return eval value based on CSPHeursitc function.  
	// if deadEnd or goal, set these flags. 
	public int evaluateState(){  
		System.out.println("    Evaluating state:"+ stateID) ;
		if(parent == null){
			GACInit(); 
			GACDomainFilteringLoop(); 
		}
		else{
			GACRerun(guessedVar); 
			System.out.println("      Running GAC-rerun");
		}
		
		int eval = cspHeuristic.getStateEvaluation(this);  
		if(eval < 0){
			isDeadEnd = true; 
			return 0; 
		}
		else{
			System.out.println("      Eval:"+ eval);
			if((eval == 0)){
				isGoalState = true; 
				System.out.println("      Is Goal state");
			}
		}
		return eval; 	
	}
	
	
	
	/*
	The queue is loaded up with REVISE* requests, one request for each pair of variable and constraint, where
	the variable actually appears in the constraint.
	*/
	public void GACInit(){
		
		for(int i = 0; i < CNETvariables.size(); i++){
			Variable var = CNETvariables.get(i); 
			if(var.getDomain(stateID).size() > 1){
				for(int j = 0; j < var.getConstraints().size(); j++){
					Constraint constr = var.getConstraints().get(j); 
					reviseQueue.add(new CVpair(constr, var)); 
				}
			}
		}
		System.out.println("      GacInit complete. Size of Queue: " + reviseQueue.size() + ". NoOf constr: " +CNETconstraints.size()); 
	}
	
	
	
	
	
	// Pop elements from the reviseQueue, Run revise.
	// If domain is reduced, add more CV pairs for all constraints where x participates, but not CV pais that include x.   
	public void GACDomainFilteringLoop(){
		
		while(reviseQueue.size() > 0){
			if(printDFL){
			System.out.println("Running DomainFilteringLoop - Queuesize: " + reviseQueue.size());
			}
			CVpair todoPair = reviseQueue.remove(0); 
			ArrayList<Integer> domain = todoPair.getVariable().getDomain(stateID); 
			int preSize = domain.size(); 
			REVISE(todoPair.getVariable(), todoPair.getConstraint()); 
			int postSize = domain.size(); 
			
			if(postSize < preSize){
				ArrayList<Constraint> constrWithX = todoPair.getVariable().getConstraints(); 
				
				for(int i = 0; i < constrWithX.size(); i++){
					if(constrWithX.get(i) !=  todoPair.getConstraint()){
						
						for(int j = 0; j < constrWithX.get(i).getVarsInvolved().size(); j++){
							Variable varInvolved = constrWithX.get(i).getVarsInvolved().get(j); 
							if(varInvolved != todoPair.getVariable()){
								reviseQueue.add(new CVpair(constrWithX.get(i), varInvolved)); 
							}
						}
		
					}
				}	
			}
		
		}	
		 
	}
	
	
	/* Runs during the search. We want to add all constraint-variable pairs for all constraints that include X.  
	 * Afterwards filtering is done to no more domains are reduced. 
	 */
	public void GACRerun(Variable X){
		ArrayList<Constraint> constrWithX = X.getConstraints(); 
		for(int i = 0; i < constrWithX.size(); i++){	
			for(int j = 0; j < constrWithX.get(i).getVarsInvolved().size(); j++){
				Variable varInvolved = constrWithX.get(i).getVarsInvolved().get(j); 
					if(varInvolved != X){
						reviseQueue.add(new CVpair(constrWithX.get(i), varInvolved)); 
					}
			}
		}
		GACDomainFilteringLoop(); 
	}
	
	
	
	
	
	
	// Tests all values for a focal variable up against a constraint.
	// This includes testing all combinations for the other variables in the constraint. 
	// If one combination is valid, the constraint is satisfied. 
	// If no combination is valid, the constrained has failed, remove value from focalVars domain. 
	public void REVISE(Variable focalVar, Constraint constr){
		ArrayList<Variable> varsInConstr = constr.getVarsInvovledWithoutFocalVar(focalVar);  
		
		for(int i = 0; i < focalVar.getDomain(stateID).size(); i++){
				
				int testValue = focalVar.getDomain(stateID).get(i); 
				focalVar.setTestValue(testValue);
				constrSatisfied = false; 
				if(varsInConstr.size() == 0){
					constrSatisfied = constr.testConstraint(); 
				}
				else{
					generateAndTestCombinations(varsInConstr, constr, 0);
				}
				if(!constrSatisfied){
					focalVar.getDomain(stateID).remove(i); 
					i--; 
				}	
		}
		
	}
		
	
	

	//Recursive function that finds all combinations for a variable list, and tests the constraint for these.  
	public void generateAndTestCombinations(ArrayList<Variable> varsInConstr, Constraint constr, int recCount){
		if(constrSatisfied){
	 		return;
	 	}
		for(int i = 0; i < varsInConstr.get(recCount).getDomain(stateID).size(); i++){
			varsInConstr.get(recCount).setTestValue(varsInConstr.get(recCount).getDomain(stateID).get(i)); 
			if(recCount == varsInConstr.size()-1){
			 	if(constr.testConstraint()){
			 		constrSatisfied = true; 
			 		return; 
			 	}	
			}	 
			else{ 
				recCount++; 
				generateAndTestCombinations(varsInConstr, constr, recCount); 
				recCount--; 
			}	
		}
	}
		
	
		

	
	
	
	// sets the domain for the first state. 
	public void setStartDomains(int parentStateID, int stateID){
		for(int i = 0; i < CNETvariables.size(); i++){																	//For each variable
			Variable var = CNETvariables.get(i); 
			var.setDomain(var.getDomain(parentStateID), stateID);  														//Set the domain as a copy of the parents domain. 																									//The stateID is set to be the size of the list, therefore for a new state, the domain is added to the end of the list.  
		}
	}
	
	
	
	
	
	
	
	//Super state interface for compatibility of the Astar-searchNode class. 
			
		public boolean isGoalState() {	
			return isGoalState; 
		}
		
		public double calcHeuristic(){
			 return evaluateState(); 
		}
	
		public int getMovementCost() {
			return 1;
		}
	
		public void setParent(State state) {
		}
	
		
		
		//these are used for graphics
		public void setOpen() {
		}
		
		public void setLookingAt(boolean lookingAt){		
			if(lookingAt && graphics != null && !isDeadEnd){
				graphics.setStateID(stateID); 	
				
			}
			lookingAt = true; 
		}
	
		public void setnotClosed() {	
		}
	
		public void setClosed() {
		}
		
		public void setNotOpen(){ 
		}
	
		public void setInPath() {
		}
	
	
	

	
		
		
	
		
		//Regular setters and getters

	public ArrayList<Variable> getVariables() {
		return CNETvariables;
	}


	public void setVariables(ArrayList<Variable> variables) {
		this.CNETvariables = variables;
	}


	public ArrayList<Constraint> getConstraints() {
		return CNETconstraints;
	}
	
	public void setConstraints(ArrayList<Constraint> constraints) {
		this.CNETconstraints = constraints;
	}

	public CSPstate getParent() {
		return parent;
	}

	public void setParent(CSPstate parent) {
		this.parent = parent;
	}

	public int getStateID() {
		return stateID;
	}

	public void setStateID(int stateID) {
		this.stateID = stateID;
	}
	
	
	public Variable getGuessedVar() {
		return guessedVar;
	}


	public void setGuessedVar(Variable guessedVar) {
		this.guessedVar = guessedVar;
	}

	public void setPrintDFL(boolean b){
		printDFL = b; 
	}
	
}
