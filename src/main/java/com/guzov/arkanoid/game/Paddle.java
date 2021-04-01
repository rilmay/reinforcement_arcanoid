package com.guzov.arkanoid.game;

import java.awt.*;
import java.util.Objects;

import static com.guzov.arkanoid.game.Сonstants.*;

public class Paddle extends Rectangle implements Cloneable{

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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Paddle)) return false;
        Paddle paddle = (Paddle) o;
        return this.x == paddle.x && this.y == paddle.y && Double.compare(paddle.velocity, velocity) == 0;
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.x, this.y, velocity);
    }

    @Override
    public Object clone() throws CloneNotSupportedException {
        Paddle paddle = new Paddle(this.x, this.y);
        paddle.velocity = this.velocity;
        return paddle;
    }
}
