package main;
import jade.core.Agent;
import jade.lang.acl.ACLMessage;

import java.util.Arrays;
import java.util.List;

import jade.core.AID;

public class Explorer  extends Agent {
	private static final long serialVersionUID = 7043315975215984640L;
	
	int x0;
	int y0;
	int regionSize;
	int capacity;
	Position position, entropot;
	PlayGround myPlayGround;
	int[][] matrix;
	String agentName;
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
		capacity= (((PlayGround)args[4]).AGENTCAPACITY);
		position = new Position(((Position)args[3]).getX(), ((Position)args[3]).getY());
		entropot = new Position(((Position)args[3]).getX(), ((Position)args[3]).getY());
		matrix = (((PlayGround)args[4]).matrix);
		agentName = args[5].toString();
		System.out.println("@"+this.getLocalName()+":"+x0+", "+y0+", "+regionSize+", "+ capacity);
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
	
	do{
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
			if (myPlayGround.matrix[nouvellePositionI][nouvellePositionJ] <= capacity) {
				myPlayGround.matrix[nouvellePositionI][nouvellePositionJ] = 0;
				int charge = myPlayGround.matrix[nouvellePositionI][nouvellePositionJ];
				myPlayGround.discovery[nouvellePositionI][nouvellePositionJ] = 1;
				System.out.println(getLocalName() + " a récupéré une ressource à la position " + nouvellePositionI + ", " + nouvellePositionJ);
				moveAgent(new Position(position.getX(),position.getY()), new Position(entropot.getX(),entropot.getY()));
				myPlayGround.discovery[entropot.getX()][entropot.getY()] += charge;
			} else {

				// Faire appel à d'autres agents selon certains critères
				AppelAutresAgents(new Position(nouvellePositionI, nouvellePositionJ), agentName);

			}
		}
	}
	
}
