package model;

import controller.ingame.GameSession;

public abstract class Turret extends Structure {
    protected int damage;
    protected int attackSpeed;

    public Turret()
    {
        improved = false;
    }

    public int getId()
    {return id;}

    protected abstract Pair[] getFieldsInRange(Pair location);

    public void tryShoot(boolean slow, Pair location, GameSession game) {
        Structure structure = game.getField(location).getStructure();

        if (slow || attackSpeed == 2)
        {
            Pair[] fieldsToShoot = getFieldsInRange(location);
            for (Pair p : fieldsToShoot) {
                Field field = game.getField(p);
                if (field != null) {
                    field.damageEnemies(damage, structure.isAoE(), game.getActualPlayer());
                }
            }
        }
    }
}
