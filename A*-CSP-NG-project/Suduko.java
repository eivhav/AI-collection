package project1;


import java.util.ArrayList;



public class Suduko {
	
	String sb1 = "300001005608023971900070030000200010009000200080006000070030009893740102400600007"; 
	String sb2 = "090350700000800029000402008710000000463508297000000051300204000940005000008037040"; 
	String sb3 = "605900100000100073071300005009010004046293510700040600200001730160002000008009401"; 
	String sb4 = "008500004070030010400007600200009100040060070005200009002600001060020080300001900"; 
	String sb5 = "029800000010603900030020150600010240000000700001000008000500006090406010005000070"; 
	String sb6 = "800000000003600000070090200050007000000045700000100030001000068008500010090000400"; 
	ArrayList<String> boards = new ArrayList<String>(); 
	ArrayList<Variable> vars = new ArrayList<Variable>(); 
	ArrayList<String> names = new ArrayList<String>(); 
	ArrayList<Integer> domain = new ArrayList<Integer>(); 
	ArrayList<Constraint> constraints = new ArrayList<Constraint>(); 
	
	
	
	
	
	public Suduko(int boardNumber){
		boards.add(sb1); boards.add(sb2); boards.add(sb3); boards.add(sb4); boards.add(sb5); boards.add(sb6);
		for(int i = 1; i < 10; i++){
			domain.add(i); 
		}
		names.add("A"); 
		names.add("B"); 
		names.add("C"); 
		names.add("D"); 
		names.add("E"); ; 
		names.add("F"); 
		names.add("G"); 
		names.add("I"); 
		names.add("J");
		
		for(int i = 0; i < 9; i++){
			for(int j = 1; j < 10; j++){
			String varsName = names.get(i)+j; 
			vars.add(new Variable(varsName, domain)); 
			}
		}
		
		String sudukoBoard = boards.get(boardNumber-1); 
		
		for(int i = 0; i < sudukoBoard.length(); i++){
			String c = sudukoBoard.substring(i, i+1); 
			int value = Integer.parseInt(c); 
			if(value > 0 && value < 10){
				ArrayList<Integer> singleDomain = new ArrayList<Integer>();
				singleDomain.add(value); 
				vars.get(i).setOrgDomain(singleDomain); 	
			}
			
		}
		
		for(int i = 0; i< 9; i++){
			constraints.add(new NotEqConstraint(i, getRowList(i, vars))); 
			constraints.add(new NotEqConstraint((i +9), getColList(i, vars))); 
			constraints.add(new NotEqConstraint((i +18), getSQList(i, vars))); 
		}
		
		System.out.println("init complete"); 
		for(int i = 0; i < 9; i++){
			for(int j = 0; j < 9; j++){
				
			Variable var = vars.get((i*9 + j)); 
			ArrayList<Integer> dom = var.getOrgDomain(); 
			if(dom.size() == 1)
				System.out.print(dom.get(0) + " "); 
			else 
				System.out.print("O" + " ");
			}
			System.out.println(); 
		}
		
		System.out.println(); 
		
		
	}
	
	public void run(){
		System.out.println("Creating A* agent and starting search"); 
		System.out.println(); 
		
	
		CSPheuristic newHeur = new CSPheuristic();
		CSPstate startState = new CSPstate(null, vars,  constraints, newHeur, null); 
		AstarAgent agent = new AstarAgent(null, startState); 
		agent.runAgent("Astar"); 
		
		if(agent.getFinalState() == null){
			System.out.println("no solution found"); 
		}
		else{
		
			for(int i = 0; i < 9; i++){
				for(int j = 0; j < 9; j++){
					
				Variable var = vars.get((i*9 + j)); 
				ArrayList<Integer> dom = var.getDomain(agent.getFinalState().getStateID()); 
				if(dom.size() == 1)
					System.out.print(dom.get(0) + " "); 
				else 
					System.out.print(" " + " ");
				}
				System.out.println(); 
			
			}
		}

	}
	
	
	
	
	
	
	
	
	private static ArrayList<Variable> getRowList(int rowNo, ArrayList<Variable> orgList){
		ArrayList<Variable> returnList = new ArrayList<Variable>(); 
		for(int i = 0; i < 9; i++){
			returnList.add(orgList.get(i + (rowNo*9))); 
		}
		
		return returnList; 
		
	}
	
	
	private static ArrayList<Variable> getColList(int colNo, ArrayList<Variable> orgList){
		ArrayList<Variable> returnList = new ArrayList<Variable>(); 
		for(int i = 0; i < 9; i++){
			returnList.add(orgList.get(colNo + (i*9))); 
		}
		
		return returnList; 
	}
	
	
	private static ArrayList<Variable> getSQList(int sqNo, ArrayList<Variable> orgList){
		ArrayList<Variable> returnList = new ArrayList<Variable>(); 
		int startPos = 0; 
		if(sqNo == 0){
			startPos = 0; }
		if(sqNo == 1){
			startPos = 3; }
		if(sqNo == 2){
			startPos = 6; }
		if(sqNo == 3){
			startPos = 27; }
		if(sqNo == 4){
			startPos =30; }
		if(sqNo == 5){
			startPos = 33; }
		if(sqNo == 6){
			startPos = 54; }
		if(sqNo == 7){
			startPos = 57; }
		if(sqNo == 8){
			startPos = 60; }
					
		for(int i = 0; i < 3; i++){
			for(int j = 0; j < 3; j++){
				int pos = (i*9) + j + startPos; 
				returnList.add(orgList.get(pos)); 
			}
		}
		
		return returnList; 
	}
	
	




}
