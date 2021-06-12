package main.arda.entity.creature;

import java.awt.Color;
import main.arda.util.Maths;
import main.arda.util.Config;
import main.arda.entity.Entity;
import main.arda.util.Graphics;
import main.arda.world.Coordinate;
import main.arda.entity.shrubbery.Bush;

/**
 * Ent class.
 */
public class Ent extends Creature {

    /**
     * Ent class constructor.
     *
     * @param name
     * @param position
     * @return self
     */
    public Ent(String name, Coordinate position) {
        super(
            name, position,
            Config.getInt("entity.creature.ent.vision"),
            Config.getInt("entity.creature.ent.speed"),
            Config.getInt("entity.creature.ent.damage"),
            Config.getInt("entity.creature.ent.maxAge"),
            Config.getInt("entity.creature.ent.maxHealth")
        );
    }

    /**
     * Get the Ent's color.
     *
     * @return Color
     */
    @Override
    public Color color() {
        return Graphics.ents;
    }

    /**
     * Make behavioral decisions.
     *
     * @return void
     */
    @Override
    public void decide() {
        if (neighborhood.canNotMove() || Maths.randomChance(60)) {
            return;
        }

        if (canReplicate() && age % 25 == 0) {
            replicate();
            return;
        }

        Coordinate destination = neighborhood.pickDestination();

        move(destination);
    }

    /**
     * Replicate the Ent at the given Coordinate.
     *
     * @param position
     * @return Entity
     */
    @Override
    protected Entity replicateAt(Coordinate position) {
        return new Bush(position);
    }
}
