package main;


import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Stroke;

import javax.swing.JFrame;
import javax.swing.JPanel;

public class MainPanel extends JPanel {
    PlayGround myPlayGround; 
    Position robot1;
    Position robot2;
    Position robot3;
    Position robot4;
    
    MainPanel(){};
	MainPanel(PlayGround myPlayGround, Position robot1, Position robot2, Position robot3, Position robot4){
		super();
			this.myPlayGround= myPlayGround;
			this.robot1= robot1;
			this.robot2= robot2;
			this.robot3= robot3;
			this.robot4= robot4;
		
			System.out.println("@@@@@GUI:\n"+robot1+"\n"+ robot2+"\n"+ robot3+"\n"+ robot4+"\nEND\n-*--*-----\n.");
	        createAndVisualizeMatrix();
	}
	
	
	
	public void createAndVisualizeMatrix() {
            repaint();
            try {
                Thread.sleep(myPlayGround.DELAY);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
    }

    /**
     * Paints the matrix and robots on the panel.
     *
     * @param g The Graphics object.
     */
    @Override
    protected void paintComponent(Graphics g) {
    	
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g;

        // Draw matrix
        for (int i = 0; i < myPlayGround.MATRIX_SIZE/2; i++) {
            for (int j = 0; j < myPlayGround.MATRIX_SIZE/2; j++) {
                g.setColor(new Color(37, 150, 190, 128));
                g.fillRect(i * myPlayGround.CASE_SIZE, j * myPlayGround.CASE_SIZE, myPlayGround.CASE_SIZE, myPlayGround.CASE_SIZE);
                
                g.setColor(Color.BLACK);
                g.drawRect(i * myPlayGround.CASE_SIZE, j * myPlayGround.CASE_SIZE, myPlayGround.CASE_SIZE, myPlayGround.CASE_SIZE);
            }
        }
        
        for (int i = 0; i < myPlayGround.MATRIX_SIZE/2; i++) {
            for (int j = myPlayGround.MATRIX_SIZE/2; j < myPlayGround.MATRIX_SIZE; j++) {
                g.setColor(new Color(153, 37, 190, 128));
                g.fillRect(i * myPlayGround.CASE_SIZE, j * myPlayGround.CASE_SIZE, myPlayGround.CASE_SIZE, myPlayGround.CASE_SIZE);
                g.setColor(Color.BLACK);
                g.drawRect(i * myPlayGround.CASE_SIZE, j * myPlayGround.CASE_SIZE, myPlayGround.CASE_SIZE, myPlayGround.CASE_SIZE);
            }
        }
        
        for (int i = myPlayGround.MATRIX_SIZE/2; i < myPlayGround.MATRIX_SIZE; i++) {
            for (int j = 0; j < myPlayGround.MATRIX_SIZE/2; j++) {
                g.setColor(new Color(190, 77, 37, 128));
                g.fillRect(i * myPlayGround.CASE_SIZE, j * myPlayGround.CASE_SIZE, myPlayGround.CASE_SIZE, myPlayGround.CASE_SIZE);
                g.setColor(Color.BLACK);
                g.drawRect(i * myPlayGround.CASE_SIZE, j * myPlayGround.CASE_SIZE, myPlayGround.CASE_SIZE, myPlayGround.CASE_SIZE);
            }
        }
        
        for (int i = myPlayGround.MATRIX_SIZE/2; i < myPlayGround.MATRIX_SIZE; i++) {
            for (int j = myPlayGround.MATRIX_SIZE/2 ; j < myPlayGround.MATRIX_SIZE; j++) {
                g.setColor(new Color(73, 190, 37, 128));
                g.fillRect(i * myPlayGround.CASE_SIZE, j * myPlayGround.CASE_SIZE, myPlayGround.CASE_SIZE, myPlayGround.CASE_SIZE);
                g.setColor(Color.BLACK);
                g.drawRect(i * myPlayGround.CASE_SIZE, j * myPlayGround.CASE_SIZE, myPlayGround.CASE_SIZE, myPlayGround.CASE_SIZE);
            }
        }
        
        // Draw robots
        System.out.println("@robot 1: "+robot1);
        //System.out.println("@robot 2: "+robot3);
        //System.out.println("@robot 3: "+robot3);
        //System.out.println("@robot 4: "+robot4);
        //System.out.println("-_-_-_-_-_-_-_-_");
        
        
        
        //finalizing the playground
        g2.setColor(Color.black);
        g2.setStroke(new BasicStroke((float)3));
        g2.drawLine(0, 0, 0, (myPlayGround.MATRIX_SIZE) * myPlayGround.CASE_SIZE);
        g2.drawLine(0, 0, (myPlayGround.MATRIX_SIZE)* myPlayGround.CASE_SIZE, 0);
        g2.drawLine((myPlayGround.MATRIX_SIZE) * myPlayGround.CASE_SIZE, 0, (myPlayGround.MATRIX_SIZE) * myPlayGround.CASE_SIZE, (myPlayGround.MATRIX_SIZE) * myPlayGround.CASE_SIZE);
        g2.drawLine(0,(myPlayGround.MATRIX_SIZE) * myPlayGround.CASE_SIZE,(myPlayGround.MATRIX_SIZE) * myPlayGround.CASE_SIZE,(myPlayGround.MATRIX_SIZE) * myPlayGround.CASE_SIZE);
        
        
        Graphics2D g2d = (Graphics2D) g.create();
        // Set the stroke of the copy, not the original 
        float[] dashPattern = {10.0f, 5.0f};
        Stroke dashed = new BasicStroke(3, BasicStroke.CAP_BUTT, BasicStroke.JOIN_BEVEL,
                                        0, dashPattern, 0);
        g2d.setStroke(dashed);
        g2d.setColor(Color.DARK_GRAY);
        g2d.drawLine((myPlayGround.MATRIX_SIZE/2)*myPlayGround.CASE_SIZE, 0, (myPlayGround.MATRIX_SIZE/2)*myPlayGround.CASE_SIZE,(myPlayGround.MATRIX_SIZE) * myPlayGround.CASE_SIZE);
        g2d.drawLine(0, (myPlayGround.MATRIX_SIZE/2)*myPlayGround.CASE_SIZE, (myPlayGround.MATRIX_SIZE) * myPlayGround.CASE_SIZE, (myPlayGround.MATRIX_SIZE/2)*myPlayGround.CASE_SIZE);
        
        float[] initPlaceDashPatter= {20.0f, 10.0f};
        dashed= new BasicStroke(2.5f, BasicStroke.CAP_BUTT, BasicStroke.JOIN_BEVEL,
                0, initPlaceDashPatter, 0);
        g2.setStroke(new BasicStroke((float)2.5));
        g2d.setColor(new Color(190, 37, 40, 128));
        g2d.drawRect((myPlayGround.MATRIX_SIZE/2- 1)*myPlayGround.CASE_SIZE, (myPlayGround.MATRIX_SIZE/2- 1)*myPlayGround.CASE_SIZE, 2*myPlayGround.CASE_SIZE, 2*myPlayGround.CASE_SIZE);
        g2d.fillRect((myPlayGround.MATRIX_SIZE/2- 1)*myPlayGround.CASE_SIZE, (myPlayGround.MATRIX_SIZE/2- 1)*myPlayGround.CASE_SIZE, 2*myPlayGround.CASE_SIZE, 2*myPlayGround.CASE_SIZE);
        
      //blue
        if(robot1 != null) {
        	g.setColor(new Color(37, 150, 190, 255));
        	g.fillRect(robot1.getX() * myPlayGround.CASE_SIZE , robot1.getY() * myPlayGround.CASE_SIZE , myPlayGround.ROBOT_SIZE, myPlayGround.ROBOT_SIZE);
        }
        
        //brown
        if(robot2 != null) {
        g.setColor(new Color(190, 77, 37, 255));
    	g.fillRect(robot2.getX() * myPlayGround.CASE_SIZE , robot2.getY() * myPlayGround.CASE_SIZE , myPlayGround.ROBOT_SIZE, myPlayGround.ROBOT_SIZE);
        }
        
        //purple
        if(robot3 != null) {
        g.setColor(new Color(153, 37, 190, 255));
        g.fillRect(robot3.getX() * myPlayGround.CASE_SIZE , robot3.getY() * myPlayGround.CASE_SIZE , myPlayGround.ROBOT_SIZE, myPlayGround.ROBOT_SIZE);
        }
        
        //green
        if(robot4 != null) {
        g.setColor(new Color(73, 190, 37, 255));
    	g.fillRect(robot4.getX() * myPlayGround.CASE_SIZE , robot4.getY() * myPlayGround.CASE_SIZE , myPlayGround.ROBOT_SIZE, myPlayGround.ROBOT_SIZE);
        }
        
    }
}
