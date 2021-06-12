package main.arda.entity.shrubbery;

import java.awt.Color;
import main.arda.item.Apple;
import main.arda.util.Maths;
import main.arda.item.Mithril;
import main.arda.util.Console;
import main.arda.util.Graphics;
import main.arda.world.Coordinate;

/**
 * Bush class.
 */
public class Bush extends Shrubbery {

    /**
     * Bush class constructor.
     *
     * @param position
     * @return self
     */
    public Bush(Coordinate position) {
        super(position);

        if (Maths.randomChance(10)) {
            Console.out(String.format("%s at %s has a Mithril!", name(), position), Console.YELLOW);
            inventory.addItem(new Mithril());
        }
    }

    /**
     * Get the Bush's color.
     *
     * @return Color
     */
    @Override
    public Color color() {
        return Graphics.bushes;
    }

    /**
     * Tick the Bush.
     *
     * @return void
     */
    @Override
    public void tick() {
        if (isDead()) {
            return;
        }

        if (decay > 100 && Maths.randomChance(1)) {
            die();
            return;
        }

        decay += 1;

        if (inventory.isEmpty() && Maths.randomChance(30)) {
            Console.out(String.format("%s spawned an Apple!", name()), Console.YELLOW);
            inventory.addItem(new Apple());
        }
    }
}
