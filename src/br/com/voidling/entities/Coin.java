package br.com.voidling.entities;

import br.com.voidling.graphics.Sprite;
import br.com.voidling.main.Game;

import java.awt.image.BufferedImage;

public class Coin extends Entity  {
    private int value = 150;

    public Coin(int x, int y) {
        super(x, y, Game.spriteSize, Game.spriteSize, Sprite.COIN_SPRITE.getSprite());
    }
    public Coin(int x, int y, int value) {
        this(x, y);
        this.value = value;
    }

    /* this sets deactivates the coin and returns what was its value */
    public int collect() {
        if (!active) return 0;
        setActive(false);
        return value;
    }
}