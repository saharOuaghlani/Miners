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

public class Main extends JFrame{
	
	/*private static final int MATRIX_SIZE = 32;
    private static final int REGION_SIZE = MATRIX_SIZE / 2;
    private static final int CASE_SIZE = 20;
    private static final int ROBOT_SIZE = 20;
    private static final int DELAY = 200; // Delay in milliseconds for smooth transition
    
    private static final int MINES= 50;
    private static final int AGENTCAPACITY= 2;
    */
	
    private static PlayGround myPlayGround;
    private static MainPanel center;
    
	public static void main(String[] args) {
		
		myPlayGround= new PlayGround();
        // Parse the command-line arguments
        String[] agents = { "ag1:main.Explorer",
                            "ag2:main.Explorer",
                            "ag3:main.Explorer",
                            "ag4:main.Explorer",
                            "boss:main.Coordinator"};

        // Start JADE runtime
        Runtime rt = Runtime.instance();
        Profile profile = new ProfileImpl();
        AgentContainer container = rt.createMainContainer(profile);
        AgentController agentController= null;
        try {
            // Start each agent based on the provided arguments
        	int i=1;
            for (String agentArg : agents) {
                String[] agentParts = agentArg.split(":");
                String agentName = agentParts[0];
                String agentClass = agentParts[1];

                switch(i)
                {
                case 1: 
                    agentController = container.createNewAgent(agentName, agentClass, new Object[]{0,0, PlayGround.REGION_SIZE, PlayGround.AGENTCAPACITY});
                    agentController.start();
                	break;
                	
                case 2:
                    agentController = container.createNewAgent(agentName, agentClass, new Object[]{PlayGround.REGION_SIZE, 0, PlayGround.REGION_SIZE, PlayGround.AGENTCAPACITY});
                    agentController.start();
                	break;

                case 3:
                	agentController = container.createNewAgent(agentName, agentClass, new Object[]{0, PlayGround.REGION_SIZE, PlayGround.REGION_SIZE, PlayGround.AGENTCAPACITY});
                	agentController.start();
                	break;
                	
                case 4: 

                	agentController = container.createNewAgent(agentName, agentClass, new Object[]{PlayGround.REGION_SIZE, PlayGround.REGION_SIZE, PlayGround.REGION_SIZE, PlayGround.AGENTCAPACITY});
                	agentController.start();
                	break;
                	
                case 5: 
                	agentController = container.createNewAgent(agentName, agentClass, null);
                	agentController.start();
                	break;
                
                default: System.out.println("ERROR�");
                }
                
            i++;
            }
            
            GUI();
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
        //north.setBackground(Color.red);
        
        JPanel east= new JPanel();
        east.setSize(PlayGround.CASE_SIZE, north.getSize().height);
        //east.setBackground(Color.blue);
        
        JPanel south= new JPanel();
        north.setSize(north.getSize().width, PlayGround.CASE_SIZE);
        //north.setBackground(Color.green);
        
        JPanel west= new JPanel();
        east.setSize(PlayGround.CASE_SIZE, north.getSize().height);
        //east.setBackground(Color.yellow);
        
        center= new MainPanel();
        
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
