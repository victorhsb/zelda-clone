package br.com.voidling.entities;

import br.com.voidling.main.Game;

import java.awt.*;
import java.awt.image.BufferedImage;


public class Entity {
    public enum TYPE {
        WEAPON,
        AMMO,
        LIFE,
        COIN,
        ENEMY,
        PLAYER
    }
    public static BufferedImage LIFEPACK_SPRITE = Game.spritesheet.getSpriteByIndex(5,0);
    public static BufferedImage WEAPON_SPRITE = Game.spritesheet.getSpriteByIndex(5,1);
    public static BufferedImage AMMO_SPRITE = Game.spritesheet.getSpriteByIndex(6,0);
    public static BufferedImage COIN_SPRITE = Game.spritesheet.getSpriteByIndex(6,1);
    public static BufferedImage ENEMY_SPRITE = Game.spritesheet.getSpriteByIndex(1,1);
    public static BufferedImage PLAYER_SPRITE = Game.spritesheet.getSpriteByIndex(1,0);

    protected double x;
    protected double y;
    protected Dimension size;
    protected BufferedImage sprite;
    public boolean inverted;

    public Entity(int x, int y, int width, int height, BufferedImage sprite) {
        this.setX(x);
        this.setY(y);
        this.setSize(new Dimension(width, height));
        this.sprite = sprite;
    }

    public int getX() {
        if (inverted)
            return (int)x + size.width; // to compensate the image being rendered the other way
        return (int)(x);
    }

    public void setX(double x) {
        this.x = x;
    }

    public int getY() {
        return (int)(y);
    }

    public void setY(double y) {
        this.y = y;
    }

    public int getWidth() {
        if (inverted)
            return size.width * -1;
        return size.width;
    }
    public int getHeight() {
        return size.height;
    }

    public Dimension getSize() {
        return size;
    }

    public void setSize(Dimension size) {
        this.size = size;
    }

    public void render(Graphics g) {
        g.drawImage(sprite, getX(), getY(), null);
    }

    // tick to be implemented
    public void tick() {
//        System.out.println("to be implemented");
    }
}
