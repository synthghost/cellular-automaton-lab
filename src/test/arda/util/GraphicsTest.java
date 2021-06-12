package test.arda.util;

import static org.junit.jupiter.api.Assertions.*;

import java.awt.Color;
import main.arda.util.Graphics;
import org.junit.jupiter.api.Test;

public class GraphicsTest {

    @Test
    void testFade() {
        Color color1 = new Color(200, 200, 200);
        Color color2 = new Color(200, 200, 200, 155);
        Color color3 = new Color(200, 200, 200, 205);

        assertEquals(color1, Graphics.fade(color1, 1));
        assertEquals(color2, Graphics.fade(color1, 0.5));
        assertEquals(color3, Graphics.fade(color1, 0.75));
    }
}
