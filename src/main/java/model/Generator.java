package model;

import controller.ingame.GameSession;
import view.resloader.GameGallery;

public class Generator extends Structure {

    public Generator(GameGallery gameGallery)
    {
        maxHealth = 3;
        actualHealth = maxHealth;
        buildCost = 10;
        improved = false;
        improveCost = 7;
        id = 4;
        baseMove = gameGallery.animationGeneratorBaseMove;
        impMove = gameGallery.animationGeneratorImpMove;
    }

    @Override
    public void improveStructure() {
        this.improved = true;
        this.maxHealth += 2;
        this.actualHealth += 2;
    }

    @Override
    public void tryShoot(boolean slow, Pair location, GameSession game) {

    }
}
