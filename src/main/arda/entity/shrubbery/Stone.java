package main.arda.entity.shrubbery;

import java.awt.Color;
import main.arda.item.Ring;
import main.arda.util.Maths;
import main.arda.util.Console;
import main.arda.util.Graphics;
import main.arda.world.Coordinate;

/**
 * Stone class.
 */
public class Stone extends Shrubbery {

    /**
     * Stone class constructor.
     *
     * @param position
     * @return self
     */
    public Stone(Coordinate position) {
        super(position);

        if (Maths.randomChance(10)) {
            Console.out(String.format("%s at %s has a Ring!", name(), position), Console.YELLOW);
            inventory.addItem(new Ring());
        }
    }

    /**
     * Get the Stone's color.
     *
     * @return Color
     */
    @Override
    public Color color() {
        return Graphics.stones;
    }

    /**
     * Tick the Stone.
     *
     * @return void
     */
    @Override
    public void tick() {
        // Stones do nothing when ticked.
    }
}
