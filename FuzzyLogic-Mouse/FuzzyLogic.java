package oving4;

import java.util.Random;

public class FuzzyLogic {
	
	
	int health;
	int oponentHealth; 
	
	
	
	public FuzzyLogic(Mouse mouse, Mouse oponent){
		health = mouse.getHealth(); 
		oponentHealth = oponent.getHealth(); 	
		System.out.print("Health :" + health + " opHealth: " + oponentHealth + "\n"); 
	}
	
	
	public String getAction(){
		 return getfuzzyAction(); 
	}
	

	
	// fuzzification
	public double getVeryHighValue(int health){
		double returnValue; 
		if (health <= 62){ 
			returnValue = 0; }
		else if(health >= 88){
			returnValue = 1;}
		else{ 
			returnValue = (88 - health)/26;   
		}	
		return returnValue; 		
	}
	
	
	public double getVeryLowValue(int health){
		double returnValue; 
		if (health <= 12){ 
			returnValue = 1; }
		else if(health >= 38){
			returnValue = 0;}
		else{ 
			returnValue = (health -12)/26;   
		}	
		return returnValue; 		
	}
	
	
	public double getLowValue(int health){
		double returnValue; 
		if (health < 13){ 
			returnValue = 0; }
		else if(health > 62){
			returnValue = 0;}
		else if (health < 38){ 
			returnValue = (health -12)/26;}   
		else{
			returnValue = (63-health)/26;} 	
		return returnValue; 		
	}
	
	
	public double getHighValue(int health){
		double returnValue; 
		if (health < 38){ 
			returnValue = 0; }
		else if(health > 88){
			returnValue = 0;}
		else if (health < 63){ 
			returnValue = (health -38)/26;}   
		else{
			returnValue = (88-health)/26;} 	
		return returnValue; 
	}
	
	
	// rule evaluation 
	public double rule1(){ 
		double action = AND(getVeryLowValue(health), getVeryLowValue(oponentHealth));  
		return action; 
	}
	
	public double rule2(){ 
		double action = AND(getLowValue(health), getVeryLowValue(oponentHealth));  
		return action; 
	}
	
	public double rule3(){ 
		double action = AND(getHighValue(health), (OR(getVeryLowValue(oponentHealth), OR(getLowValue(oponentHealth), getHighValue(oponentHealth)))));  
		return action; 
	}
	
	public double rule4(){
		double action = getVeryHighValue(health); 
		return action; 
	}
	
	public double rule5(){
		double action = AND(getVeryLowValue(health), (OR(getVeryHighValue(oponentHealth), OR(getLowValue(oponentHealth), getHighValue(oponentHealth)))));  
		return action; 
	}
	
	public double rule6(){
		double action = AND(getLowValue(health), (OR(getVeryHighValue(oponentHealth), getHighValue(oponentHealth))));  
		return action; 
	}
	
	public double rule7(){ 
		double action = AND(getLowValue(health), getLowValue(oponentHealth));  
		return action;
	}
	
	public double rule8(){ 
		double action = AND(getHighValue(health), getVeryHighValue(oponentHealth));  
		return action;
	}
	
	public double AND(double value1 , double value2){
		if(value1 <= value2)
			return value1; 
		else 
			return value2; 
	}
	
	public double OR(double value1 , double value2){
		if(value1 >= value2)
			return value1; 
		else 
			return value2; 
	}
	
	
	public double attack(){
		double attackValue; 
		attackValue = (rule1() + rule2() + rule3() + rule4())/4; 
		System.out.print("attackValue: " + attackValue + "\n"); 
		return attackValue; 
	}
	
	public double escape(){
		double escapeValue; 
		escapeValue = (rule5() + rule6())/2; 
		System.out.print("escapeValue: " + escapeValue + "\n"); 
		return escapeValue; 
	}
	
	public double standby(){
		double waitValue; 
		waitValue = (rule7() + rule8())/2; 
		System.out.print("waitValue: " + waitValue + "\n"); 
		return waitValue; 
	}
	
	
	//Aggregation
	public double getAttackValue(int value){
		double returnValue; 
		if(value <= 35){
			returnValue = 1;} 
		else if(value > 50){ 
			returnValue = 0;} 
		else{
			returnValue = (51-value)/16;  
		}
		if(returnValue > attack()){	
			returnValue = attack(); 
		}
			
		return returnValue; 		
	}
	
	
	public double getEscapeValue(int value){
		double returnValue; 
		if(value >= 75){
			returnValue = 1;} 
		else if(value < 55){ 
			returnValue = 0;} 
		else{
			returnValue = (value-56)/21;  
		}
		if(returnValue > escape()){	
			returnValue = escape(); 
		}
			
		return returnValue; 		
	}
	
	
	public double getStandByValue(int value){
		double returnValue; 
		if(value >= 60 || value <= 40){
			returnValue = 0;} 
		else{
			if(value >= 50){
				returnValue = (60-value)/10;}  
			else{
				returnValue = (value-40)/10; } 
		}
		if(returnValue > standby()){	
			returnValue = standby(); 
		}
			
		return returnValue;
		
	}
	
	// Defuzzification: 
	public double getZenterOfGravity(){
		double cog; 
		double aboveSum = 0; 
		double belowSum = 1; 
		
		for(int i = 0; i < 100; i++){
			aboveSum = aboveSum + (getMax(getAttackValue(i), getStandByValue(i), getEscapeValue(i))*i);  
			belowSum = belowSum + (getMax(getAttackValue(i), getStandByValue(i), getEscapeValue(i))); 
		}
		cog = aboveSum/belowSum; 
		return cog; 
	}
	
	public double getMax(double v1, double v2, double v3){
		if(v1 >= v2 && v1 >= v3)
			return v1; 
		else if(v2 >= v1 && v2 >= v3)
			return v2; 
		else
			return v3; 
	}
		
	public String getfuzzyAction(){
		int cog = (int) getZenterOfGravity(); 
		String action = ""; 
		double attackValue = getAttackValue(cog);  		
		double standbyValue = getStandByValue(cog); 
		double escapeValue = getEscapeValue(cog); 
		double max = getMax(attackValue, standbyValue, escapeValue); 
		if(max == attackValue){
			action = "attack";} 
		else if(max == standbyValue){
			action = "standby"; }
		else if(max == escapeValue){
			action = "escape"; }
		
		return action ; 
	
	}
	
	
	
}


	
	/* The rules: 
	 * 
	 *  rule 1 : attack
	 * if health is very low 
	 * AND oponent.health is very low
	 * then action is attack
	 * 
	 * rule 2 : attack
	 * if health is low 
	 * AND oponent.health is very low
	 * then action is attack
	 * 
	 * rule 3 : attack 
	 * 	if health is high
	 *  AND oponent.health is high OR low OR very low 
	 *  then action is attack
	 *  
	 *  rule 4 : attack 
	 * 	if health is very high 
	 *  then action is attack
	 * 
	 * * rule 5 : escape 
	 * if health is very low 
	 * AND oponent.health is low OR high OR very high 
	 * Then action is escape
	 * 
	 * rule 6 : escape 
	 * if health is low 
	 * AND oponent.health is high OR very high 
	 * Then action is escape 
	 *
	 * rule 7 : standby
	 * if health is low 
	 * AND oponent.health is low 
	 * Then action is standby
	 * 
	 * rule 8 : standby 
	 * if health is high
	 * AND oponent.health is very high 
	 * Then action is standby 
	 * 
	 * 
	 * 
	 * 
	 * 
	
	
	
	
	
	*/

