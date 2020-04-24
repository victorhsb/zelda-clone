package br.com.voidling.graphics;

import br.com.voidling.main.Game;
import br.com.voidling.world.World;

import java.awt.*;

public class HUD {
    private Font font = new Font("arial", Font.BOLD, 10);

    public void renderScore(Graphics g) {
        g.setColor(new Color(0,0,0,55));
        g.fillRect(0,0,100,10);
        g.setColor(Color.white);
        g.setFont(font);
        g.drawString(String.format("Score: %s", Game.score),0,8);
    }

    public void renderAmmo(Graphics g) {
        g.setColor(new Color(0,0,0,45));
        g.fillRect(Game.WIDTH - 50, Game.HEIGHT - 10, 50, 10);
        g.setFont(font);
        g.setColor(Color.white);
        g.drawString(String.format("Ammo: %s", World.player.getAmmo()), Game.WIDTH - 47, Game.HEIGHT - 1);
    }

    public void renderGun(Graphics g) {
        g.setColor(new Color(0,0,0,45));
        g.fillRect(0, Game.HEIGHT - 10, 100, 10);
        g.setFont(font);
        g.setColor(Color.white);
        g.drawString(String.format("Gun: %s", World.player.gun), 10, Game.HEIGHT - 1);
    }

    public void render(Graphics g) {
        renderScore(g);
        renderAmmo(g);
        if (World.player.gun != null) renderGun(g);
    }
}
