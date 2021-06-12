package main.arda.entity;

import java.util.List;
import java.util.ArrayList;
import main.arda.item.Item;
import java.util.stream.Collectors;

/**
 * Inventory class.
 */
public class Inventory {

    /**
     * The stash of Items in the Inventory.
     */
    private final List<Item> stash = new ArrayList<>();

    /**
     * The Items in the Inventory that are food types.
     */
    private List<Item> food = new ArrayList<>();

    /**
     * Get the food Items from the Inventory.
     *
     * @return List<Item>
     */
    public List<Item> getFood() {
        return food;
    }

    /**
     * Get the Inventory's size.
     *
     * @return int
     */
    public int getSize() {
        return stash.size();
    }

    /**
     * Check whether the Inventory is empty.
     *
     * @return boolean
     */
    public boolean isEmpty() {
        return stash.isEmpty();
    }

    /**
     * Scan the Inventory for different types of Items.
     *
     * @return void
     */
    public void scan() {
        food = findHealthItems();
    }

    /**
     * Check whether there are food Items in the Inventory.
     *
     * @return boolean
     */
    public boolean hasFood() {
        return !food.isEmpty();
    }

    /**
     * Get an Item at the given index from the Inventory.
     *
     * @param index
     * @return Item
     * @throws IndexOutOfBoundsException
     */
    public Item getItem(int index) throws IndexOutOfBoundsException {
        return stash.get(index);
    }

    /**
     * Add the given Item to the Inventory.
     *
     * @param item
     * @return boolean
     */
    public boolean addItem(Item item) {
        return stash.add(item);
    }

    /**
     * Remove the given Item from the Inventory.
     *
     * @param item
     * @return boolean
     */
    public boolean removeItem(Item item) {
        return stash.remove(item);
    }

    /**
     * Remove an Item at the given index from the Inventory.
     *
     * @param index
     * @return Item
     * @throws IndexOutOfBoundsException
     */
    public Item removeItem(int index) throws IndexOutOfBoundsException {
        return stash.remove(index);
    }

    /**
     * Sum the vision bonuses of all Items in the Inventory.
     *
     * @return int
     */
    public int sumVisionItems() {
        return stash.stream().mapToInt(item -> item.VISION_BONUS).sum();
    }

    /**
     * Sum the defense bonuses of all Items in the Inventory.
     *
     * @return int
     */
    public int sumArmorItems() {
        return stash.stream().mapToInt(item -> item.DEFENSE_BONUS).sum();
    }

    /**
     * Sum the max health bonuses of all Items in the Inventory.
     *
     * @return int
     */
    public int sumMaxHealthItems() {
        return stash.stream().mapToInt(item -> item.MAX_HEALTH_BONUS).sum();
    }

    /**
     * Find Items with a health bonus in the Inventory.
     *
     * @return List<Item>
     */
    private List<Item> findHealthItems() {
        return stash.stream().filter(item -> item.HEALTH_BONUS != 0).collect(Collectors.toList());
    }
}
