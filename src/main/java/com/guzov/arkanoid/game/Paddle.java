package com.guzov.arkanoid.game;

import java.awt.*;

import static com.guzov.arkanoid.Game.*;

public class Paddle extends Rectangle {

    public double velocity = 0.0;

    public Paddle(double x, double y) {
        this.x = x;
        this.y = y;
        this.sizeX = PADDLE_WIDTH;
        this.sizeY = PADDLE_HEIGHT;
    }

    public void update() {
        x += velocity * FT_STEP;
    }

    public void stopMove() {
        velocity = 0.0;
    }

    public void moveLeft() {
        if (left() > 0.0) {
            velocity = -PADDLE_VELOCITY;
        } else {
            velocity = 0.0;
        }
    }

    public void moveRight() {
        if (right() < SCREEN_WIDTH) {
            velocity = PADDLE_VELOCITY;
        } else {
            velocity = 0.0;
        }
    }

    public void draw(Graphics g) {
        g.setColor(Color.RED);
        g.fillRect((int) (left()), (int) (top()), (int) sizeX, (int) sizeY);
    }

}
