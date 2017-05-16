package project2;

import java.util.ArrayList;
import java.util.Arrays;

public class Game2048Tester {
	
	static int simSpeed = 10; 
	static int[] boardStart = {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0};
	static GUI gui = GUI.getWindow();
	static GameLogic logic = new GameLogic(); 
	

	public static void runGameStats(){
		int totalCount =0; 
		for(int i = 0; i < 30; i++){
			ArrayList<Integer> stats = new ArrayList<Integer>(); 
		
			boolean n1024 = false; 
			for(int k = 0; k < 5; k++){
					int highestTile = 0; 
					int[] board = Arrays.copyOf(boardStart,16); 
					ExpMaxAgent4 newAgent = new ExpMaxAgent4();   
					boolean running = true; 
					board = logic.getNextRandomstate(board);
				    while(running){
				    	
				    	int moveVal = newAgent.getNextMove(Arrays.copyOf(board, 16));
						
						int[] nextBoard = logic.manipulateState(board, moveVal, 16); 
						if(nextBoard != null){
							board = nextBoard;
							board = logic.getNextRandomstate(board);
							
						}
						else 
							running = false; 
				    }
				    
				    for(int j = 0; j < 16; j++){
				    	
				    	if(board[j] > highestTile){
				    		highestTile = board[j]; 

				    	}
				    }
				    
				    if(highestTile >= 10){
				    	n1024 = true; 
				    }
				    stats.add((int)Math.pow(2,highestTile)); 
				    
				    //System.out.println((int)Math.pow(2,highestTile)); 
				    totalCount = totalCount + (int)Math.pow(2,highestTile); 
				}
			System.out.println(n1024); 

					
		}
		
		System.out.println(totalCount/200); 
	    
	    
	}

}
