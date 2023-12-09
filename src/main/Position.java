package main;

public class Position {
    private int x;
    private int y;

    public Position()
    {
        super();
    }
    
    public Position(int x, int y)
        {
            super();
            this.x = x;
            this.y = y;
        }

    public int getX()
        {
            return x;
        }
        
    public int getY()
        {
            return y;
        }

    public void setX(int x)
        {
            this.x = x;
        }

    public void setY(int y)
        {
            this.y = y;
        }



	@Override
	public String toString() {
		return "Position [x=" + x + ", y=" + y + "]";
	}
    
    
}
