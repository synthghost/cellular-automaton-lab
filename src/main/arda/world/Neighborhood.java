package main.arda.world;

import java.util.List;
import java.util.ArrayList;
import main.arda.util.Maths;
import java.util.Collections;
import main.arda.util.Console;
import main.arda.entity.Entity;
import java.util.stream.Collectors;
import main.arda.entity.creature.Creature;
import main.arda.util.CoordinateComparator;

/**
 * Neighborhood class.
 */
public class Neighborhood {

    /**
     * The Neighborhood's owning Creature.
     */
    private final Creature owner;

    /**
     * The Coordinates in visual range.
     */
    private List<Coordinate> vision = new ArrayList<>();

    /**
     * The Entities in visual range.
     */
    private List<Entity> occupants = new ArrayList<>();

    /**
     * The Coordinates in movement range.
     */
    private List<Coordinate> movements = new ArrayList<>();

    /**
     * The open Coordinates in movement range.
     */
    private List<Coordinate> choices = new ArrayList<>();

    /**
     * Neighborhood class constructor.
     *
     * @param owner
     * @return self
     */
    public Neighborhood(Creature owner) {
        this.owner = owner;
    }

    /**
     * Find all Coordinates within visual range of the Creature's position.
     *
     * @return void
     */
    public void setVision() {
        vision = findNeighborhood(owner.vision());
    }

    /**
     * Find open Coordinates within movement range of the Creature's position.
     *
     * @return void
     */
    public void setChoices() {
        setEntities();
        movements = (owner.speed() != owner.vision()) ? findNeighborhood(owner.speed()) : vision;
        choices = findOpenSpaces();
    }

    /**
     * Check whether the Neighborhood has available movement choices.
     *
     * @return boolean
     */
    public boolean canMove() {
        return !choices.isEmpty();
    }

    /**
     * Check whether the Neighborhood does not have available movement choices.
     *
     * @return boolean
     */
    public boolean canNotMove() {
        return !canMove();
    }

    /**
     * Find the nearest Entity of a given type.
     *
     * @param type
     * @return Entity
     */
    public Entity findNearestEntity(Class<? extends Entity> type) {
        List<Entity> entities = findEntitiesOfType(type);

        if (entities.isEmpty()) {
            return null;
        }

        Collections.shuffle(entities);
        Collections.sort(entities, new CoordinateComparator<Entity>(owner.getPosition()));

        return entities.get(0);
    }

    /**
     * Find the position of a given target Entity based on a given TargetPriority.
     *
     * @param target
     * @param priority
     * @return Coordinate
     */
    public Coordinate findEntityPosition(Entity target, TargetPriority priority) {
        if (choices.isEmpty()) {
            return null;
        }

        Collections.shuffle(choices);
        Collections.sort(choices, new CoordinateComparator<Coordinate>(target.getPosition()));

        int choice = (priority == TargetPriority.FURTHEST) ? choices.size() - 1 : 0;

        return choices.get(choice);
    }

    /**
     * Pick a random destination from the list of movement choices.
     *
     * @return Coordinate
     */
    public Coordinate pickDestination() {
        if (canNotMove()) {
            Console.out(String.format("%s is surrounded!", owner.name()), Console.BLACK);
            return null;
        }

        int choice = Maths.randomInt(choices.size());

        return choices.get(choice);
    }

    /**
     * Find Entities in visual range of the Creature's position.
     *
     * @return void
     */
    private void setEntities() {
        occupants = World.findEntitiesInNeighborhood(vision);
    }

    /**
     * Find the Creature's neighborhood within a given range.
     *
     * @param range
     * @return List<Coordinate>
     */
    private List<Coordinate> findNeighborhood(int range) {
        List<Coordinate> vision = owner.getPosition().findCoordinatesInRange(range);

        if (owner.boundaryCondition() == BoundaryCondition.BLOCK) {
            vision.removeIf(coordinate -> !coordinate.isInBounds(0, 0, World.WIDTH - 1, World.HEIGHT - 1));
            return vision;
        }

        return vision.stream().map(coordinate -> {
            coordinate.wrapBoundary(World.WIDTH, World.HEIGHT);
            return coordinate;
        }).collect(Collectors.toList());
    }

    /**
     * Find Coordinates not occupied by Entities within movement range of the
     * Creature's position.
     *
     * @return List<Coordinate>
     */
    private List<Coordinate> findOpenSpaces() {
        List<Coordinate> occupied = occupants.stream()
            .map(Entity::getPosition)
            .collect(Collectors.toList());

        return movements.stream()
            .filter(position -> !occupied.contains(position))
            .collect(Collectors.toList());
    }

    /**
     * Find Entities of a given type.
     *
     * @param type
     * @return List<Entity>
     */
    private List<Entity> findEntitiesOfType(Class<? extends Entity> type) {
        return occupants.stream()
            .filter(entity -> type.isInstance(entity) && entity.isAlive())
            .collect(Collectors.toList());
    }
}
