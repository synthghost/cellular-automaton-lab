package test.arda.entity.shrubbery;

import static org.junit.jupiter.api.Assertions.*;

import java.awt.Color;
import main.arda.world.Coordinate;
import org.junit.jupiter.api.Test;
import main.arda.entity.shrubbery.Stone;

public class StoneTest {

    Stone bush = new Stone(new Coordinate(0, 0));

    @Test
    void testColor() {
        assertEquals(new Color(100, 100, 100, 255), bush.color());
    }
}
