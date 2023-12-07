package main;

import java.util.Random;

public class PlayGround {

	public static final int MATRIX_SIZE = 32;
	public static final int REGION_SIZE = MATRIX_SIZE / 2;
	public static final int CASE_SIZE = 20;
	public static final int ROBOT_SIZE = 20;
	public static final int DELAY = 200; 
	public static final int MINES= 50;
    public static final int AGENTCAPACITY= 2;
	public static final int MAX_RESOURCES= 8;
    
	public int[][] matrix;
	public int[][] discovery;
	
	public PlayGround()
	{
		matrix= new int[MATRIX_SIZE][MATRIX_SIZE];
		discovery= new int[MATRIX_SIZE][MATRIX_SIZE];
		
		initMatrix();
		initDiscovery();
	}
	
	private void initDiscovery()
	{
		//0 pour dire non exploré
				for(int i=0; i< MATRIX_SIZE; i++)
					for(int j=0; j< MATRIX_SIZE; j++)
						discovery[i][j]= 0;
		//System.out.println("dicovery shuffling");
		//displayMatrix(discovery);

	}
	
	private void initMatrix()
	{
		int counter=0;
		matrix= new int[MATRIX_SIZE][MATRIX_SIZE];
		for(int i=0; i< MATRIX_SIZE; i++)
			for(int j=0; j< MATRIX_SIZE; j++)
				{
						if(counter< MINES) {
								matrix[i][j]= generateRandomNumber(1, MAX_RESOURCES);
								counter++;
						}
						else matrix[i][j]= 0;
				}
		//System.out.println("matrix before shuffling");
	    //displayMatrix(matrix);
		
		// Shuffle the matrix using Fisher-Yates algorithm
	    shuffleMatrix();
	    //System.out.println("matrix after shuffling");
	    //displayMatrix(matrix);
	}
	
	private void shuffleMatrix() {
	    int totalCells = MATRIX_SIZE * MATRIX_SIZE;
	    Random random = new Random();

	    for (int i = totalCells - 1; i > 0; i--) {
	        int j = random.nextInt(i + 1);

	        // Swap matrix elements
	        int temp = matrix[i / MATRIX_SIZE][i % MATRIX_SIZE];
	        matrix[i / MATRIX_SIZE][i % MATRIX_SIZE] = matrix[j / MATRIX_SIZE][j % MATRIX_SIZE];
	        matrix[j / MATRIX_SIZE][j % MATRIX_SIZE] = temp;
	    }
	}
	
	// Generate a random number between min (inclusive) and max (exclusive)
    private static int generateRandomNumber(int min, int max) {
        Random random = new Random();
        return random.nextInt(max - min) + min;
    }
    
    private void displayMatrix(int[][] matrice) {
        for (int i = 0; i < matrice.length; i++) {
            for (int j = 0; j < matrice[i].length; j++) {
                System.out.printf("%-4d", matrice[i][j]);
            }
        }
    }

}
