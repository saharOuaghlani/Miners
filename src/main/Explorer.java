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
	private PlayGround myPlayGround;
	private int[][] matrix;
	private String agentName;
	private boolean dispo;
	public Explorer(){super();}
	ReceiverBehaviour receiverBehaviour;
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
		agentName = args[5].toString();
		System.out.println("@"+this.getLocalName()+":"+x0+", "+y0+", "+regionSize+", "+ capacity);
	}
	while(dispo == false)
	{
		explorerMatriceRessources();
		receiverBehaviour = new ReceiverBehaviour(this);
		String message = receiverBehaviour.getReceivedContent();
		if (message.equals("Region DONE!")){
			moveAgent(position, entropot);
			dispo = true;
		}
	}
	while(true)
	{
		addBehaviour(new ReceiverBehaviour(this));
		///Recuperer location du message////////////////////////////////////////////////
		String message = receiverBehaviour.getReceivedContent();
		Position posR = new Position(0,0); 
		
		if (message.equals("Help me please! This is the location of ressources")){
			moveAgent(entropot, posR);
			if (myPlayGround.matrix[posR.getX()][posR.getY()] <= capacity) {
				int charge = myPlayGround.matrix[posR.getX()][posR.getY()];
				myPlayGround.matrix[posR.getX()][posR.getY()] = 0;
				myPlayGround.discovery[posR.getX()][posR.getY()] = -1;
				moveAgent(posR, entropot);
				myPlayGround.discovery[entropot.getX()][entropot.getY()] += charge;		}
				else{
					int charge = myPlayGround.matrix[posR.getX()][posR.getY()];
					myPlayGround.matrix[posR.getX()][posR.getY()] -= capacity;
					myPlayGround.discovery[posR.getX()][posR.getY()] = -1;
					moveAgent(posR, entropot);
					myPlayGround.discovery[entropot.getX()][entropot.getY()] += charge;		
				}


	}
	}
	}
	private void sendMessage(String to, Position pos) {
		ACLMessage msg = new ACLMessage(ACLMessage.INFORM);
		msg.addReceiver(new AID(to,AID.ISLOCALNAME));
		msg.setContent("Help me please! This is the location of ressources : ("+pos.getX()+" ,"+pos.getY()+")");
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
            // Déplace horizontalement
            if (currentX < endX) {
				currentX++;
            } else if (currentX > endX) {
                currentX--;
            }
            currentPosition.setX(currentX);

            // Déplace verticalement
            if (currentY < endY) {
                currentY++;
            } else if (currentY > endY) {
                currentY--;
            }
			currentPosition.setY(currentY);
            // Affiche les coordonnées de l'agent à chaque pas
            System.out.println("Position actuelle : (" + currentX + ", " + currentY + ")");
        }
    }

	private void explorerMatriceRessources() {	
	do
	{
		// Générer un nombre aléatoire pour la direction du déplacement
		int direction = (int) (Math.random() * 4); // 0: gauche, 1: droite, 2: haut, 3: bas
		switch (direction) {
			case 0: // Gauche
				position.setY(position.getY()-1);
				break;
			case 1: // Droite
				position.setY(position.getY() + 1);
				break;
			case 2: // Haut
				position.setX(position.getX() - 1);
				break;
			case 3: // Bas
				position.setX(position.getX() + 1);
				break;
		}
	}while((position.getX()!= entropot.getX()&& position.getY()!=entropot.getY())||position.getX()<x0 || position.getY()<y0 || position.getX() == x0 + regionSize || position.getY() == y0 + regionSize);
		
		int nouvellePositionI = position.getX();
		int nouvellePositionJ = position.getY();
		if (myPlayGround.matrix[nouvellePositionI][nouvellePositionJ] > 0) {
			// Récupérer la ressource si elle est dans la capacité de l'agent

			if (myPlayGround.matrix[nouvellePositionI][nouvellePositionJ] == 0)
					matrix[nouvellePositionI][nouvellePositionJ] = -1;
			if (myPlayGround.matrix[nouvellePositionI][nouvellePositionJ] <= capacity) {
				int charge = myPlayGround.matrix[nouvellePositionI][nouvellePositionJ];
				myPlayGround.matrix[nouvellePositionI][nouvellePositionJ] = 0;
				myPlayGround.discovery[nouvellePositionI][nouvellePositionJ] = -1;
				System.out.println(getLocalName() + " a récupéré une ressource à la position " + nouvellePositionI + ", " + nouvellePositionJ);
				moveAgent(position, entropot);
				myPlayGround.discovery[entropot.getX()][entropot.getY()] += charge;
			} else {

				// Faire appel à d'autres agents 
				AppelAutresAgents(position, agentName);
				//selection d'agent selon le critére de distance
				//si aucune réponse retour de l'agent lui méme pour la récuperation

			}
		}
	}
	
	
}
