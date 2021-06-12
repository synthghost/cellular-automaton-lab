package test.arda.world;

import static org.junit.jupiter.api.Assertions.*;

import java.util.List;
import java.util.ArrayList;
import main.arda.world.World;
import test.arda.ResetsWorld;
import main.arda.world.Coordinate;
import org.junit.jupiter.api.Test;
import main.arda.entity.creature.Hobbit;
import main.arda.entity.creature.Rabbit;

public class WorldTest extends ResetsWorld {

    @Test
    void testAddEntity() {
        World.addEntity(new Hobbit("A", new Coordinate(0, 0)));
        assertEquals(1, World.getEntities().size());

        World.addEntity(new Hobbit("B", new Coordinate(0, 0)));
        assertEquals(1, World.getEntities().size());
    }

    @Test
    void testRemoveDeadEntities() {
        Hobbit hobbit = new Hobbit("A", new Coordinate(0, 0));

        World.addEntity(hobbit);
        assertEquals(1, World.getEntities().size());

        hobbit.die();

        World.removeDeadEntities();

        assertEquals(1, World.getEntities().size());

        for (int i = 0; i < 10; i++) {
            hobbit.tick();
        }

        World.removeDeadEntities();

        assertEquals(0, World.getEntities().size());
    }

    @Test
    void testSeed() {
        World.seed();
        assertTrue(World.getEntities().size() > 1);
    }

    @Test
    void testTick() {
        Rabbit rabbit = new Rabbit("A", new Coordinate(0, 0));
        World.addEntity(rabbit);

        assertTrue(rabbit.isAlive());

        for (int i = 0; i < 20; i++) {
            World.tick();
        }

        assertTrue(rabbit.isDead());
    }

    @Test
    void testFindEntitiesInNeighborhood() {
        Coordinate position1 = new Coordinate(10, 5);
        Coordinate position2 = new Coordinate(11, 5);

        World.addEntity(new Rabbit("A", position1));
        World.addEntity(new Rabbit("B", position2));

        List<Coordinate> coordinates = new ArrayList<>();

        coordinates.add(position1);
        coordinates.add(position2);

        assertEquals(2, World.findEntitiesInNeighborhood(coordinates).size());
    }
}
