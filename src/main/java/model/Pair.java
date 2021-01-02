package model;

public class Pair {
    private int x;
    private int y;

    public Pair(int x, int y)
    {
        this.x = x;
        this.y = y;
    }

    public Pair(Pair p) {
        this.x = p.x;
        this.y = p.y;
    }
    public int getX(){
        return x;
    }
    public int getY(){
        return y;
    }

    public void advance()
    {
        --y;
    }
    public boolean equals(Pair obj) {
        return (obj.x == this.x && obj.y == this.y);
    }

    @Override
    public String toString() {
        return "(" + x + ";" + y + ")";
    }
}
