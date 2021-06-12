package main.arda.util;

import java.util.Comparator;
import main.arda.entity.Entity;
import main.arda.world.Coordinate;

/**
 * CoordinateComparator class.
 */
public class CoordinateComparator<T> implements Comparator<T> {

    /**
     * The origin Coordinate against which to compare.
     */
    private final Coordinate origin;

    /**
     * CoordinateComparator class constructor.
     *
     * @return self
     */
    public CoordinateComparator(Coordinate origin) {
        this.origin = origin;
    }

    /**
     * Compare two given Objects against the origin Coordinate.
     *
     * @param obj1
     * @param obj2
     * @return int
     */
    @Override
    public int compare(T obj1, T obj2) {
        double distance1 = prepare(obj1).distanceSquared(origin);
        double distance2 = prepare(obj2).distanceSquared(origin);

        return Double.compare(distance1, distance2);
    }

    /**
     * Prepare a given Object by evaluating its Coordinate.
     *
     * @param value
     * @return Coordinate
     */
    private Coordinate prepare(T value) {
        if (value instanceof Coordinate) {
            return (Coordinate) value;
        }

        if (!(value instanceof Entity)) {
            throw new IllegalArgumentException("Compared types must have positional properties");
        }

        return ((Entity) value).getPosition();
    }
}
