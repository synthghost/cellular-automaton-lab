package test.arda.world;

import static org.junit.jupiter.api.Assertions.*;

import main.arda.world.World;
import test.arda.ResetsWorld;
import main.arda.world.Coordinate;
import org.junit.jupiter.api.Test;
import main.arda.world.Neighborhood;
import main.arda.world.TargetPriority;
import main.arda.entity.creature.Hobbit;
import main.arda.entity.creature.Rabbit;
import main.arda.entity.shrubbery.Stone;

public class NeighborhoodTest extends ResetsWorld {

    @Test
    void testCanMove() {
        Rabbit rabbit = new Rabbit("A", new Coordinate(0, 0));
        World.addEntity(rabbit);

        Neighborhood hood = new Neighborhood(rabbit);

        hood.setVision();
        hood.setChoices();

        assertTrue(hood.canMove());
        assertFalse(hood.canNotMove());

        Stone stone1 = new Stone(new Coordinate(1, 0));
        Stone stone2 = new Stone(new Coordinate(0, 1));

        World.addEntity(stone1);
        World.addEntity(stone2);

        hood.setVision();
        hood.setChoices();

        assertFalse(hood.canMove());
        assertTrue(hood.canNotMove());
    }

    @Test
    void testFindNearestEntity() {
        Rabbit rabbit1 = new Rabbit("A", new Coordinate(0, 0));
        Rabbit rabbit2 = new Rabbit("A", new Coordinate(1, 0));
        World.addEntity(rabbit1);
        World.addEntity(rabbit2);

        Neighborhood hood = new Neighborhood(rabbit1);

        hood.setVision();
        hood.setChoices();

        assertEquals(null, hood.findNearestEntity(Hobbit.class));
        assertEquals(rabbit2, hood.findNearestEntity(Rabbit.class));
    }

    @Test
    void testFindEntityPosition() {
        Hobbit hobbit1 = new Hobbit("A", new Coordinate(10, 10));
        Hobbit hobbit2 = new Hobbit("B", new Coordinate(11, 11));
        Stone stone1 = new Stone(new Coordinate(9, 10));
        Stone stone2 = new Stone(new Coordinate(10, 11));

        World.addEntity(hobbit1);
        World.addEntity(hobbit2);
        World.addEntity(stone1);
        World.addEntity(stone2);

        Neighborhood hood = new Neighborhood(hobbit1);

        hood.setVision();
        hood.setChoices();

        assertEquals(new Coordinate(11, 10), hood.findEntityPosition(hobbit2, TargetPriority.CLOSEST));
        assertEquals(new Coordinate(10, 9), hood.findEntityPosition(hobbit2, TargetPriority.FURTHEST));
    }

    @Test
    void testPickDestination() {
        Hobbit hobbit1 = new Hobbit("A", new Coordinate(10, 10));
        Stone stone1 = new Stone(new Coordinate(9, 10));
        Stone stone2 = new Stone(new Coordinate(10, 9));
        Stone stone3 = new Stone(new Coordinate(11, 10));

        World.addEntity(hobbit1);
        World.addEntity(stone1);
        World.addEntity(stone2);
        World.addEntity(stone3);

        Neighborhood hood = new Neighborhood(hobbit1);

        hood.setVision();
        hood.setChoices();

        assertEquals(new Coordinate(10, 11), hood.pickDestination());
    }
}
