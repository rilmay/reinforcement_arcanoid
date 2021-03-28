package com.guzov.arkanoid.game;

public abstract class GameObject {
    public double x;
    public double y;

    public static boolean isIntersecting(GameObject mA, GameObject mB) {
        return mA.right() >= mB.left() && mA.left() <= mB.right()
                && mA.bottom() >= mB.top() && mA.top() <= mB.bottom();
    }

    public abstract double left();

    public abstract double right();

    public abstract double top();

    public abstract double bottom();
}