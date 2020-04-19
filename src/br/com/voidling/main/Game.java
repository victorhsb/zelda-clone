package br.com.voidling.main;

import br.com.voidling.entities.Entity;
import br.com.voidling.entities.Player;
import br.com.voidling.graphics.IndexedSpritesheet;
import br.com.voidling.graphics.Spritesheet;
import br.com.voidling.world.Camera;
import br.com.voidling.world.World;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

import javax.swing.JFrame;

public class Game extends Canvas implements Runnable, KeyListener {
    private static final long serialVersionUID = 1L;
    
    private Thread thread;
    private boolean isRunning = true;
    public static final int spriteSize = 16;

    // background image
    private BufferedImage image;
    // display
    public static JFrame frame;
    public static int WIDTH = 240, HEIGHT = 160, SCALE = 3;

    public static ArrayList<Entity> entities;
    public static IndexedSpritesheet spritesheet;
    public static World world;

    public static Player player;

    public Game() {
        addKeyListener(this);
        image = new BufferedImage(WIDTH, HEIGHT, BufferedImage.TYPE_INT_RGB);
        spritesheet = new IndexedSpritesheet("/spritesheet.png");
        this.setPreferredSize(new Dimension(WIDTH*SCALE, HEIGHT*SCALE));
        this.initFrame();
        entities = new ArrayList<>();

        player = new Player(0,0,16,16, spritesheet.getSprite(32,0,16,16));
        entities.add(player);
        world = new World("/map.png");
    }

    // basic setup of the window to be used on the game;
    public void initFrame() {
        String gameName = "Zolda";
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
        g.setColor(new Color(0,0,0));
        g.fillRect(0, 0, WIDTH, HEIGHT);
        world.render(g);

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

        requestFocus();

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

    @Override
    public void keyPressed(KeyEvent e) {
        switch (e.getKeyCode()) {
            case KeyEvent.VK_RIGHT:
            case KeyEvent.VK_D: {
                player.right = true;
                player.inverted = false;
                break;
            }
            case KeyEvent.VK_LEFT:
            case KeyEvent.VK_A: {
                player.left = true;
                player.inverted = true;
                break;
            }
            case KeyEvent.VK_DOWN:
            case KeyEvent.VK_S: {
                player.down = true;
                break;
            }
            case KeyEvent.VK_UP:
            case KeyEvent.VK_W: {
                player.up = true;
                break;
            }
            case KeyEvent.VK_SHIFT: {
                player.running = true;
                break;
            }
            default:
                System.out.println("some key pressed " + e.getKeyChar());
        }
    }

    @Override
    public void keyTyped(KeyEvent e) {

    }

    @Override
    public void keyReleased(KeyEvent e) {
        switch (e.getKeyCode()) {
            case KeyEvent.VK_RIGHT:
            case KeyEvent.VK_D: {
                player.right = false;
                break;
            }
            case KeyEvent.VK_LEFT:
            case KeyEvent.VK_A: {
                player.left = false;
                break;
            }
            case KeyEvent.VK_DOWN:
            case KeyEvent.VK_S: {
                player.down = false;
                break;
            }
            case KeyEvent.VK_UP:
            case KeyEvent.VK_W: {
                player.up = false;
                break;
            }
            case KeyEvent.VK_SHIFT: {
                player.running = false;
                break;
            }
            default:
                System.out.println("some key pressed " + e.getKeyChar());
        }
    }
}
