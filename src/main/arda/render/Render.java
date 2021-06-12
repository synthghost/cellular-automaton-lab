package main.arda.render;

import java.awt.Color;
import java.awt.Graphics;
import javax.swing.Timer;
import javax.swing.JFrame;
import javax.swing.JPanel;
import java.awt.Graphics2D;
import main.arda.util.Config;
import main.arda.world.World;
import main.arda.util.Console;
import main.arda.entity.Entity;
import java.awt.event.ActionEvent;
import java.text.AttributedString;
import java.awt.font.TextAttribute;
import java.awt.event.ActionListener;

/**
 * Render class.
 */
public class Render extends JPanel implements ActionListener {

    /**
     * The serialization version for the class.
     */
    private static final long serialVersionUID = 1L;

    /**
     * The window's width.
     */
    public static final int WIDTH = Math.max(Config.getInt("render.width", 720), 100);

    /**
     * The window's height.
     */
    public static final int HEIGHT = Math.max(Config.getInt("render.height", 480), 100);

    /**
     * The window object.
     */
    public static final JFrame window = new JFrame();

    /**
     * The size of cells drawn to the canvas.
     */
    private final int CELL = Config.getInt("render.cell.size", 8);

    /**
     * The time to wait between ticks and rendered frames.
     */
    private final int TICKRATE = Config.getInt("render.tickrate", 500);

    /**
     * The Timer governing the Render.
     */
    private final Timer timer = new Timer(TICKRATE, this);

    /**
     * The 2D graphics canvas object.
     */
    private Graphics2D canvas;

    /**
     * The internal frame counter.
     */
    private int counter = 0;

    /**
     * Render class constructor.
     *
     * @return self
     */
    public Render() {
        initWindow();

        timer.setInitialDelay(TICKRATE);
        timer.start();

        World.seed();
    }

    /**
     * Paint components to the JPanel.
     *
     * @param graphics
     * @return void
     */
    @Override
    public void paintComponent(Graphics graphics) {
        super.paintComponent(graphics);

        canvas = (Graphics2D) graphics;

        try {
            // Tick every Entity in the World.
            World.tick();

            drawWorld();

            drawStatistics();

            // Remove dead Entities from the World.
            World.removeDeadEntities();
        } catch (Exception e) {
            Console.out(e.getMessage(), Console.RED);
        }

        // Increment the counter.
        counter++;
    }

    /**
     * Listen for JPanel action triggers.
     *
     * @param event
     * @return void
     */
    @Override
    public void actionPerformed(ActionEvent event) {
        if (event.getSource() == timer) {
            repaint();
        }
    }

    /**
     * Initialize the window.
     *
     * @return void
     */
    private void initWindow() {
        // Set the Render's background color.
        this.setBackground(main.arda.util.Graphics.background);

        window.add(this);
        window.setVisible(true);
        window.setSize(WIDTH, HEIGHT);
        window.setTitle(Config.get("app.name"));
        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // Center the window in the system's display.
        window.setLocationRelativeTo(null);

        // Prevent redraws by disabling window resizing.
        window.setResizable(false);
    }

    /**
     * Draw the World to the canvas.
     *
     * @return void
     */
    private void drawWorld() {
        for (Entity entity : World.getEntities()) {
            canvas.setColor(entity.isDead() ? main.arda.util.Graphics.death : entity.color());
            canvas.fillRect(entity.getPosition().getX() * CELL, entity.getPosition().getY() * CELL, CELL, CELL);
        }
    }

    /**
     * Draw statistics to the canvas.
     *
     * @return void
     */
    private void drawStatistics() {
        int statusbar = Config.getInt("render.statusbar.height");

        // Draw a black bar across the bottom of the canvas.
        canvas.setColor(main.arda.util.Graphics.statistics);
        canvas.fillRect(0, HEIGHT - statusbar - window.getInsets().top, WIDTH, statusbar + window.getInsets().top);

        String statistics = String.format("> %d | %d Entities", counter, World.getEntities().size());

        int firstSeparator = statistics.indexOf(">");
        int secondSeparator = statistics.indexOf("|");

        // Write the counter to the canvas.
        AttributedString attributed = new AttributedString(statistics);

        attributed.addAttribute(TextAttribute.FOREGROUND, Color.WHITE, firstSeparator + 1, secondSeparator);
        attributed.addAttribute(TextAttribute.FOREGROUND, Color.GRAY, secondSeparator, secondSeparator + 1);
        attributed.addAttribute(TextAttribute.FOREGROUND, Color.GREEN, secondSeparator + 1, statistics.length());

        canvas.setColor(Color.GRAY);
        canvas.drawString(attributed.getIterator(), 10, HEIGHT - window.getInsets().top - (statusbar / 2) + 4);
    }
}
