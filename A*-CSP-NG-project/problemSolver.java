package project1;

import java.util.ArrayList;
import java.util.Scanner;



public class problemSolver { 
	
 	
	public static void main(String[] args){
		System.out.println("*****        Welcome to the A*GAC solving system        *******");
		System.out.println("*                                                             *");
		System.out.println("* Type for: Nav: nav  'filename'  'extra'                     *"); 
		System.out.println("*            VC: vc   'filename'  'extra'                     *"); 
		System.out.println("*         NonoG: nono 'filename'  'extra'                     *"); 
		System.out.println("*                                                             *");
		System.out.println("* extra: all        : runs all nav searchMethods              *");
		System.out.println("*        dfs        : runs DFS instead of A*                  *");
		System.out.println("*        bfs        : runs BFS instead of A*                  *");
		System.out.println("*        djikstra   : runs djikstra instead of A*             *");
		System.out.println("*        euc        : runs nav with euc-distance              *");
		System.out.println("*        simspeed:x : set the delay for each node pop         *");
		System.out.println("*        k:x        : vc-domainSize                           *");
		System.out.println("*        addc       : Opportunity to add constraints          *");
		System.out.println("*        printDFL   : prints the domainfilteringLoop in s0    *");
		System.out.println("*                                                             *");
		System.out.println("");
		
	
		Boolean running = true; 
		String input ="";  
		
		while(running){
			
			Toolbox utils = new Toolbox(); 
			String searchMethod = "Astar"; 
			int simSpeed = -1;
			int VCdomainSize = 4;
			boolean addC = false; 
			boolean printDFL = false; 
			
			System.out.println("Please enter command for next task: "); 
			@SuppressWarnings("resource")
			Scanner scan = new Scanner(System.in);
			input = scan.nextLine();
			
			ArrayList<String> inputList = utils.getWordInput(input); 
			
			String typeOfProblem ="";  
			String filePath = "";  
			String fileName = "";
			String navHeurType = "manhatten"; 
			
			if(inputList.size() < 2){
				System.out.println("Not a vaild command..."); 
			}
			else{
			
			
				String s = inputList.remove(0); 
				if(s.equalsIgnoreCase("nav")){
					typeOfProblem = "NAV";
					filePath = "C:\\java\\aiproject1\\nav\\"; 
				}
				else if(s.equalsIgnoreCase("vc")){
					typeOfProblem = "VC";
					filePath = "C:\\java\\aiproject1\\vc\\"; 
				}
				else if(s.equalsIgnoreCase("nono")){
					typeOfProblem = "NG"; 
					filePath = "C:\\java\\aiproject1\\nono\\"; 
				}
						
				String s2 = inputList.remove(0);  
				fileName = s2; 
				filePath = filePath + fileName + ".txt"; 
				
				for(int i = 0; i < inputList.size(); i++){
					String s3 = inputList.get(i); 
					
					if(s3.equalsIgnoreCase("dfs")){
						searchMethod = "DFS"; 	
					}
					else if(s3.equalsIgnoreCase("bfs")){
						searchMethod = "BFS"; 	
					}
					else if(s3.equalsIgnoreCase("djikstra")){
						searchMethod = "Djikstra"; 	
					}
					else if(s3.length() > 2 && s3.charAt(0) == 'k' && s3.charAt(1) == ':'){  
							VCdomainSize = utils.getDigitNumbers(s3.substring(2, s3.length())).get(0); 
					}
					else if(s3.length() > 8 && s3.substring(0, 9).equalsIgnoreCase("simspeed:")){  
						simSpeed = utils.getDigitNumbers(s3.substring(8, s3.length())).get(0); 
					}
					else if(s3.equalsIgnoreCase("all")){
						typeOfProblem = "allNAV"; 
					}
					else if(s3.equalsIgnoreCase("euc")){
						navHeurType = "euc"; 
					}
					else if(s3.equalsIgnoreCase("addc")){
						addC = true;  
					}
					else if(s3.equalsIgnoreCase("printDFL")){
						printDFL = true;  
					}
					
				}
			
				args = new String[1]; 	
				args[0]  = filePath; 
				
				if(typeOfProblem.equals("NG")){
					NGproblem problem = new NGproblem(); 
					problem.inputReader(args); 
					if(addC){
						problem.addConstraints(); 
					}
					problem.setPrintDFL(printDFL); 
					problem.setSimSpeed(simSpeed);
					problem.setSearchMethod(searchMethod); 
					problem.run(); 
				}
				
				else if(typeOfProblem.equals("VC")){
					VCproblem problem = new VCproblem(VCdomainSize); 
					problem.inputReader(args);
					if(addC){
						problem.addConstraints(); 
					}
					problem.setPrintDFL(printDFL);
					problem.setSimSpeed(simSpeed);
					problem.setSearchMethod(searchMethod); 
					problem.run(); 		
				}
				
				else if(typeOfProblem.equals("NAV")){
					NAVproblem problem = new NAVproblem(); 
					problem.setHeurType(navHeurType); 
					problem.inputReader(args);
					problem.setSimSpeed(simSpeed); 
					problem.setSearchMethod(searchMethod); 
					problem.run(); 	
				}
				
				else if(typeOfProblem.equals("allNAV")){
					NAVproblem  problemAstar = new NAVproblem(); 
					problemAstar.setHeurType(navHeurType);
					problemAstar.inputReader(args); 
					problemAstar.setSimSpeed(simSpeed); 
					problemAstar.setSearchMethod("Astar"); 
					problemAstar.run(); 
					
					NAVproblem  problembfs = new NAVproblem(); 
					problembfs.setHeurType(navHeurType);
					problembfs.inputReader(args); 
					problembfs.setSimSpeed(simSpeed); 
					problembfs.setSearchMethod("BFS"); 
					problembfs.run();
					
					NAVproblem  problemdfs = new NAVproblem(); 
					problembfs.setHeurType(navHeurType);
					problemdfs.inputReader(args); 
					problemdfs.setSimSpeed(simSpeed); 
					problemdfs.setSearchMethod("DFS"); 
					problemdfs.run();
					
					System.out.println();
					System.out.println();
					System.out.println("       Overall Statistics:      ");
					utils.printSpaceAndLine(); 
					problemAstar.agent.printStats(); 
					problembfs.agent.printStats(); 
					problemdfs.agent.printStats(); 	
				}
				
				utils.printSpaceAndLine();  
				System.out.println(); 
			}
		}		
	}
	
	
	

	

}
