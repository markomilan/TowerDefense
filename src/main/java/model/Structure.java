package model;

import controller.ingame.GameSession;
import view.resloader.Animation;
import view.resloader.GameGallery;

import java.awt.Color;
import java.awt.Graphics2D;
import java.io.IOException;

public abstract class Structure{
    protected int actualHealth;
    protected int maxHealth;
    protected int buildCost;
    protected int improveCost;
    protected int id;
    protected boolean aoe; // Attacks all enemies in the target field
    protected boolean improved;
    protected Animation baseMove;
    protected Animation impMove;


    public int getCost() {
        return buildCost;
    }
    public int getId(){return this.id;}
    public abstract void improveStructure();
    public int getActualHealth(){return actualHealth;}
    public int getImproveCost(){return improveCost;}
    public boolean isImproved(){return improved;}
    public boolean isAoE() {return aoe;}
    public Character isImprovedToChar(){
        if(isImproved()){
            return 'T';
        }else{
            return 'F';
        }
    }
    public void setImproved()
    {
        improved = true;
    }
    public void draw(Graphics2D g, int cellWidth, int cellHeight, Pair location, GameGallery gameGallery) throws IOException
    {
        int x = location.getX();
        int y = location.getY();

        if (improved) {
            impMove.tick();
            impMove.render(g,x * cellWidth + 1,y * cellHeight + 1, cellWidth - 1,cellHeight - 1);
        }else {
            baseMove.tick();
            baseMove.render(g, x * cellWidth + 1, y * cellHeight + 1, cellWidth - 1, cellHeight - 1);
        }
        drawHealthBar(g, location, cellWidth, cellHeight);
    }

    // An enemy hits this structure. Returns true if the structure gets destroyed.
    public void hit(int damage)
    {
        actualHealth -= damage;
    }

    public boolean isDemolished()
    {
        return actualHealth <= 0;
    }

    // The structure tries to shoot from @location.
    public abstract void tryShoot(boolean slow, Pair location, GameSession game);


    public void drawHealthBar(Graphics2D g, Pair location, int cellWidth, int cellHeight)
    {
        int x = location.getX();
        int y = location.getY();
        double healthPercent = (float)actualHealth / (float) maxHealth;
        g.setColor(Color.RED);
        g.fillRect((int)((x + 0.25) * cellWidth), y * cellHeight, (int) (cellWidth * 0.5), 10);
        g.setColor(Color.GREEN);
        g.fillRect((int)((x + 0.25) * cellWidth), y * cellHeight, (int) ((cellWidth * 0.5) * healthPercent), 10);
    }
    public void setActualHealth(int h){this.actualHealth = h;}
}
