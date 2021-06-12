package main.arda.item;

/**
 * Item class.
 */
public abstract class Item {

    /**
     * The Item's vision bonus.
     */
    public final int VISION_BONUS;

    /**
     * The Item's health bonus.
     */
    public final int HEALTH_BONUS;

    /**
     * The Item's defense bonus.
     */
    public final int DEFENSE_BONUS;

    /**
     * The Item's max health bonus.
     */
    public final int MAX_HEALTH_BONUS;

    /**
     * Item class constructor.
     *
     * @param vision
     * @param health
     * @param defense
     * @param maxHealth
     * @return self
     */
    protected Item(int vision, int health, int defense, int maxHealth) {
        this.VISION_BONUS = vision;
        this.HEALTH_BONUS = health;
        this.DEFENSE_BONUS = defense;
        this.MAX_HEALTH_BONUS = maxHealth;
    }

    /**
     * Get the Item's name.
     *
     * @return String
     */
    public String name() {
        return getClass().getSimpleName();
    }

    /**
     * Formulate a string representation of the Item.
     *
     * @return String
     */
    @Override
    public String toString() {
        return name();
    }
}
