package test.arda.entity;

import static org.junit.jupiter.api.Assertions.*;

import test.arda.LoadsConfig;
import main.arda.world.Coordinate;
import org.junit.jupiter.api.Test;
import main.arda.entity.creature.Ent;
import main.arda.entity.shrubbery.Bush;
import main.arda.entity.creature.Hobbit;
import main.arda.entity.creature.Nazgul;
import main.arda.entity.creature.Rabbit;
import main.arda.entity.shrubbery.Stone;
import org.junit.jupiter.api.BeforeEach;

public class EntityTest extends LoadsConfig {

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
    void testGetPosition() {
        assertEquals(new Coordinate(0, 0), ent.getPosition());
        assertEquals(new Coordinate(10, 10), hobbit.getPosition());
        assertEquals(new Coordinate(20, 20), nazgul.getPosition());
        assertEquals(new Coordinate(10, 10), rabbit.getPosition());
    }

    @Test
    void testDieAndIsAliveAndIsDead() {
        assertTrue(ent.isAlive());
        assertTrue(hobbit.isAlive());
        assertTrue(nazgul.isAlive());
        assertTrue(rabbit.isAlive());

        assertFalse(ent.isDead());
        assertFalse(hobbit.isDead());
        assertFalse(nazgul.isDead());
        assertFalse(rabbit.isDead());

        hobbit.die();
        nazgul.die();

        assertTrue(ent.isAlive());
        assertFalse(hobbit.isAlive());
        assertFalse(nazgul.isAlive());
        assertTrue(rabbit.isAlive());

        assertFalse(ent.isDead());
        assertTrue(hobbit.isDead());
        assertTrue(nazgul.isDead());
        assertFalse(rabbit.isDead());
    }

    @Test
    void testDieAndShouldRemove() {
        assertFalse(ent.shouldRemove());
        assertFalse(hobbit.shouldRemove());
        assertFalse(nazgul.shouldRemove());
        assertFalse(rabbit.shouldRemove());

        hobbit.die();
        nazgul.die();
        rabbit.die();

        for (int i = 0; i < 10; i++) {
            hobbit.tick();
            nazgul.tick();
            rabbit.tick();
        }

        assertFalse(ent.shouldRemove());
        assertTrue(hobbit.shouldRemove());
        assertTrue(nazgul.shouldRemove());
        assertTrue(rabbit.shouldRemove());
    }

    @Test
    void testName() {
        Bush bush = new Bush(new Coordinate(40, 40));
        Stone stone = new Stone(new Coordinate(50, 50));

        assertEquals("Bush", bush.name());
        assertEquals("Stone", stone.name());
        assertEquals("Bilbo (H)", hobbit.name());
        assertEquals("Treebeard (E)", ent.name());
    }

    @Test
    void testCompareTo() {
        assertTrue(ent.compareTo(hobbit) < 0);
        assertTrue(hobbit.compareTo(rabbit) == 0);
        assertTrue(nazgul.compareTo(hobbit) > 0);
    }
}
