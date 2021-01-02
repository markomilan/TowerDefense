package model;

import view.resloader.Animation;

import java.awt.Color;
import java.awt.Graphics2D;

public abstract class Enemy implements Comparable<Enemy>{
    protected double pixelLocation=-525;
    protected int goldRewards;
    protected int moveSpeed;
    protected int damage;
    protected int id;
    protected Pair location;
    protected int health;
    protected int maxHealth;
    protected Animation animationAtk;
    protected Animation animationDie;
    protected Animation animationRun;
    protected boolean running;
    protected boolean attacking;
    protected int dyingCounter;

    public Enemy(Pair location)
    {
        this.location = location;
        goldRewards = 3;
        running = true;
        attacking = false;
        dyingCounter = 0;
    }

    public Pair getLocation(){return location;}

    public int getHealth() {
        return health;
    }

    public void setHealth(int health) {
        this.health = health;
    }

    public void setPixelLocation(double d){pixelLocation = d;}

    public double getPixelLocation() { return pixelLocation;}

    public int getMoveSpeed() {
        return moveSpeed;
    }

    public int getId() { return id; }

    public int getDamage(){ return damage;}

    public void move() {
        if (!dying())
            location.advance();
    }

    public void removeHealth(int damage) {
        health -= damage;
    }

    public void draw(Graphics2D g, int cellWidth, int cellHeight) {
        int x = location.getX();
        int y = location.getY();

        drawHealthBar(g, cellWidth, cellHeight);
        if(dying()) {
            if (dyingCounter < 6) {
                animationDie.tick();
                ++dyingCounter;
            }
            animationDie.render(g, x * cellWidth + 1, y * cellHeight + 1, cellWidth - 1, cellHeight - 1);
        }
        else if(attacking){
            animationAtk.tick();
            animationAtk.render(g,x * cellWidth + 1,y * cellHeight + 1, cellWidth - 1,cellHeight - 1);
        }else if(running){
            animationRun.tick();
            animationRun.render(g,x * cellWidth + 1,pixelLocation*(-1), cellWidth - 1,cellHeight - 1);
            if(pixelLocation<=0)
                pixelLocation=pixelLocation + 3.0 / moveSpeed;
            else
                System.out.println(pixelLocation);
        }else {
            animationRun.render(g,x * cellWidth + 1,y * cellHeight + 1, cellWidth - 1,cellHeight - 1);
        }
    }

    public void setRunning(boolean state){ running = state;}
    public boolean isAttacking(){ return attacking;}    // Used in unit tests
    public boolean isRunning() {return running;}        // Used in unit tests
    public void setAttacking(boolean state){ attacking = state;}
    public void resetPixelLoc(int cellHeight) { pixelLocation = -1 * location.getY() * cellHeight;}
    public boolean dying(){
        return health <= 0;
    }
    public int getGoldReward(){
        return goldRewards;
    }
    protected void drawHealthBar(Graphics2D g, int cellWidth, int cellHeight)
    {
        int x = location.getX();
        double healthPercent = (float)health / (float) maxHealth;
        g.setColor(Color.RED);
        g.fillRect((int)((x + 0.25) * cellWidth), (int) Math.abs(pixelLocation) - 20, (int) (cellWidth * 0.5), 10);
        g.setColor(Color.GREEN);
        g.fillRect((int)((x + 0.25) * cellWidth), (int) Math.abs(pixelLocation) - 20, (int) ((cellWidth * 0.5) * healthPercent), 10);
    }

    public void setLocation(Pair location) {
        this.location = location;
    }

    @Override
    public int compareTo(Enemy o) {
        return Integer.compare(this.getId(), o.getId());
    }
}
