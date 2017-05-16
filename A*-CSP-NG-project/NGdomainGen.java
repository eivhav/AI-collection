package project1;

import java.util.ArrayList;
import java.util.Arrays;

public class NGdomainGen {
	
	
	private ArrayList<int[]> tempResults = new ArrayList<int[]>(); 
	private ArrayList<int[]> finalResults = new ArrayList<int[]>(); 
	private ArrayList<Integer> source = new ArrayList<Integer>(); 
	private int dim = 20; 
	
	
	
	public NGdomainGen(int dim, ArrayList<Integer> source){
		this.source = source; 
		this.dim = dim; 
		if(source.size() == 1 && source.get(0) == 0){
			int[] list = new int[1]; 
			list[0] = 0; 
			finalResults.add(list); 
		}
		else {
			generateTempresults(); 
		}
	}
	
	
	
	public ArrayList<Integer> getIntegerValues(){
		ArrayList<Integer> returnList = new ArrayList<Integer>(); 
		
		for(int i = 0; i < finalResults.size(); i++){
			int value = 0; 
			int[] result = finalResults.get(i); 
			
			for(int j = result.length-1 ; j >= 0; j--){
				int exp = (result.length-1) -j;  
				value = (int) (value + (result[j] * Math.pow(2, exp))); 
			}
			returnList.add(value);		
		}
		
		return returnList; 
				
	}
		
	
	
	public void generateTempresults(){
		
		int firstPos = dim - source.get(source.size()-1);   
		while(firstPos >= 0){
			int[] vector = new int[dim];  
			for(int i = 0; i < source.get(source.size()-1); i++){
				vector[firstPos+i] = 1; 
			}
			firstPos--; 
			tempResults.add(vector); 
			
			
		}
		
		if(source.size() == 1){
			finalResults = tempResults; 
		}
		else{
		
			for(int i = source.size()-2; i >= 0; i--){
			
				ArrayList<Integer> removeList = new ArrayList<Integer>(); 
				int tempsize = tempResults.size();
				int sourceValue = source.get(i); 
			
				for(int j = 0; j < tempsize; j++){
				
					int[] vector = Arrays.copyOf(tempResults.get(j), tempResults.get(j).length); 
				
					int lastPos = 0; 
					for(int a = 0; a < vector.length;  a++ ){
						if(vector[a] == 1){
							lastPos = a; 
							break; 
						}
					}
				
					for(int k = lastPos; k >= 0; k--){
					
						int[] vectorK = Arrays.copyOf(vector, vector.length); 
						boolean hasRoom = true; 
					
						for(int n = 0; n < sourceValue +1; n++){
							if(k+n >= vector.length){
								hasRoom = false; 
								break; 
							}
							else if(vectorK[k+n] == 1){
								hasRoom = false;
								break; 
							}	
						}
						if(hasRoom){
							for(int n = 0; n < sourceValue; n++){
								vectorK[n+k] = 1; 
							}
							tempResults.add(vectorK);
							if(i == 0){
								finalResults.add(vectorK); 
							}	
						}
					}
				removeList.add(j); 		
				}
				for(int k = 0; k < removeList.size(); k++){
					tempResults.remove(0); 
				}
				
			}	
		}	
	}	
	
	
	
	public static void printArray(int [] array){
		for(int x = 0; x < array.length; x++){
			System.out.print(array[x]); 
		}
		System.out.println(); 
	}
	

	
	public String getStringValue(int number, int dimSize){
		String s = Integer.toBinaryString(number); 
		while(s.length() < dimSize){
			String p = "0";
			s = p + s; 
		}
		return s; 	
	}


		
		
}
	
	
	
	
	
	
	
	

	
	

