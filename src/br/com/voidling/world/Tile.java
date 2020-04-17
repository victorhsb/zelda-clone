package br.com.voidling.world;

import br.com.voidling.main.Game;

import java.awt.*;
import java.awt.image.BufferedImage;

public class Tile {
    public static BufferedImage TILE_FLOOR = Game.spritesheet.getSpriteByIndex(0,0);
    public static BufferedImage TILE_WALL = Game.spritesheet.getSpriteByIndex(0,1);

    private BufferedImage sprite;
    private int x, y;

    public Tile(int x, int y, BufferedImage sprite) {
        this.x = x;
        this.y = y;
        this.sprite = sprite;
    }

    public void render(Graphics g) {
        g.drawImage(sprite, x, y, null);
    }
}
