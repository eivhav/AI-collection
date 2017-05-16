package project1;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.InputStreamReader;
import java.util.ArrayList;

import javax.swing.JFrame;

public class VCproblem {
	
	 private int numOfVertices = 0; 
	 private int numOfEdges = 0; 
	 public ArrayList<Variable> CNETvariables = new ArrayList<Variable>(); 
	 public ArrayList<Constraint> CNETconstraints = new ArrayList<Constraint>(); 
	 private ArrayList<VarPoint> varPoints = new ArrayList<VarPoint>(); 
	 private ArrayList<ConstrSet> constrSets = new ArrayList<ConstrSet>(); 
	 private int domainSize;  
	 private int simSpeed = 100; 
	 private String searchMethod = "Astar"; 
	 Toolbox utils = new Toolbox(); 
	 private boolean printDFL = false;  
	
	
	public VCproblem(int kValue){
		domainSize = kValue; 
	}
	
	
	//run a VC problem. Set up graphics, agent and start state of type CSP. 
	public void run(){
		
		if(CNETvariables.size() > 0){
		
			System.out.println("Creating A* agent and starting search"); 
			System.out.println(); 
			
			JFrame frame = new JFrame(); 
			GraphicVC GS = new GraphicVC(800, 800, varPoints, constrSets); 
			frame.add(GS); 
			frame.setSize(860, 860);
			frame.setVisible(true);
			GS.setPrintNumbers(false); 
			GS.repaint(); 
			
			CSPheuristic newHeur = new CSPheuristic();
			CSPstate startState = new CSPstate(null, CNETvariables,  CNETconstraints, newHeur, GS); 
			startState.setPrintDFL(printDFL); 
			AstarAgent agent = new AstarAgent(frame, startState); 
			agent.setSimSpeed(simSpeed); 
			agent.runAgent(searchMethod); 
			
			
			//result
			
			if(agent.getFinalState() == null){
				System.out.println("no solution found"); 
			}
			else{
				utils.printVariables(CNETvariables, agent.getFinalState().getStateID()); 
				utils.testAndPrint(CNETconstraints, CNETvariables, agent.getFinalState().getStateID()); 
			}
			frame.repaint();
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
            double numOfVertices1 = utils.getDoubleDigitNumbers(firstline).get(0); 	
            double numOfEdges1 = utils.getDoubleDigitNumbers(firstline).get(1); 	 
            numOfVertices = (int) numOfVertices1; 
            numOfEdges = (int) numOfEdges1; 
            
            ArrayList<Integer> domain = new  ArrayList<Integer>(); 
            for(int i = 0; i< domainSize; i++){
            	domain.add(i); 
            }
            
            for(int i = 0; i < numOfVertices; i++){
            	String line = in.readLine();  
            	ArrayList<Double> arrayLine = utils.getDoubleDigitNumbers(line);  	    
            	double xPos = arrayLine.get(1); 
            	double yPos = arrayLine.get(2);  
            	String name = "V"+i; 
            	Variable newVar = new Variable(name, domain); 
            	CNETvariables.add(newVar); 
            	VarPoint varPoint = new VarPoint(xPos, yPos, newVar); 
            	varPoints.add(varPoint); 
            }
            
            for(int i = numOfVertices+1; i < numOfVertices+numOfEdges +1; i++){
            	String line = in.readLine(); 
            	ArrayList<Double> arrayLine = utils.getDoubleDigitNumbers(line); 
            	double varPos1 = arrayLine.get(0); 
            	double varPos2 = arrayLine.get(1);
            	Variable var1 = CNETvariables.get((int) varPos1);  
            	Variable var2 = CNETvariables.get((int) varPos2);
            	ArrayList<Variable> varsInvolved = new ArrayList<Variable>(); 
            	varsInvolved.add(var1); varsInvolved.add(var2); 
            	
            	NotEqConstraint constraint = new NotEqConstraint(i-(numOfVertices+1), varsInvolved); 
            	CNETconstraints.add(constraint); 	
            	
            	constrSets.add(new ConstrSet(constraint, var1, var2, varPoints.get((int) varPos1).getxPos(), varPoints.get((int) varPos1).getyPos(), varPoints.get((int) varPos2).getxPos(), varPoints.get((int) varPos2).getyPos())); 
            	
            }
            
            System.out.println("init complete"); 
            utils.printVariables(CNETvariables, -1); 
        }
        
        catch(Exception e){
            e.printStackTrace();
        }
	}	
	
	
	
	
	public void addConstraints(){
		utils.constrAdder(CNETconstraints, CNETvariables); 
	}

	public int getSimSpeed() {
		return simSpeed;
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
	
	
	
	
	
	
	

	
	


