package main.arda.item;

import main.arda.util.Config;

/**
 * Mithril class.
 */
public class Mithril extends Item {

    /**
     * Mithril class constructor.
     *
     * @return self
     */
    public Mithril() {
        super(
            Config.getInt("entity.item.mithril.visionBonus"),
            Config.getInt("entity.item.mithril.healthBonus"),
            Config.getInt("entity.item.mithril.defenseBonus"),
            Config.getInt("entity.item.mithril.maxHealthBonus")
        );
    }
}
