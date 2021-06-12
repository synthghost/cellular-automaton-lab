package test.arda.entity.creature;

import static org.junit.jupiter.api.Assertions.*;

import main.arda.item.Ring;
import main.arda.world.World;
import test.arda.LoadsConfig;
import main.arda.world.Coordinate;
import org.junit.jupiter.api.Test;
import main.arda.entity.creature.Ent;
import main.arda.entity.creature.Hobbit;
import main.arda.entity.creature.Nazgul;
import main.arda.entity.creature.Rabbit;
import main.arda.entity.shrubbery.Stone;
import org.junit.jupiter.api.BeforeEach;

public class CreatureTest extends LoadsConfig {

    Ent ent;
    Hobbit hobbit;
    Nazgul nazgul;
    Rabbit rabbit;

    @BeforeEach
    public void init() {
        super.init();

        ent = new Ent("Treebeard", new Coordinate(0, 0));
        rabbit = new Rabbit("Hop", new Coordinate(10, 10));
        hobbit = new Hobbit("Bilbo", new Coordinate(10, 10));
        nazgul = new Nazgul("Witch King", new Coordinate(20, 20));
    }

    @Test
    void testName() {
        assertEquals("Hop (R)", rabbit.name());
        assertEquals("Bilbo (H)", hobbit.name());
        assertEquals("Treebeard (E)", ent.name());
        assertEquals("Witch King (N)", nazgul.name());
    }

    @Test
    void testVision() {
        hobbit.getInventory().addItem(new Ring());

        assertEquals(10, hobbit.vision());
    }

    @Test
    void testMaxHealth() {
        hobbit.getInventory().addItem(new Ring());

        assertEquals(5, hobbit.maxHealth());
    }

    @Test
    void testMove() {
        hobbit.move(new Coordinate(11, 11));

        assertEquals(new Coordinate(11, 11), hobbit.getPosition());

        hobbit.move(null);

        assertEquals(new Coordinate(11, 11), hobbit.getPosition());
    }

    @Test
    void testStarveAndHeal() {
        assertEquals(10, hobbit.getHealth());

        hobbit.starve(5);

        assertEquals(5, hobbit.getHealth());

        hobbit.heal(1);

        assertEquals(6, hobbit.getHealth());
    }

    @Test
    void testAttack() {
        assertEquals(3, rabbit.getHealth());

        hobbit.attack(rabbit);

        assertEquals(2, rabbit.getHealth());
    }

    @Test
    void testLoot() {
        assertFalse(rabbit.getInventory().isEmpty());
        // All Hobbits have an Apple
        assertEquals(1, hobbit.getInventory().getSize());

        hobbit.loot(rabbit);

        assertTrue(rabbit.getInventory().isEmpty());
        assertEquals(2, hobbit.getInventory().getSize());

        Stone stone = new Stone(new Coordinate(30, 30));

        stone.getInventory().addItem(new Ring());

        hobbit.loot(stone);
        // Loot again in case Stone spawned with a random item
        hobbit.loot(stone);

        assertTrue(stone.getInventory().isEmpty());
        assertTrue(hobbit.getInventory().getSize() > 2);

        // Rings reduce health
        assertTrue(hobbit.getHealth() < 10);
    }

    @Test
    void testReplicate() {
        assertEquals(0, World.getEntities().size());

        hobbit.tick();
        hobbit.replicate();

        assertEquals(1, World.getEntities().size());
        assertEquals("Bilbo:2 (H)", World.getEntities().get(0).name());
    }
}
