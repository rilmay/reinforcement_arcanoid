package com.guzov.arkanoid.ml;

import com.guzov.arkanoid.game.Ball;
import com.guzov.arkanoid.game.Brick;
import com.guzov.arkanoid.game.Paddle;

import java.util.List;

public class GameState {
    Ball ball;
    Paddle paddle;
    List<Brick> bricks;
    int screenWidth;
    int screenHeight;
    int paddleHeight;

    public GameState(Ball ball, Paddle paddle, List<Brick> bricks, int screenWidth, int screenHeight, int paddleHeight) {
        this.ball = ball;
        this.paddle = paddle;
        this.bricks = bricks;
        this.screenWidth = screenWidth;
        this.screenHeight = screenHeight;
        this.paddleHeight = paddleHeight;
    }

    public Ball getBall() {
        return ball;
    }

    public void setBall(Ball ball) {
        this.ball = ball;
    }

    public Paddle getPaddle() {
        return paddle;
    }

    public void setPaddle(Paddle paddle) {
        this.paddle = paddle;
    }

    public List<Brick> getBricks() {
        return bricks;
    }

    public void setBricks(List<Brick> bricks) {
        this.bricks = bricks;
    }

    public int getScreenWidth() {
        return screenWidth;
    }

    public void setScreenWidth(int screenWidth) {
        this.screenWidth = screenWidth;
    }

    public int getScreenHeight() {
        return screenHeight;
    }

    public void setScreenHeight(int screenHeight) {
        this.screenHeight = screenHeight;
    }

    public int getPaddleHeight() {
        return paddleHeight;
    }

    public void setPaddleHeight(int paddleHeight) {
        this.paddleHeight = paddleHeight;
    }
}
