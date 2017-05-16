package oving2;

import java.util.ArrayList; 

public class HMM {

	static double[][] TransMatrix = new double[2][2];  						//Contains the transition matrix for Rt --> Rt+1    
	static double[][] umTrueObserMatrix = new double[2][2];					//Contains the evidence matrix for umbrella= true;   
	static double[][] umFalseObserMatrix = new double[2][2]; 				//Contains the evidence matrix for umbrella= false; 
	static ArrayList<double[][]> evidences = new ArrayList<double[][]>(); 	//Stores the evidence matrixes in the correct sequence 
	static double[] rainPropAtStart = new double[2];						//Contains the probability for rain/not rain at t= 0;(50/50)   
	static double[] rainPropAtfinal = new double[2];						//Init backward vector(1/1)
	static int daysOfInterest;												//The number of days one which to look forward/back
	static String umbrellaEvidenceString;									//String indicating the sequence of days were the umbrella was present
	
	static String taskToRun = "c2"; 										//users choice about which task to run. "b1" , "b2" , "c1" or "c2"
	

	
	public static void main(String[] args){
		
		if(taskToRun.equals("b1")){				//Decides which task to run, how many days forward/backwards and the evidence string
			umbrellaEvidenceString = "TT"; 
			daysOfInterest = 2; 
		}
		else if(taskToRun.equals("b2")){
			umbrellaEvidenceString = "TTFTT"; 
			daysOfInterest = 5; 
		}
		else if (taskToRun.equals("c1")){
			umbrellaEvidenceString = "TT"; 
			daysOfInterest = 2; 
		}
		else if (taskToRun.equals("c2")){
			umbrellaEvidenceString = "TTFTT"; 
			daysOfInterest = 5; 
		}	
		
		generateConstantMatrices(); 				
		makeEvidencelist(umbrellaEvidenceString); 	
		
		if(taskToRun.equals("b1") || taskToRun.equals("b2")){
			double[] rainMatrix = rainPropAtStart; 
			for(int day = 0; day < daysOfInterest; day++){   	//Iterates forward and runs the FORWARD estimation for dysOfInterst number of days. 
				rainMatrix = FORWARD(rainMatrix, day); 
				int printDay = day +1; 
				System.out.print("day: " + printDay + "\n" + rainMatrix[0] + "\n" + rainMatrix[1] + "\n"+ "\n"); 
			}
		}
		if(taskToRun.equals("c1") || taskToRun.equals("c2")){
			double[] rainMatrixBack = rainPropAtfinal; 
				for(int day = daysOfInterest; day > 0; day--){  	//Iterates backwards and runs the BACKWARD estimation for dysOfInterst number of days. 
					rainMatrixBack = BACKWARDS(rainMatrixBack, day); 
					int printDay = day ; 
					System.out.print("day: " + printDay + "\n" + rainMatrixBack[0] + "\n" + rainMatrixBack[1] + "\n"+ "\n"); 	
				}
		}
	}
	
	
	// The forward method: returns Ft+1 = a * (evidence at time t) * (TransMatrix)T * Ft
	public static double[] FORWARD(double[] lastMatrix, int time){ 
		double[][] T = Matrix.transpose(TransMatrix);
		double[][] evidence = evidences.get(time); 
		
		double[] returnMatrix = Matrix.multiplySingle(Matrix.multiply(evidence, T), lastMatrix);
		
		double factoringConstant = returnMatrix[0] + returnMatrix[1]; 
		returnMatrix[0] = returnMatrix[0] / factoringConstant; 
		returnMatrix[1] = returnMatrix[1] / factoringConstant; 
		
		return returnMatrix; 
	}
	
	
	// The Backward method: returns Bt-1 = a * (evidence at time t-1) * TransMatrix * Bt
	public static double[] BACKWARDS(double[] lastMatrix, int time){ 
		double[][] evidence =  evidences.get(time-1);
		
		double[] returnMatrix = Matrix.multiplySingle(Matrix.multiply(evidence, TransMatrix), lastMatrix);
		
		double factoringConstant = returnMatrix[0] + returnMatrix[1]; 
		returnMatrix[0] = returnMatrix[0] / factoringConstant; 
		returnMatrix[1] = returnMatrix[1] / factoringConstant;
		
		return returnMatrix; 
	}
	
	
	
	//Fills the evidence list with evidence matrix at day i.  
	public static void makeEvidencelist(String umbrellaEvidenceString){
		for(int i = 0; i < umbrellaEvidenceString.length(); i++){
			if(umbrellaEvidenceString.charAt(i) == 'T'){
				evidences.add(umTrueObserMatrix); 
			}
			else if(umbrellaEvidenceString.charAt(i) == 'F'){
				evidences.add(umFalseObserMatrix);
			}
		}
	}
	

	//Fllls the matrix with constants
	public static void generateConstantMatrices(){
		TransMatrix[0][0] = 0.7; TransMatrix[0][1] = 0.3;
		TransMatrix[1][0] = 0.3; TransMatrix[1][1] = 0.7;  
		
		umTrueObserMatrix[0][0] = 0.9; umTrueObserMatrix[0][1] = 0.0;
		umTrueObserMatrix[1][0] = 0.0; umTrueObserMatrix[1][1] = 0.2;
		
		umFalseObserMatrix[0][0] = 0.1; umFalseObserMatrix[0][1] = 0.0;
		umFalseObserMatrix[1][0] = 0.0; umFalseObserMatrix[1][1] = 0.8; 
		
		rainPropAtStart[0] = 0.5;
		rainPropAtStart[1] = 0.5;  
		
		rainPropAtfinal[0] = 1.0;
		rainPropAtfinal[1] = 1.0; 
	}
     
} 
       


