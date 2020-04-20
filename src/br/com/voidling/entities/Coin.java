package br.com.voidling.entities;

import br.com.voidling.graphics.Sprite;
import br.com.voidling.main.Game;

import java.awt.image.BufferedImage;

public class Coin extends Entity  {
    public Coin(int x, int y) {
        super(x, y, Game.spriteSize, Game.spriteSize, Sprite.COIN_SPRITE.getSprite());
    }
}