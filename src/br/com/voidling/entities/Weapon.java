package br.com.voidling.entities;

import br.com.voidling.graphics.Sprite;
import br.com.voidling.main.Game;

import java.awt.*;

public class Weapon extends Entity {
    public enum Gun {
        REVOLVER(25),
        DEAGLE(50);

        private int damage;

        Gun(int damage) {
            this.damage = damage;
        }

        public Bullet Shoot(Point position, Point direction) {
            return new Bullet(position, direction, this.damage);
        }
    }

    private Gun type;

    public Weapon(int x, int y) {
        super(x, y, Game.spriteSize, Game.spriteSize, Sprite.WEAPON_SPRITE.getSprite());
    }
    public Weapon(int x, int y, Gun type) {
        this(x, y);
        this.type = type;
    }

    public Gun getGun() {
        this.setActive(false);
        return this.type;
    }
}
