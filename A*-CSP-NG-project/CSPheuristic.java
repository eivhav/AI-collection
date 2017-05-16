package project1;

import java.util.ArrayList;

/*
 * The CSPheursitc class. Has the two method that CSPheurstic object must contain.
 * If the heuristic needs to be defined in a different fashion, the designer can extend this class.   
 * 
 */


public class CSPheuristic {
	
	
	public Variable getOptimalVariable(CSPstate state){		
		ArrayList<Variable> CNETvariables = state.getVariables();  
		Variable optimalVar = null;   
		for(int k = 0; k < CNETvariables.size(); k++){
			if(CNETvariables.get(k).getDomain(state.getStateID()).size() >= 2){
				optimalVar = CNETvariables.get(k);  
				break; 
			}
		}    
		if(optimalVar != null){
			for(int i = 0; i < CNETvariables.size(); i++){															
				int currentSize = CNETvariables.get(i).getDomain(state.getStateID()).size(); 
				if(currentSize < optimalVar.getDomain(state.getStateID()).size() && currentSize > 1){
					optimalVar = CNETvariables.get(i);  
				}
			}
		}
		return optimalVar; 
	}
	
	
	
	public int getStateEvaluation(CSPstate state){
		ArrayList<Variable> CNETvariables = state.getVariables();  
		int stateID = state.getStateID(); 
		int returnValue = 0; 
		
		for(int i = 0; i< CNETvariables.size(); i++ ){
			int domainSize = CNETvariables.get(i).getDomain(stateID).size(); 
			if(domainSize == 0){
				returnValue = -1; 
				System.out.println("      Var:"+ CNETvariables.get(i).getName() + " reduced to null. State DeadEnd");
				break; 
			}
			else if(domainSize > 1){
				returnValue = returnValue + domainSize - 1; 
			}	
		}
		return returnValue; 
		
	}

}
