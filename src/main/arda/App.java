package main.arda;

import java.awt.EventQueue;
import main.arda.util.Config;
import main.arda.util.Console;
import main.arda.render.Render;

/**
 * App class.
 */
public class App {

    /**
     * Main method.
     *
     * @param args
     * @return void
     * @throws Exception
     */
    public static void main(String[] args) throws Exception {
        Config.init();

        // Check command-line flags for console output option.
        if (Config.get("app.console").equals("true")) {
            Console.enable();
        }

        EventQueue.invokeLater(() -> new Render());
    }
}
