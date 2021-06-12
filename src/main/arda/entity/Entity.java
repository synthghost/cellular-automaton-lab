package main.arda.entity;

import java.awt.Color;
import main.arda.world.Coordinate;

/**
 * Entity class.
 */
public abstract class Entity implements Comparable<Entity> {

    /**
     * The Entity's position in the World.
     */
    protected final Coordinate position;

    /**
     * The Entity's inventory.
     */
    protected final Inventory inventory = new Inventory();

    /**
     * Whether the Entity is alive.
     */
    protected boolean isAlive = true;

    /**
     * The Entity's stage of decay.
     */
    protected int decay = 0;

    /**
     * Entity class constructor.
     *
     * @param xPos
     * @param yPos
     * @return self
     */
    protected Entity(Coordinate position) {
        this.position = new Coordinate(position);
    }

    /**
     * Get the Entity's position.
     *
     * @return Coordinate
     */
    public Coordinate getPosition() {
        return new Coordinate(position);
    }

    /**
     * Get the Entity's inventory.
     *
     * @return Inventory
     */
    public Inventory getInventory() {
        return inventory;
    }

    /**
     * Check whether the Entity is alive.
     *
     * @return boolean
     */
    public boolean isAlive() {
        return isAlive;
    }

    /**
     * Check whether the Entity is dead.
     *
     * @return boolean
     */
    public boolean isDead() {
        return !isAlive();
    }

    /**
     * Check whether an Entity should be removed.
     *
     * @return boolean
     */
    public boolean shouldRemove() {
        return isDead() && decay > 2;
    }

    /**
     * Get the Entity's name.
     *
     * @return String
     */
    public String name() {
        return getClass().getSimpleName();
    }

    /**
     * Get the Entity's color.
     *
     * @return Color
     */
    public abstract Color color();

    /**
     * Tick the Entity.
     *
     * @return void
     */
    public abstract void tick();

    /**
     * Kill the Entity.
     *
     * @return void
     */
    public void die() {
        isAlive = false;
    }

    /**
     * Compare a given Entity to the current instance.
     *
     * @param other
     * @return int
     */
    @Override
    public int compareTo(Entity other) {
        return this.position.compareTo(other.position);
    }

    /**
     * Formulate a string representation of the Entity.
     *
     * @return String
     */
    @Override
    public String toString() {
        return String.format("%s at %s holding %d items", name(), position, inventory.getSize());
    }
}
