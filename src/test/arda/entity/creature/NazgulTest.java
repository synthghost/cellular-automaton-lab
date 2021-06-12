package test.arda.entity.creature;

import static org.junit.jupiter.api.Assertions.*;

import java.awt.Color;
import main.arda.world.World;
import test.arda.ResetsWorld;
import main.arda.world.Coordinate;
import org.junit.jupiter.api.Test;
import main.arda.entity.creature.Hobbit;
import main.arda.entity.creature.Nazgul;
import main.arda.entity.shrubbery.Stone;
import org.junit.jupiter.api.BeforeEach;

public class NazgulTest extends ResetsWorld {

    Nazgul nazgul;

    @Override
    @BeforeEach
    public void init() {
        super.init();

        nazgul = new Nazgul("Witch King", new Coordinate(10, 10));

        World.addEntity(nazgul);
    }

    @Test
    void testColor() {
        assertEquals(new Color(182, 5, 26, 255), nazgul.color());

        nazgul.starve(5);

        assertEquals(new Color(182, 5, 26, 205), nazgul.color());
    }

    @Test
    void testDecide() {
        Stone stone1 = new Stone(new Coordinate(8, 10));
        Stone stone2 = new Stone(new Coordinate(8, 11));
        Stone stone3 = new Stone(new Coordinate(9, 9));
        Stone stone4 = new Stone(new Coordinate(9, 10));
        Stone stone5 = new Stone(new Coordinate(9, 11));
        Stone stone6 = new Stone(new Coordinate(9, 12));
        Stone stone7 = new Stone(new Coordinate(10, 8));
        Stone stone8 = new Stone(new Coordinate(10, 9));
        Stone stone9 = new Stone(new Coordinate(10, 11));
        Stone stone10 = new Stone(new Coordinate(10, 12));
        Stone stone11 = new Stone(new Coordinate(10, 13));
        Stone stone12 = new Stone(new Coordinate(11, 9));
        Stone stone13 = new Stone(new Coordinate(11, 10));
        Stone stone14 = new Stone(new Coordinate(11, 11));
        Stone stone15 = new Stone(new Coordinate(11, 12));
        Stone stone16 = new Stone(new Coordinate(12, 10));
        Stone stone17 = new Stone(new Coordinate(12, 11));

        World.addEntity(stone1);
        World.addEntity(stone2);
        World.addEntity(stone3);
        World.addEntity(stone4);
        World.addEntity(stone5);
        World.addEntity(stone6);
        World.addEntity(stone7);
        World.addEntity(stone8);
        World.addEntity(stone9);
        World.addEntity(stone10);
        World.addEntity(stone11);
        World.addEntity(stone12);
        World.addEntity(stone13);
        World.addEntity(stone14);
        World.addEntity(stone15);
        World.addEntity(stone16);
        World.addEntity(stone17);

        // Ticking calls decide()
        nazgul.tick();

        // Nazgul is surrounded and can't move
        assertEquals(new Coordinate(10, 10), nazgul.getPosition());

        World.getEntities().remove(stone9);

        nazgul.tick();

        // Nazgul has an opening
        assertEquals(new Coordinate(10, 11), nazgul.getPosition());

        nazgul.tick();

        // Nazgul moves to the only other opening, back from where it came
        assertEquals(new Coordinate(10, 10), nazgul.getPosition());
    }

    @Test
    void testDecideWhenHobbitNearby() {
        Hobbit hobbit1 = new Hobbit("A", new Coordinate(10, 7));

        World.addEntity(hobbit1);

        // Ticking calls decide()
        nazgul.tick();

        // Nazgul has an opening
        assertEquals(new Coordinate(10, 8), nazgul.getPosition());

        nazgul.tick();

        // Nazgul moves back from where it came to get closer to Hobbits
        assertNotEquals(new Coordinate(10, 8), nazgul.getPosition());
    }
}
