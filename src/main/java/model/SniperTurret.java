package model;

import controller.ingame.GameSession;
import view.resloader.GameGallery;

public class SniperTurret extends Turret {

    public SniperTurret(GameGallery gameGallery)
    {
        super();
        attackSpeed = 1;
        damage = 3;
        buildCost = 5;
        id = 1;
        improveCost = 4;
        maxHealth = 7;
        actualHealth = maxHealth;
        aoe = false;
        baseMove = gameGallery.animationSniperBaseMove;
        impMove = gameGallery.animationSniperImpMove;
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
        Pair[] ret = new Pair[25];
        for (int i = -2; i < 3; ++i)
        {
            for (int j = -2; j < 3; ++j)
            {
                ret[(i+2) * 5 + (j+2)] = new Pair(x + j, y + i);
            }
        }
        return ret;
    }

    @Override
    public void tryShoot(boolean slow, Pair location, GameSession game) {
        if (slow)
        {
            boolean needsToReload = false;
            Pair[] fieldsToShoot = getFieldsInRange(location);
            for (Pair p : fieldsToShoot) {
                if (!needsToReload) {
                    Field field = game.getField(p);
                    if (field != null) {
                        needsToReload = field.hasAliveEnemies();
                        if (field.getEnemies().size() > 0) {
                            field.damageEnemies(damage, false, game.getActualPlayer());
                        }
                    }
                }
            }
        }
    }

}
