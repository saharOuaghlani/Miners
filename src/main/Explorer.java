package main;

import jade.core.Agent;
import jade.core.behaviours.ReceiverBehaviour;
import jade.lang.acl.ACLMessage;

import java.util.Arrays;
import java.util.List;

import jade.core.AID;
public class Explorer  extends Agent {
	private static final long serialVersionUID = 7043315975215984640L;
	
	private int x0;
	private int y0;
	private int regionSize;
	private int capacity;
	private Position position, entropot;
	private int[][] matrix;
	private int[][] discovery;
	private String agentName;
	private boolean dispo;
	public Explorer(){super(); System.out.println("yooo");}
	ReceiverBehaviour receiverBehaviour;
	private int DELAy;
	
	private boolean direction= true;
	
	protected void setup(){		
	// Get arguments
	Object[] args = getArguments();
	dispo = false;

	System.out.println("1111111111");

	if(args == null) System.out.println("args are null");
	else{
		
		System.out.println("3ammi");
		x0= Integer.parseInt( args[0].toString());
		y0= Integer.parseInt( args[1].toString());
		regionSize= Integer.parseInt( args[2].toString());
		capacity= (((PlayGround)args[4]).AGENTCAPACITY);
		
		position = (Position)args[3];
		entropot = new Position(((Position)args[3]).getX(), ((Position)args[3]).getY());
		DELAy= ((PlayGround)args[4]).DELAY;
		matrix = ((PlayGround)args[4]).matrix;
		discovery = ((PlayGround)args[4]).discovery;
		agentName = args[5].toString();
	
		System.out.println("@@@"+ position);
		while(dispo == false)
		{
			explorerMatriceRessources();
			try {
				wait(DELAy);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}}
	
	private void explorerMatriceRessources() {	
		if(direction )
		{
			position.setX(position.getX()-1);
			if(position.getX()== x0) direction = !direction;
		} 
		
		else
		{
			position.setX(position.getX()+1);
			if(position.getX()== x0+ regionSize) direction = !direction;
		}
		System.out.println("@@@"+ position);

	}
}
