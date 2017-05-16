package Oving5;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.InputStreamReader;
import java.util.ArrayList;

public class Suduko {
	
	static ArrayList<ArrayList<Cell>> rows = new ArrayList<ArrayList<Cell>>(); 
	static ArrayList<ArrayList<Cell>> columns = new ArrayList<ArrayList<Cell>>(); 
	static ArrayList<ArrayList<Cell>> squares = new ArrayList<ArrayList<Cell>>(); 
	static ArrayList<Integer> startNumbers = new ArrayList<Integer>();  
	
	
	public static void printBoard(){
		for(int rowNo = 0; rowNo < rows.size(); rowNo++){
			String rowString = "";   
			for(int colNo = 0; colNo < rows.get(rowNo).size(); colNo++){
				Cell cell = rows.get(rowNo).get(colNo); 
				String rowSpace; 
				if(rowNo == 2 || rowNo == 5 || rowNo == 8){
					rowSpace = "_"; } 
				else{ 
					rowSpace = " ";} 	
				if(colNo == 0 || colNo == 3 || colNo == 6){
					rowSpace = "|"; }
				rowString = rowString + rowSpace;
				if(cell.getFinalNumber() == 0){
					rowString = rowString + " "; }  
				else {
					rowString = rowString + cell.getFinalNumber(); }	
			}
			rowString = rowString + "|"; 
			System.out.print(rowString + "\n"); 
		}
		System.out.print("\n"); 
	}
	
	
	
	public static int backTrackingAlgorithmPrint(){ 
		//saveStartNumbers(); 
		int backtracking = 0; 
		int startConstraints = getTotalNumberofConstr(); 
		int startNoOfNumbers = getTotalNoOfSetNumbers(); 
		Cell startCell = getcellbyLowestConstrMRV(null, true); 
		while(!solutionFound() && backtracking < 2000000){
			
			System.out.print("no of constr start: " + startConstraints + "\n");
			System.out.print("no of constr left: " + getTotalNumberofConstr() + "\n");
			System.out.print("no of numbers start: " + startNoOfNumbers + "\n");
			System.out.print("no of numbers set: " + getTotalNoOfSetNumbers() + "\n");
			//retriveStartNumbers(); 
		 
			startCell = getcellbyLowestConstrMRV(startCell, true);  
			System.out.print("New startCell: " + startCell.getPos() + "\n");
			int startnumber = startCell.getLowestPossbleValue(); 
			startCell.setFinalNumber(startnumber); 
			startCell.savePossibleNumbers(); 
			System.out.print(" value set: " + startCell.getFinalNumber() + "\n");
			System.out.print(" antall possibleNumbers: " + startCell.getPossibleNumbers().size() + "\n");
			boolean failure = false;  
			Cell current = startCell; 
			updateConstraintsALL(current, current.getFinalNumber()); 
			System.out.print( "\n");
			
			while(!solutionFound() && !failure && backtracking < 2000000){
				if(current.getPossibleNumbers().size() > 0){
					System.out.print(" antall possNumber > 0 : " + current.getPossibleNumbers().size()+  "\n");
					Cell nextCell = getcellbyLowestConstrMRV(current, false);  
					nextCell.setLastCell(current); 
					nextCell.savePossibleNumbers(); 
					current = nextCell; 
					current.setFinalNumber(current.getLowestPossbleValue()); 
					updateConstraintsALL(current, current.getFinalNumber()); 	
					System.out.print("Current moves to: " + current.getPos() + "  with value: " + current.getFinalNumber() +  "\n");
					if(current.getLastCell() != null)
						System.out.print(" current: " + current.getPos() + " lastCell: " + current.getLastCell().getPos()  +   "\n");
				}
				else{
					System.out.print(" antall possNumber = 0 : " +   "\n");
					backtracking ++; 
					current.setFinalNumber(0); 
					if(current.getLastCell() != null){
						System.out.print(" current: " + current.getPos() + " lastCell: " + current.getLastCell().getPos()  +   "\n"); 
						current.retrivePossibleNumbers();
						current = current.getLastCell(); 
					}
					current.removePossibleNumber(current.getFinalNumber()); 	
					current.setFinalNumber(current.getLowestPossbleValue());
					resetConstraintsALL(current, current.getFinalNumber()); 
					
					System.out.print("Current backtracks to: " + current.getPos() + "  with value: " + current.getFinalNumber() +  "\n");
				}
				
				System.out.print("no of constr start: " + startConstraints + "\n");
				System.out.print("no of constr left: " + getTotalNumberofConstr() + "\n");
				System.out.print("no of numbers start: " + startNoOfNumbers + "\n");
				System.out.print("no of numbers set: " + getTotalNoOfSetNumbers() + "\n"); 
				if(startCell == current && startCell.getPossibleNumbers().size() == 0){
					failure = true; 
					System.out.print("failure" + "\n"); 
				}
				System.out.print("\n");
			}
			printBoard();
			System.out.print("\n");
			
			resetConstraintsALL(startCell, startnumber); 
			startCell.setFinalNumber(0); 
			startCell.retrivePossibleNumbers();
			startCell.triedAsStart = true; 
		
		}
		return backtracking; 
	}
	
	
	public static int backTrackingAlgorithm(){ 
		
		int backtracking = 0; 
		//saveStartNumbers();
		while(!solutionFound()){
			//retriveStartNumbers(); 
			Cell startCell = getcellbyLowestConstrMRV(null, true);  
			int startnumber = startCell.getLowestPossbleValue(); 
			startCell.setFinalNumber(startnumber); 
			startCell.savePossibleNumbers(); 
			boolean failure = false;  
			Cell current = startCell; 
			updateConstraintsALL(current, current.getFinalNumber()); 
			
			while(!solutionFound() && !failure){
				if(current.getPossibleNumbers().size() > 0){
					Cell nextCell = getcellbyLowestConstrMRV(current, false);  
					nextCell.setLastCell(current); 
					nextCell.savePossibleNumbers(); 
					current = nextCell; 
					current.setFinalNumber(current.getLowestPossbleValue()); 
					updateConstraintsALL(current, current.getFinalNumber()); 	
					}
				else{
					backtracking ++; 
					current.setFinalNumber(0); 
					resetConstraintsALL(current, current.getFinalNumber());
					if(current.getLastCell() != null){
						current.retrivePossibleNumbers();
						current = current.getLastCell(); 
					}
					resetConstraintsALL(current, current.getFinalNumber()); 
					current.removePossibleNumber(current.getFinalNumber()); 	
					current.setFinalNumber(current.getLowestPossbleValue());
				}
				if(startCell == current && startCell.getPossibleNumbers().size() == 0){
					failure = true; 
				}
			}
			
			resetConstraintsALL(startCell, startnumber); 
			startCell.setFinalNumber(0); 
			startCell.retrivePossibleNumbers();
			startCell.triedAsStart = true; 
			
		}
		return backtracking; 
	}
	
	
	
	public static void saveStartNumbers(){
		for(int i = 0; i < 9; i++){
			for(int j = 0; j < 9; j++){
				startNumbers.add(rows.get(i).get(j).getFinalNumber());   
			}
			}
	}
	
	public static void retriveStartNumbers(){
		int k= 0; 
		for(int i = 0; i < 9; i++){
			for(int j = 0; j < 9; j++){
				rows.get(i).get(j).setFinalNumber(startNumbers.get(k));   
				k++; 
			}
		}
	}
	
	
	
	private static int getTotalNoOfSetNumbers() {
		int number = 0;  
		for(int i = 0; i < 9; i++){
			for(int j = 0; j < 9; j++){
				if(rows.get(i).get(j).getFinalNumber() != 0) { 
					number++; 
				}
			}
		}
		return number; 
	}


	public static void removeNonValidConstrALL(){
		removeNonValidConstr(rows); 
		removeNonValidConstr(columns); 
		removeNonValidConstr(squares); 	
	}
	
	
	public static int AC3(){
		int runs = 0; 
		int value = 0; 
		while(value != getTotalNumberofConstr()){ 
	        	value = getTotalNumberofConstr(); 
	        	removeNonValidConstrALL();  
	        	setNewNumbers();
	        	printBoard(); 	
	        	runs++; 
	       }
		return runs; 
	}
	
	
	public static boolean solutionFound(){
		boolean tempreturn = true; 
		for(int i = 0; i < 9; i++){
			for(int j = 0; j < 9; j++){
				if(rows.get(i).get(j).getFinalNumber() == 0) { 
					tempreturn = false; 
					break; 
				}
			}
		}
		return tempreturn; 
	}
	
	
	public static void resetConstraintsALL(Cell cell, int number){
		resetConstraints(number, rows.get(cell.getRowPos())); 
		resetConstraints(number, columns.get(cell.getColPos()));
		resetConstraints(number, squares.get(cell.getSqaurePos()));
	}
	
	
	public static void resetConstraints(int number, ArrayList<Cell> list){
		for(int i = 0; i < list.size(); i++){
			if(number > 0 && list.get(i).getFinalNumber() == 0)
				list.get(i).possibleNumbers.set(number -1, true);  
		}
	}
		
	
	public static void updateConstraints(int number, ArrayList<Cell> list){
		for(int i = 0; i < list.size(); i++){
			if(number > 0 && list.get(i).getFinalNumber() == 0)
				list.get(i).possibleNumbers.set(number -1, false);  
		}
	}
	
	public static void updateConstraintsALL(Cell cell, int number){
		updateConstraints(number, rows.get(cell.getRowPos())); 
		updateConstraints(number, columns.get(cell.getColPos()));
		updateConstraints(number, squares.get(cell.getSqaurePos()));
	}
	
	
	public static void removeNonValidConstr(ArrayList<ArrayList<Cell>> list){
		for(int i = 0; i < 9; i++){
			for(int j = 0; j < 9; j++){
				int number = list.get(i).get(j).getFinalNumber();  
				if(number != 0){
					for(int k = 0; k < 9; k++){
						list.get(i).get(k).removePossibleNumber(number); 
					}
				}
			}
		}		
	}
	
	
	public static int getTotalNumberofConstr(){ 
		int numberOfConstr = 0; 
		for(int i = 0; i < 9; i++){
			for(int j = 0; j < 9; j++){
				numberOfConstr = numberOfConstr + rows.get(i).get(j).getPossibleNumbers().size(); 
			}
		}
		return numberOfConstr; 
	}
	

	public static Cell getcellbyLowestConstrMRV(Cell cell, boolean isForStart){
		ArrayList<Cell> possibleCells = new ArrayList<Cell>(); 
		for(int i = 0; i < 9; i++){
			for(int j = 0; j < 9; j++){ 
				if(rows.get(i).get(j).getFinalNumber() == 0 && rows.get(i).get(j) != cell)
					possibleCells.add(rows.get(i).get(j)); 
			}
		}
		Cell returnCell = null; 
		if(possibleCells.size() > 0){
			returnCell = possibleCells.get(0);
			for(int i = 0; i < possibleCells.size(); i++){
				if(isForStart){
					if((cell != possibleCells.get(i) && (possibleCells.get(i).getPossibleNumbers().size() <= returnCell.getPossibleNumbers().size()) && !possibleCells.get(i).triedAsStart) ){
						returnCell = possibleCells.get(i); }
					}
				else{
					if((cell != possibleCells.get(i) && (possibleCells.get(i).getPossibleNumbers().size() <= returnCell.getPossibleNumbers().size()))){
						returnCell = possibleCells.get(i); 
					}
				}
			}
		}
		return returnCell; 
	}
	

	
	
	public static void setNewNumbers(){
		for(int i = 0; i < rows.size(); i++){
			ArrayList<Cell> row = rows.get(i); 
			for(int j = 0; j < row.size(); j++){
				if(row.get(j).getPossibleNumbers().size() == 1){
					row.get(j).setFinalNumber(row.get(j).getPossibleNumbers().get(0)); 
					row.get(j).removePossibleNumber(row.get(j).getPossibleNumbers().get(0)); 
				}		
			}		
		} 
	}
	
	
	public static void printCellConstr(int xpos, int ypos){
		Cell cell = rows.get(ypos).get(xpos); 
		for(int j = 0; j < 9; j++){ 
			System.out.print((j+1) + " : " + cell.possibleNumbers.get(j) + "\n"); 
		}
	}
		
	
	public static void main(String[] args){
        try{
            BufferedReader in;
            if (args.length > 0) {
                try {
                    in = new BufferedReader(new FileReader(args[0]));
                }
                catch (FileNotFoundException e) {
                    System.out.println("Kunne ikke åpne filen " + args[0]);
                    return;
                }
            }
            else {
                in = new BufferedReader(new InputStreamReader(System.in));
            }
            
            for(int i = 0; i < 9; i++){
            	ArrayList<Cell> column = new ArrayList<Cell>(); 
            	ArrayList<Cell> sqaure = new ArrayList<Cell>(); 
            	columns.add(column); 
            	squares.add(sqaure); 
            }
            String line = in.readLine(); 
            int colCount = 0; 
            while(line != null){
            	ArrayList<Cell> row = new ArrayList<Cell>(); 
            	for(int rowCount = 0; rowCount < line.length(); rowCount++){ 
            		char c = line.charAt(rowCount); 
            		String s = ""+ c;  
            		int value = Integer.parseInt(s); 
            		Cell cell = new Cell(rowCount, colCount, value); 
            		row.add(cell); 
            		columns.get(rowCount).add(cell); 
            		squares.get(cell.getSqaurePos()).add(cell); 
            	}
            	rows.add(row); 	
            	line = in.readLine();
            	colCount++; 
            }
        
        printBoard(); 
        int runs = AC3(); 
        System.out.print("no of runs:  " + runs + "\n");  
        int backtracking = backTrackingAlgorithmPrint(); 
        System.out.print("no of backtracking:  " + backtracking);

        }
        catch(Exception e){
            e.printStackTrace();
        }  
	}
	
	
	
	/*
	 * Pseudo for the Backtracking algorithm: 
	 * Select cell with lowest number of possibleValues 
	 * 	Set lowest value 
	 * 
	 * while(solution not found)
	 * 
	 * 	if(cell has possbileValues) 
	 * 		cell.saveState();
	 * 		newCell = nextUnasingedCell(with lowest number  of possibleValues)  
	 *  	NewCell(setLastCell(cell))
	 *  	Cell = newCell
	 *  	choiceValue() 
	 *  	updateConstraints()
	 *  	
	 * else 
	 *		backtracking ++
	 * 		cell = cell.getLastCell()
	 * 		all cells = cell.getSavedState
	 * 		remove cell.value from last cell possible values
	 *  
	 *  
	 * 
	 * 	
	 			
	 * 			
	 * 
	 * 		
	 * 
	 *	
	 *	
	 *	
	 * 
	 * 
	 * 
	 * 
	 * 
	 * 
	 * 
	 */

}
