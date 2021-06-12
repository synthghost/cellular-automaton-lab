package main.arda.world;

import java.util.List;
import java.util.ArrayList;
import main.arda.util.Maths;
import main.arda.util.Config;
import main.arda.entity.Entity;
import main.arda.render.Render;
import java.util.stream.Collectors;
import main.arda.entity.creature.Ent;
import main.arda.entity.shrubbery.Bush;
import main.arda.entity.creature.Hobbit;
import main.arda.entity.creature.Nazgul;
import main.arda.entity.creature.Rabbit;
import main.arda.entity.shrubbery.Stone;

/**
 * World class.
 */
public abstract class World {

    /**
     * The World's width.
     */
    public static final int WIDTH = width();

    /**
     * The World's height.
     */
    public static final int HEIGHT = height();

    /**
     * All Entities in the World.
     */
    private static final List<Entity> entities = new ArrayList<>();

    /**
     * The seeded Coordinates in the World.
     */
    private static final List<Coordinate> seeded = new ArrayList<>();

    /**
     * Get the list of Entities in the World.
     *
     * @return List<Entity>
     */
    public static List<Entity> getEntities() {
        return entities;
    }

    /**
     * Add the given Entity to the World.
     *
     * @param entity
     * @return boolean
     */
    public static boolean addEntity(Entity addition) {
        if (entities.stream().anyMatch(entity -> entity.getPosition().equals(addition.getPosition()))) {
            return false;
        }

        return entities.add(addition);
    }

    /**
     * Remove dead Entities from the World.
     *
     * @return boolean
     */
    public static boolean removeDeadEntities() {
        return entities.removeAll(entities.stream()
            .filter(Entity::shouldRemove)
            .collect(Collectors.toList()));
    }

    /**
     * Seed the World with Entities.
     *
     * @return void
     */
    public static void seed() {
        for (int i = 0; i < Config.getInt("world.seed.entity.creature.ent.count"); i++) {
            addEntity(new Ent(randomName("ent", i), randomCoordinate()));
        }

        for (int i = 0; i < Config.getInt("world.seed.entity.creature.hobbit.count"); i++) {
            addEntity(new Hobbit(randomName("hobbit", i), randomCoordinate()));
        }

        for (int i = 0; i < Config.getInt("world.seed.entity.creature.nazgul.count"); i++) {
            addEntity(new Nazgul(randomName("nazgul", i), randomCoordinate()));
        }

        for (int i = 0; i < Config.getInt("world.seed.entity.creature.rabbit.count"); i++) {
            addEntity(new Rabbit(randomName("rabbit", i), randomCoordinate()));
        }

        for (int i = 0; i < Config.getInt("world.seed.entity.shrubbery.bush.count"); i++) {
            addEntity(new Bush(randomCoordinate()));
        }

        for (int i = 0; i < Config.getInt("world.seed.entity.shrubbery.stone.count"); i++) {
            addEntity(new Stone(randomCoordinate()));
        }
    }

    /**
     * Tick every Entity in the World.
     *
     * @return void
     */
    public static void tick() {
        int i = 0;
        while (i < entities.size()) {
            Entity entity = entities.get(i);
            entity.tick();
            i++;
        }
    }

    /**
     * Find Entities in a given neighborhood.
     *
     * @param neighborhood
     * @return List<Entity>
     */
    public static List<Entity> findEntitiesInNeighborhood(List<Coordinate> neighborhood) {
        return entities.stream()
            .filter(entity -> neighborhood.contains(entity.getPosition()))
            .collect(Collectors.toList());
    }

    /**
     * Calculate the World's width.
     *
     * @return int
     */
    private static int width() {
        // Find the width by dividing the desired resolution by the size of each entity.
        return Math.max(Render.WIDTH / Config.getInt("render.cell.size", 1), 1);
    }

    /**
     * Calculate the World's height.
     *
     * @return int
     */
    private static int height() {
        // Find the height by subtracting the heights of the statusbar and window title
        // bar from the desired resolution, and dividing that number by the size of each
        // entity.
        return Math.max((
            Render.HEIGHT - Config.getInt("render.statusbar.height") - Render.window.getInsets().top
        ) / Config.getInt("render.cell.size", 1), 1);
    }

    /**
     * Generate a random name for a given type of Entity.
     *
     * @param type
     * @param index
     * @return String
     */
    private static String randomName(String type, int index) {
        List<String> names = Config.getNames(type.toLowerCase());

        if (names == null) {
            return type.substring(0, 1).toUpperCase() + type.substring(1).toLowerCase();
        }

        return names.get(index % names.size());
    }

    /**
     * Generate a random Coordinate.
     *
     * @return Coordinate
     */
    private static Coordinate randomCoordinate() {
        Coordinate coordinate;
        int count = 0;

        do {
            coordinate = new Coordinate(Maths.randomInt(WIDTH), Maths.randomInt(HEIGHT));
            count++;
        } while (count < 3 && seeded.contains(coordinate));

        seeded.add(coordinate);

        return coordinate;
    }
}
