package main.arda.item;

import main.arda.util.Config;

/**
 * Ring class.
 */
public class Ring extends Item {

    /**
     * Ring class constructor.
     *
     * @return self
     */
    public Ring() {
        super(
            Config.getInt("entity.item.ring.visionBonus"),
            Config.getInt("entity.item.ring.healthBonus"),
            Config.getInt("entity.item.ring.defenseBonus"),
            Config.getInt("entity.item.ring.maxHealthBonus")
        );
    }
}
