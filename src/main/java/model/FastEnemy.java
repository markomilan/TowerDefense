package model;

import model.menu.Difficulty;
import view.resloader.GameGallery;

public class FastEnemy extends Enemy {

    public FastEnemy(Pair p, GameGallery gameGallery, Difficulty difficulty) {
        super(p);
        goldRewards = 5;
        moveSpeed = 2;
        maxHealth = 5;
        damage = 1;
        switch (difficulty)
        {
            case MEDIUM:
                maxHealth += 5;
                damage += 2;
                break;
            case HARD:
                maxHealth += 10;
                damage += 4;
                break;
            default:
                break;
        }
        health = maxHealth;
        id = 1;
        animationAtk = gameGallery.animationFastEnemyAtk;
        animationDie = gameGallery.animationFastEnemyDie;
        animationRun = gameGallery.animationFastEnemyRun;
    }
}
