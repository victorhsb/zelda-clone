package br.com.voidling.entities;

import br.com.voidling.graphics.Sprite;
import br.com.voidling.main.Game;
import br.com.voidling.util.Util;
import br.com.voidling.world.Camera;
import br.com.voidling.world.World;

import java.awt.*;
import java.awt.image.BufferedImage;

public class Player extends Entity {
    public boolean up, left, right, down, running;
    public int speed = 2;

    /* the execution count and at what execution it will shift sprites. */
    private int frames=0, maxFrames = 10, animationState = 0;
    private boolean moved = false;
    private BufferedImage[] playerSprites;

    public Player(int x, int y) {
        super(x, y);

        playerSprites = Sprite.PLAYER_SPRITE.getSpriteArray(4);
    }

    public boolean willCollide(int x, int y) {
        return false;
    }

    public void moveTo(int x, int y) {
        if (World.isFree(x, y) && !willCollide(x, y)) {
            super.moveTo(x, y);
            moved = true;
        }
    }

    public void tick() {
        if (right && World.isFree(pos.x+speed, pos.y)) {
            pos.x += speed;
        } else if (left && World.isFree(pos.x-speed, pos.y)) {
            pos.x -= speed;
        }

        if (up && World.isFree(pos.x, pos.y-speed)) {
            pos.y -= speed;
        } else if (down && World.isFree(pos.x, pos.y+speed)) {
            pos.y += speed;
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

        Camera.moveTo(this.pos.x - (Game.WIDTH / 2), this.pos.y - (Game.HEIGHT / 2));
    }

    public void render(Graphics g) {
        g.drawImage(playerSprites[animationState], getX() - Camera.getX(), getY() - Camera.getY(), getWidth(), getHeight(), null);
    }
}
