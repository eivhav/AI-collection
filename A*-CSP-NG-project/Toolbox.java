package project1;

import java.util.ArrayList;
import java.util.Scanner;


/*
 * A collection of methods used for printing and reading input
 * 
 * 
 */


public class Toolbox {
	
	public Toolbox(){
		
	}
	
	
	public ArrayList<Integer> getDigitNumbers(String s){
		ArrayList<Integer> digits = new ArrayList<Integer>();   
		ArrayList<Integer> results = new ArrayList<Integer>(); 
		int number = 0;   
		boolean numFound = false; 
		
		for(int i = 0;  i < s.length(); i++){
			char c = s.charAt(i); 
			
			if(!Character.isDigit(c) && !numFound){ 
			}
			else if(Character.isDigit(c)){
				digits.add(Character.getNumericValue(c)); 
				numFound = true; 
			}
			if((!Character.isDigit(c) || i == (s.length()-1)) && numFound){  
				int k = digits.size()-1; 
				for(int j = 0; j < digits.size(); j++){
					number = (int) (number + (digits.get(j)*(Math.pow(10,k))));  
					k--; 
				}
				results.add(number); 
				numFound = false; 
				digits = new ArrayList<Integer>(); 	
				number = 0; 
			}
		}	
		return results; 	
				
	}
	
	
	public ArrayList<Double> getDoubleDigitNumbers(String s){
		ArrayList<Integer> digits = new ArrayList<Integer>();   
		ArrayList<Integer> decdigits = new ArrayList<Integer>();  
		ArrayList<Double> results = new ArrayList<Double>(); 
		double number = 0;    
		boolean numFound = false; 
		boolean decFound = false; 
		boolean negative = false; 
		s = s+" "; 
		
		for(int i = 0;  i < s.length(); i++){
			char c = s.charAt(i); 
			
			if(!Character.isDigit(c) && !numFound && c != '.' && c!= '-'){ 
			}
			else if(c == '-'){
				negative = true; 	
			}
			else if(c == '.' && numFound){
				decFound = true; 
			}
			else if(Character.isDigit(c) && !decFound){
				digits.add(Character.getNumericValue(c)); 
				numFound = true; 
			}
			else if(Character.isDigit(c) && decFound){
				decdigits.add(Character.getNumericValue(c));  
			}
			
			
			else if(!Character.isDigit(c) && numFound){  
				int k = digits.size()-1; 
				for(int j = 0; j < digits.size(); j++){
					number = (int) (number + (digits.get(j)*(Math.pow(10,k))));  
					k--; 
				}
				if(decFound){ 
					for(int j= 0; j < decdigits.size(); j++){
						number = (number + (decdigits.get(j)*(Math.pow(10,-j-1))));  
					}
					
				}
				
				if(negative){
					number = number*(-1);  
				}
				results.add(number); 
				numFound = false; 
				decFound = false; 
				negative = false; 
				digits = new ArrayList<Integer>();
				decdigits = new ArrayList<Integer>();
				number = 0; 
			}
		}	
		
		return results; 
	}
	
	
	
	public ArrayList<String> getWordInput(String inputLine){
		ArrayList<String> rList = new ArrayList<String>(); 
		String word = ""; 
		boolean wordFound = false; 
		for(int i = 0; i < inputLine.length(); i++){
			char c = inputLine.charAt(i); 
			if(c == ' ' && !wordFound){
			}
			else if(c != ' '){	
				word = word + c; 
				wordFound = true; 
			}
			if((c == ' ' || i == inputLine.length()-1) && wordFound){ 
				rList.add(word); 
				
				word = ""; 
				wordFound = false; 
			}
			
		}
		
		return rList; 
		
		
	}
	
	
	
	public void printVariables(ArrayList<Variable> vars, int stateID){
		System.out.println("Variables:");
		for(int i = 0; i < vars.size(); i++){
			Variable var = vars.get(i); 
			ArrayList<Integer> dom = new ArrayList<Integer>(); 
			if(stateID < 0){
				 dom = var.getOrgDomain();
				 System.out.println(var.getName() + " " + dom);	 
			}
			else {
				dom = var.getDomain(stateID); 
				System.out.print(var.getName() + " " + dom + " ");
				if(var.getName().length() == 2){
					System.out.print("  ");
				}
				else if((var.getName().length() == 3)){
					System.out.print(" ");
				}
					
				double mod = ((double) (i+1) )/5; 
				int modint = (int) mod; 
				if((double) modint == mod){
					System.out.print("\n");
				}
			}	
		}
		printSpaceAndLine(); 
	}
	
	
	
	public void testAndPrint(ArrayList<Constraint> constr, ArrayList<Variable> vars,  int goalStateID){
		
		System.out.println("        Testing CSP result       "); 
		System.out.println(); 
		int constrSatisfied = 0; 
		int constrFailed = 0; 
		for(int i = 0; i < constr.size(); i++){
			Constraint c = constr.get(i); 
			for(int j = 0; j < c.getVarsInvolved().size(); j++){
				c.getVarsInvolved().get(j).setTestValue(c.getVarsInvolved().get(j).getDomain(goalStateID).get(0)); 
			}
			boolean test = c.testConstraint(); 
			//System.out.println("Testing constr:" +constr.get(i).getNumber() +":" + test); 
			if(test){
				constrSatisfied++; 
			}
			else{ 
				constrFailed++; 			
			}
		}
		System.out.println("Testing constraints: ");
		System.out.println("  Result: Satisfied: " + constrSatisfied);
		System.out.println("          Failed   : " + constrFailed);
		System.out.println();
		
		int varDetermined = 0; 
		int varNotDetermined = 0; 
		for(int i = 0; i < vars.size(); i++){
			if(vars.get(i).getDomain(goalStateID).size() == 1){
				varDetermined++; 
			}
			else {
				varNotDetermined++; //determined
			}
		}
		System.out.println("Testing variables: ");
		System.out.println("  Result: Variables determined    : " + varDetermined);
		System.out.println("          Variables not determined: " + varNotDetermined);
	
		printSpaceAndLine(); 
			
	}
	
	
	public void printNGdrawing(ArrayList<Variable> vars, int stateID, int[] dim){
		for(int i = 0; i < (dim[1]); i++){
			Variable var = vars.get(i); 
			ArrayList<Integer> dom = var.getDomain(stateID); 
			System.out.println(getNGStringValue(dom.get(0), dim[0]));  
		}
		System.out.println( ); 
	}
	
	
	 

	public String getNGStringValue(int number, int dimSize){
		String s = Integer.toBinaryString(number); 
		while(s.length() < dimSize){
			String p = "0";
			s = p + s; 
		}
		String result = ""; 
		for(int i = 0; i < s.length(); i++){
			if(s.charAt(i) == '1'){
				result = result + 'X' + " ";
			}
			else {
				result = result + " " + " ";
			}
			 
		}
		
		return result; 	
	}
	
	
	public void printVarsNames(ArrayList<Variable> vars){
		System.out.println("Variables:");
		for(int i = 0; i < vars.size(); i++){
			Variable var = vars.get(i); 
				System.out.print(var.getName() + " ");
				if(i < 10){
					System.out.print(" ");
				}
				if(i < 100){
					System.out.print(" ");
				}
					
					
				double mod = ((double) (i+1) )/5; 
				int modint = (int) mod; 
				if((double) modint == mod){
					System.out.print("\n");
				}
		}
		System.out.println();
	}
	
		
			
			
	
	public void constrAdder(ArrayList<Constraint> constrList, ArrayList<Variable> allVars){
		
		System.out.println("Please type inn constraints"); 
		System.out.println("To se varaibles, type: showVars"); 
		System.out.println("When finnished, type: done");
		System.out.println();
		
		@SuppressWarnings("resource")
		Scanner scan = new Scanner(System.in);
		String input = scan.nextLine(); 
		
		while(!input.equalsIgnoreCase("done")){
			
			if(input.equalsIgnoreCase("showvars")){
				printVarsNames(allVars);
			}	 
			else {
				CmplxConstraint newConstr = new CmplxConstraint(constrList.size(), input, allVars); 
				constrList.add(newConstr); 
				System.out.println("Constr:"+newConstr.getNumber() + " added. Desc: "+ newConstr.getDescription() + "  Vars: " +newConstr.getVarsInvolvedByNames());
			}
			
			input = scan.nextLine();
			
		}	
		
	}
	
	public void printSpaceAndLine(){
		System.out.println();
		System.out.println("--------------------------------------------------"); 	
	}
	
			
			
	
	
	
	

}
