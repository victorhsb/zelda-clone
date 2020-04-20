package br.com.voidling.graphics;

import br.com.voidling.main.Game;

import java.awt.*;
import java.awt.image.BufferedImage;

public enum Sprite {
    LIFEPACK_SPRITE(5,0),
    WEAPON_SPRITE(5,1),
    AMMO_SPRITE(6,0),
    COIN_SPRITE(6,1),
    ENEMY_SPRITE(1,1),
    PLAYER_SPRITE(1,0);

    public Point pos;

    Sprite(int idx, int idy) {
        this.pos = new Point(idx, idy);
    }

    public BufferedImage getSprite(){
        return Game.spritesheet.getSpriteByIndex(pos.x, pos.y);
    }

    public BufferedImage[] getSpriteArray(int amount) {
        BufferedImage[] buf = new BufferedImage[amount];
        for (int i = 0; i < amount; i++) {
            buf[i] = Game.spritesheet.getSpriteByIndex(pos.x + i, pos.y);
        }
        return buf;
    }
}
