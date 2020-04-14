package br.com.voidling.entities;

import java.awt.*;
import java.awt.image.BufferedImage;

public class Entity {
    private int x;
    private int y;
    private Dimension size;
    private BufferedImage sprite;

    public Entity(int x, int y, int width, int height, BufferedImage sprite) {
        this.setX(x);
        this.setY(y);
        this.setSize(new Dimension(width, height));
        this.sprite = sprite;
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
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
        System.out.println("to be implemented");
    }
}
