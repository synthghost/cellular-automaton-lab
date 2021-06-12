package main.arda.item;

import main.arda.util.Config;

/**
 * Apple class.
 */
public class Apple extends Item {

    /**
     * Apple class constructor.
     *
     * @return self
     */
    public Apple() {
        super(
            Config.getInt("entity.item.apple.visionBonus"),
            Config.getInt("entity.item.apple.healthBonus"),
            Config.getInt("entity.item.apple.defenseBonus"),
            Config.getInt("entity.item.apple.maxHealthBonus")
        );
    }
}
