package main.arda.util;

import java.util.Random;
import java.util.function.Consumer;

/**
 * Maths class.
 */
public class Maths {

    /**
     * Clamp a given number into a given range.
     *
     * @param value
     * @param min
     * @param max
     * @return int
     */
    public static int clamp(int value, int min, int max) {
        return Math.min(Math.max(value, min), max);
    }

    /**
     * Calculate the distance between two points.
     *
     * @param x1
     * @param y1
     * @param x2
     * @param y2
     * @return double
     */
    public static int distanceSquared(int x1, int y1, int x2, int y2) {
        return (int) Math.floor(Math.pow(x2 - x1, 2) + Math.pow(y2 - y1, 2));
    }

    /**
     * Find points in a circle of a given radius.
     *
     * @param xCenter
     * @param yCenter
     * @param radius
     * @param callback
     * @return void
     */
    public static void findPointsInRange(int xCenter, int yCenter, int radius, Consumer<Symmetry> callback) {
        for (int x = xCenter - radius; x <= xCenter; x++) {
            for (int y = yCenter - radius; y <= yCenter; y++) {
                int a = Maths.distanceSquared(x, y, xCenter, yCenter);
                double b = Math.pow(radius, 2);
                if (a <= b && a != 0) {
                    callback.accept(Symmetry.reflect(x, y, xCenter, yCenter));
                }
            }
        }
    }

    /**
     * Check if a given number is in a given range.
     *
     * @param value
     * @param min
     * @param max
     * @return boolean
     */
    public static boolean isInRange(int value, int min, int max) {
        return value >= min && value <= max;
    }

    /**
     * Determine a random chance.
     *
     * @param percentage
     * @return boolean
     */
    public static boolean randomChance(int percentage) {
        return randomInt(100) < percentage;
    }

    /**
     * Generate a random integer in a given range.
     *
     * @param range
     * @return int
     */
    public static int randomInt(int range) {
        return new Random().nextInt(range);
    }
}
