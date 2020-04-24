package br.com.voidling.entities;

import java.awt.*;

public class Bullet extends Entity {
    private int damage;
    private Point direction;
    protected Bullet(int x, int y) {
        super(x, y);
    }
    public Bullet(Point pos, Point dir, int damage) {
        this(pos.x, pos.y);
        this.direction = dir;
        this.damage = damage;
    }
}
