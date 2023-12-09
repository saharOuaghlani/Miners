package main;

import java.awt.BorderLayout;
import java.awt.Color;

import javax.swing.JFrame;
import javax.swing.JPanel;

import jade.core.Profile;
import jade.core.ProfileImpl;
import jade.core.Runtime;
import jade.wrapper.AgentContainer;
import jade.wrapper.AgentController;
import main.MainPanel;
import javax.swing.SwingUtilities;

public class Main extends JFrame{
	
    private static PlayGround myPlayGround= new PlayGround();

    private static MainPanel center;
    
    private static Position
    //blue
    p1= new Position(PlayGround.REGION_SIZE - 1, PlayGround.REGION_SIZE - 1),
    
    //brown
    p2= new Position(PlayGround.REGION_SIZE, PlayGround.REGION_SIZE - 1),
    
    //purple
    p3= new Position (PlayGround.REGION_SIZE - 1, PlayGround.REGION_SIZE),
    
    //green
    p4= new Position (PlayGround.REGION_SIZE , PlayGround.REGION_SIZE );
  
    
    public static void main(String[] args) {
		
		
		
		// Start JADE runtime
        Runtime rt = Runtime.instance();
        Profile profile = new ProfileImpl();
        AgentContainer container = rt.createMainContainer(profile);

        //sGUI();
        launchAgents(container);
        
        /*
        // Launch the agents in a separate thread
        Thread agentThread = new Thread(() -> launchAgents(container));
        agentThread.start();

        // Launch the GUI on the Event Dispatch Thread
        SwingUtilities.invokeLater(() -> GUI());*/
         
    }

	private static void launchAgents(AgentContainer container) {
        // Parse the command-line arguments
        String[] agents = { "ag1:main.Explorer",
                "ag2:main.Explorer",
                "ag3:main.Explorer",
                "ag4:main.Explorer",
                "boss:main.Coordinator" };

        AgentController agentController = null;
        try {
            // Start each agent based on the provided arguments
            int i = 1;
            for (String agentArg : agents) {
                String[] agentParts = agentArg.split(":");
                String agentName = agentParts[0];
                String agentClass = agentParts[1];

                
                switch (i) {
                    case 1:
                        agentController = container.createNewAgent(agentName, agentClass,
                                new Object[] { 0, 0, PlayGround.REGION_SIZE, p1, myPlayGround, agentName });
                        agentController.start();
                        break;
                        /* 	
                        case 2:
                            agentController = container.createNewAgent(agentName, agentClass, new Object[]{PlayGround.REGION_SIZE, 0, PlayGround.REGION_SIZE, p2, myPlayGround, agentName});
                            agentController.start();
                        	break;

                        case 3:
                        	agentController = container.createNewAgent(agentName, agentClass, new Object[]{0, PlayGround.REGION_SIZE, PlayGround.REGION_SIZE, p3, myPlayGround, agentName});
                        	agentController.start();
                        	break;
                        	
                        case 4: 

                        	agentController = container.createNewAgent(agentName, agentClass, new Object[]{PlayGround.REGION_SIZE, PlayGround.REGION_SIZE, PlayGround.REGION_SIZE, p4, myPlayGround, agentName});
                        	agentController.start();
                        	break;
                        	
                        case 5: 
                        	agentController = container.createNewAgent(agentName, agentClass, null);
                        	agentController.start();
                        	break;
                        */
                    default:
                        System.out.println("ERROR");
                }

                i++;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
	
	static void GUI()
	{
		JFrame frame = new JFrame("Matrix Visualization");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        //frame.setSize(MATRIX_SIZE * (CASE_SIZE+2) , MATRIX_SIZE * (CASE_SIZE+3));
        frame.setResizable(true);
        frame.setLocationRelativeTo(null);
        
        JPanel mainPan= new JPanel();
        frame.setContentPane(mainPan);
        mainPan.setLayout(new BorderLayout());
        mainPan.setBackground(Color.black);
        
        JPanel north= new JPanel();
        north.setSize(north.getSize().width, PlayGround.CASE_SIZE);
        
        JPanel east= new JPanel();
        east.setSize(PlayGround.CASE_SIZE, north.getSize().height);
        
        JPanel south= new JPanel();
        north.setSize(north.getSize().width, PlayGround.CASE_SIZE);
        
        JPanel west= new JPanel();
        east.setSize(PlayGround.CASE_SIZE, north.getSize().height);
        
        center= new MainPanel(myPlayGround, p1, p2, p3, p4);
        
        mainPan.add(north, BorderLayout.NORTH);
        mainPan.add(center, BorderLayout.CENTER);
        mainPan.add(south, BorderLayout.SOUTH);
        mainPan.add(east, BorderLayout.WEST);
        mainPan.add(west, BorderLayout.EAST);
        
        frame.setSize(PlayGround.MATRIX_SIZE * (PlayGround.CASE_SIZE+1)+5 , PlayGround.MATRIX_SIZE * (PlayGround.CASE_SIZE+2));
        frame.setResizable(false);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
        
        while(true)
        	center.createAndVisualizeMatrix();
    
	}
}