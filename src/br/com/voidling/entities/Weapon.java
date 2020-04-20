package br.com.voidling.entities;

import br.com.voidling.graphics.Sprite;
import br.com.voidling.main.Game;

import java.awt.image.BufferedImage;

public class Weapon extends Entity {
    public Weapon(int x, int y) {
        super(x, y, Game.spriteSize, Game.spriteSize, Sprite.WEAPON_SPRITE.getSprite());
    }
}
