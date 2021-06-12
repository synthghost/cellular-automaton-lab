package test.arda;

import main.arda.util.Config;
import org.junit.jupiter.api.BeforeEach;

public class LoadsConfig {
    @BeforeEach
    public void init() {
        Config.init();
    }
}
