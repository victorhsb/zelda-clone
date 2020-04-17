package br.com.voidling.entities;

import br.com.voidling.main.Game;

import java.awt.*;
import java.awt.image.BufferedImage;

public class Player extends Entity {
    public boolean up, left, right, down, running;
    public double speed = 1.5f;

    // the execution count and at what execution it will shift sprites.
    private int frames=0, maxFrames = 10, animationState = 0;
    private boolean moved = false;
    private BufferedImage[] playerSprites;

    // default constructor
    public Player(int x, int y) {
        this(x, y, Game.spriteSize, Game.spriteSize, Entity.PLAYER_SPRITE);
    }

    public Player(int x, int y, int width, int height, BufferedImage sprite) {
        super(x, y, width, height, sprite);

        playerSprites = new BufferedImage[4]; // the player movement animation has 4 sprites
        for (int i = 0; i < playerSprites.length; i++) {
            playerSprites[i] = Game.spritesheet.getSpriteByIndex(1 + (i), 0);
        }
    }

    public double getSpeed() {
        if (running)
            return speed * 1.5f;
        return speed;
    }

    public void tick() {
        if (right) {
            x += getSpeed();
        } else if (left) {
            x -= getSpeed();
        }

        if (up) {
            y -= getSpeed();
        } else if (down) {
            y += getSpeed();
        }
        if (right || left || up || down) {
            frames++;
            if (frames == maxFrames) {
                animationState++;
                if (animationState >= playerSprites.length)
                    animationState = 0;
                frames = 0;
            }
        }
    }

    public void render(Graphics g) {
        g.drawImage(playerSprites[animationState], getX(), getY(), getWidth(), getHeight(), null);
    }
}
