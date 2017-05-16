package project2;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;

public class GameLogic {

	
	
	public int[] getNextRandomstate(int[] state){
		ArrayList<Integer> emptys = new ArrayList<Integer>();  
	
		for(int i = 0; i < 16; i++){
			if(state[i] == 0){
				emptys.add(i);  
			}
		}
		
		Random rnd1 = new Random();
		int pos = rnd1.nextInt(emptys.size()); 
		Random rnd2 = new Random();
		int chance = rnd2.nextInt(10); 
		int nextTileValue = 1;  
		if(chance == 9){
			nextTileValue = 2; 
		}
			
		state[emptys.get(pos)] = nextTileValue; 
		return state; 
	}
	
	

	
	
	
	
	public int[] manipulateState(int[] state, int move, int length){
		boolean stateChanged = false; 
		int[] returnState = Arrays.copyOf(state, length);  
		if(move == 1){
			for(int i = 0; i < 4; i++){
				int[] row = new int[4];
				for(int j = 0; j < 4; j++){
					row[j] = state[(i*4) + j]; 
				}
				int[] postRow = getRowMerge(row);  
				if(postRow != null){
					for(int j = 0; j < 4; j++){ 
						returnState[(i*4) + j] = postRow[j]; 
					}
					stateChanged = true; 
				}
			}
		}
		
		else if(move == 2){
			for(int i = 0; i < 4; i++){
				int[] row = new int[4];
				for(int j = 0; j < 4; j++){
					row[j] = state[i + (j*4)]; 
				}
				int[] postRow = getRowMerge(row);  
				if(postRow != null){
					for(int j = 0; j < 4; j++){ 
						returnState[i + (j*4)] = postRow[j]; 
					}
					stateChanged = true; 
				}
			}
		}
			
		else if(move == 3){
			for(int i = 0; i < 4; i++){
				int[] row = new int[4];
				for(int j = 3; j >= 0; j--){
					row[3-j] = state[(i*4) + j]; 
				}
				int[] postRow = getRowMerge(row);  
				if(postRow != null){
					for(int j = 3; j >= 0; j--){
						returnState[(i*4) + j] = postRow[3-j]; 
					}
					stateChanged = true; 
				}
			}
		}
		
		else if(move == 4){
			for(int i = 0; i < 4; i++){
				int[] row = new int[4];
				for(int j = 3; j >= 0; j--){
					row[3-j] = state[i + (j*4)]; 
				}
				int[] postRow = getRowMerge(row);  
				if(postRow != null){
					for(int j = 3; j >= 0; j--){
						returnState[i + (j*4)] = postRow[3-j]; 
					}
					stateChanged = true; 
				}
			}
		}	
		
		
		
		if(!stateChanged){
			return null; 
		}
		else{
			if(length > 16){
				returnState[length-1] = getEmptyCount(returnState); 
			}
			return returnState;  
		}	
	}
	
	
	
	
	
	public static int[] getRowMerge(int[] row){
		
		boolean movement = false; 
		int[] seq = new int[4]; 
		int pos = 3; 
		for(int i = 3; i >= 0; i--){
			if(row[i] != 0){
				seq[pos] = row[i];  
				pos--; 
			}
		}
		for(int i = 0; i < 4; i++){
			if(row[i] != seq[i]){ 
				movement = true; 
				break; 
			}
		}
		
		int[] preRow = new int[4]; 
		boolean merge = false;    
		if(seq[2] == seq[3] && seq[3] != 0){
			preRow[3] = seq[2] +1;
			if(seq[0] == seq[1] && seq[1] != 0){
				preRow[2] = seq[0] +1;
			}
			else{
				preRow[2] = seq[1]; 
				preRow[1] = seq[0]; 
			}
			merge = true; 
		}
		else if(seq[1] == seq[2] && seq[2] != 0){
			preRow[2] = seq[1] +1;
			preRow[1] = seq[0]; 
			preRow[3] = seq[3];
			merge = true; 
		}
		else if(seq[0] == seq[1] && seq[1] != 0){
			preRow[1] = seq[0] +1;
			preRow[2] = seq[2];
			preRow[3] = seq[3];
			merge = true; 
		}
		else{
			preRow = seq; 
		}
		
		
		if(merge || movement){
			return preRow; 
		}
		else{
			return null; 
		}
	}
	
	
	
	public int getEmptyCount(int[] state){
		int count = 0; 
		for(int i = 0; i < 16; i++){
			if(state[i] == 0){
				count++;   
			}
		}
		return count; 
		
		
	}
	
	
		
		
	
	
	
	
	
}
