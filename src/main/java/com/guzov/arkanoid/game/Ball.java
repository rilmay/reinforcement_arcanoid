package com.guzov.arkanoid.game;

import java.awt.*;
import java.util.Objects;

import static com.guzov.arkanoid.game.Сonstants.*;

public class Ball extends GameObject {

    public double radius = BALL_RADIUS;
    public double velocityX = BALL_VELOCITY;
    public double velocityY = BALL_VELOCITY;

    public Ball(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public void draw(Graphics g) {
        g.setColor(Color.RED);
        g.fillOval((int) left(), (int) top(), (int) radius * 2,
                (int) radius * 2);
    }

    public void update(ScoreBoard scoreBoard, Paddle paddle) {
        x += velocityX * FT_STEP;
        y += velocityY * FT_STEP;

        if (left() < 0)
            velocityX = BALL_VELOCITY;
        else if (right() > SCREEN_WIDTH)
            velocityX = -BALL_VELOCITY;
        if (top() < 0) {
            velocityY = BALL_VELOCITY;
        } else if (bottom() > SCREEN_HEIGHT) {
            velocityY = -BALL_VELOCITY;
            velocityX = 0;
            x = paddle.x;
            y = paddle.y - 50;
            scoreBoard.die();
        }

    }

    public double left() {
        return x - radius;
    }

    public double right() {
        return x + radius;
    }

    public double top() {
        return y - radius;
    }

    public double bottom() {
        return y + radius;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Ball)) return false;
        Ball ball = (Ball) o;
        return Double.compare(ball.x, x) == 0 &&
                Double.compare(ball.y, y) == 0 &&
                Double.compare(ball.velocityX, velocityX) == 0 &&
                Double.compare(ball.velocityY, velocityY) == 0;
    }

    @Override
    public int hashCode() {
        return Objects.hash(x, y, velocityX, velocityY);
    }
}
