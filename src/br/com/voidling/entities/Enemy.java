package br.com.voidling.entities;

import br.com.voidling.main.Game;
import br.com.voidling.world.Camera;
import br.com.voidling.world.World;

import java.awt.*;
import java.awt.image.BufferedImage;

public class Enemy extends Entity {
    public int animationSize = 4, animationState = 0;
    public BufferedImage[] animation;

    private double speed = 1;
    // the execution count and at what execution it will shift sprites.
    private int frames=0, maxFrames = 10;

    public Enemy(int x, int y) {
        super(x, y, Game.spriteSize, Game.spriteSize, Entity.ENEMY_SPRITE);
        animation = new BufferedImage[animationSize];
        for (int i = 0; i < animationSize; i++) {
            animation[i] = Game.spritesheet.getSpriteByIndex(1 + i, 1);
        }
    }

    public void tick() {
        boolean moved = false;
        if ((int)x < Game.player.x && World.isFree((int)(x + speed), (int)y)) {
            x += speed;
            inverted = false;
            moved = true;
        } else if ((int)x > Game.player.x && World.isFree((int)(x - speed), (int)y)) {
            x -= speed;
            inverted = true;
            moved = true;
        }

        if ((int)y < Game.player.y && World.isFree((int)(x), (int)(y + speed))) {
            y += speed;
            moved = true;
        } else if ((int)y > Game.player.y && World.isFree((int)(x), (int)(y - speed))) {
            y -= speed;
            moved = true;
        }

        if (moved) {
            frames++;
            if (frames == maxFrames) {
                animationState++;
                if (animationState >= animation.length)
                    animationState = 0;
                frames = 0;
            }
        }
    }

    public void render(Graphics g) {
        g.drawImage(animation[animationState], getX() - Camera.getX(), getY() - Camera.getY(), getWidth(), getHeight(), null);
    }
}
