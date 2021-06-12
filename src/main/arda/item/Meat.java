package main.arda.item;

import main.arda.util.Config;

/**
 * Meat class.
 */
public class Meat extends Item {

    /**
     * Meat class constructor.
     *
     * @return self
     */
    public Meat() {
        super(
            Config.getInt("entity.item.meat.visionBonus"),
            Config.getInt("entity.item.meat.healthBonus"),
            Config.getInt("entity.item.meat.defenseBonus"),
            Config.getInt("entity.item.meat.maxHealthBonus")
        );
    }
}
