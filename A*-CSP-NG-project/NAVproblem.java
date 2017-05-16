package project1;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.InputStreamReader;
import java.util.ArrayList;


import javax.swing.JFrame;


public class NAVproblem {
	
	private ArrayList<Gridstate> grid = new ArrayList<Gridstate>(); 
	private int panelXsize; 
	private int panelYsize; 
	private Gridstate start;  
	private int simSpeed = 100;
	private String searchMethod = "Astar"; 
	public AstarAgent agent; 
	private String heurType = "manhatten"; 

	
	//run a navigation problem.Set up graphics, agent and start state of type grid. 
	public void run(){
		
		if(grid.size() > 0){
		
			JFrame frame = new JFrame();
		    frame.add(new GraphicBoard(grid.get(0), panelXsize, panelYsize));
		    frame.setSize(panelXsize+16, panelYsize +38);
		    frame.setVisible(true);
		    
		    System.out.println("Creating A* agent and starting search"); 
			System.out.println(); 
		    agent = new AstarAgent(frame, start); 
		    agent.setSimSpeed(simSpeed); 
		    agent.setShowSolutionPath(true); 
		    agent.runAgent(searchMethod); 
		    frame.repaint(); 
		}
		
	}
	
	
	// read the input file and create all the grid states
	public void inputReader(String[] args){
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
            
           
            ArrayList<int[]> obstacles = new ArrayList<int[]>(); 
            int xSize, ySize, xStartPos, yStartPos, xGoalPos, yGoalPos;    
       
            Toolbox utils = new Toolbox(); 
            String line1= in.readLine(); 
            ArrayList<Integer> line1Nums = utils.getDigitNumbers(line1);   
            xSize = line1Nums.get(0);    
            ySize = line1Nums.get(1);
            panelXsize = xSize*22;  
            panelYsize = ySize*22;  
           
            String line2 =in.readLine(); 
            ArrayList<Integer> line2Nums = utils.getDigitNumbers(line2);    
            xStartPos = line2Nums.get(0);   
            yStartPos = line2Nums.get(1);
            xGoalPos = line2Nums.get(2);
            yGoalPos = line2Nums.get(3);
         
            String nextline = in.readLine();   
        
            while(nextline != null && nextline.length() > 2){
            	  
            	ArrayList<Integer> obstcleLine = utils.getDigitNumbers(nextline);  	
            
            	for(int x = obstcleLine.get(0); x < (obstcleLine.get(0) + obstcleLine.get(2)); x++){
            		for (int y = obstcleLine.get(1); y < (obstcleLine.get(1) + obstcleLine.get(3)); y++){
            			int[] obstacleCordinate = new int[2]; 
            			obstacleCordinate[0] = x; obstacleCordinate[1] = y; 
            			obstacles.add(obstacleCordinate); 
            		}
            	} 
            	nextline = in.readLine();
            } 
  
            for(int y = 0; y < ySize; y++){
            	for(int x = 0; x < xSize; x++){
            		Gridstate cell = new Gridstate(x,y, heurType); 
            		grid.add(cell); 	
            	}
            }
          
            for(int i = 0; i < obstacles.size(); i++){
            	int[] obstacle = obstacles.get(i);  
            	int listPos = (obstacle[1]*xSize) + obstacle[0]; 
            	grid.get(listPos).setObstacle();  	
            }
            
            int startListPos = (yStartPos*xSize)+ xStartPos; 
            grid.get(startListPos).setStartState(); 
            start = grid.get(startListPos); 
            int goalListPos = (yGoalPos*xSize)+ xGoalPos; 
            grid.get(goalListPos).setIsGoalState();  
            
            for(int i = 0; i < grid.size(); i++){
            	grid.get(i).assaignRelations(xSize, ySize, grid); 
            	grid.get(i).setGoalState(grid.get(goalListPos)); 
            }
            
            System.out.println("Init complete");  
            
            utils.printSpaceAndLine(); 
        	System.out.println();
            
         
        }
        catch(Exception e){
            e.printStackTrace();
        }
	}

	
	
	

	public void setSearchMethod(String typeOfsearch) {
		this.searchMethod = typeOfsearch;
	}
	
	public void setSimSpeed(int simSpeed2) {
		if(simSpeed2 > 0){
			this.simSpeed = simSpeed2;
		}
	}

	public String getHeurType() {
		return heurType;
	}

	public void setHeurType(String heurType) {
		this.heurType = heurType;
	}
	
	
	
	
}
        
        
	

	

	
	


