package test.arda.util;

import static org.junit.jupiter.api.Assertions.*;

import java.util.List;
import java.util.Arrays;
import java.util.ArrayList;
import java.util.Collections;
import main.arda.entity.Entity;
import main.arda.world.Coordinate;
import org.junit.jupiter.api.Test;
import main.arda.entity.creature.Hobbit;
import main.arda.util.CoordinateComparator;

public class CoordinateComparatorTest {

    @Test
    void testEntityComparison() {
        Hobbit hobbit1 = new Hobbit("A", new Coordinate(10, 10));
        Hobbit hobbit2 = new Hobbit("B", new Coordinate(11, 11));
        Hobbit hobbit3 = new Hobbit("C", new Coordinate(12, 12));

        List<Entity> entities = new ArrayList<>(Arrays.asList(hobbit3, hobbit1, hobbit2));

        List<Entity> expected = new ArrayList<>(Arrays.asList(hobbit1, hobbit2, hobbit3));

        Collections.sort(entities, new CoordinateComparator<Entity>(new Coordinate(1, 1)));

        assertEquals(expected, entities);
    }

    @Test
    void testCoordinateComparison() {
        List<Coordinate> coordinates = new ArrayList<>(
            Arrays.asList(new Coordinate(11, 11), new Coordinate(12, 12), new Coordinate(10, 10))
        );

        List<Coordinate> expected = new ArrayList<>(
            Arrays.asList(new Coordinate(10, 10), new Coordinate(11, 11), new Coordinate(12, 12))
        );

        Collections.sort(coordinates, new CoordinateComparator<Coordinate>(new Coordinate(1, 1)));

        assertEquals(expected, coordinates);
    }
}
