package test.arda.world;

import static org.junit.jupiter.api.Assertions.*;

import java.util.List;
import java.util.Arrays;
import java.util.ArrayList;
import java.util.Collections;
import main.arda.world.Coordinate;
import org.junit.jupiter.api.Test;

public class CoordinateTest {

    @Test
    void testWrapBoundary() {
        Coordinate coordinate = new Coordinate(12, 12);

        coordinate.wrapBoundary(10, 10);

        assertEquals(2, coordinate.getX());
        assertEquals(2, coordinate.getY());
    }

    @Test
    void testDistancedSquared() {
        Coordinate coordinate1 = new Coordinate(10, 10);
        Coordinate coordinate2 = new Coordinate(20, 20);

        assertEquals(2, coordinate1.distanceSquared(11, 11));
        assertEquals(200, coordinate1.distanceSquared(coordinate2));
    }

    @Test
    void testFindCoordinatesInRange() {
        Coordinate coordinate = new Coordinate(10, 10);

        List<Coordinate> actual = coordinate.findCoordinatesInRange(2);
        List<Coordinate> expected = new ArrayList<>(Arrays.asList(
            new Coordinate(8, 10),
            new Coordinate(9, 9),
            new Coordinate(9, 10),
            new Coordinate(9, 11),
            new Coordinate(10, 8),
            new Coordinate(10, 9),
            new Coordinate(10, 11),
            new Coordinate(10, 12),
            new Coordinate(11, 9),
            new Coordinate(11, 10),
            new Coordinate(11, 11),
            new Coordinate(12, 10)
        ));

        Collections.sort(expected);
        Collections.sort(actual);

        assertEquals(12, actual.size());
        assertEquals(expected, actual);
    }

    @Test
    void testIsInBounds() {
        Coordinate position1 = new Coordinate(10, 10);

        assertTrue(position1.isInBounds(0, 0, 20, 20));
        assertFalse(position1.isInBounds(0, 0, 5, 5));
    }

    @Test
    void testIsInRange() {
        Coordinate position1 = new Coordinate(10, 10);
        Coordinate position2 = new Coordinate(15, 15);

        assertTrue(position1.isInRange(position2, 100));
        assertFalse(position1.isInRange(position2, 10));
    }

    @Test
    void testEquals() {
        Coordinate position1 = new Coordinate(10, 10);
        Coordinate position2 = new Coordinate(15, 15);
        Coordinate position3 = new Coordinate(15, 15);

        assertTrue(position2.equals(position3));
        assertFalse(position1.equals(position2));
        assertFalse(position1.equals(position3));
    }

    @Test
    void testCompareTo() {
        Coordinate position1 = new Coordinate(10, 10);
        Coordinate position2 = new Coordinate(15, 15);
        Coordinate position3 = new Coordinate(15, 15);

        assertTrue(position1.compareTo(position2) < 0);
        assertTrue(position2.compareTo(position3) == 0);
        assertTrue(position3.compareTo(position1) > 0);
    }
}
