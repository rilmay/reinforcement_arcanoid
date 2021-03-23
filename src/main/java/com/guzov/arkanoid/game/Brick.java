package com.guzov.arkanoid.game;

import java.awt.*;
import java.util.Objects;

import static com.guzov.arkanoid.Game.BLOCK_HEIGHT;
import static com.guzov.arkanoid.Game.BLOCK_WIDTH;

public class Brick extends Rectangle {

    public boolean destroyed = false;

    public boolean isGolden = false;

    public boolean isFalling = false;

    public int goldenBrickCounter = 3;

    public Brick(double x, double y) {
        this.x = x;
        this.y = y;
        this.sizeX = BLOCK_WIDTH;
        this.sizeY = BLOCK_HEIGHT;
    }

    public Brick(double x, double y, boolean isGolden) {
        this.x = x;
        this.y = y;
        this.isGolden = isGolden;
        this.sizeX = BLOCK_WIDTH;
        this.sizeY = BLOCK_HEIGHT;
    }

    public void draw(Graphics g) {
        if (isFalling) {
            this.y++;
        }
        if (isGolden) {
            g.setColor((this.goldenBrickCounter < 2) ? Color.GREEN : Color.CYAN);
        } else {
            g.setColor(Color.YELLOW);
        }
        g.fillRect((int) left(), (int) top(), (int) sizeX, (int) sizeY);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Brick)) return false;
        Brick brick = (Brick) o;
        return this.x == brick.x && this.y == brick.y &&
                destroyed == brick.destroyed &&
                isGolden == brick.isGolden &&
                isFalling == brick.isFalling &&
                goldenBrickCounter == brick.goldenBrickCounter;
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.x, this.y, destroyed, isGolden, isFalling, goldenBrickCounter);
    }
}
