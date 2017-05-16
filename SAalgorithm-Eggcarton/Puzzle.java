package Oving4;

import java.util.ArrayList;
import java.util.Random;

public class Puzzle {
	
	private int panelSize; 
	private int eggs; 
	
	public ArrayList<ArrayList<Integer>> eggPos = new ArrayList<ArrayList<Integer>>(); 
	//Info of eggs positions are stored as a list of each column, with row placement, by example: 0,2,3 
	private ArrayList<ArrayList<Integer>> board = new ArrayList<ArrayList<Integer>>(); 
	//The complete board is stored as an array within and array, with 0 as empty and 1 as egg.  
	
	
	public Puzzle(int panelSize, int eggs){
		this.panelSize = panelSize; 
		this.eggs = eggs; 
	}
	
	
	public void randomizeEggs(){ 
		//Randomizes the eggpos list with a "eggs" number of eggs in each column. 
		//Used in the beginning of the running of the program. 
		for(int i = 0; i < panelSize;  i++){
			ArrayList<Integer> column = new ArrayList<Integer>(); 
			
			while(column.size() < eggs){
				Random rnd = new Random();
				int rand = rnd.nextInt(panelSize); 
				boolean isInList = false; 
				
				for(int j = 0; j < column.size();  j++){
					if(rand == column.get(j)){
						isInList = true; 
						break; 
					}
				}
				if(!isInList){	
					column.add(rand); 
				}
			}	
			eggPos.add(column); 
		}
	}
	
	
	public void generateBoard(){
		//Creates the Array<Array> board with 0 as empty and 1 as egg. Based upon the eggPos list.  
		for(int row = 0; row < panelSize; row++){
			ArrayList<Integer> arrayRow = new ArrayList<Integer>();   
			for(int col = 0; col < panelSize; col++){
				boolean isEgg = false; 
				for(int i = 0; i < eggPos.get(col).size(); i++){
					if(row == eggPos.get(col).get(i)){
						isEgg = true; 
						arrayRow.add(1); 
					}	
				}
				if(!isEgg){
					arrayRow.add(0);  
				}	
			}
			board.add(arrayRow);  
		}
	}
	
	public ArrayList<Puzzle> generateNeigbours(){  
		//returns a list of neighbors where each neighbor is a new puzzle board with... 
		//displacement of 1, in the row position of each egg 
		ArrayList<Puzzle> Neighbours = new ArrayList<Puzzle>(); 
		for(int i = 0; i < panelSize;  i++){
			for(int j = 0;  j < eggs; j++){
				
				int rowPosforEgg = eggPos.get(i).get(j); 
				boolean isEggNorth = false; 
				boolean isEggSouth = false;  
				
				for(int k = 0;  k < eggs; k++){
					if(rowPosforEgg -1 == eggPos.get(i).get(k)){
						isEggNorth = true; }
					if(rowPosforEgg +1 == eggPos.get(i).get(k)){
						isEggSouth = true;} 
				}
				
				if((rowPosforEgg -1 >= 0) && !isEggNorth){
						Puzzle Neighbour = new Puzzle(panelSize, eggs); 
						Neighbour.eggPos = getEggList(); 
						Neighbours.add(Neighbour); 
						Neighbour.eggPos.get(i).set(j, rowPosforEgg - 1);   
						Neighbour.generateBoard();
				}
					
				if((rowPosforEgg +1 < panelSize) && !isEggSouth){
						Puzzle Neighbour = new Puzzle(panelSize, eggs); 
						Neighbour.eggPos = getEggList(); 
						Neighbours.add(Neighbour); 
						Neighbour.eggPos.get(i).set(j, rowPosforEgg + 1); 
						Neighbour.generateBoard();
				}	
			}
		}
		return Neighbours; 	
	}
	
	
	public double calcObjectiveFunction(){
		//returns the objective function based upon the number of row and diagonal errors.
		//The lower the better. 0 = problem solved. 
		int diag1 = calculateDiagErrors(board); 
		int diag2 = calculateDiagErrors(revereseBoard(board)); 	  
		return diag1 + diag2 + getRowErrors(); 
	}	
	
	
	public ArrayList<ArrayList<Integer>> revereseBoard(ArrayList<ArrayList<Integer>>board){
		//Creates a duplicate board turned clockwise, so diagonal in both directions can be calculated. 
		ArrayList<ArrayList<Integer>> returnBoard = new ArrayList<ArrayList<Integer>>(); 
		for(int col = 0; col < panelSize; col++){ 
			ArrayList<Integer> arrayRow = new ArrayList<Integer>();  
			for(int row = 0; row < panelSize; row++){ 
				arrayRow.add(board.get(panelSize - row -1).get(col)); 
			}
		returnBoard.add(arrayRow); 
		}
		return returnBoard; 
	}
		
	
	public int getRowErrors(){
		//Returns no of rowErrors. That is, if eggs = 3 and there is 4 in one row, the error is 1 in that row. 
		int rowErrors = 0; 
		for(int row = 0; row < panelSize; row++){
			int rowValue = 0; 
			for(int col = 0; col < panelSize; col++){ 
				if(board.get(row).get(col) == 1){
						rowValue++;  		
				}
			}
			if(rowValue > eggs){ 
				rowErrors = rowErrors + rowValue - eggs; 
			}
		}
		return rowErrors; 
	}
	
	
	public int calculateDiagErrors(ArrayList<ArrayList<Integer>> board){
		//As with rowErrors, iterates through each diagonal and returns the total of diagonal errors. 
		int errors = 0; 
		for(int diag = 1; diag < (panelSize*2) -1; diag++){
			int eggsOnDiag = 0; 
			if(diag < panelSize){
				for(int diagSize = 0; diagSize < diag +1; diagSize++){
					if(board.get(diag - diagSize).get(diagSize) == 1){ 
					eggsOnDiag++; } 	
				}
			}
			else{ 
				int nDiag = ((panelSize*2)-1) - diag; 
				for(int diagSize = 0; diagSize < nDiag; diagSize++){
					if(board.get(nDiag - diagSize).get(diagSize +1) == 1){ 
					eggsOnDiag++;} 
				}
			}	
			if(eggsOnDiag > eggs){
				errors = errors + (eggsOnDiag -eggs); 	 
			} 
		}	
		return errors;  
	}
		
	
	private ArrayList<ArrayList<Integer>> getEggList(){
		//Had to be added due object-reference(mutating) problem. 
		//Returns the egglist for the original puzzle object 
		ArrayList<ArrayList<Integer>> returnlist = new ArrayList<ArrayList<Integer>>(); 
			for(int i = 0; i < eggPos.size(); i++){
				returnlist.add(new ArrayList<Integer>()); 
				for(int j = 0; j < eggPos.get(i).size(); j++){
					for(int k = 0; j < panelSize; k++)
					if(eggPos.get(i).get(j) == k){ 
						returnlist.get(i).add(k);
						break;
					}		
				}
			}
		return returnlist; 
	}
	
	
	//the rest is getters and setters
	public int getPanelSize() {
		return panelSize;
	}

	public void setPanelSize(int panelSize) {
		this.panelSize = panelSize;
	}

	public int getEggs() {
		return eggs;
	}

	public void setEggs(int eggs) {
		this.eggs = eggs;
	}

	public ArrayList<ArrayList<Integer>> getEggPos() {
		return eggPos;
	}

	public void setEggPos(ArrayList<ArrayList<Integer>> eggPos) {
		this.eggPos = eggPos;
	}
	
	public ArrayList<ArrayList<Integer>> getBoard() {
		return board;
	}

	public void setBoard(ArrayList<ArrayList<Integer>> board) {
		this.board = board;
	}
	
}
