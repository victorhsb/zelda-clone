package br.com.voidling.entities;

import br.com.voidling.graphics.Sprite;
import br.com.voidling.main.Game;
import br.com.voidling.world.Camera;
import br.com.voidling.world.World;

import java.awt.*;
import java.awt.image.BufferedImage;

public class Enemy extends Entity {
    public int animationSize = 4, animationState = 0;
    public BufferedImage[] animation;

    private int speed = 1;
    // the execution count and at what execution it will shift sprites.
    private int frames=0, maxFrames = 10;
    private boolean moving = false;

    public Enemy(int x, int y) {
        super(x, y, Game.spriteSize, Game.spriteSize, Entity.ENEMY_SPRITE);
        animation = Sprite.ENEMY_SPRITE.getSpriteArray(animationSize);
        rect = new Rectangle(x, y, size.width, size.height);
    }

    public void moveTo(int x, int y) {
        /* check collision with the world and with other entities */
        if (World.isFree(x, y) && !willCollide(x, y)) {
            super.moveTo(x, y);
            moving = true;
        }
    }

    public void tick() {
        moving = false;
        Point player = World.player.pos;
        if (pos.x < player.x) {
            moveTo((pos.x+speed), pos.y);
            inverted = false;
        } else if (pos.x > player.x) {
            moveTo(pos.x-speed, pos.y);
            inverted = true;
        }

        if (pos.y < player.y) {
            moveTo(pos.x, pos.y+speed);
        } else if (pos.y > player.y) {
            moveTo(pos.x, pos.y-speed);
        }

        // update own rectangle

        if (moving) {
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

    // check if the position will be colliding to any other enemy
    // used to block its movement
    public boolean willCollide(double xN, double yN) {
        for (Enemy e : World.enemies) {
            if (e == this)
                continue;
            if (e.rect.intersects(xN, yN, size.width, size.height)) {
                return true;
            }
        }
        return false;
    }
}
