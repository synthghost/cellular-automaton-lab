package main.arda.entity.shrubbery;

import main.arda.entity.Entity;
import main.arda.world.Coordinate;

/**
 * Shrubbery class.
 */
public abstract class Shrubbery extends Entity {

    /**
     * Shrubbery class constructor.
     *
     * @param position
     * @return self
     */
    protected Shrubbery(Coordinate position) {
        super(position);
    }
}
