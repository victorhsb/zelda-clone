package br.com.voidling.entities;

import br.com.voidling.main.Game;

import java.awt.image.BufferedImage;

public class Lifepack extends Entity {
    public Lifepack(int x, int y) {
        this(x, y, Game.spriteSize, Game.spriteSize, Entity.WEAPON_SPRITE);
    }

    public Lifepack(int x, int y, int width, int height, BufferedImage sprite) {
        super(x, y, width, height, sprite);
    }
}
