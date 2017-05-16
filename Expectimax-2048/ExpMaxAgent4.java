package project2;

import java.util.Arrays;
import java.util.Random;

public class ExpMaxAgent4 {
	
	 
	GameLogic logic = new GameLogic(); 
	Heuristic heur = new Heuristic(); 
	int[] startState; 
	boolean print = false; 
	int[] randomNumbers = new int[16]; 
	int bestMove = 0; 
	
	
	public int getNextMove(int[] state){
		generateRndNumbers(); 
		this.startState = Arrays.copyOf(state, 17); 
		expRecrusion(startState, 0, getDepthLimit(getEmptyCount(startState)), true); 
		
		return bestMove; 
	}
	
	
	// expState[] = {0,1,2,3,4,5,6,7,8,9,10,11,12,13,14,15,   utility, emptySpaces } 

	
	public double expRecrusion(int[] state, int depth, int depthLimit, boolean first){
		
		double bestUtility = 0; 
		for(int move = 0; move < 4; move++){
			int[] expState = logic.manipulateState(state, move+1, 18);
			double utility = 0;
			if(expState != null){
				
				for(int k = 0; k < 16; k++){
					 
					if(expState[k] == 0){
						int[] maxState = Arrays.copyOf(expState,18); 
						
						boolean isTwo = true; 
						if(randomNumbers[k] != 0){ 
							maxState[k] = 1; 
						}
						else{
							maxState[k] = 2; 
							isTwo = false; 
						}	
						maxState[17] = maxState[17] -1; 
						
						if(depth == depthLimit){
							maxState[16] = (int) heur.calcHeuristics(maxState); 	
						}
						else{
							maxState[16] = (int) expRecrusion(maxState, depth+1, depthLimit, false); 
						}
						
						double chance = 0; 
						if(isTwo){
							chance = 0.9; 
						}
						else{
							chance = 0.1; 
						}
						
						double maxValue = (double) maxState[16] * chance * ( 1 / (double) expState[17]); 
						utility = utility + maxValue; 
					}
				}
				
				expState[16] = (int) utility; 
				if(utility > bestUtility){
					bestUtility = utility; 
					if(first){
						bestMove = move +1; 
					}
				}
			}
		}	
		
		return bestUtility; 
	}
	
			
	public void generateRndNumbers(){
		for(int i = 0; i < 16; i++){
			Random rnd = new Random(); 
			int n = rnd.nextInt(10); 
			randomNumbers[i] = n; 
		}
		
	}
	
	public int getDepthLimit(int emptyCount){
		int depthLimit;  
		if(emptyCount >= 9){
			depthLimit = 2; 
		}
		else if(emptyCount >= 5){
			depthLimit = 3;  
		}
		else if(emptyCount >= 3){
			depthLimit = 4; 
		}
		else{
			depthLimit = 5; 
		}
		return depthLimit; 	
	}
	
	
	
	

	public int getEmptyCount(int[] state){
		int count = 0; 
		for(int i = 0; i < state.length; i++){
			if(state[i] == 0){
				count++;   
			}
		}
		return count; 
	}
	

}
