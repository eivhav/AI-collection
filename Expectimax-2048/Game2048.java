package project2;

import java.util.Arrays;

public class Game2048 {
	
	
	
	static int simSpeed = 30; 
	static int[] boardStart = {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0};
	static GUI gui = GUI.getWindow();
	static GameLogic logic = new GameLogic(); 
	
	
	public static void main(String[] args){
		
	
			runGameWithGUI(); 
			
		
		
	
	}
	    
	
	
	public static void runGameWithGUI(){
		int[] board = Arrays.copyOf(boardStart,16); 
		int highestTile = 0; 
		ExpMaxAgent4 newAgent = new ExpMaxAgent4(); 		
		gui.drawBoard(board);
		
		boolean running = true; 
		board = logic.getNextRandomstate(board);
	    while(running){
	    	
	    	int moveVal = newAgent.getNextMove(Arrays.copyOf(board, 16));
			
			int[] nextBoard = logic.manipulateState(board, moveVal, 16); 
			if(nextBoard != null){
				board = nextBoard;
				gui.drawBoard(board);
				waitForDelay();
				board = logic.getNextRandomstate(board);
				gui.drawBoard(board);	
				waitForDelay();
			}
			else 
				running = false; 	
			
			for(int j = 0; j < 16; j++){
		    	if(board[j] > highestTile){
		    		highestTile = board[j]; 
		    	}
			}
	    }
	    
	    
	    
	    System.out.println("Score: "+ ((int) Math.pow(2,highestTile))); 
		
	}
	

			
		public static void waitForDelay(){
			long timeNow = System.currentTimeMillis();  	
	    	while(System.currentTimeMillis() < timeNow +simSpeed){
	    	}
	    }
		
		public static void waitForDelay2(int time){
			long timeNow = System.currentTimeMillis();  	
	    	while(System.currentTimeMillis() < timeNow +time){
	    	}
	    }
		
		
		
		

}
