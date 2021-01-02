package model;

import view.resloader.GameGallery;

import java.awt.Graphics2D;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.HashSet;

public class Field {
    private List<Enemy> enemies;
    private Structure structure;
    private int enemyTypes;

    public Field() {
        enemies = new ArrayList<>();
        enemyTypes = 0;
    }

    public Enemy getEnemy(int index) throws IllegalArgumentException {
        if (index < 0 || index > 2)
            throw new IllegalArgumentException("Index is out of bounds: " + index);
        return enemies.get(index);
    }
    public Structure getStructure() {
        return structure;
    }
    public String getFieldType(){
        String s;
        if (enemies.size() > 0){
            s = "E";
        }else if(this.structure != null){
            s = "S";
        }else{
            s = "e";
        }
        return s;
    }
    public void build(Structure structure) {
        this.structure = structure;
    }
    public List<Enemy> getEnemies()
    {
        return enemies;
    }
    public void draw(Graphics2D g, int cellWidth, int cellHeight, Pair location, GameGallery gameGallery) throws IOException {
        if (structure != null) {
            structure.draw(g, cellWidth, cellHeight, location, gameGallery);
        }
        else {
            for (Enemy enemy : enemies) {
                enemy.draw(g, cellWidth, cellHeight);
            }
        }
    }

    public boolean canFitEnemy(Enemy enemy){
        // enemyTypes: 0-7: b000 if empty, b001 if FastEnemy, b010 if GenericEnemy, b100 if SlowEnemy,
        // b011 if GenericEnemy and FastEnemy etc.
        // enemy.getId() returns 1,2,3 -> 2^(Id-1) will be b001, b010, b100.
        return ((int)Math.pow(2, enemy.getId() - 1) & enemyTypes) == 0;
    }

    public void addEnemy(Enemy enemy){
        enemyTypes += (int)Math.pow(2, enemy.getId() - 1);
        enemies.add(enemy);
    }

    public void demolishStructure() {
        structure = null;
    }

    public void move(int time, Field nextField) {
        HashSet<Enemy> delete = new HashSet<>();
        // Check for enemies who were waiting for an enemy in front of them.
        for (Enemy enemy : enemies)
        {
            if (!enemy.dying() && nextField.canFitEnemy(enemy) && !enemy.isRunning()){
                enemy.setRunning(true);
            }
        }
        // Main moving loop
        for (Enemy enemy : enemies)
        {
            // Enemies move every @moveSpeed seconds
            if (Math.floorMod(time, enemy.getMoveSpeed()) == 0)
            {
                if (enemy.dying())
                {
                    // Enemies disappear when their health is < 0 and try to move
                    delete.add(enemy);
                }
                // Else: if they're alive
                else if (nextField.getStructure() != null) {
                    // If there is a structure in the way: hit it
                    enemy.setRunning(false);
                    enemy.setAttacking(true);
                    nextField.getStructure().hit(enemy.getDamage());
                    if (nextField.getStructure().isDemolished())
                    {
                        // If the structure is demolished: all enemies should start moving again
                        nextField.demolishStructure();
                        for (Enemy enemyDelStruct : enemies) {
                            // If they fit in the previous field, they will fit the next one too, no canFitEnemy needed
                            enemyDelStruct.setAttacking(false);
                            nextField.addEnemy(enemyDelStruct);
                            enemyDelStruct.resetPixelLoc(70);
                            enemyDelStruct.setRunning(true);
                            enemyDelStruct.move();
                            delete.add(enemyDelStruct);
                        }
                    }
                }
                else {
                    // There is no structure in the next field: If they can move, they should
                    enemy.setAttacking(false);
                    if (nextField.canFitEnemy(enemy))
                    {
                        nextField.addEnemy(enemy);
                        enemy.setRunning(true);
                        enemy.move();
                        delete.add(enemy);
                    }
                    else
                    {
                        // The enemy can't fit in the next field, wait for next move.
                        enemy.setRunning(false);
                        enemy.resetPixelLoc(70);
                    }
                }
            }
        }
        // Remove enemies that left the field and corpses that tried to move
        for (Enemy enemy : delete) {
            enemyTypes -= Math.pow(2, (enemy.getId() - 1));
            enemies.remove(enemy);
        }

    }

    public void damageEnemies(int damage, boolean isAoE, Player player) {
        if (isAoE) {
            for (Enemy enemy : enemies) {
                if (!enemy.dying()) {
                    enemy.removeHealth(damage);
                    if (enemy.dying())
                    {
                        player.addGold(enemy.goldRewards);
                    }
                }
            }
        }
        else
        {
            Iterator<Enemy> it = enemies.iterator();
            Enemy enemy;
            boolean found = false;
            while(it.hasNext() && !found)
            {
                enemy = it.next();
                if (!enemy.dying()) {
                    enemy.removeHealth(damage);
                    if (enemy.dying())
                    {
                        player.addGold(enemy.goldRewards);
                    }
                    found = true;
                }
            }
        }
    }
    public int getEnemyCount() {return enemies.size();}
    public boolean hasAliveEnemies() {
        for (Enemy enemy : enemies) {
            if (!enemy.dying()) {
                return true;
            }
        }
        return false;
    }
}
