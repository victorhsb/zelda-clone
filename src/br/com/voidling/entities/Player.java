package br.com.voidling.entities;

import br.com.voidling.graphics.Sprite;
import br.com.voidling.main.Game;
import br.com.voidling.util.Util;
import br.com.voidling.world.Camera;
import br.com.voidling.world.World;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

public class Player extends Entity {
    public boolean up, left, right, down, running;
    public int speed = 2;

    /* the execution count and at what execution it will shift sprites. */
    private int frames=0, maxFrames = 10, animationState = 0;
    private boolean moved = false;

    private BufferedImage[] playerSprites;

    public int health = 100, maxHealth;
    private int healthBarYOffset = -5;

    private Entity[] colliding;
    public HealthBar healthBar;
    public int ammo = 0;
    public Weapon.Gun gun;

    public Player(int x, int y) {
        super(x, y);
        playerSprites = Sprite.PLAYER_SPRITE.getSpriteArray(4);
        healthBar = new HealthBar(x, y, size.width, 3, health);
        maxHealth = health;
    }

    public int getAmmo() {
        return this.ammo;
    }

    public void addHealth(int health) {
        this.health += health;
        this.health = Util.Clamp(maxHealth, 0, this.health);
        healthBar.health = this.health; // update health at health bar asset
    }

    public void moveTo(int x, int y) {
        if (World.isFree(x, y)) {
            super.moveTo(x, y);
            moved = true;
        }
    }

    public Entity[] isColliding(){
        ArrayList<Entity> entities = new ArrayList<>();
        for (Entity e : World.entities) {
            if (e == this || e == null)
                continue;

            if (e.rect.intersects(this.rect)) {
                entities.add(e);
            }
        }
        return entities.toArray(new Entity[0]);
    }

    public void processCollision() {
        colliding = isColliding();
        for (Entity e: colliding) {
            if (e != null) {
                if (e instanceof Lifepack) {
                    this.addHealth(e.collect());
                }
                if (e instanceof Enemy) {
                    this.addHealth(-((Enemy) e).damage);
                }
                if (e instanceof Coin) {
                    Game.score += e.collect();
                }
                if (e instanceof Ammo) {
                    ammo += e.collect();
                }
                if (e instanceof Weapon) {
                    gun = ((Weapon) e).getGun();
                }
            }
        }
    }

    public void tick() {
        moved = false;
        processCollision();
        if (right) {
            moveTo(pos.x + speed, pos.y);
        } else if (left) {
            moveTo(pos.x - speed, pos.y);
        }

        if (up ) {
            moveTo(pos.x, pos.y - speed);
        } else if (down) {
            moveTo(pos.x, pos.y + speed);
        }

        if (moved) {
            frames++;
            if (frames == maxFrames) {
                animationState++;
                if (animationState >= playerSprites.length)
                    animationState = 0;
                frames = 0;
            }
        }

        Camera.moveTo(this.pos.x - (Game.WIDTH / 2), this.pos.y - (Game.HEIGHT / 2));
        healthBar.moveTo(this.pos.x, this.pos.y + healthBarYOffset); // the difference between oldx and x, and oldy and y
    }

    public void render(Graphics g) {
        if (healthBar != null) {
            healthBar.render(g);
        }
        g.drawImage(playerSprites[animationState], getX() - Camera.getX(), getY() - Camera.getY(), getWidth(), getHeight(), null);
    }
}
