package model;

import model.menu.Difficulty;
import view.resloader.GameGallery;

public class GenericEnemy extends Enemy {
    public GenericEnemy(Pair p, GameGallery gameGallery, Difficulty difficulty) {
        super(p);
        goldRewards = 5;
        moveSpeed = 4;
        maxHealth = 10;
        damage = 2;
        switch (difficulty)
        {
            case MEDIUM:
                maxHealth += 7;
                damage += 3;
                break;
            case HARD:
                maxHealth += 15;
                damage += 5;
                break;
            default:
                break;
        }
        health = maxHealth;
        id = 2;
        animationAtk = gameGallery.animationGenericEnemyAtk;
        animationDie = gameGallery.animationGenericEnemyDie;
        animationRun = gameGallery.animationGenericEnemyRun;
        //pixelLocation += 19;    // For some reason this enemy spawns 19 pixels behind. We fix it here.
    }
}
