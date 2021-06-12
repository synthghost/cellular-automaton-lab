package test.arda.entity.creature;

import static org.junit.jupiter.api.Assertions.*;

import java.awt.Color;
import main.arda.world.Coordinate;
import org.junit.jupiter.api.Test;
import main.arda.entity.creature.Ent;

public class EntTest {

    Ent ent = new Ent("Treebeard", new Coordinate(0, 0));

    @Test
    void testColor() {
        assertEquals(new Color(50, 182, 48, 255), ent.color());
    }
}
