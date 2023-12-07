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

    int getX()
        {
            return x;
        }
        
        int getY()
        {
            return y;
        }

    void setX(int x)
        {
            this.x = x;
        }

    void setY(int y)
        {
            this.y = y;
        }
}
