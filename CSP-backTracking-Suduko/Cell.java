package Oving5;

import java.util.ArrayList;

public class Cell {
	
	int finalNumber;   
	ArrayList<Boolean> possibleNumbers = new ArrayList<Boolean>();  
	int rowPos; 
	int colPos; 
	int sqaurePos; 
	Cell lastCell; 
	ArrayList<Integer> savedPossNumb; 
	boolean triedAsStart = false; 


	public Cell(int rowPos, int colPos, int finalValue){
		this.rowPos = rowPos; 
		this.colPos = colPos; 
		this.finalNumber = finalValue;  
		if(rowPos < 3)
			sqaurePos = ((colPos)/3);  		
		else if(rowPos < 6)
			sqaurePos = 3 + ((colPos)/3);  
		else 
			sqaurePos = 6 + ((colPos)/3); 
		for(int i = 0; i < 9; i++){
			if(finalValue == 0){
				possibleNumbers.add(true); }
			else {
				possibleNumbers.add(false);
			}
			
		}
		
		
	}


	public int getFinalNumber() {
		return finalNumber;
	}


	public void setFinalNumber(int finalNumber) {
		this.finalNumber = finalNumber;
	}


	public ArrayList<Integer> getPossibleNumbers() {
		ArrayList<Integer> numbers = new ArrayList<Integer>();  
		for(int i = 0; i < possibleNumbers.size(); i++){
			if(possibleNumbers.get(i)){
				numbers.add(i+1); 
			}
		}
		return numbers;
	}

	
	public boolean isPossibleNumber(int number){
		return possibleNumbers.get(number -1); 
	}
	
	public void removePossibleNumber(int number){
		if(number != 0)
			possibleNumbers.set(number-1, false);  
	}
		

	
	public int getRowPos() {
		return rowPos;
	}


	public void setRowPos(int rowPos) {
		this.rowPos = rowPos;
	}


	public int getColPos() {
		return colPos;
	}


	public void setColPos(int colPos) {
		this.colPos = colPos;
	}


	public int getSqaurePos() {
		return sqaurePos;
	}


	public void setSqaurePos(int sqaurePos) {
		this.sqaurePos = sqaurePos;
	}
	
	
	public Cell getLastCell() {
		return lastCell;
	}


	public void setLastCell(Cell lastCell) {
		if(this != lastCell){
			this.lastCell = lastCell;
		}
	}


	public void savePossibleNumbers(){
		savedPossNumb = getPossibleNumbers(); 
	}
	
	public void retrivePossibleNumbers(){
		for(int i = 0; i < savedPossNumb.size(); i++)
			possibleNumbers.set(savedPossNumb.get(i) -1, true); 
	}
	
	
	public int getLowestPossbleValue(){
		int lowestValue = 0; 
		for(int i = 0; i < possibleNumbers.size(); i++){
			if(possibleNumbers.get(i)){
				lowestValue = i+1;  
				break; 
			}
		}
		return lowestValue; 
	}
	
	public String getPos(){
		return getRowPos() + " " + getColPos();  
	}

}
