package test.arda.entity.creature;

import static org.junit.jupiter.api.Assertions.*;

import java.awt.Color;
import main.arda.world.World;
import test.arda.ResetsWorld;
import main.arda.world.Coordinate;
import org.junit.jupiter.api.Test;
import main.arda.entity.creature.Rabbit;
import main.arda.entity.shrubbery.Stone;
import org.junit.jupiter.api.BeforeEach;

public class RabbitTest extends ResetsWorld {

    Rabbit rabbit;

    @Override
    @BeforeEach
    public void init() {
        super.init();

        rabbit = new Rabbit("Bilbo", new Coordinate(10, 10));

        World.addEntity(rabbit);
    }

    @Test
    void testColor() {
        assertEquals(new Color(154, 106, 51, 255), rabbit.color(), rabbit.toString());

        rabbit.starve(1);

        assertEquals(new Color(154, 106, 51, 188), rabbit.color());
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
        rabbit.tick();

        // Rabbit is surrounded and can't move
        assertEquals(new Coordinate(10, 10), rabbit.getPosition());

        World.getEntities().remove(stone4);

        rabbit.tick();

        // Rabbit has an opening
        assertEquals(new Coordinate(10, 11), rabbit.getPosition());

        rabbit.tick();

        // Rabbit moves somewhere else
        assertNotEquals(new Coordinate(10, 11), rabbit.getPosition());
    }
}
