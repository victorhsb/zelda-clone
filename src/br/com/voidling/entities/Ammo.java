package br.com.voidling.entities;

import br.com.voidling.graphics.Sprite;
import br.com.voidling.main.Game;

import java.awt.image.BufferedImage;

public class Ammo extends Entity  {
    public int amount = 1;
    public Ammo(int x, int y) {
        super(x, y, Game.spriteSize, Game.spriteSize, Sprite.AMMO_SPRITE.getSprite());
    }
    public Ammo(int x, int y, int amount) {
        this(x, y);
        this.amount = amount;
    }

    public int collect() {
        if (!active) return 0;
        setActive(false);
        return amount;
    }
}