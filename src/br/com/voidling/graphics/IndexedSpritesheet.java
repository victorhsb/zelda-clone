package br.com.voidling.graphics;

import java.awt.*;
import java.awt.image.BufferedImage;

public class IndexedSpritesheet extends Spritesheet {
    private Dimension sptSize;

    public IndexedSpritesheet(String path) {
        super(path);
        sptSize = new Dimension(16, 16);
    }
    public IndexedSpritesheet(String path, int width, int height) {
        super(path);
        this.sptSize = new Dimension(width, height);
    }

    public BufferedImage getSpriteByIndex(int idx, int idy) {
        return this.getSprite(idx*sptSize.width, idy*sptSize.height, sptSize.width, sptSize.height);
    }
}
