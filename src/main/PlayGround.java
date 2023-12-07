package main;

public class PlayGround {

	public static final int MATRIX_SIZE = 32;
	public static final int REGION_SIZE = MATRIX_SIZE / 2;
	public static final int CASE_SIZE = 20;
	public static final int ROBOT_SIZE = 20;
	public static final int DELAY = 200; // Delay in milliseconds for smooth transition
	public static final int DIM= 2;
	public static final int MINES= 50;
    
	public int[][] matrix;
	public boolean[][] Discovery;
	
	public PlayGround()
	{
		
	}
}
