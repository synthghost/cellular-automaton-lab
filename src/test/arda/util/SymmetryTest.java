package test.arda.util;

import static org.junit.jupiter.api.Assertions.*;

import main.arda.util.Symmetry;
import org.junit.jupiter.api.Test;

public class SymmetryTest {

    @Test
    void testReflect() {
        Symmetry symmetrical1 = Symmetry.reflect(5, 5, 0, 0);

        assertEquals(5, symmetrical1.x);
        assertEquals(5, symmetrical1.y);
        assertEquals(-5, symmetrical1.xPrime);
        assertEquals(-5, symmetrical1.yPrime);

        Symmetry symmetrical2 = Symmetry.reflect(5, 5, 1, 1);

        assertEquals(5, symmetrical2.x);
        assertEquals(5, symmetrical2.y);
        assertEquals(-3, symmetrical2.xPrime);
        assertEquals(-3, symmetrical2.yPrime);

        Symmetry symmetrical3 = Symmetry.reflect(-3, -3, -2, -2);

        assertEquals(-3, symmetrical3.x);
        assertEquals(-3, symmetrical3.y);
        assertEquals(-1, symmetrical3.xPrime);
        assertEquals(-1, symmetrical3.yPrime);

        Symmetry symmetrical4 = Symmetry.reflect(-5, -5, 2, 2);

        assertEquals(-5, symmetrical4.x);
        assertEquals(-5, symmetrical4.y);
        assertEquals(9, symmetrical4.xPrime);
        assertEquals(9, symmetrical4.yPrime);
    }
}
