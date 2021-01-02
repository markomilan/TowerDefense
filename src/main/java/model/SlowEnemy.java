package model;

import model.menu.Difficulty;
import view.resloader.GameGallery;

public class SlowEnemy extends Enemy {
    public SlowEnemy(Pair p, GameGallery gameGallery, Difficulty difficulty) {
        super(p);
        goldRewards = 4;
        moveSpeed = 6;
        maxHealth = 15;
        damage = 3;
        switch (difficulty)
        {
            case MEDIUM:
                maxHealth += 10;
                damage += 4;
                break;
            case HARD:
                maxHealth += 20;
                damage += 7;
                break;
            default:
                break;
        }
        health = maxHealth;
        id = 3;
        animationAtk = gameGallery.animationSlowEnemyAtk;
        animationDie = gameGallery.animationSlowEnemyDie;
        animationRun = gameGallery.animationSlowEnemyRun;
    }
}
