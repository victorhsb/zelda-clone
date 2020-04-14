package br.com.voidling.entities;

import java.awt.image.BufferedImage;

public class Player extends Entity {
    public Player(int x, int y, int width, int height, BufferedImage sprite) {
        super(x, y, width, height, sprite);
    }

    public void tick() {
        System.out.println("to be implemented, player method");
    }
}
