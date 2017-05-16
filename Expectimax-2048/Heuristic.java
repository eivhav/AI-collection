package project2;

public class Heuristic {
	
	double w1 = 100000;  
	double w2 = 1;
	double w3 = 1;
	double w4 = 10;
	
	int[] startState; 
	
	
	public double calcHeuristics(int[] state){
		double eval = 0; 
		eval = eval + ( w1 * calcHeurstic1(state)); 
		eval = eval + ( w2 * calcHeurstic2(state));
		eval = eval + ( w3 * calcHeurstic3(state));
		eval = eval + ( w4 * calcHeurstic4(state));

		return eval; 
		
		
	}
	
	
	
	private double calcHeurstic1(int[] state) {
		double val = Math.pow(3,state[state.length-1]); 
		return val;  	
	}
	
	
	
	private double calcHeurstic2(int[] state) {
		int tileValue = 0; 
		for(int i = 0; i < 16; i++){
			tileValue = tileValue + (state[i] * state[i] * state[i]); 	
		}
		return tileValue; 
	}
	
	
	
	private double calcHeurstic3(int[] state) {
		int highPos = 0;  
		for(int i = 1; i < 16; i++){
			if(state[highPos] < state[i]){
				highPos = i; 
			}
		}
		if(highPos == 0 || highPos == 3 || highPos == 12 || highPos == 15){
			return 700; 
		}
		else 
			return 0; 
	}
	
	
	
	private double calcHeurstic4(int[] state){
		int rank = 0; 
		for(int i = 0; i < 4; i++){
			int[] row = new int[4];
			for(int j = 0; j < 4; j++){
				row[j] = state[(i*4) + j]; 
			}
			if(row[0] + row[1] >= row[2] + row[3]){
				rank = rank + getRowOrderRank(row); 
			}
			else{
				int[] reverseRow = new int[4]; 
				for(int j = 0; j < 4; j++){
					reverseRow[j] = row[3-j];  
				}
				rank = rank + getRowOrderRank(reverseRow);
			}	
		}
		
		for(int i = 0; i < 4; i++){
			int[] row = new int[4];
			for(int j = 0; j < 4; j++){
				row[j] = state[i + (j*4)]; 
			}
			if(row[0] + row[1] >= row[2] + row[3]){
				rank = rank + getRowOrderRank(row); 
			}
			else{
				int[] reverseRow = new int[4]; 
				for(int j = 0; j < 4; j++){
					reverseRow[j] = row[3-j];  
				}
				rank = rank + getRowOrderRank(reverseRow);
			}	
		}
		
		return rank; 
	}
	
	
	private int getRowOrderRank(int[] row){
		int rank = row[0]; 
		for(int i = 1; i < 4; i++){
			rank = rank + row[i];  
			if(row[i-1] > row[i]){
				rank = rank - (row[i-1] - row[i]-1); 
			}
			else if(row[i-1] < row[i]){
				rank = rank - (3*(row[i] - row[i-1])); 
			}
			else{
				
			}
		}
		return rank; 
	}

	
	public void printHeurValues(int[] state){
		System.out.println("    Spaces: " + calcHeurstic1(state) + "  w1:" + w1); 
		System.out.println("    Greedy: " + calcHeurstic2(state) + "  w2:" + w2); 
		System.out.println("    Corner: " + calcHeurstic3(state) + "  w3:" + w3); 
		System.out.println("    Order : " + calcHeurstic4(state) + "  w4:" + w4); 

		
	}

	
	
	
	public double calcSnake(int[] state){
		double value = 0; 
		value = value + (state[0]^8); 
		value = value + ((state[1]^7) * (state[1] /2)); 
		value = value + ((state[2]^7));  
		value = value + ((state[3]^6) * (state[3] /2)); 
		value = value + ((state[7]^6)); 
		value = value + ((state[6]^5) * (state[6] /2)); 
		value = value + ((state[5]^5)); 
		value = value + ((state[4]^4) * (state[4] /2)); 
		value = value + ((state[8]^4));
		value = value + ((state[9]^3) * (state[9] /2)); 
		value = value + ((state[10]^3));
		value = value + ((state[11]^2) * (state[11] /2));
		value = value + ((state[15]^2));
		value = value + ((state[14]) * (state[14] /2));
		value = value + ((state[13]));
		value = value + ((state[12] / 2));
		return value; 
	
		
		
		
		
		
	}
}
