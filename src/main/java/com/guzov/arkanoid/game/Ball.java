package com.guzov.arkanoid.game;

import java.awt.*;

import static com.guzov.arkanoid.Game.*;

public class Ball extends GameObject {

    public double x, y;
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
}
