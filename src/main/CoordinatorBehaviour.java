package main;
import jade.core.behaviours.CyclicBehaviour;
import jade.lang.acl.ACLMessage;
import jade.core.AID;
import jade.core.Agent;
import java.util.List;

public class CoordinatorBehaviour extends CyclicBehaviour {

    private int totalResources;
    private int[][] resourceMatrix;
    private int[][] discoveryMatrix;
    private List<Explorer> robotExplorers;

    public CoordinatorBehaviour(Agent agent, int totalResources, int[][] resourceMatrix, int[][] discoveryMatrix, List<Explorer> robotExplorers) {
        super(agent);
        this.totalResources = totalResources;
        this.resourceMatrix = resourceMatrix;
        this.discoveryMatrix = discoveryMatrix;
        this.robotExplorers = robotExplorers;
    }

    @Override
    public void action() {
        // Check if the sum of entrepot values for all agents is equal to totalResources && check that the ressource matrix is ZEROS
        if (isTotalEntrepotSumEqual() && areAllResourcesConsumed()) {
            notifyEmptyResources();

        } else {
            // Call notifyEndRegion to check if regions are fully explored
            notifyEndRegion();
        }
        // For example, use a blocking receive with a timeout:
        block(1000); // This will block for 1 second
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
        // Create a message to notify explorers
        ACLMessage message = new ACLMessage(ACLMessage.INFORM);
        
        for (Explorer robot : robotExplorers) {
            // Add explorers' AIDs to the message
            message.addReceiver(new AID(robot.getName(), AID.ISLOCALNAME));
        }

        // Set the content of the message
        message.setContent("EmptyResourcesSignal, Well done!");

        // Send the message
        myAgent.send(message);
    }
    private boolean isRegionExplored(Explorer robot) {
        int startX = robot.getStartX();
        int startY = robot.getStartY();
        int endX = robot.getEndX();
        int endY = robot.getEndY();
    
        // Check if all values in the region-specific matrix (excluding entrepôt) are -1
        for (int i = startX; i <= endX; i++) {
            for (int j = startY; j <= endY; j++) {
                if (discoveryMatrix[i][j] != -1 && !isEntrepot(i, j, robot)) {
                    return false;  // The region is not fully explored (excluding entrepôt)
                }
            }
        }

        return true;  // The entire region is explored
    }
    private boolean isTotalEntrepotSumEqual() {
        int totalEntrepotSum = 0;

        // Calculate the sum of entrepot values for all agents
        for (Explorer robot : robotExplorers) {
            totalEntrepotSum += resourceMatrix[robot.getX0()][robot.getY0()];
        }

        // Check if the sum is equal to totalResources
        return totalEntrepotSum == totalResources;
    }
    private boolean isEntrepot(int x, int y, Explorer robot) {
        // Implement the logic to check if the cell at (x, y) is the entrepôt for the given robot
        // You might need to adjust this based on how your entrepôt is represented
        return x == robot.getX0() && y == robot.getY0();
    }
    private void makeAvailable(Explorer robot) {
        // Implement the logic to make the robot available
        // You might want to set a flag or update the robot's status
        robot.setStatus(true);
        // Send a signal to the explorer that the robot is available to help
        sendAvailableSignal(robot);
    }
    private void sendAvailableSignal(Explorer robot) {
        // Create a message to signal the explorer that the robot is available to help
        ACLMessage message = new ACLMessage(ACLMessage.INFORM);
        message.addReceiver(new AID(robot.getName(), AID.ISLOCALNAME));
        message.setContent("AvailableToHelpSignal");

        // Send the message
        myAgent.send(message);    }
    private void notifyEndRegion(){
        
            System.out.println("Rupture de ressources. Notifying robots...");

            // Iterate through robot explorers
            for (Explorer robot : robotExplorers) {
                // Check if the region is fully explored
                if (isRegionExplored(robot)) {
                    // If the region is fully explored, send a signal to the explorer
                    sendRegionExploredSignal(robot);
                    // Make the robot available
                    makeAvailable(robot);
                }
            }
    }
    private void sendRegionExploredSignal(Explorer robot) {
        // Create a message to signal the explorer that the region is fully explored
        ACLMessage message = new ACLMessage(ACLMessage.INFORM);
        message.addReceiver(new AID(robot.getName(), AID.ISLOCALNAME));
        message.setContent("RegionExploredSignal");

        // Send the message
        myAgent.send(message);    }

}
