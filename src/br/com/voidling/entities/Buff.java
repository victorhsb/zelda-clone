package br.com.voidling.entities;

import br.com.voidling.main.Game;

import java.awt.image.BufferedImage;

public class Buff extends Entity {
    public Buff(int x, int y, TYPE t) {
        super(x, y, Game.spriteSize, Game.spriteSize, null);

        switch (t) {
            case WEAPON:{
                this.sprite = Entity.WEAPON_SPRITE;
                break;
            }
            case AMMO:{
                this.sprite = Entity.AMMO_SPRITE;
                break;
            }
            case LIFE:{
                this.sprite = Entity.LIFEPACK_SPRITE;
                break;
            }
            case COIN:{
                this.sprite = Entity.COIN_SPRITE;
                break;
            }
        }
    }

    public Buff(int x, int y, int width, int height, BufferedImage sprite) {
        super(x, y, width, height, sprite);
    }
}
