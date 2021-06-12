package main.arda.util;

/**
 * Symmetry class.
 */
public class Symmetry {

    /**
     * The original X-value.
     */
    public final int x;

    /**
     * The original Y-value.
     */
    public final int y;

    /**
     * The reflected X-value.
     */
    public final int xPrime;

    /**
     * The reflected Y-value.
     */
    public final int yPrime;

    /**
     * Symmetry class constructor.
     *
     * @param x
     * @param y
     * @param xPrime
     * @param yPrime
     * @return self
     */
    public Symmetry(int x, int y, int xPrime, int yPrime) {
        this.x = x;
        this.y = y;
        this.xPrime = xPrime;
        this.yPrime = yPrime;
    }

    /**
     * Reflect a given set of points about an origin.
     *
     * @param x
     * @param y
     * @param xOrigin
     * @param yOrigin
     */
    public static Symmetry reflect(int x, int y, int xOrigin, int yOrigin) {
        return new Symmetry(x, y, prime(x, xOrigin), prime(y, yOrigin));
    }

    /**
     * Find the prime of a value about an origin.
     *
     * @param value
     * @param origin
     */
    private static int prime(int value, int origin) {
        return origin - (value - origin);
    }
}
