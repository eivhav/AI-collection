package project1;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.InputStreamReader;
import java.util.ArrayList;

import javax.swing.JFrame;


public class NGproblem {
	 
	 public ArrayList<Variable> CNETvariables = new ArrayList<Variable>(); 
	 public ArrayList<Constraint> CNETconstraints = new ArrayList<Constraint>();   
	 private int[] dim = new int[2]; 
	 private int simSpeed = 1000; 
	 private String searchMethod = "Astar"; 
	 Toolbox utils = new Toolbox(); 
	 private boolean printDFL;
	 
	 
	 public NGproblem(){
		 
	 }
	 
	 
	 
	//run a nonogram problem. Set up graphics, agent and start state of type CSP. 
	 public void run(){
		 
		 if(CNETvariables.size() > 0){
			
			System.out.println("Creating A* agent and starting search"); 
			System.out.println(); 
			
			JFrame frame = new JFrame(); 
			GraphicNG GS = new GraphicNG(CNETconstraints, dim); 
			frame.add(GS); 
			frame.setSize(60+(dim[0]*22), 80+(dim[1]*22));
			frame.setVisible(true);
			 
			CSPheuristic newHeur = new CSPheuristic(); 
			CSPstate startState = new CSPstate(null, CNETvariables,  CNETconstraints, newHeur, GS); 
			startState.setPrintDFL(printDFL); 
			AstarAgent agent = new AstarAgent(frame, startState); 
			agent.setSimSpeed(simSpeed); 
			agent.runAgent(searchMethod); 
			
			
			
			//result
			
			System.out.println(); 
			if(agent.getFinalState() == null){
				System.out.println("no solution found"); 
			}
			else{
				utils.printNGdrawing(CNETvariables, agent.getFinalState().getStateID(), dim);  
				utils.printVariables(CNETvariables, agent.getFinalState().getStateID());
				utils.testAndPrint(CNETconstraints, CNETvariables, agent.getFinalState().getStateID());
				frame.repaint();
			}
		 }
	 }
	 
	 
	 
	 
	// read the input file and create all the constraints and variables 
	 public  void inputReader(String[] args){
			// Starts with input form file or text
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
	            
	            Toolbox utils = new Toolbox(); 
	            String firstline = in.readLine();  
	            dim[0] = utils.getDigitNumbers(firstline).get(0); 
	            dim[1] = utils.getDigitNumbers(firstline).get(1);  
	            
	            ArrayList<String> stringRowInput = new ArrayList<String>(); 
	            for(int i = 0; i < dim[1]; i++){
	            	String line = in.readLine(); 
	            	stringRowInput.add(line); 
	            	
	            }
	          
	            for(int i = 0; i < dim[1]; i++){
	            	String line = stringRowInput.get(dim[1]-i -1);  
	            	ArrayList<Integer> arrayLine = utils.getDigitNumbers(line);   
	            	NGdomainGen domainGen = new NGdomainGen(dim[0], arrayLine);  
	            	ArrayList<Integer> domain = domainGen.getIntegerValues(); 
	            	String name = "R"+i;
	            
	            	Variable newVar = new Variable(name, domain); 
	            	CNETvariables.add(newVar); 
	            }
	           
	           
	            for(int i = 0; i < dim[0]; i++){
	            	String line = in.readLine();  
	            	ArrayList<Integer> arrayLine = utils.getDigitNumbers(line);  
	            	NGdomainGen domainGen = new NGdomainGen(dim[1], arrayLine); 
	            	ArrayList<Integer> domain = domainGen.getIntegerValues();
	            	String name = "C"+i;
	            
	            	Variable newVar = new Variable(name, domain); 
	            	CNETvariables.add(newVar); 
	            }
	            
	            int count = 0; 
	            for(int col = 0; col < dim[0]; col++){
	            	for(int row = 0; row < dim[1]; row++){
	            		
	            		NGConstraint constraint = new NGConstraint(count, dim, row, col, CNETvariables); 
	            		CNETconstraints.add(constraint); 
	            		count++; 
	            	}			
	            }
	         
	            System.out.println("Init complete");   
	            utils.printVariables(CNETvariables, -1); 
	      }
	        catch(Exception e){
	            e.printStackTrace();
	        }
	}	

	 
	
	 public void addConstraints(){
			utils.constrAdder(CNETconstraints, CNETvariables); 	
	}
	
	public void setSimSpeed(int simSpeed2) {
		if(simSpeed2 >= 0){				
			this.simSpeed = simSpeed2;
		}
	}

	public void setSearchMethod(String searchMethod) {
		this.searchMethod = searchMethod; 	
	}
	
	public void setPrintDFL(boolean b){
		printDFL = b; 
	}
		
	 
}
