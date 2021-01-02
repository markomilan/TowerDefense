package model;

import view.resloader.GameGallery;

public class MachineGunTurret extends Turret {

    public MachineGunTurret(GameGallery gameGallery) {
        super();
        attackSpeed = 2;
        damage = 1;
        buildCost = 5;
        id = 3;
        improveCost = 4;
        maxHealth = 10;
        actualHealth = maxHealth;
        aoe = false;
        baseMove = gameGallery.animationMachineGunBaseMove;
        impMove = gameGallery.animationMachineGunImpMove;
    }

    @Override
    public void improveStructure() {
        this.improved = true;
        this.maxHealth += 2;
        this.actualHealth += 2;
    }

    protected Pair[] getFieldsInRange(Pair location)
    {
        int x = location.getX();
        int y = location.getY();
        Pair[] ret = new Pair[9];
        for (int i = -1; i < 2; ++i)
        {
            for (int j = -1; j < 2; ++j)
            {
                ret[(i+1) * 3 + (j+1)] = new Pair(x + j, y + i);
            }
        }
        return ret;
    }
}
