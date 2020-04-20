package br.com.voidling.entities;

import br.com.voidling.main.Game;
import br.com.voidling.world.Camera;
import org.w3c.dom.css.Rect;

import java.awt.*;
import java.awt.image.BufferedImage;


public class Entity {
    public static BufferedImage LIFEPACK_SPRITE = Game.spritesheet.getSpriteByIndex(5,0);
    public static BufferedImage WEAPON_SPRITE = Game.spritesheet.getSpriteByIndex(5,1);
    public static BufferedImage AMMO_SPRITE = Game.spritesheet.getSpriteByIndex(6,0);
    public static BufferedImage COIN_SPRITE = Game.spritesheet.getSpriteByIndex(6,1);
    public static BufferedImage ENEMY_SPRITE = Game.spritesheet.getSpriteByIndex(1,1);
    public static BufferedImage PLAYER_SPRITE = Game.spritesheet.getSpriteByIndex(1,0);

    protected Point pos;
    protected Dimension size;
    protected BufferedImage sprite;
    protected Rectangle rect;
    public boolean inverted;

    protected Entity(int x, int y) {
        this(x, y, Game.spriteSize, Game.spriteSize, null);
    }

    public Entity(int x, int y, int width, int height, BufferedImage sprite) {
        this.pos = new Point(x, y);
        this.setSize(new Dimension(width, height));
        this.rect = new Rectangle(this.pos, this.size);
        this.sprite = sprite;
    }

    public void moveTo(int x, int y) {
        this.pos.setLocation(x, y);
        this.rect.setLocation(x, y);
    }

    // if inverted, should return the position with the width
    public int getX() {
        return inverted ? pos.x + size.width : pos.x;
    }

    public int getY() {
        return pos.y;
    }

    public int getWidth() {
        return inverted ? size.width * -1 : size.width;
    }

    public int getHeight() {
        return size.height;
    }

    public void setSize(Dimension size) {
        this.size = size;
    }

    public void render(Graphics g) {
        g.drawImage(sprite, getX() - Camera.getX(), getY() - Camera.getY(), null);
    }

    // tick to be implemented
    public void tick() {
//        System.out.println("to be implemented");
    }
}
