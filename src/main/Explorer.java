package main;

import jade.core.Agent;

public class Explorer  extends Agent {
	private static final long serialVersionUID = 7043315975215984640L;
	
	int x0;
	int y0;
	int regionSize;
	int capacity;
	Position position, entropot;
	
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
		position = new Position(Integer.parseInt( args[4].toString()), Integer.parseInt( args[5].toString()));
		entropot = position;

		System.out.println("@"+this.getLocalName()+":"+x0+", "+y0+", "+regionSize+", "+ capacity);
	}
	}

	private void explorerMatriceRessources() {
		// Générer un nombre aléatoire pour la direction du déplacement
		int direction = (int) (Math.random() * 4); // 0: gauche, 1: droite, 2: haut, 3: bas
	
		int nouvellePositionI = -1;
		int nouvellePositionJ = -1;
	
		switch (direction) {
			case 0: // Gauche
				nouvellePositionI = position.getX();
				position.setY(Math.max(position.getX() - 1, 0));
				nouvellePositionJ = position.getY();
				break;
			case 1: // Droite
				nouvellePositionI = position.getX();
				position.setY(position.getY() + 1);
				nouvellePositionJ = position.getY();
				break;
			case 2: // Haut
				position.setX(position.getX() - 1);
				nouvellePositionI = position.getX()
				nouvellePositionJ = position.getY();
				break;
			case 3: // Bas
				position.setX(position.getX() + 1);
				nouvellePositionI = position.getX();
				nouvellePositionJ = position.getY();
				break;
		}
	
		if (matriceRessources[nouvellePositionI][nouvellePositionJ] > 0 && matricePositions[nouvellePositionI][nouvellePositionJ] == 0) {
			// Récupérer la ressource si elle est dans la capacité de l'agent
			if (matriceRessources[nouvellePositionI][nouvellePositionJ] <= capacity) {
				matriceRessources[nouvellePositionI][nouvellePositionJ] = 0;
				matricePositions[nouvellePositionI][nouvellePositionJ] = 1;
				System.out.println(getLocalName() + " a récupéré une ressource à la position " + nouvellePositionI + ", " + nouvellePositionJ);
			} else {
				// Faire appel à d'autres agents selon certains critères
				faireAppelAutresAgents(nouvellePositionI, nouvellePositionJ);
			}
		}
	}
	
}
