package project1;

import java.awt.Graphics;

import javax.swing.JComponent;

@SuppressWarnings("serial")
public class GraphicCSP extends JComponent {
	
	
	public void paint(Graphics g){
		System.out.println("Superclass GraphicCSP reached1"); 
	}

	public void setStateID(int stateID) {
		System.out.println("Superclass GraphicCSP reached2"); 
		
	}
	

}
