package main.arda.entity.creature;

import java.awt.Color;
import main.arda.item.Item;
import main.arda.item.Apple;
import main.arda.util.Config;
import main.arda.util.Console;
import main.arda.entity.Entity;
import main.arda.util.Graphics;
import main.arda.world.Coordinate;
import main.arda.world.TargetPriority;
import main.arda.world.BoundaryCondition;
import main.arda.entity.shrubbery.Shrubbery;

/**
 * Hobbit class.
 */
public class Hobbit extends Creature {

    /**
     * The Hobbit's max nourishment.
     */
    private final int MAX_NOURISHMENT;

    /**
     * The Hobbit's nourishment.
     */
    private int nourishment;

    /**
     * Hobbit class constructor.
     *
     * @param name
     * @param position
     * @return self
     */
    public Hobbit(String name, Coordinate position) {
        super(
            name, position,
            Config.getInt("entity.creature.hobbit.vision"),
            Config.getInt("entity.creature.hobbit.speed"),
            Config.getInt("entity.creature.hobbit.damage"),
            Config.getInt("entity.creature.hobbit.maxAge"),
            Config.getInt("entity.creature.hobbit.maxHealth")
        );

        boundaryCondition = BoundaryCondition.PASS;

        MAX_NOURISHMENT = 20;
        nourishment = MAX_NOURISHMENT;

        inventory.addItem(new Apple());
    }

    /**
     * Get the Hobbit's color.
     *
     * @return Color
     */
    @Override
    public Color color() {
        return Graphics.fade(Graphics.hobbits, (double) health / MAX_HEALTH);
    }

    /**
     * Make behavioral decisions.
     *
     * @return void
     */
    @Override
    public void decide() {
        inventory.scan();

        nourishment -= 1;

        // There are no open spaces for the Hobbit to move into
        if (neighborhood.canNotMove()) {
            rummage();
            eat();
            return;
        }

        Entity nazgul = neighborhood.findNearestEntity(Nazgul.class);

        if (nazgul != null) {
            Coordinate choice = neighborhood.findEntityPosition(nazgul, TargetPriority.FURTHEST);
            move(choice);
            return;
        }

        if (isHungry() && inventory.hasFood()) {
            eat();
            return;
        }

        if (canReplicate() && age % 30 == 0 && age < MAX_AGE / 2) {
            replicate();
            return;
        }

        Entity rabbit = neighborhood.findNearestEntity(Rabbit.class);

        if (rabbit != null) {
            Coordinate choice = neighborhood.findEntityPosition(rabbit, TargetPriority.CLOSEST);

            move(choice);

            if (!choice.isInRange(rabbit.getPosition(), 1)) {
                return;
            }

            attack((Rabbit) rabbit);

            return;
        }

        Entity hobbit = neighborhood.findNearestEntity(Hobbit.class);

        if (hobbit != null) {
            Coordinate choice = neighborhood.findEntityPosition(hobbit, TargetPriority.CLOSEST);
            move(choice);
            return;
        }

        Coordinate destination = neighborhood.pickDestination();

        move(destination);

        rummage();
    }

    /**
     * Move the Hobbit.
     *
     * @param destination
     * @return void
     */
    @Override
    public void move(Coordinate destination) {
        if (isHungry()) {
            starve(1);
        }

        super.move(destination);
    }

    /**
     * Eat the given food Item.
     *
     * @param food
     * @return void
     */
    public void eat() {
        if (!isHungry()) {
            return;
        }

        if (!inventory.hasFood()) {
            starve(1);
            return;
        }

        Item food = inventory.getFood().get(0);

        nourishment = MAX_NOURISHMENT;

        Console.out(String.format("%s ate %s!", name(), food.name()), Console.CYAN);

        heal(food.HEALTH_BONUS);

        inventory.removeItem(food);
        inventory.scan();
    }

    /**
     * Replicate the Hobbit at the given Coordinate.
     *
     * @param position
     * @return Entity
     */
    @Override
    protected Entity replicateAt(Coordinate position) {
        if (isHungry()) {
            starve(1);
        }

        return new Hobbit(nameOfOffspring(), position);
    }

    /**
     * On kill hook.
     *
     * @param target
     * @return void
     */
    @Override
    protected void onKillHook(Creature target) {
        super.onKillHook(target);
        loot(target);
    }

    /**
     * Check if Hobbit is hungry.
     *
     * @return boolean
     */
    private boolean isHungry() {
        return nourishment <= 0;
    }

    /**
     * Try to loot nearby Shrubbery.
     *
     * @return void
     */
    private void rummage() {
        Entity shrubbery = neighborhood.findNearestEntity(Shrubbery.class);

        if (shrubbery == null) {
            return;
        }

        if (!position.isInRange(shrubbery.getPosition(), 2)) {
            return;
        }
        
        loot((Shrubbery) shrubbery);
    }
}
