package main.arda.world;

import java.util.List;
import java.util.Arrays;
import java.util.ArrayList;
import main.arda.util.Maths;
import main.arda.util.Symmetry;

/**
 * Coordinate class.
 */
public class Coordinate implements Comparable<Coordinate> {

    /**
     * The Coordinate's X-value.
     */
    private int xPos;

    /**
     * The Coordinate's Y-value.
     */
    private int yPos;

    /**
     * Coordinate class constructor.
     *
     * @param xPos
     * @param yPos
     * @return self
     */
    public Coordinate(int xPos, int yPos) {
        setX(xPos);
        setY(yPos);
    }

    /**
     * Coordinate copy constructor.
     *
     * @param original
     * @return self
     */
    public Coordinate(Coordinate original) {
        if (original == null) {
            return;
        }

        setFrom(original);
    }

    /**
     * Get the Coordinate's X-value.
     *
     * @return int
     */
    public int getX() {
        return xPos;
    }

    /**
     * Get the Coordinate's Y-value.
     *
     * @return int
     */
    public int getY() {
        return yPos;
    }

    /**
     * Set the X-value for the Coordinate.
     *
     * @param xPos
     * @return void
     */
    public void setX(int xPos) {
        this.xPos = xPos;
    }

    /**
     * Set the Y-value for the Coordinate.
     *
     * @param yPos
     * @return void
     */
    public void setY(int yPos) {
        this.yPos = yPos;
    }

    /**
     * Set the X- and Y-values for the Coordinate based on another Coordinate.
     *
     * @param other
     * @return void
     */
    public void setFrom(Coordinate other) {
        setX(other.getX());
        setY(other.getY());
    }

    /**
     * Wraps the Coordinate's position around a given boundary.
     *
     * @param width
     * @param height
     * @return void
     */
    public void wrapBoundary(int width, int height) {
        setX((xPos + width) % width);
        setY((yPos + height) % height);
    }

    /**
     * Calculate the distance between a given Coordinate and the current instance.
     *
     * @param other
     * @return double
     */
    public double distanceSquared(Coordinate other) {
        if (other == null) {
            return -1;
        }

        return distanceSquared(other.xPos, other.yPos);
    }

    /**
     * Calculate the distance between a given set of X- and Y-values and the current
     * instance.
     *
     * @param xPos
     * @param yPos
     * @return double
     */
    public double distanceSquared(int xPos, int yPos) {
        return Maths.distanceSquared(xPos, yPos, this.xPos, this.yPos);
    }

    /**
     * Find Coordinates within a given range of the given center.
     *
     * @param range
     * @return List<Coordinate>
     */
    public List<Coordinate> findCoordinatesInRange(int range) {
        List<Coordinate> coordinates = new ArrayList<>();

        Maths.findPointsInRange(xPos, yPos, range, (Symmetry points) -> {
            coordinates.addAll(
                Arrays.asList(new Coordinate(points.x, points.y), new Coordinate(points.xPrime, points.yPrime))
            );

            if (points.x == xPos || points.y == yPos) {
                return;
            }

            coordinates.addAll(
                Arrays.asList(new Coordinate(points.x, points.yPrime), new Coordinate(points.xPrime, points.y))
            );
        });

        return coordinates;
    }

    /**
     * Check if the Coordinate is in given bounds.
     *
     * @param xMin
     * @param yMin
     * @param xMax
     * @param yMax
     * @return boolean
     */
    public boolean isInBounds(int xMin, int yMin, int xMax, int yMax) {
        return xPos >= xMin && xPos <= xMax && yPos >= yMin && yPos <= yMax;
    }

    /**
     * Check if the Coordinate is in range of another Coordinate.
     *
     * @param other
     * @param range
     * @return boolean
     */
    public boolean isInRange(Coordinate other, double range) {
        return distanceSquared(other) <= range;
    }

    /**
     * Compare a given object for equality.
     *
     * @param obj
     * @return boolean
     */
    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }

        if (obj == null || obj.getClass() != getClass()) {
            return false;
        }

        Coordinate that = (Coordinate) obj;

        return that.xPos == xPos && that.yPos == yPos;
    }

    /**
     * Compare a given Coordinate to the current instance.
     *
     * @param other
     * @return int
     */
    @Override
    public int compareTo(Coordinate other) {
        if (this.yPos == other.yPos) {
            return this.xPos - other.xPos;
        }

        return this.yPos - other.yPos;
    }

    /**
     * Formulate a string representation of the Coordinate.
     *
     * @return String
     */
    @Override
    public String toString() {
        return String.format("(%d,%d)", xPos, yPos);
    }
}
