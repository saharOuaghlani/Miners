package main;


import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Stroke;

import javax.swing.JFrame;
import javax.swing.JPanel;


/**
 * This class represents a 64x64 matrix divided into 4 regions, with moving robots exploring cases looking for resources.
 */
public class MainPanel extends JPanel {
    
	private static final int MATRIX_SIZE = 32;
    private static final int REGION_SIZE = MATRIX_SIZE / 2;
    private static final int CASE_SIZE = 20;
    private static final int ROBOT_SIZE = 20;
    private static final int DELAY = 200; // Delay in milliseconds for smooth transition
    private static final int DIM= 2;
    private static final int MINES= 50;
    
    private int[][] matrix;
    private boolean[][] Discovery;
    private int robot1X, robot1Y;
    private int robot2X, robot2Y;
    private int robot3X, robot3Y;
    private int robot4X, robot4Y;
	
	MainPanel(){
		super();
			initMatrix();
	        robot1X = REGION_SIZE -1;
	        robot1Y = REGION_SIZE -1;
	        
	        robot2X = 0;
	        robot2Y = REGION_SIZE -1;
	        
	        robot3X = REGION_SIZE -1;
	        robot3Y = 0;
	        
	        robot4X = 0;
	        robot4Y = 0;
	        createAndVisualizeMatrix();
	}
	
	void initMatrix()
	{
		matrix = new int[MATRIX_SIZE][MATRIX_SIZE];
		
	}
	
	public void createAndVisualizeMatrix() {
            repaint();
            try {
                Thread.sleep(DELAY);
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
        for (int i = 0; i < MATRIX_SIZE/2; i++) {
            for (int j = 0; j < MATRIX_SIZE/2; j++) {
                g.setColor(new Color(37, 150, 190, 128));
                g.fillRect(i * CASE_SIZE, j * CASE_SIZE, CASE_SIZE, CASE_SIZE);
                
                g.setColor(Color.BLACK);
                g.drawRect(i * CASE_SIZE, j * CASE_SIZE, CASE_SIZE, CASE_SIZE);
            }
        }
        
        for (int i = 0; i < MATRIX_SIZE/2; i++) {
            for (int j = MATRIX_SIZE/2; j < MATRIX_SIZE; j++) {
                g.setColor(new Color(153, 37, 190, 128));
                g.fillRect(i * CASE_SIZE, j * CASE_SIZE, CASE_SIZE, CASE_SIZE);
                g.setColor(Color.BLACK);
                g.drawRect(i * CASE_SIZE, j * CASE_SIZE, CASE_SIZE, CASE_SIZE);
            }
        }
        
        for (int i = MATRIX_SIZE/2; i < MATRIX_SIZE; i++) {
            for (int j = 0; j < MATRIX_SIZE/2; j++) {
                g.setColor(new Color(190, 77, 37, 128));
                g.fillRect(i * CASE_SIZE, j * CASE_SIZE, CASE_SIZE, CASE_SIZE);
                g.setColor(Color.BLACK);
                g.drawRect(i * CASE_SIZE, j * CASE_SIZE, CASE_SIZE, CASE_SIZE);
            }
        }
        
        for (int i = MATRIX_SIZE/2; i < MATRIX_SIZE; i++) {
            for (int j = MATRIX_SIZE/2 ; j < MATRIX_SIZE; j++) {
                g.setColor(new Color(73, 190, 37, 128));
                g.fillRect(i * CASE_SIZE, j * CASE_SIZE, CASE_SIZE, CASE_SIZE);
                g.setColor(Color.BLACK);
                g.drawRect(i * CASE_SIZE, j * CASE_SIZE, CASE_SIZE, CASE_SIZE);
            }
        }
        
        // Draw robots
        
        //blue
        g.setColor(new Color(37, 150, 190, 255));
        g.fillRect(robot1X * CASE_SIZE + (CASE_SIZE - ROBOT_SIZE) / 2, robot1Y * CASE_SIZE + (CASE_SIZE - ROBOT_SIZE) / 2, ROBOT_SIZE, ROBOT_SIZE);
        
        //brown
        g.setColor(new Color(190, 77, 37, 255));
        g.fillRect((robot2X + REGION_SIZE) * CASE_SIZE + (CASE_SIZE - ROBOT_SIZE) / 2, robot2Y * CASE_SIZE + (CASE_SIZE - ROBOT_SIZE) / 2, ROBOT_SIZE, ROBOT_SIZE);
        
        //purple
        g.setColor(new Color(153, 37, 190, 255));
        g.fillRect(robot3X * CASE_SIZE + (CASE_SIZE - ROBOT_SIZE) / 2, (robot3Y + REGION_SIZE) * CASE_SIZE + (CASE_SIZE - ROBOT_SIZE) / 2, ROBOT_SIZE, ROBOT_SIZE);
        
        //green
        g.setColor(new Color(73, 190, 37, 255));
        g.fillRect((robot4X + REGION_SIZE) * CASE_SIZE + (CASE_SIZE - ROBOT_SIZE) / 2, (robot4Y + REGION_SIZE) * CASE_SIZE + (CASE_SIZE - ROBOT_SIZE) / 2, ROBOT_SIZE, ROBOT_SIZE);
    
        //finalizing the playground
        g2.setColor(Color.black);
        g2.setStroke(new BasicStroke((float)3));
        g2.drawLine(0, 0, 0, (MATRIX_SIZE ) * CASE_SIZE);
        g2.drawLine(0, 0, (MATRIX_SIZE )* CASE_SIZE, 0);
        g2.drawLine((MATRIX_SIZE ) * CASE_SIZE, 0, (MATRIX_SIZE ) * CASE_SIZE, (MATRIX_SIZE ) * CASE_SIZE);
        g2.drawLine(0,(MATRIX_SIZE ) * CASE_SIZE,(MATRIX_SIZE ) * CASE_SIZE,(MATRIX_SIZE ) * CASE_SIZE);
        
        
        Graphics2D g2d = (Graphics2D) g.create();
        // Set the stroke of the copy, not the original 
        float[] dashPattern = {10.0f, 5.0f};
        Stroke dashed = new BasicStroke(3, BasicStroke.CAP_BUTT, BasicStroke.JOIN_BEVEL,
                                        0, dashPattern, 0);
        g2d.setStroke(dashed);
        g2d.setColor(Color.DARK_GRAY);
        g2d.drawLine((MATRIX_SIZE/2)*CASE_SIZE, 0, (MATRIX_SIZE/2)*CASE_SIZE,(MATRIX_SIZE ) * CASE_SIZE);
        g2d.drawLine(0, (MATRIX_SIZE/2)*CASE_SIZE, (MATRIX_SIZE ) * CASE_SIZE, (MATRIX_SIZE/2)*CASE_SIZE);
        
        float[] initPlaceDashPatter= {20.0f, 10.0f};
        dashed= new BasicStroke(2.5f, BasicStroke.CAP_BUTT, BasicStroke.JOIN_BEVEL,
                0, initPlaceDashPatter, 0);
        //g2.setStroke(new BasicStroke((float)2.5));
        g2d.setColor(new Color(190, 37, 40, 128));
        g2d.drawRect((MATRIX_SIZE/2- 1)*CASE_SIZE, (MATRIX_SIZE/2- 1)*CASE_SIZE, 2*CASE_SIZE, 2*CASE_SIZE);
        g2d.fillRect((MATRIX_SIZE/2- 1)*CASE_SIZE, (MATRIX_SIZE/2- 1)*CASE_SIZE, 2*CASE_SIZE, 2*CASE_SIZE);
        
    }
}
