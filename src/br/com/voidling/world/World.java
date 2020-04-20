package br.com.voidling.world;

import br.com.voidling.entities.*;
import br.com.voidling.main.Game;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.ArrayList;

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
                            Enemy e = new Enemy(xpos, ypos);
//                            entities.add(e);
                            enemies.add(e);
                            break;
                        }
                        case GREEN: {
                            tileset[idx] = new FloorTile(xpos, ypos);
                            entities.add(new Weapon(xpos, ypos));
                            break;
                        }
                        case PINK: {
                            tileset[idx] = new FloorTile(xpos, ypos);
                            entities.add(new Lifepack(xpos, ypos));
                            break;
                        }
                        case YELLOW: {
                            tileset[idx] = new FloorTile(xpos, ypos);
                            entities.add(new Coin(xpos, ypos));
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

    public static boolean isFree(int xnext, int ynext) {
        int left = xnext / Game.spriteSize;
        int up = ynext / Game.spriteSize;
        int right = (xnext + Game.spriteSize - 1) / Game.spriteSize;
        int down = (ynext + Game.spriteSize - 1) / Game.spriteSize;

        // check if position is inside a wall from the four different perspectives
        // from (0,0), from (0, 1), from (1, 0) and from (1, 1)
        return !(getTile(left ,up) instanceof WallTile ||
                    getTile(left, down) instanceof WallTile ||
                    getTile(right, up) instanceof WallTile ||
                    getTile(right, down) instanceof WallTile);
    }

    public static Tile getTile(int idx, int idy) {
        return tileset[idx + (idy * World.width)];
    }

    public void render(Graphics g) {
        // the first tiles to be rendered, starting from 0
        int xstart = Math.max(Camera.getX() >> 4, 0),
                ystart = Math.max(Camera.getY() >> 4, 0);
        // the last tiles to be rendered, limited to the last tile of the world
        int xend = Math.min(xstart + (Game.WIDTH >> 4) + 1, width),
                yend = Math.min(ystart + (Game.HEIGHT >> 4) + 1, height);

        for (int x = xstart; x < xend; x++) {
            for (int y = ystart; y < yend; y++) {
                Tile t = tileset[x + (y * width)];
                t.render(g);
            }
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
