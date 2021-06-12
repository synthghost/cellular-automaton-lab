package main.arda.util;

import java.util.List;
import java.util.HashMap;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Properties;
import java.util.Collections;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.nio.charset.StandardCharsets;

/**
 * Config class.
 */
public class Config {

    /**
     * The configuration directory.
     */
    private static final String directory = "config";

    /**
     * The configuration filename.
     */
    private static final String configFile = "silmarillion.cfg";

    /**
     * The name file format string.
     */
    private static final String nameFileFormat = "%s/names.%s.txt";

    /**
     * The name types.
     */
    private static final String[] nameTypes = { "ent", "hobbit", "nazgul", "rabbit" };

    /**
     * The configuration map.
     */
    private static final Properties config = new Properties();

    /**
     * The names map.
     */
    private static final HashMap<String, List<String>> names = new HashMap<>();

    /**
     * Initialize configurations.
     *
     * @return void
     */
    public static void init() {
        loadMap();
        loadNames();
    }

    /**
     * Retrieve a given key from the configuration map.
     *
     * @param key
     * @return String
     */
    public static String get(String key) {
        return config.getProperty(key);
    }

    /**
     * Retrieve a given key from the configuration map or a given fallback if the
     * key did not exist.
     *
     * @param key
     * @param fallback
     * @return String
     */
    public static String get(String key, String fallback) {
        return config.getProperty(key, fallback);
    }

    /**
     * Retrieve a given key from the configuration map.
     *
     * @param key
     * @return int
     */
    public static int getInt(String key) {
        return getInt(key, 0);
    }

    /**
     * Retrieve a given key from the configuration map or a given fallback if the
     * key did not exist.
     *
     * @param key
     * @param fallback
     * @return int
     */
    public static int getInt(String key, int fallback) {
        try {
            return Integer.parseInt(config.getProperty(key), 10);
        } catch (NumberFormatException e) {
            return fallback;
        }
    }

    /**
     * Retrieve a given key from the names map.
     *
     * @param key
     * @return List<String>
     */
    public static List<String> getNames(String key) {
        if (!names.containsKey(key)) {
            return null;
        }

        return names.get(key);
    }

    /**
     * Load the configuration map.
     *
     * @return void
     */
    private static void loadMap() {
        FileInputStream stream = null;

        try {
            stream = new FileInputStream(String.format("%s/%s", directory, configFile));
        } catch (FileNotFoundException e) {
            Console.force(String.format("Config file %s not found!", configFile), Console.RED);
            return;
        }

        try {
            config.load(stream);
            stream.close();
        } catch (IOException e) {
            Console.force(String.format("Could not parse config file %s!", configFile), Console.RED);
            return;
        }
    }

    /**
     * Load the names map.
     *
     * @return void
     */
    private static void loadNames() {
        String nameFile;
        List<String> lines;

        for (String type : nameTypes) {
            nameFile = String.format(nameFileFormat, directory, type);

            try {
                lines = Files.readAllLines(Paths.get(nameFile), StandardCharsets.UTF_8);
                Collections.shuffle(lines);
                names.put(type.toLowerCase(), lines);
            } catch (IOException e) {
                Console.force(String.format("Could not parse name file %s!", nameFile), Console.RED);
            }
        }
    }
}
