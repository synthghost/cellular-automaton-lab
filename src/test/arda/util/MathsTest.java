package test.arda.util;

import static org.junit.jupiter.api.Assertions.*;

import java.util.List;
import java.util.Arrays;
import java.util.ArrayList;
import main.arda.util.Maths;
import java.util.Collections;
import main.arda.util.Symmetry;
import main.arda.world.Coordinate;
import org.junit.jupiter.api.Test;

public class MathsTest {

    @Test
    void testClamp() {
        assertEquals(50, Maths.clamp(50, 0, 100));
        assertEquals(0, Maths.clamp(-10, 0, 100));
        assertEquals(100, Maths.clamp(110, 0, 100));
        assertEquals(1, Maths.clamp(-10, 1, 99));
        assertEquals(99, Maths.clamp(110, 1, 99));
    }

    @Test
    void testDistanceSquared() {
        assertEquals(2, Maths.distanceSquared(1, 1, 2, 2));
        assertEquals(8, Maths.distanceSquared(1, 1, 3, 3));
        assertEquals(50, Maths.distanceSquared(1, 1, 6, 6));
        assertEquals(128, Maths.distanceSquared(1, 1, 9, 9));
    }

    @Test
    void testFindPointsInRange() {
        List<Coordinate> actual = new ArrayList<>();

        Maths.findPointsInRange(0, 0, 2, (Symmetry points) -> {
            actual.addAll(
                Arrays.asList(new Coordinate(points.x, points.y), new Coordinate(points.xPrime, points.yPrime))
            );

            if (points.x == 0 || points.y == 0) {
                return;
            }

            actual.addAll(
                Arrays.asList(new Coordinate(points.x, points.yPrime), new Coordinate(points.xPrime, points.y))
            );
        });

        List<Coordinate> expected = new ArrayList<>(Arrays.asList(
            new Coordinate(-1, -1),
            new Coordinate(-1, 0),
            new Coordinate(-1, 1),
            new Coordinate(-2, 0),
            new Coordinate(0, -1),
            new Coordinate(0, -2),
            new Coordinate(0, 1),
            new Coordinate(0, 2),
            new Coordinate(1, -1),
            new Coordinate(1, 0),
            new Coordinate(1, 1),
            new Coordinate(2, 0)
        ));

        Collections.sort(actual);
        Collections.sort(expected);

        assertEquals(expected, actual);
    }

    @Test
    void testIsInRange() {
        assertTrue(Maths.isInRange(50, 0, 100));
        assertFalse(Maths.isInRange(-10, 0, 100));
        assertFalse(Maths.isInRange(110, 0, 100));
    }
}
