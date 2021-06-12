package main.arda.util;

/**
 * Console class.
 */
public class Console {

    /**
     * ANSI escape codes for Console output formatting.
     */
    public static final String RESET = "\u001B[0m";
    public static final String BLACK = "\u001B[30m";
    public static final String RED = "\u001B[31m";
    public static final String GREEN = "\u001B[32m";
    public static final String YELLOW = "\u001B[33m";
    public static final String BLUE = "\u001B[34m";
    public static final String MAGENTA = "\u001B[35m";
    public static final String CYAN = "\u001B[36m";
    public static final String WHITE = "\u001B[37m";
    public static final String GRAY = "\u001B[90m";

    /**
     * Whether to output messages to the system stream.
     */
    private static boolean output = false;

    /**
     * Enable the Console's output.
     *
     * @return void
     */
    public static void enable() {
        output = true;
    }

    /**
     * Disable the Console's output.
     *
     * @return void
     */
    public static void disable() {
        output = false;
    }

    /**
     * Output a message.
     *
     * @param message
     * @param color
     * @return void
     */
    public static void out(String message, String color) {
        if (!output) {
            return;
        }

        force(message, color);
    }

    /**
     * Forcefully output a message.
     *
     * @param message
     * @param color
     * @return void
     */
    public static void force(String message, String color) {
        System.out.println(color + message + RESET);
    }
}
