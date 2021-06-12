package main.arda.entity.creature;

import java.awt.Color;
import main.arda.item.Meat;
import main.arda.util.Config;
import main.arda.entity.Entity;
import main.arda.util.Graphics;
import main.arda.world.Coordinate;

/**
 * Rabbit class.
 */
public class Rabbit extends Creature {

    /**
     * Rabbit class constructor.
     *
     * @param name
     * @param position
     * @return self
     */
    public Rabbit(String name, Coordinate position) {
        super(
            name, position,
            Config.getInt("entity.creature.rabbit.vision"),
            Config.getInt("entity.creature.rabbit.speed"),
            Config.getInt("entity.creature.rabbit.damage"),
            Config.getInt("entity.creature.rabbit.maxAge"),
            Config.getInt("entity.creature.rabbit.maxHealth")
        );

        inventory.addItem(new Meat());
    }

    /**
     * Get the Rabbit's color.
     *
     * @return Color
     */
    @Override
    public Color color() {
        return Graphics.fade(Graphics.rabbits, (double) health / MAX_HEALTH);
    }

    /**
     * Make behavioral decisions.
     *
     * @return void
     */
    @Override
    public void decide() {
        if (canReplicate() && age % 3 == 0 && age < MAX_AGE / 2) {
            replicate();
            return;
        }

        Coordinate destination = neighborhood.pickDestination();

        move(destination);
    }

    /**
     * Replicate the Rabbit at the given Coordinate.
     *
     * @param position
     * @return Entity
     */
    @Override
    protected Entity replicateAt(Coordinate position) {
        return new Rabbit(nameOfOffspring(), position);
    }
}
