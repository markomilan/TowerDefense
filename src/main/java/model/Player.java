package model;

public class Player {
    private int gold;
    private boolean isEnd;

    public Player(){
        this.gold = 10;

    }

    public int getGold(){
        return gold;
    }
    public void setGold(int g){gold = g;}
    public void addGold(int amount) {
        gold += amount;
    }

}
