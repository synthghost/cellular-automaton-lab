package main.arda.entity.creature;

import java.awt.Color;

import main.arda.util.Config;
import main.arda.entity.Entity;
import main.arda.util.Graphics;
import main.arda.world.Coordinate;
import main.arda.world.TargetPriority;

/**
 * Nazgul class.
 */
public class Nazgul extends Creature {

    /**
     * Nazgul class constructor.
     *
     * @param name
     * @param position
     * @return self
     */
    public Nazgul(String name, Coordinate position) {
        super(
            name, position,
            Config.getInt("entity.creature.nazgul.vision"),
            Config.getInt("entity.creature.nazgul.speed"),
            Config.getInt("entity.creature.nazgul.damage"),
            Config.getInt("entity.creature.nazgul.maxAge"),
            Config.getInt("entity.creature.nazgul.maxHealth")
        );
    }

    /**
     * Get the Nazgul's color.
     *
     * @return Color
     */
    @Override
    public Color color() {
        return Graphics.fade(Graphics.nazgul, (double) health / MAX_HEALTH);
    }

    /**
     * Make behavioral decisions.
     *
     * @return void
     */
    @Override
    public void decide() {
        starve(1);

        if (neighborhood.canNotMove()) {
            return;
        }

        Entity hobbit = neighborhood.findNearestEntity(Hobbit.class);

        if (hobbit != null) {
            Coordinate choice = neighborhood.findEntityPosition(hobbit, TargetPriority.CLOSEST);

            move(choice);

            if (!choice.isInRange(hobbit.getPosition(), 1)) {
                return;
            }

            attack((Hobbit) hobbit);

            return;
        }

        if (canReplicate() && age % 15 == 0) {
            replicate();
            return;
        }

        Coordinate destination = neighborhood.pickDestination();

        move(destination);
    }

    /**
     * Replicate the Nazgul at the given Coordinate.
     *
     * @param position
     * @return Entity
     */
    @Override
    protected Entity replicateAt(Coordinate position) {
        return new Nazgul(nameOfOffspring(), position);
    }

    /**
     * On kill hook.
     *
     * @param target
     * @return void
     */
    @Override
    protected void onAttackHook(Creature target, int damage) {
        super.onAttackHook(target, damage);
        heal(damage * 5);
    }
}
