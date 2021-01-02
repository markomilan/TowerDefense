package model;

import view.resloader.GameGallery;

public class ShotgunTurret extends Turret {

    public ShotgunTurret(GameGallery gameGallery)
    {
        super();
        attackSpeed = 1;
        damage = 2;
        buildCost = 5;
        id = 2;
        improveCost = 4;
        maxHealth = 13;
        actualHealth = maxHealth;
        aoe = true;
        baseMove = gameGallery.animationShotGunBaseMove;
        impMove = gameGallery.animationShotGunImpMove;
    }

    @Override
    public void improveStructure() {
        this.improved = true;
        this.maxHealth += 2;
        this.actualHealth += 2;
    }

    public Pair[] getFieldsInRange(Pair location)
    {
        int x = location.getX();
        int y = location.getY();
        return new Pair[]{new Pair(x, y-1), new Pair(x, y+1), new Pair(x + 1, y), new Pair(x - 1, y)};
    }

}
