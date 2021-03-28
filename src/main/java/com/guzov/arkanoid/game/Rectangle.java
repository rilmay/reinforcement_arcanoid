package com.guzov.arkanoid.game;

public class Rectangle extends GameObject {

    public double sizeX;
    public double sizeY;

    public double left() {
        return x - sizeX / 2.0;
    }

    public double right() {
        return x + sizeX / 2.0;
    }

    public double top() {
        return y - sizeY / 2.0;
    }

    public double bottom() {
        return y + sizeY / 2.0;
    }

}
