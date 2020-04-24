package br.com.voidling.world;

import br.com.voidling.entities.*;
import br.com.voidling.main.Game;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.desktop.QuitEvent;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;

public class World {
    public static Tile[] tileset;
    public static int width, height, size;

    public static Player player;
    public static ArrayList<Enemy> enemies;
    public static ArrayList<Entity> entities;

    public World(String path) {
        entities = new ArrayList<>();
        enemies = new ArrayList<>();
        try {
            BufferedImage map = ImageIO.read(getClass().getResource(path));
            width = map.getWidth();
            height = map.getHeight();
            size = width * height;
            int[] pixels = new int[size];
            tileset = new Tile[size];
            map.getRGB(0, 0, map.getWidth(), map.getHeight(), pixels, 0, map.getWidth());
            for (int xAxis = 0; xAxis < width; xAxis++) {
                for (int yAxis = 0; yAxis < height; yAxis++) {
                    int idx = xAxis + (yAxis * width),
                            actualPixel = pixels[idx],
                            xpos = xAxis * Game.spriteSize, ypos = yAxis * Game.spriteSize;
                    switch (actualPixel) {
                        case BLACK: {
                            tileset[idx] = new FloorTile(xpos, ypos);
                            break;
                        }
                        case WHITE: {
                            tileset[idx] = new WallTile(xpos, ypos);
                            break;
                        }
                        case BLUE: {
                            tileset[idx] = new FloorTile(xpos, ypos);
                            player = new Player(xpos, ypos);
                            entities.add(player);
                            break;
                        }
                        case CYAN: {
                            tileset[idx] = new FloorTile(xpos, ypos);
                            entities.add(new Ammo(xpos, ypos));
                            break;
                        }
                        case RED: {
                            tileset[idx] = new FloorTile(xpos, ypos);
                            enemies.add(new Enemy(xpos, ypos, 1));
                            break;
                        }
                        case GREEN: {
                            tileset[idx] = new FloorTile(xpos, ypos);
                            // TODO: randomize what gun is deployed or differ it by distance of the player
                            entities.add(new Weapon(xpos, ypos, Weapon.Gun.REVOLVER));
                            break;
                }
                        case PINK: {
                            tileset[idx] = new FloorTile(xpos, ypos);
                            entities.add(new Lifepack(xpos, ypos));
                            break;
                        }
                        case YELLOW: {
                            tileset[idx] = new FloorTile(xpos, ypos);
                            entities.add(new Coin(xpos, ypos, 150));
                            break;
                        }
                        default: {
                            tileset[idx] = new FloorTile(xpos, ypos);
                        }
                    }
                }
            }
            entities.addAll(enemies);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static boolean isFree(int x, int y) {
        /* dividing the raw position by the sprite size will give us at what tile
         * index we will be looking at at that point.
         * so we get the horizontal positions by dividing both the x and the x +
         * sprite size minus 1 (to get the horizontal from the left and the right side
         * of the sprite as well) and the vertical by doing the same with the given y */
        int left = x / Game.spriteSize,
                up = y / Game.spriteSize,
                right = (x + Game.spriteSize - 1) / Game.spriteSize,
                down = (y + Game.spriteSize - 1) / Game.spriteSize;

        /* check if position is inside a wall from the four different perspectives
         * from (0,0), from (0, 1), from (1, 0) and from (1, 1)
         * just like: 0,0  *   *  1,0
         *             *   *   *   *
         *             *   *   *   *
         *            0,1  *   *  1,1
         * working with static walls don't have any need of complex and complete collision
         * validation as no walls have more than the sprite size of size and can never penetrate
         * the user. */
        return !(getTile(left ,up) instanceof WallTile ||
                    getTile(left, down) instanceof WallTile ||
                    getTile(right, up) instanceof WallTile ||
                    getTile(right, down) instanceof WallTile);
    }

    public static Tile getTile(int idx, int idy) {
        return tileset[idx + (idy * World.width)];
    }

    public void tick() throws Exception {
        boolean refreshArray = false;
        for (Entity e : World.entities) {
            e.tick();
            if (!e.isActive()) refreshArray = true;
        }

        // TODO: any way to optimize this?
        /* this is done here to avoid concurrent modifications of the array.
        * its done when some inactive object is found on the entities array.
        * we remove it and let it for the garbage collector to handle as we won't need
        * this object anymore being rendered and ticked */
        if (refreshArray) {
            ArrayList<Entity> newEntities = new ArrayList<>();
            for (Entity e : World.entities) {
                if (e.isActive()) {
                    newEntities.add(e);
                }
            }
            World.entities = newEntities;
        }

        /* ultra basic endgame check */
        if (player.health <= 0) {
            // TODO: this is temporary, must implement some proper ending
            throw new Exception("quit");
        }
    }

    public void render(Graphics g) {
        /* the first tiles comprehended by the camera's position, starting from 0 */
        int xstart = Math.max(Camera.getX() >> 4, 0),
                ystart = Math.max(Camera.getY() >> 4, 0);
        /* the last tiles comprehended by the camera's position,
         * limited to the last tile of the world.
         * this is the xstart/ystart values plus the game view size divided by 16
         * which is the amount of tiles that actually appear on screen (plus 1, used as buffer)
         * (shifting 4 binary slots divides by 2^4 which is 16) */
        int xend = Math.min(xstart + (Game.WIDTH >> 4) + 1, width),
                yend = Math.min(ystart + (Game.HEIGHT >> 4) + 1, height);

        /* properly render the tiles */
        for (int x = xstart; x < xend; x++) {
            for (int y = ystart; y < yend; y++) {
                Tile t = getTile(x, y);
                t.render(g);
            }
        }

        for (Entity e : World.entities) {
            e.render(g);
        }
    }

    public static final int WHITE = 0xFFFFFFFF; // the walls
    public static final int RED = 0xFFFF0000; // the enemies
    public static final int GREEN = 0xFF00FF00; // the life
    public static final int BLUE = 0xFF0000FF; // the player
    public static final int BLACK = 0xFF000000; // the floor
    public static final int PINK = 0xFFFF00FF;  // the weapon
    public static final int CYAN = 0xFF00FFFF;  // the ammo
    public static final int YELLOW = 0xFFFFFF00;// the coin
}
