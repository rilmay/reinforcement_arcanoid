package com.guzov.arkanoid.game;

import java.awt.*;
import java.util.Objects;

import static com.guzov.arkanoid.game.Сonstants.BLOCK_HEIGHT;
import static com.guzov.arkanoid.game.Сonstants.BLOCK_WIDTH;

public class Brick extends Rectangle implements Cloneable{

    public boolean destroyed = false;

    public boolean isFalling = false;

    public double fallingVelocity = 0.1;




    public Brick(double x, double y) {
        this.x = x;
        this.y = y;
        this.sizeX = BLOCK_WIDTH;
        this.sizeY = BLOCK_HEIGHT;
    }

    public Brick(double x, double y, boolean isMlMode) {
        this.x = x;
        this.y = y;
        this.sizeX = BLOCK_WIDTH;
        this.sizeY = BLOCK_HEIGHT;
        this.fallingVelocity = isMlMode? 0.2: 0.9;
    }

    public void draw(Graphics g) {
        if (isFalling) {
            this.y += this.fallingVelocity;
        }
        g.setColor(Color.YELLOW);
        g.fillRect((int) left(), (int) top(), (int) sizeX, (int) sizeY);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Brick)) return false;
        Brick brick = (Brick) o;
        return this.x == brick.x && this.y == brick.y &&
                destroyed == brick.destroyed && isFalling == brick.isFalling;
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.x, this.y, destroyed, isFalling);
    }

    @Override
    public Object clone() throws CloneNotSupportedException {
        Brick clone = new Brick(this.x, this.y);
        clone.sizeX = this.sizeX;
        clone.sizeY = this.sizeY;
        clone.destroyed = this.destroyed;
        clone.isFalling = this.isFalling;
        return clone;
    }
}
