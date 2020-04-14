package br.com.voidling.main;

import br.com.voidling.entities.Entity;
import br.com.voidling.entities.Player;
import br.com.voidling.graphics.Spritesheet;

import java.awt.*;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

import javax.swing.JFrame;

public class Game extends Canvas implements Runnable {
    private static final long serialVersionUID = 1L;
    
    private Thread thread;
    private boolean isRunning = true;
    private String gameName = "Zolda";

    // background image
    private BufferedImage image;
    // display
    public static JFrame frame;
    public final int WIDTH = 240;
    public final int HEIGHT = 160;
    public final int SCALE = 3;

    public ArrayList<Entity> entities;
    public Spritesheet spritesheet;

    public Game() {
        this.setPreferredSize(new Dimension(WIDTH*SCALE, HEIGHT*SCALE));
        this.initFrame();
        image = new BufferedImage(WIDTH, HEIGHT, BufferedImage.TYPE_INT_RGB);
        entities = new ArrayList<>();
        spritesheet = new Spritesheet("/spritesheet.png");

        // testing purposes only. player shall be initialized by the map frame.
        Player player = new Player(0,0,16,16, spritesheet.getSprite(32,0,16,16));
        entities.add(player);
    }

    // basic setup of the window to be used on the game;
    public void initFrame() {
        frame = new JFrame(gameName);
        frame.add(this);
        frame.setResizable(false);
        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);
    }

    public static void main(String[] args) {
        Game game = new Game();
        game.run();
    }

    public synchronized void start() {
        thread = new Thread(this);
        thread.start();
    }

    public synchronized void stop() {
        // used to ensure that the threads stop
        isRunning = false;
        try {
            thread.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public void tick() {
        for (Entity e : entities) {
            if (e instanceof Player) {
                System.out.println("player ticking");
            }
            e.tick();
        }
    }

    public void render() {
        BufferStrategy bs = this.getBufferStrategy();
        if (bs == null) {
            this.createBufferStrategy(3);
            return;
        }

        // get the graphics component to be drawn on
        Graphics g = image.getGraphics();
        // clear its background
        g.setColor(new Color(100,100,100));
        g.fillRect(0, 0, WIDTH, HEIGHT);

        for (Entity e : entities) {
            e.render(g);
        }

        g.dispose();
        g = bs.getDrawGraphics();
        g.drawImage(image, 0, 0, WIDTH*SCALE, HEIGHT*SCALE, null);
        bs.show();
    }

    public void run() {
        long lastTime = System.nanoTime();
        double amountOfTicks = 60.0;
        double ns = 10E8 / amountOfTicks; // equals to 10^9
        double delta = 0;

        // frame counter
        int frames = 0;
        double timer = System.currentTimeMillis();

        while(isRunning) {
            long now = System.nanoTime();
            delta += (now - lastTime) / ns; // delta is equal the difference between now and the last time the program ran, in nanoseconds
            lastTime = now;

            if (delta >= 1) {
                this.tick();
                this.render();
                frames++;
                delta--;
            }

            if(System.currentTimeMillis() - timer >= 1000) {
                System.out.println("FPS: " + frames);
                frames = 0;
                timer += 1000;
            }
        }

        this.stop();
    }
}
