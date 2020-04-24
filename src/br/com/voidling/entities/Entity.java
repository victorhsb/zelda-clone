package br.com.voidling.entities;

import br.com.voidling.main.Game;
import br.com.voidling.world.Camera;

import java.awt.*;
import java.awt.image.BufferedImage;


public class Entity {
    public static BufferedImage ENEMY_SPRITE = Game.spritesheet.getSpriteByIndex(1,1);

    protected Point pos;
    protected Dimension size;
    protected BufferedImage sprite;
    protected Rectangle rect;
    public boolean inverted;
    protected boolean active = true;

    protected Entity(int x, int y) {
        this(x, y, Game.spriteSize, Game.spriteSize, null);
    }
    protected Entity(int x, int y, int width, int height) {
        this(x, y, width, height, null);
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

    public void setActive(boolean state) { this.active = state; }
    public boolean isActive() { return this.active; }

    public int collect() { return 0; }

    public void slideTo(int x, int y) {
        moveTo(pos.x + x, pos.y + y);
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
        if (active) g.drawImage(sprite, getX() - Camera.getX(), getY() - Camera.getY(), null);
    }

    // tick to be implemented
    public void tick() {
//        System.out.println("to be implemented");
    }
}
