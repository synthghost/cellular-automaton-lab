package test.arda.entity.shrubbery;

import static org.junit.jupiter.api.Assertions.*;

import java.awt.Color;
import main.arda.world.Coordinate;
import org.junit.jupiter.api.Test;
import main.arda.entity.shrubbery.Bush;

public class BushTest {

    Bush bush = new Bush(new Coordinate(0, 0));

    @Test
    void testColor() {
        assertEquals(new Color(0, 97, 15, 255), bush.color());
    }

    @Test
    void testTick() {
        bush.tick();
        bush.tick();

        bush.die();

        assertFalse(bush.shouldRemove());
    }
}
