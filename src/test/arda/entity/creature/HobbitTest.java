package test.arda.entity.creature;

import static org.junit.jupiter.api.Assertions.*;

import java.awt.Color;
import main.arda.world.World;
import test.arda.ResetsWorld;
import main.arda.world.Coordinate;
import org.junit.jupiter.api.Test;
import main.arda.entity.creature.Hobbit;
import main.arda.entity.creature.Nazgul;
import main.arda.entity.creature.Rabbit;
import main.arda.entity.shrubbery.Stone;
import org.junit.jupiter.api.BeforeEach;

public class HobbitTest extends ResetsWorld {

    Hobbit hobbit;

    @Override
    @BeforeEach
    public void init() {
        super.init();

        hobbit = new Hobbit("Bilbo", new Coordinate(10, 10));

        World.addEntity(hobbit);
    }

    @Test
    void testColor() {
        assertEquals(new Color(0, 200, 255, 255), hobbit.color(), hobbit.toString());

        hobbit.starve(5);

        assertEquals(new Color(0, 200, 255, 155), hobbit.color());
    }

    @Test
    void testDecide() {
        Stone stone1 = new Stone(new Coordinate(9, 10));
        Stone stone2 = new Stone(new Coordinate(10, 9));
        Stone stone3 = new Stone(new Coordinate(11, 10));
        Stone stone4 = new Stone(new Coordinate(10, 11));

        World.addEntity(stone1);
        World.addEntity(stone2);
        World.addEntity(stone3);
        World.addEntity(stone4);

        // Ticking calls decide()
        hobbit.tick();

        // Hobbit is surrounded and can't move
        assertEquals(new Coordinate(10, 10), hobbit.getPosition());

        World.getEntities().remove(stone4);

        hobbit.tick();

        // Hobbit has an opening
        assertEquals(new Coordinate(10, 11), hobbit.getPosition());

        hobbit.tick();

        // Hobbit moves somewhere else
        assertNotEquals(new Coordinate(10, 11), hobbit.getPosition());
    }

    @Test
    void testDecideWhenNazgulNearby() {
        Nazgul nazgul1 = new Nazgul("A", new Coordinate(9, 10));
        Nazgul nazgul2 = new Nazgul("B", new Coordinate(10, 9));
        Nazgul nazgul3 = new Nazgul("C", new Coordinate(11, 10));
        Nazgul nazgul4 = new Nazgul("D", new Coordinate(10, 11));

        World.addEntity(nazgul1);
        World.addEntity(nazgul2);
        World.addEntity(nazgul3);
        World.addEntity(nazgul4);

        // Ticking calls decide()
        hobbit.tick();

        // Hobbit is surrounded and can't move
        assertEquals(new Coordinate(10, 10), hobbit.getPosition());

        World.getEntities().remove(nazgul4);

        hobbit.tick();

        // Hobbit has an opening
        assertEquals(new Coordinate(10, 11), hobbit.getPosition());

        hobbit.tick();

        // Hobbit does not move back from where it came, as that would
        // move it closer to the Nazgul
        assertNotEquals(new Coordinate(10, 10), hobbit.getPosition());

        // Hobbit moves somewhere else
        assertNotEquals(new Coordinate(10, 11), hobbit.getPosition());
    }

    @Test
    void testDecideWhenRabbitNearby() {
        Rabbit rabbit1 = new Rabbit("A", new Coordinate(10, 7));

        World.addEntity(rabbit1);

        // Ticking calls decide()
        hobbit.tick();

        // Hobbit moves toward Rabbit
        assertEquals(new Coordinate(10, 9), hobbit.getPosition());

        hobbit.tick();

        // Hobbit moves somewhere else
        assertNotEquals(new Coordinate(10, 9), hobbit.getPosition());
    }

    @Test
    void testDecideWhenHobbitNearby() {
        Hobbit hobbit1 = new Hobbit("A", new Coordinate(10, 7));

        World.addEntity(hobbit1);

        // Ticking calls decide()
        hobbit.tick();

        // Hobbit moves toward other Hobbit
        assertEquals(new Coordinate(10, 9), hobbit.getPosition());

        hobbit.tick();

        // Hobbit moves somewhere else
        assertNotEquals(new Coordinate(10, 9), hobbit.getPosition());
    }

    @Test
    void testMove() {
        hobbit.move(new Coordinate(11, 11));

        assertEquals(new Coordinate(11, 11), hobbit.getPosition());

        hobbit.move(null);

        assertEquals(new Coordinate(11, 11), hobbit.getPosition());
    }

    @Test
    void testEat() {
        hobbit.getInventory().scan();

        // Reduce the Hobbit's health
        hobbit.starve(5);

        assertEquals(5, hobbit.getHealth());
        assertTrue(hobbit.getInventory().hasFood());

        for (int i = 0; i < 20; i++) {
            // Ticking calls eat()
            hobbit.tick();
        }

        assertEquals(7, hobbit.getHealth());
        assertFalse(hobbit.getInventory().hasFood());

        // When out of food, eating again has no effect
        hobbit.eat();

        assertEquals(7, hobbit.getHealth());
    }
}
