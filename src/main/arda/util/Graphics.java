package main.arda.util;

import java.awt.Color;

/**
 * Graphics class.
 */
public class Graphics {

    /**
     * Define helper colors.
     */
    public static final Color background = new Color(38, 50, 56, 255);
    public static final Color statistics = new Color(0, 0, 0, 128);
    public static final Color death = new Color(0, 0, 0, 80);

    /**
     * Define Entity colors.
     */
    public static final Color bushes = new Color(0, 97, 15, 255);
    public static final Color ents = new Color(50, 182, 48, 255);
    public static final Color hobbits = new Color(0, 200, 255, 255);
    public static final Color nazgul = new Color(182, 5, 26, 255);
    public static final Color rabbits = new Color(154, 106, 51, 255);
    public static final Color stones = new Color(100, 100, 100, 255);

    /**
     * Set a new opacity on an existing Color based on a given percentage.
     *
     * @param base
     * @param percentage
     * @return
     */
    public static Color fade(Color base, double percentage) {
        return new Color(base.getRed(), base.getGreen(), base.getBlue(), (int) (percentage * 200) + 55);
    }
}
