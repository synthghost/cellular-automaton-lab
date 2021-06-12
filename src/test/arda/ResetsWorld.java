package test.arda;

import main.arda.world.World;
import org.junit.jupiter.api.BeforeEach;

public class ResetsWorld extends LoadsConfig {
    @BeforeEach
    public void init() {
        super.init();
        World.getEntities().clear();
    }
}
