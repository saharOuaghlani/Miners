package main;

import jade.core.Agent;

public class Explorer  extends Agent {
	private static final long serialVersionUID = 7043315975215984640L;
	
	private String service;
	int x0;
	int y0;
	int regionSize;
	int capacity;
	
	
	public Explorer(){super();}
	
	protected void setup(){		
	// Get arguments
	Object[] args = getArguments();
	
	//System.out.println("@"+ this.getLocalName()+" "+args.length);
	//Verify arguments
	if(args == null) System.out.println("args are null");
	else{
		x0= Integer.parseInt( args[0].toString());
		y0= Integer.parseInt( args[1].toString());
		regionSize= Integer.parseInt( args[2].toString());
		capacity= Integer.parseInt( args[3].toString());
		
		System.out.println("@"+this.getLocalName()+":"+x0+", "+y0+", "+regionSize+", "+ capacity);
	}
	}
}
