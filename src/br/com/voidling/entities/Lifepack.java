package br.com.voidling.entities;

import br.com.voidling.graphics.Sprite;
import br.com.voidling.main.Game;

public class Lifepack extends Entity {
    public Lifepack(int x, int y) {
        super(x, y, Game.spriteSize, Game.spriteSize, Sprite.LIFEPACK_SPRITE.getSprite());
    }
}
