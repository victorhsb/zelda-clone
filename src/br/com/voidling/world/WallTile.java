package br.com.voidling.world;

import java.awt.image.BufferedImage;

public class WallTile extends Tile {
    public WallTile(int x, int y, BufferedImage sprite) {
        super(x, y, sprite);
    }
    public WallTile(int x, int y) {
        super(x, y, Tile.TILE_WALL);
    }
}
