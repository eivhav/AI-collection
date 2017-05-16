package Oving4;

import java.util.ArrayList;
import java.util.Random;


public class Eggcarton {
	static int cartonSize = 5; 
	static int noOfEggs = 2; 
	
	public static void main(String[] args){
		//Main method creates a new puzzle with number of row/columns and eggs, randomizes eggs and generates board. 
		//Then prints the start state, and thereafter it prints the resulting board from the SAsearch.  
		Puzzle puzzle = new Puzzle(cartonSize,noOfEggs); 
		puzzle.randomizeEggs(); 
		puzzle.generateBoard(); 
		System.out.print("Start: " + "\n"); 
		print(puzzle); 
		print(SearchSA(puzzle)); 
		System.out.print("start: " + "\n"); 
		print(puzzle); 
	}
	
	
	public static void print(Puzzle puzzle){
		//The print function than generates boards in output with 0 as empty and 1 as eggs. 
		for(int row = 0; row < puzzle.getPanelSize(); row++){
			String rowString = " "; 
			for(int col = 0; col < puzzle.getPanelSize(); col++){
				rowString = rowString + puzzle.getBoard().get(row).get(col) + " ";  
			}
		System.out.print(rowString + "\n");
		}
	}
	
	
	public static Puzzle SearchSA(Puzzle puzzle){
		//The Tmax temperature is defined along with the change in temperature
		double Tmax = 150; 
		double temperatur = Tmax;   
		double dT = 0.001;
		
		Puzzle state = puzzle;   
		double F = state.calcObjectiveFunction(); // the objective function, smaller the better  
		Puzzle pMax = state; 
		
		while(F > 0 && temperatur > 0){
			// iterates as long as temp > 0 and the objective function > 0. 
			
			System.out.print("Looking at State: " +"\n");
			print(state); 
			System.out.print("value: " + state.calcObjectiveFunction() + "\n");
			System.out.print("temp " + temperatur + "\n");
			
			//Neighbors are generated and iterated to see if one is better(has lower F)
			ArrayList<Puzzle> neighbours = state.generateNeigbours(); 
			for(int i = 0; i < neighbours.size(); i++){
				if(neighbours.get(i).calcObjectiveFunction() < pMax.calcObjectiveFunction()){
					pMax = neighbours.get(i); 
				}
			}		
			
			double q = ((state.calcObjectiveFunction() - pMax.calcObjectiveFunction()) / state.calcObjectiveFunction()); 
			double a = Math.exp((-q)/temperatur); 
			double p = minium(a, 1.0); 
			Random rnd = new Random(); 
			double x = ((double) rnd.nextInt(1000))/1000;  
			
			if(x > p){
				//Exploiting: the best state becomes the search state
				state = pMax;  
			}
			else{
				//Exploring: a random neighbor state becomes the search state
				Random rnd2 = new Random();  
				int rndNeighbour = rnd2.nextInt(neighbours.size());
				state = neighbours.get(rndNeighbour); 
			}
			
			F = state.calcObjectiveFunction(); 
			temperatur = temperatur - dT; 
				
			System.out.print("xValue: " + x +  "\n" + "pValue: " + p + "\n" +  "\n");
			System.out.print("Iterasjon: " +((Tmax - temperatur)/dT) + "\n");
		}
		
		//Once the optimal state has been found, or the temp has reached zero, the iterations stops.   
		System.out.print("Resultat: " + "\n");
		System.out.print("value: " + pMax.calcObjectiveFunction() + "\n");
		if(pMax.calcObjectiveFunction() > 0){
			System.out.print("Optimal result not found " + "\n");}
		return pMax; 		
	}
	
	
	private static double minium(double val1, double val2){
		//Help-function that returns the smalles of two values. 
		if(val1 < val2)
			return val1; 
		else
			return val2; 
	}

}
