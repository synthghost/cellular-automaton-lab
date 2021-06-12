package test.arda.entity;

import static org.junit.jupiter.api.Assertions.*;

import main.arda.item.Meat;
import main.arda.item.Ring;
import main.arda.item.Apple;
import test.arda.LoadsConfig;
import main.arda.item.Mithril;
import main.arda.entity.Inventory;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;

public class InventoryTest extends LoadsConfig {

    Inventory inventory;

    Meat meat;
    Ring ring;
    Apple apple;
    Mithril mithril;

    @BeforeEach
    public void init() {
        super.init();

        inventory = new Inventory();

        meat = new Meat();
        ring = new Ring();
        apple = new Apple();
        mithril = new Mithril();

        inventory.addItem(meat);
        inventory.addItem(ring);
        inventory.addItem(apple);
        inventory.addItem(mithril);
    }

    @Test
    void testGetSizeAndIsEmpty() {
        assertEquals(4, inventory.getSize());
        assertFalse(inventory.isEmpty());
    }

    @Test
    void testScanGetFoodAndHasFood() {
        inventory.scan();

        assertEquals(2, inventory.getFood().size());
        assertTrue(inventory.hasFood());
    }

    @Test
    void testGetItem() {
        assertEquals(meat, inventory.getItem(0));
        assertEquals(apple, inventory.getItem(2));
        assertEquals(mithril, inventory.getItem(3));
    }

    @Test
    void testRemoveItem() {
        inventory.removeItem(meat);

        assertEquals(3, inventory.getSize());

        // Item 1 is now Apple
        inventory.removeItem(1);

        assertEquals(2, inventory.getSize());

        // Deleted all the food
        assertFalse(inventory.hasFood());
    }

    @Test
    void testSumVisionItems() {
        assertEquals(5, inventory.sumVisionItems());
    }

    @Test
    void testSumArmorItems() {
        assertEquals(1, inventory.sumArmorItems());
    }

    @Test
    void testSumMaxHealthItems() {
        assertEquals(-5, inventory.sumMaxHealthItems());
    }
}
