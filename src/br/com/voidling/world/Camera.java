package br.com.voidling.world;

import br.com.voidling.main.Game;
import br.com.voidling.util.Util;

public class Camera {
    private static int x;
    private static int y;

    public static int getX() {
        return Util.Clamp((World.width << 4) - Game.WIDTH, 0, x);
    }

    public static void setX(int x) {
        Camera.x = x;
    }

    public static int getY() {
        return Util.Clamp((World.height << 4) - Game.HEIGHT, 0, y);
    }

    public static void setY(int y) {
        Camera.y = y;
    }

    public static void moveTo(int x, int y) {
        Camera.x = x;
        Camera.y = y;
    }
}
