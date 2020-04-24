package br.com.voidling.entities;

import br.com.voidling.util.Util;
import br.com.voidling.world.Camera;

import java.awt.*;

/*
* The health bar will be 16 pixels wide, and 5 pixels tall, with an offset of 1 pixel.
* the usable health bar will be 14 pixels wide and 3 pixels tall (after removing the offset)
* */
public class HealthBar extends Entity {
    public int maxHealth, health;
    private int offset = 1;
    private Dimension usableBar;
    public int alpha = 255;
    protected HealthBar(int x, int y, int width, int height) {
        super(x, y, width, height);
    }

    public HealthBar(int x, int y, int width, int height, int maxHealth) {
        this(x, y, width, height);

        this.usableBar = new Dimension(width - (offset * 2), height - (offset * 2));
        this.maxHealth = maxHealth; this.health = maxHealth;
        System.out.printf("new health bar with %s width, %s height, %s offset, %s pos x %s pos y", width, height, offset, x, y);
    }

    /* to calculate the internal bar width we'll divide the current health by the max health and multiply it by the
    * usable bar dimension */
    private int getBarWidth() {
        double healthPercentage = (double)health / (double)maxHealth;
        return Util.Clamp(usableBar.width, 0, (int)(usableBar.width * healthPercentage));
    }

    public void render(Graphics g) {
        if (alpha <= 0) return;

        g.setColor(new Color(0, 0, 0, alpha));
        g.fillRect(pos.x - Camera.getX(), pos.y - Camera.getY(), size.width, size.height);
        g.setColor(new Color(255, 0, 0, alpha));
        g.fillRect((pos.x + offset) - Camera.getX(), (pos.y + offset) - Camera.getY(), getBarWidth(), usableBar.height);
    }
}
