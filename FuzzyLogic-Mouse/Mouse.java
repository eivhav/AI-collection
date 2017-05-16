package oving4;

import java.util.ArrayList;
import java.util.Random;

public class Mouse {
	
	int number; 
	int health; 
	int speed; 
	int direction;
	int xPos; 
	int yPos; 
	Mouse oponment; 
	boolean isAlive = true; 
	double pi = Math.PI; 
 
	

	
	public void moveByDirection(int stepValue, boolean turn){
		if((xPos < 30 || xPos > 585 || yPos < 30 || yPos  > 585)){
			direction = direction - 180; 
			if(xPos < 30)
				xPos = 45; 
			if(xPos > 585)
				xPos = 570; 
			if(yPos < 30)
				yPos = 45; 
			if(yPos > 585)
				yPos = 570; 
		}
		if(direction > 360)
			direction = direction - 360; 
		else if(direction < 0)
			direction = direction + 360; 
		if(turn){
			Random rnd = new Random(); 
			int dir = rnd.nextInt(40); 
			direction = direction + dir -20; 
		}
		
		int dx = (int) (stepValue * ((Math.sin((getDirection() * 2 *pi/360))))); 
		int dy = (int) (stepValue * ((Math.cos((getDirection() * 2 *pi/360)))));
		xPos = xPos + dx; 
		yPos = yPos - dy; 
	
	}
	
	public boolean isInSeight(int seightAngle, int sightDistance, Mouse otherMouse){ // 30, 70 
		int mouseDirection;  
		int dx = otherMouse.getxPos() - xPos; 
		int dy = otherMouse.getyPos() - yPos; 
		double distance = Math.sqrt((dx*dx) + (dy*dy));  
		mouseDirection = (int) (360 * Math.asin(((double) dx)/distance)/(2 * pi) ); 
		if(yPos < otherMouse.getyPos()){
			mouseDirection = 180 - mouseDirection; }
		if(mouseDirection < 0){
			mouseDirection = mouseDirection + 360; }
		if(mouseDirection > 360){
			mouseDirection = mouseDirection - 360; 
		}
		
		if(sightDistance > calculateDistance(otherMouse) && mouseDirection <= (direction + (seightAngle/2)) && mouseDirection >= (direction - (seightAngle/2))){
	
			return true;}
		else{ 
			return false;} 
	}
		

	
	public void findAndSetOponment(ArrayList<Mouse> mouses, int range, int viewAngle){
		for(int i = 0; i < mouses.size(); i++){ 
			if(this != mouses.get(i) && mouses.get(i).getOponment() == null && isInSeight(viewAngle, range, mouses.get(i))){
				oponment = mouses.get(i); 
				mouses.get(i).setOponment(this); 
				break; 
			}
		}
	}
	
	
	public double calculateDistance(Mouse mouse){
		double distX = (xPos - mouse.getxPos());    
		double distY = (yPos - mouse.getyPos()); 
		double distance = Math.sqrt((distX * distX) + (distY * distY)); 
		return distance; 
	}
	

	public void followMouse(Mouse mouse, int stepspeed){
		int dx = mouse.getxPos() - xPos; 
		int dy = mouse.getyPos() - yPos; 
		double distance = Math.sqrt((dx*dx) + (dy*dy));  
		direction = (int) (360 * Math.asin(((double) dx)/distance)/(2 * pi) ); 
		if(yPos < mouse.getyPos())
			direction = 180 - direction ; 
		moveByDirection(stepspeed, false); 
	}
	
	public void attack(){
		Random rnd = new Random(); 
		int injury = rnd.nextInt(10) +7; 
		oponment.setHealth(oponment.getHealth() - injury); 
		int distance = (int) calculateDistance(oponment); 
		followMouse(oponment, distance - 5); 
		followMouse(oponment, (5 - distance)/2); 
	}
	
	
	public void flee(int range){
		direction = direction - 180; 
		//moveByDirection((int)(range * 1.1), false); 
	}
	
	
	public void ignore(int stepvalue){
		moveByDirection(stepvalue, false); 
	}
	
	
	public void turnToOpnent(Mouse otherMouse){
		int mouseDirection;  
		int dx = otherMouse.getxPos() - xPos; 
		int dy = otherMouse.getyPos() - yPos; 
		double distance = Math.sqrt((dx*dx) + (dy*dy));  
		mouseDirection = (int) (360 * Math.asin(((double) dx)/distance)/(2 * pi) ); 
		if(yPos < otherMouse.getyPos()){
			mouseDirection = 180 - mouseDirection; }
		if(mouseDirection < 0){
			mouseDirection = mouseDirection + 360; }
		if(mouseDirection > 360){
			mouseDirection = mouseDirection - 360; 
		}
		direction = mouseDirection; 
		
	}
	
	
	public int getNumber() {
		return number;
	}

	public void setNumber(int number) {
		this.number = number;
	}

	public int getHealth() {
		return health;
	}

	public void setHealth(int health) {
		this.health = health;
	}

	public int getSpeed() {
		return speed;
	}

	public void setSpeed(int speed) {
		this.speed = speed;
	}

	public int getDirection() {
		return direction; 
	}

	public void setDirection(int direction) {
		this.direction = direction;
	}

	public int getxPos() {
		return xPos;
	}

	public void setxPos(int xPos) {
		this.xPos = xPos;
	}

	public int getyPos() {
		return yPos;
	}

	public void setyPos(int yPos) {
		this.yPos = yPos;
	} 
	 
	public boolean isAlive() {
		return isAlive;
	}

	public void setAlive(boolean isAlive) {
		this.isAlive = isAlive;
	}
	
	
	public Mouse getOponment() {
		return oponment;
	}

	public void setOponment(Mouse oponment) {
		this.oponment = oponment;
	}


		
	
}
