package test.arda.item;

import static org.junit.jupiter.api.Assertions.*;

import main.arda.item.Meat;
import main.arda.item.Apple;
import main.arda.item.Mithril;
import org.junit.jupiter.api.Test;

public class ItemTest {

    @Test
    void testName() {
        Meat meat = new Meat();
        Apple apple = new Apple();
        Mithril mithril = new Mithril();

        assertEquals("Meat", meat.name());
        assertEquals("Apple", apple.name());
        assertEquals("Mithril", mithril.name());
    }
}
