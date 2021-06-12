package main.arda.entity.creature;

import main.arda.item.Item;
import main.arda.util.Maths;
import main.arda.world.World;
import main.arda.util.Console;
import main.arda.entity.Entity;
import main.arda.world.Coordinate;
import main.arda.world.Neighborhood;
import main.arda.world.BoundaryCondition;

/**
 * Creature class.
 */
public abstract class Creature extends Entity {

    /**
     * The Creature's name.
     */
    protected final String NAME;

    /**
     * The Creature's vision.
     */
    protected final int VISION;

    /**
     * The Creature's speed.
     */
    protected final int SPEED;

    /**
     * The Creature's damage.
     */
    protected final int DAMAGE;

    /**
     * The Creature's max age.
     */
    protected final int MAX_AGE;

    /**
     * The Creature's max health.
     */
    protected final int MAX_HEALTH;

    /**
     * The Creature's neighborhood.
     */
    protected final Neighborhood neighborhood;

    /**
     * The Creature's age.
     */
    protected int age = 0;

    /**
     * The Creature's health.
     */
    protected int health = 0;

    /**
     * The Creature's boundary condition.
     */
    protected BoundaryCondition boundaryCondition = BoundaryCondition.BLOCK;

    /**
     * Creature class constructor.
     *
     * @param name
     * @param position
     * @param range
     * @param speed
     * @param damage
     * @param maxAge
     * @param maxHealth
     * @return self
     */
    protected Creature(String name, Coordinate position, int vision, int speed, int damage, int maxAge, int maxHealth) {
        super(position);

        this.NAME = name;
        this.VISION = vision;
        this.SPEED = Maths.clamp(speed, 1, vision);
        this.DAMAGE = damage;
        this.MAX_AGE = maxAge;
        this.MAX_HEALTH = maxHealth;

        this.neighborhood = new Neighborhood(this);

        // Spawn Creatures fully healed.
        this.health = maxHealth();
    }

    /**
     * Get the Creature's name.
     *
     * @return String
     */
    @Override
    public String name() {
        return String.format("%s (%C)", NAME, super.name().charAt(0));
    }

    /**
     * Get the Creature's range of vision.
     *
     * @return int
     */
    public int vision() {
        return VISION + inventory.sumVisionItems();
    }

    /**
     * Get the Creature's speed.
     *
     * @return int
     */
    public int speed() {
        return SPEED;
    }

    /**
     * Get the Creature's defense rating.
     *
     * @return int
     */
    public int defense() {
        return inventory.sumArmorItems();
    }

    /**
     * Get the Creature's max health.
     *
     * @return int
     */
    public int maxHealth() {
        return MAX_HEALTH + inventory.sumMaxHealthItems();
    }

    /**
     * Get the Creature's health.
     *
     * @return int
     */
    public int getHealth() {
        return health;
    }

    /**
     * Check whether the Creature has health.
     *
     * @return boolean
     */
    public boolean hasHealth() {
        return health > 0;
    }

    /**
     * Get the Creature's boundary condition.
     *
     * @return BoundaryCondition
     */
    public BoundaryCondition boundaryCondition() {
        return boundaryCondition;
    }

    /**
     * Tick the Entity.
     *
     * @return void
     */
    @Override
    public void tick() {
        if (isDead()) {
            decay += 1;
            return;
        }

        if (isMaxAge() || !hasHealth()) {
            die();
            onDeathHook();
            return;
        }

        neighborhood.setVision();
        neighborhood.setChoices();

        decide();

        age += 1;
    }

    /**
     * Move the Creature.
     *
     * @param destination
     * @return void
     */
    public void move(Coordinate destination) {
        if (destination == null) {
            Console.out(String.format("%s has nowhere to go", name()), Console.BLACK);
            return;
        }

        position.setFrom(destination);
    }

    /**
     * Heal the Creature.
     *
     * @param amount
     * @return void
     */
    public void heal(int amount) {
        amount = Maths.clamp(amount, 0, maxHealth() - health);

        health += amount;

        Console.out(String.format("%s regained %d health!", name(), amount), Console.GREEN);
    }

    /**
     * Starve the Creature.
     *
     * @param target
     * @return void
     */
    public void starve(int damage) {
        takeStarvationDamage(damage);
    }

    /**
     * Attack a given Creature.
     *
     * @param target
     * @return void
     */
    public void attack(Creature target) {
        target.takeAttackDamage(DAMAGE - target.defense(), this);
    }

    /**
     * Loot a given Entity.
     *
     * @param target
     * @return void
     */
    public void loot(Entity entity) {
        Console.out(String.format("%s tries to loot %s!", name(), entity.name()), Console.WHITE);

        if (entity.getInventory().isEmpty()) {
            return;
        }

        Item item = entity.getInventory().removeItem(0);

        inventory.addItem(item);

        int maxHealthBonus = inventory.sumMaxHealthItems();

        if (maxHealthBonus != 0) {
            health = MAX_HEALTH + maxHealthBonus;
        }

        Console.out(String.format("%s looted %s's %s!", name(), entity.name(), item), Console.BLUE);
    }

    /**
     * Replicate the Creature.
     *
     * @param neighborhood
     * @param occupied
     * @return void
     */
    public void replicate() {
        Coordinate destination = neighborhood.pickDestination();

        if (destination == null) {
            Console.out(String.format("%s has no room to replicate", name()), Console.BLACK);
            return;
        }

        Entity offspring = replicateAt(destination);

        Console.out(String.format("%s spawned %s at %s", name(), offspring.name(), destination), Console.GRAY);

        World.addEntity(offspring);
    }

    /**
     * Formulate a string representation of the object.
     *
     * @return String
     */
    @Override
    public String toString() {
        return String.format(
            "%s [%d/%d] at %s holding %d items", name(), health, maxHealth(), position, inventory.getSize()
        );
    }

    /**
     * Get the name for the Creature's offspring.
     *
     * @return String
     */
    protected String nameOfOffspring() {
        if (!NAME.contains(":")) {
            return NAME + ":2";
        }

        String lineage = NAME.substring(0, NAME.indexOf(":"));
        int generation = Integer.parseInt(NAME.substring(NAME.indexOf(":") + 1));

        return lineage + ":" + (generation + 1);
    }

    /**
     * Check whether the Creature has reached it max age.
     *
     * @return boolean
     */
    protected boolean isMaxAge() {
        // Creatures are immortal if their max age is non-positive.
        return MAX_AGE > 0 && age >= MAX_AGE;
    }

    /**
     * Check whether the Creature has reached replication age.
     *
     * @return boolean
     */
    protected boolean canReplicate() {
        return age > 0;
    }

    /**
     * Make behavioral decisions.
     *
     * @return void
     */
    protected abstract void decide();

    /**
     * Replicate the Entity at the given Coordinate.
     *
     * @param position
     * @return Entity
     */
    protected abstract Entity replicateAt(Coordinate position);

    /**
     * Hook when attacking another Creature.
     *
     * @param target
     * @param damage
     * @return void
     */
    protected void onAttackHook(Creature target, int damage) {
        Console.out(String.format("%s dealt %d damage to %s!", name(), damage, target.name()), Console.MAGENTA);
    }

    /**
     * Hook when killing another Creature.
     *
     * @param target
     * @return void
     */
    protected void onKillHook(Creature target) {
        Console.out(String.format("%s slayed %s!", name(), target.name()), Console.MAGENTA);
    }

    /**
     * Hook when the Creature starves.
     *
     * @return void
     */
    protected void onStarvationHook() {
        Console.out(String.format("%s died of starvation!", name()), Console.RED);
    }

    /**
     * Hook when the Creature dies.
     *
     * @return void
     */
    protected void onDeathHook() {
        Console.out(String.format("%s died!", name()), Console.RED);
    }

    /**
     * Take damage from another Creature.
     *
     * @param amount
     * @param from
     * @return void
     */
    private void takeAttackDamage(int amount, Creature from) {
        int taken = takeDamage(amount);

        from.onAttackHook(this, taken);

        if (hasHealth()) {
            return;
        }

        from.onKillHook(this);

        die();
    }

    /**
     * Take damage from starvation.
     *
     * @param amount
     * @return void
     */
    private void takeStarvationDamage(int amount) {
        takeDamage(amount);

        if (hasHealth()) {
            return;
        }

        onStarvationHook();

        die();
    }

    /**
     * Take damage.
     *
     * @param amount
     * @return int
     */
    private int takeDamage(int amount) {
        int taken = Maths.clamp(amount, 0, health);

        health -= taken;

        return taken;
    }
}
