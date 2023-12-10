package main;
import jade.core.Agent;
import jade.lang.acl.ACLMessage;
import main.ReceiverBehaviour;
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
	private PlayGround myPlayGround;
	private int[][] matrix;
	private int[][] discovery;
	private String agentName;
	private boolean dispo;
	ReceiverBehaviour receiverBehaviour;

	public Explorer(){super();};	

	public Position getEntropot(){
		return this.entropot;
	}

	public boolean getStatus(){
		return this.dispo;
	}

	public void setStatus(boolean dispo){
		this.dispo = dispo;
	}
	public Position getPosition(){
		return this.position;
	}

	public String getAgentname(){
		return this.agentName;
	}


	protected void setup(){		
	// Get arguments
	Object[] args = getArguments();
	dispo = false;

	if(args == null) System.out.println("args are null");
	else{
		x0= Integer.parseInt( args[0].toString());
		y0= Integer.parseInt( args[1].toString());
		regionSize= Integer.parseInt( args[2].toString());
		capacity= (((PlayGround)args[4]).AGENTCAPACITY);
		position = new Position(((Position)args[3]).getX(), ((Position)args[3]).getY());
		entropot = new Position(((Position)args[3]).getX(), ((Position)args[3]).getY());
		matrix = (((PlayGround)args[4]).matrix);
		discovery = (((PlayGround)args[4]).discovery);
		agentName = args[5].toString();
		System.out.println("@"+this.getLocalName()+":"+x0+", "+y0+", "+regionSize+", "+ capacity);
	}
	ReceiverBehaviour receiverBehaviour = new ReceiverBehaviour(this);
	while(dispo == false)
	{
		explorerMatriceRessources();
    	String message = receiverBehaviour.getReceivedContent();

    	if (message != null && message.equals("Region explored")) {
			moveAgent(position, entropot);
		}
	}


	while(true)
	{
		ACLMessage msg = blockingReceive();//récuperer message reçu
		String message = msg.toString();
		
		if (message.equals("Well done!")){//Tous les ressources sont récupérées
			System.exit(0);}
		else{
			
			// Split the message using the comma as a delimiter
			String[] coordinates = message.split(",");
			Position posR = new Position(Integer.parseInt(coordinates[0]), Integer.parseInt(coordinates[1]));

			moveAgent(entropot, posR);
			if (matrix[posR.getX()][posR.getY()] <= capacity) {
				int charge = matrix[posR.getX()][posR.getY()];
				matrix[posR.getX()][posR.getY()] = 0;
				discovery[posR.getX()][posR.getY()] = -1;
				moveAgent(posR, entropot);
				discovery[entropot.getX()][entropot.getY()] += charge;		}
			else{
					matrix[posR.getX()][posR.getY()] -= capacity;
					discovery[posR.getX()][posR.getY()] = -1;
					moveAgent(posR, entropot);
					discovery[entropot.getX()][entropot.getY()] += capacity;		
				}
			}

	}
	}
	
	private void sendMessage(String to, Position pos) {
		ACLMessage msg = new ACLMessage(ACLMessage.INFORM);
		msg.addReceiver(new AID(to,AID.ISLOCALNAME));
		msg.setContent(pos.getX()+","+pos.getY());   // message de la forme posX,posY
		send(msg);
			}
	
	private void AppelAutresAgents(Position ressourcePosition, String agentName){
		List<String> agentList = Arrays.asList("ag1", "ag2", "ag3", "ag4");		
		for (String agent : agentList) {
			if (!agent.equals(agentName)) {
				sendMessage(agent, ressourcePosition);
			}
		}
	}

	private static void moveAgent(Position currentPosition, Position endPosition) {
		int currentX = currentPosition.getX();
		int currentY = currentPosition.getY();
		int endX = endPosition.getX();
		int endY = endPosition.getY();
	
		while (currentX != endX || currentY != endY) {
			// Déplace horizontalement si nécessaire
			if (currentX < endX) {
				currentX++;
			} else if (currentX > endX) {
				currentX--;
			}
			currentPosition.setX(currentX);		
			System.out.println("Position actuelle : (" + currentX + ", " + currentY + ")");
	
			// Si l'agent a atteint la position finale en x, il ne se déplace plus en y
			if (currentX == endX && currentY == endY) {
				break;
			}
	
			// Déplace verticalement si nécessaire
			if (currentY < endY) {
				currentY++;
			} else if (currentY > endY) {
				currentY--;
			}
			currentPosition.setY(currentY);	
			System.out.println("Position actuelle : (" + currentX + ", " + currentY + ")");
		}
	}
	

	private void explorerMatriceRessources() {	
	
		int nouvellePositionI = -1;
		int nouvellePositionJ = -1;
		do
		{
			// Générer un nombre aléatoire pour la direction du déplacement
			int direction = (int) (Math.random() * 4); 
			
			if( direction == 0)// Bas
				nouvellePositionJ = position.getY()-1;
			else if( direction ==1 )	// Haut
				nouvellePositionJ = position.getY() + 1;
			else if( direction ==2 ) // Gauche
				nouvellePositionI = position.getX()-1;
			else // Droite
				nouvellePositionI = position.getX() + 1;
		
		}while(nouvellePositionI<x0 || nouvellePositionJ<y0 || nouvellePositionI == x0 + regionSize || nouvellePositionJ == y0 + regionSize);
		
		position.setX(nouvellePositionI);
		position.setY(nouvellePositionJ);

		if (matrix[nouvellePositionI][nouvellePositionJ] > 0) {

			// Récupérer la ressource si elle est dans la capacité de l'agent
			if (discovery[nouvellePositionI][nouvellePositionJ] == 0 && nouvellePositionI!= entropot.getX()&& nouvellePositionJ!=entropot.getY())
				discovery[nouvellePositionI][nouvellePositionJ] = -1;
			if (matrix[nouvellePositionI][nouvellePositionJ] <= capacity) {
				int charge = matrix[nouvellePositionI][nouvellePositionJ];
				matrix[nouvellePositionI][nouvellePositionJ] = 0;
				System.out.println(getLocalName() + " a récupéré une ressource à la position " + nouvellePositionI + ", " + nouvellePositionJ);
				moveAgent(position, entropot);
				discovery[entropot.getX()][entropot.getY()] += charge;
			} else {

				matrix[nouvellePositionI][nouvellePositionJ] -= capacity;
				discovery[nouvellePositionI][nouvellePositionJ] = -1;
				moveAgent(position, entropot);
				discovery[entropot.getX()][entropot.getY()] += capacity;

				// Faire appel à d'autres agents 
				AppelAutresAgents(position, agentName);
				String message = null;
				boolean test = true;
				while(test){
					try {
						Thread.sleep(3000);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
					message = receiverBehaviour.getReceivedContent();
					test = false;
				}
				
				if (message != null && message.equals("Region explored")) {
					moveAgent(position, entropot);
				}
					
				
				//selection d'agent selon le critére de distance
				//si aucune réponse retour de l'agent lui méme pour la récuperation

			}
		}
	}


	//retourne l'agent ayant distance inferieure
	private String selectAgentBasedOnDistance(List<Position> posList, List<String> nameList) {
		double minDistance = Double.MAX_VALUE;
		String selectedAgent = null;
	
		for (int i = 0; i < nameList.size(); i++) {
			String agent = nameList.get(i);
			Position pos = posList.get(i);
	
			if (!agent.equals(agentName)) {
				double distance = calculateDistance(position.getX(), position.getY(), pos.getX(), pos.getY());
	
				if (distance < minDistance) {
					minDistance = distance;
					selectedAgent = agent;
				}
			}
		}
	
		return selectedAgent;
	}
	
	
	private double calculateDistance(int x1, int y1, int x2, int y2) {
		return Math.sqrt(Math.pow(x2 - x1, 2) + Math.pow(y2 - y1, 2));
	}
	
}
