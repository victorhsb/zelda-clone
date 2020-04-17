package br.com.voidling.world;

import java.awt.image.BufferedImage;

public class FloorTile extends Tile {
    public FloorTile(int x, int y, BufferedImage sprite) {
        super(x, y, sprite);
    }

    public FloorTile(int x, int y) {
        super(x, y, Tile.TILE_FLOOR);
    }
}
