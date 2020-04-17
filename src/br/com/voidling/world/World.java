package br.com.voidling.world;

import br.com.voidling.entities.Buff;
import br.com.voidling.entities.Enemy;
import br.com.voidling.entities.Entity;
import br.com.voidling.main.Game;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;

public class World {
    private Tile[] tileset;
    public static int width, height, size;

    public World(String path) {
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
                            Game.player.setX(xpos);
                            Game.player.setY(ypos);
                            break;
                        }
                        case CYAN: {
                            tileset[idx] = new FloorTile(xpos, ypos);
                            Game.entities.add(new Buff(xpos, ypos, Entity.TYPE.AMMO));
                            break;
                        }
                        case RED: {
                            tileset[idx] = new FloorTile(xpos, ypos);
                            Game.entities.add(new Enemy(xpos, ypos));
                            break;
                        }
                        case GREEN: {
                            tileset[idx] = new FloorTile(xpos, ypos);
                            Game.entities.add(new Buff(xpos, ypos, Entity.TYPE.WEAPON));
                            break;
                        }
                        case PINK: {
                            tileset[idx] = new FloorTile(xpos, ypos);
                            Game.entities.add(new Buff(xpos, ypos, Entity.TYPE.LIFE));
                            break;
                        }
                        case YELLOW: {
                            tileset[idx] = new FloorTile(xpos, ypos);
                            Game.entities.add(new Buff(xpos, ypos, Entity.TYPE.COIN));
                            break;
                        }
                        default: {
                            tileset[idx] = new FloorTile(xpos, ypos);
                        }
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void render(Graphics g) {
        for (Tile t : tileset) {
            t.render(g);
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
