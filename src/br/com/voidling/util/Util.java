package br.com.voidling.util;

public class Util {
    public static int Clamp(int max, int min, int tgt) {
        return (max < tgt) ? max : ((min > tgt) ? min : tgt);
    }

    public static double Clamp(double max, double min, double tgt) {
        return (max < tgt) ? max : (min > tgt) ? min : tgt;
    }
}
