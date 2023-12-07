package main;

import jade.core.Agent;
import java.util.List;


public class Coordinator extends Agent{

	
	private int totalResources;
	private int[][] resourceMatrix;
	private int[][] discoveryMatrix;
	private List<Explorer> robotExplorers;


	public Coordinator CoordinatorAgent(int totalResources, int[][] resourceMatrix, int[][] discoveryMatrix, List<Explorer> robotExplorers) {
        this.totalResources = totalResources;
        this.resourceMatrix = resourceMatrix;
        this.discoveryMatrix = discoveryMatrix;
        this.robotExplorers = robotExplorers;
    }

    public void checkResources() {
        if (areAllResourcesConsumed()) {
            notifyEmptyResources();
        }
    }

    private boolean areAllResourcesConsumed() {
        for (int[] row : resourceMatrix) {
            for (int value : row) {
                if (value != 0) {
                    return false;  // There are still resources
                }
            }
        }
        return true;  // All resources have been consumed
    }

    private void notifyEmptyResources() {
        System.out.println("Rupture de ressources. Notifying robots...");
        for (Explorer robot : robotExplorers) {
            robot.handleEmptyResources();
        }
    }
}



